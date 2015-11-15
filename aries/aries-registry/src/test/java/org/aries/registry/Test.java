package org.aries.registry;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.CountDownLatch;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 */
public class Test {
	private final static Operation 	DEFAULT_OPERATION 		= Operation.PUT;
	private final static int 		DEFAULT_ELEMENTS_NUM 	= 10000;
	private final static int 		DEFAULT_SIZE_BYTES 		= 100;
	private final static int 		DEFAULT_REPEAT_TIMES		= 1;

	private static final Log log = LogFactory.getLog(Test.class);

	public static enum Operation {
		GET,
		PUT,
		DELETE
	}

	private static String 		configFile;
	private static Operation 	operation;
	private static int			numOfElements;
	private static int			dataSizeBytes;
	private static int 			repeatTimes;

	public static void main(String[] args) throws Exception {
		if ( !readCommandLine(args) ) {
			printUsage();
			System.exit(0);
		}


		CacheManager manager = new CacheManager(configFile);
		Cache cache = manager.getCache("sampleCache1");

		long sum = 0;
		for(int i = 0; i != repeatTimes; ++i) {
			log.info("Attempt num [" + (i+1) + "]");

			cache.removeAll();

			CountDownLatch startLatch = new CountDownLatch(1);
			CountDownLatch threadInitLatch = new CountDownLatch(1);
			CountDownLatch threadDoneLatch = new CountDownLatch(1);

			BaseCacheOperation cacheOperation = null;
			if ( operation == Test.Operation.PUT ) {
				cacheOperation = new CachePut(cache, startLatch, threadInitLatch, threadDoneLatch);
			} else if ( operation == Test.Operation.GET ) {
				cacheOperation = new CacheGet(cache, startLatch, threadInitLatch, threadDoneLatch);
			} else {
				printUsage();
				System.exit(0);
			}

			new Thread(cacheOperation).start();

			startLatch.countDown();

			try {
				threadDoneLatch.await();
			} catch (InterruptedException e) {
				// ignore
			}

			sum += cacheOperation.getOperationTimeMs();
			log.info("Operation time [" + cacheOperation.getOperationTimeMs()  + "]");
		}
		manager.shutdown();

		log.info("Average time [" + sum/repeatTimes  + "]");
	}

	private static boolean readCommandLine(String[] args) {
		boolean result = true;

		if ( args.length < 1 ) {
			result = false;
		} else {
			try {
				configFile = args[0];
				if ( args.length > 1 ) {
					operation = Operation.valueOf(args[1].toUpperCase());
				} else {
					operation = Operation.PUT;
				}
				if ( args.length > 2 ) {
					numOfElements = Integer.parseInt(args[2]);
				} else {
					numOfElements = DEFAULT_ELEMENTS_NUM;
				}
				if ( args.length > 3 ) {
					dataSizeBytes = Integer.parseInt(args[3]);
				} else {
					dataSizeBytes = DEFAULT_SIZE_BYTES;
				}
				if ( args.length > 4 ) {
					repeatTimes = Integer.parseInt(args[4]);
				} else {
					repeatTimes = DEFAULT_REPEAT_TIMES;
				}
			}
			catch (Exception e) {
				result = false;
				e.printStackTrace();
			}
		}

		return result;
	}

	private static void printUsage() {
		System.out.println("Usage: ");
		System.out.println("Test <config_file_name> <operation> <num_of_elemens> <data_size_chs>");
		System.out.println("");
		System.out.println("Where:");
		System.out.println("\tconfig_file_name - name of the ehcache configuration file.");
		System.out.println("\toperation - operation name. Can be put/get/remove. Default is '" + DEFAULT_OPERATION + "'.");
		System.out.println("\tnum_of_elemens - number of elements which will be used to perform the operation. Default is " + DEFAULT_ELEMENTS_NUM + ".");
		System.out.println("\tdata_size_bytes - the size of the values in bytes. Default size is " + DEFAULT_SIZE_BYTES + ".");
		System.out.println();
	}


	public static class CachePut extends BaseCacheOperation {
		private byte[] data ;

