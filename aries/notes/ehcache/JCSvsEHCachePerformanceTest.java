package org.apache.jcs;

import junit.framework.TestCase;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.jcs.engine.CompositeCacheAttributes;
import org.apache.jcs.engine.behavior.ICompositeCacheAttributes;
import org.apache.jcs.utils.struct.LRUMap;

/**
 * Compare JCS vs ehcache performance.
 *
 * @author Aaron Smuts
 *
 */
public class JCSvsEHCachePerformanceTest
    extends TestCase
{

    float ratioPut = 0;

    float ratioGet = 0;

    // the jcs to competitor
    float target = 1.0f;

    int loops = 20;

    int tries = 50000;

    /**
     * Compare performance between JCS and EHCache. Fail if JCS is not as fast.
     * Print the ratio.
     *
     * @throws Exception
     *
     */
    public void testJCSvsEHCache()
        throws Exception
    {

        Log log = LogFactory.getLog( LRUMap.class );
        if ( log.isDebugEnabled() )
        {
            System.out.println( "The log level must be at info or above for the a performance test." );
            return;
        }

        doWork();

        assertTrue( this.ratioPut < target );
        assertTrue( this.ratioGet < target );

    }

    /**
     * This runs a series of gets and puts for both JCS and EHCache. The test
     * will fail if JCS is not faster.
     *
     * @throws Exception
     *
     */
    public void doWork()
        throws Exception
    {

        int maxSize = 1000000;

        // create the two caches.
        CacheManager ehMgr = CacheManager.getInstance();
        // Create an ehcache with a max size of maxSize, no swap, with items
        // that can expire, with maximum idle time to live of 500 seconds, and
        // maximum idel time of 500 seconds.
        Cache eh = new Cache( "testJCSvsEHCache", maxSize, false, false, 500, 500 );
        ehMgr.addCache( eh );

        // Create a similarly configured JCS that uses the LRU memory cache.
        // maxSize elements that are not eternal. No disk cache is configured.
        ICompositeCacheAttributes cattr = new CompositeCacheAttributes();
        cattr.setMaxObjects( maxSize );
        JCS jcs = JCS.getInstance( "testJCSvsEHCache", cattr );

        // run settings
        long start = 0;
        long end = 0;
        long time = 0;
        float tPer = 0;

        long putTotalJCS = 0;
        long getTotalJCS = 0;
        long putTotalEHCache = 0;
        long getTotalEHCache = 0;

        String jcsDisplayName = "JCS";
        String ehCacheDisplayName = "";

        try
        {
            for ( int j = 0; j < loops; j++ )
            {

                jcsDisplayName = "JCS      ";
                start = System.currentTimeMillis();
                for ( int i = 0; i < tries; i++ )
                {
                    jcs.put( "key:" + i, "data" + i );
                }
                end = System.currentTimeMillis();
                time = end - start;
                putTotalJCS += time;
                tPer = Float.intBitsToFloat( (int) time ) / Float.intBitsToFloat( tries );
                System.out
                    .println( jcsDisplayName + " put time for " + tries + " = " + time + "; millis per = " + tPer );

                start = System.currentTimeMillis();
                for ( int i = 0; i < tries; i++ )
                {
                    jcs.get( "key:" + i );
                }
                end = System.currentTimeMillis();
                time = end - start;
                getTotalJCS += time;
                tPer = Float.intBitsToFloat( (int) time ) / Float.intBitsToFloat( tries );
                System.out
                    .println( jcsDisplayName + " get time for " + tries + " = " + time + "; millis per = " + tPer );

                // /////////////////////////////////////////////////////////////
                ehCacheDisplayName = "EHCache  ";

                start = System.currentTimeMillis();
                for ( int i = 0; i < tries; i++ )
                {
                    Element ehElm = new Element( "key:" + i, "data" + i );

                    eh.put( ehElm );
                }
                end = System.currentTimeMillis();
                time = end - start;
                putTotalEHCache += time;
                tPer = Float.intBitsToFloat( (int) time ) / Float.intBitsToFloat( tries );
                System.out.println( ehCacheDisplayName + " put time for " + tries + " = " + time + "; millis per = "
                    + tPer );

                start = System.currentTimeMillis();
                for ( int i = 0; i < tries; i++ )
                {
                    eh.get( "key:" + i );
                }
                end = System.currentTimeMillis();
                time = end - start;
                getTotalEHCache += time;
                tPer = Float.intBitsToFloat( (int) time ) / Float.intBitsToFloat( tries );
                System.out.println( ehCacheDisplayName + " get time for " + tries + " = " + time + "; millis per = "
                    + tPer );

                System.out.println( "\n" );
            }

        }
        catch ( Exception e )
        {
            e.printStackTrace( System.out );
            System.out.println( e );
        }

        long putAvJCS = putTotalJCS / loops;
        long getAvJCS = getTotalJCS / loops;
        long putAvHashtable = putTotalEHCache / loops;
        long getAvHashtable = getTotalEHCache / loops;

        System.out.println( "Finished " + loops + " loops of " + tries + " gets and puts" );

        System.out.println( "\n" );
        System.out.println( "Put average for " + jcsDisplayName + "  = " + putAvJCS );
        System.out.println( "Put average for " + ehCacheDisplayName + " = " + putAvHashtable );
        ratioPut = Float.intBitsToFloat( (int) putAvJCS ) / Float.intBitsToFloat( (int) putAvHashtable );
        System.out.println( jcsDisplayName + " puts took " + ratioPut + " times the " + ehCacheDisplayName
            + ", the goal is <" + target + "x" );

        System.out.println( "\n" );
        System.out.println( "Get average for  " + jcsDisplayName + "  = " + getAvJCS );
        System.out.println( "Get average for " + ehCacheDisplayName + " = " + getAvHashtable );
        ratioGet = Float.intBitsToFloat( (int) getAvJCS ) / Float.intBitsToFloat( (int) getAvHashtable );
        System.out.println( jcsDisplayName + " gets took " + ratioGet + " times the " + ehCacheDisplayName
            + ", the goal is <" + target + "x" );

    }

}