		public CachePut(Cache cache, CountDownLatch startLatch, CountDownLatch initLatch, CountDownLatch threadDoneLatch) {
			super(cache, startLatch, initLatch, threadDoneLatch);
		}

		@Override
		public void doOperation() {
			log.info("Sending elements.");

			final long start = System.currentTimeMillis();
			for(int i = 0; i != numOfElements; ++i) {
				byte[] tmp = new byte[data.length];

				System.arraycopy(tmp, 0, data, 0, tmp.length);

				tmp[0] = (byte)i;
				tmp[1] = (byte)(i>>8);
				tmp[2] = (byte)(i>>16);
				tmp[3] = (byte)(i>>24);

				getCache().put(new Element(Integer.toString(i), tmp));
			}
			final long end = System.currentTimeMillis();
			this.setOperationTimeMs(end-start);

			log.info("All elements sent.");

			log.info("Waiting for 'get' complete element to arrive.");
			while ( getCache().get(CacheGet.class.getName()) == null ) {
				try {
					Thread.sleep(1);
				}
				catch (InterruptedException e) {
					// ignore
				}
			}
		}

		@Override
		public void init() {
			data = new byte[dataSizeBytes];
			for(int i = 0; i != dataSizeBytes; ++i) {
				data[i] = (byte)i;
			}
		}
	}

	public static class CacheGet extends BaseCacheOperation {
		private LinkedList<Integer> keys = new LinkedList<Integer>();

		public CacheGet(Cache cache, CountDownLatch startLatch, CountDownLatch initLatch, CountDownLatch threadDoneLatch) {
			super(cache, startLatch, initLatch, threadDoneLatch);
		}

		@Override
		public void doOperation() {
			long start = -1;

			log.info("Waiting for elements...");
			while ( !keys.isEmpty() ) {
				for(Iterator<Integer> iterator = keys.iterator(); iterator.hasNext();) {
					Integer key = iterator.next();

					Element element;
					if ( (element = getCache().get(key.toString())) != null ) {
						byte[] value = (byte[])element.getValue();
						if ( value[0] != (byte)key.intValue() ||
								value[1] != (byte)(key >> 8) ||
								value[2] != (byte)(key >> 16) ||
								value[3] != (byte)(key >> 24) ) {
							log.warn("Wrong value received for the key [" + key + "]!!!");
						}

						if ( start == -1 ) {
							start = System.currentTimeMillis();
							log.info("First element received.");
						}
						iterator.remove();
					}
				}
			}
			log.info("All elements read.");

			final long end = System.currentTimeMillis();
			this.setOperationTimeMs(end-start);
		}

		@Override
		public void init() {
			for(int i = 0; i != numOfElements; ++i) {
				keys.add(i);
			}
		}
	}

	public static abstract class BaseCacheOperation implements Runnable {
		private final Cache cache;

		private final CountDownLatch startLatch;
		private final CountDownLatch initLatch;
		private final CountDownLatch threadDoneLatch;

		private long operationTimeMs = -1;

		protected BaseCacheOperation(Cache cache, CountDownLatch startLatch, CountDownLatch initLatch, CountDownLatch threadDoneLatch) {
			this.cache = cache;
			this.startLatch = startLatch;
			this.initLatch = initLatch;
			this.threadDoneLatch = threadDoneLatch;
		}

		public void run() {
			init();
			initLatch.countDown();

			try {
				startLatch.await();
			}
			catch (InterruptedException e) {
				// ignore
			}

			doOperation();

			cache.put(new Element(this.getClass().getName(), "complete"));
			try {
				// sleep to allow message to be delivered
				Thread.sleep(5000);
			}
			catch (InterruptedException e) {
				// ignore
			}

			threadDoneLatch.countDown();
		}

		public Cache getCache() {
			return cache;
		}

		public long getOperationTimeMs() {
			return operationTimeMs;
		}

		public void setOperationTimeMs(long operationTimeMs) {
			this.operationTimeMs = operationTimeMs;
		}

		public abstract void doOperation();
		public abstract void init();
	}
}
