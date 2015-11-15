package bookshop2.supplier.data.supplierOrderCache;

import static org.mockito.Mockito.mock;

import java.util.List;
import java.util.Map;

import javax.management.MBeanOperationInfo;
import javax.management.MBeanServerFactory;

import org.aries.runtime.RequestContext;
import org.aries.test.ArquillianConfiguration;
import org.aries.util.FieldUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import bookshop2.Book;
import bookshop2.BookKey;
import bookshop2.supplier.SupplierContext;
import bookshop2.util.Bookshop2Fixture;

import common.jmx.MBeanUtil;
import common.jmx.client.JmxManager;


@RunWith(MockitoJUnitRunner.class)
public class SupplierOrderCacheManagerTest {
	
	private SupplierOrderCacheManager supplierOrderCacheManager;
	
	private SupplierOrderCache mockSupplierOrderCache;
	
	private SupplierOrderCacheProcessor mockSupplierOrderCacheProcessor;

	private RequestContext mockRequestContext;
	
	private ArquillianConfiguration configuration;
	
	private JmxManager jmxManager;


	@Before
	public void setUp() throws Exception {
		MBeanUtil.setMBeanServer(MBeanServerFactory.createMBeanServer());		
		
		supplierOrderCacheManager = createSupplierOrderCacheManager();
		registerSupplierOrderCacheManager(supplierOrderCacheManager);
		
//		configuration = new ArquillianConfiguration();
//		configuration.setJndiPropertyFileName("provider/hornetq/node1-jndi-remote.properties");
//		configuration.setJmsPropertyFileName("provider/hornetq/node1-jms-remote.properties");
//		configuration.initialize();
//		
//		assertNotNull(configuration.getBindAddress());
//		assertNotNull(configuration.getManagementPort());
//		assertNotNull(configuration.getInitialContextProperties());
//		
//		jmxManager = new JmxManager();
//		jmxManager.setUsername(configuration.getUsername());
//		jmxManager.setPassword(configuration.getPassword());
//		jmxManager.setBindAddress(configuration.getBindAddress());
//		jmxManager.setManagementPort(configuration.getManagementPort());
//		jmxManager.setJndiProperties(configuration.getInitialContextProperties());
//		jmxManager.initialize();
	}
	
	public SupplierOrderCacheManager createSupplierOrderCacheManager() {
		mockSupplierOrderCache = mock(SupplierOrderCache.class);
		mockSupplierOrderCacheProcessor = mock(SupplierOrderCacheProcessor.class);
		mockRequestContext = mock(RequestContext.class);
		SupplierOrderCacheManager supplierOrderCacheManager = new SupplierOrderCacheManager();
		FieldUtil.setFieldValue(supplierOrderCacheManager, "supplierOrderCache", mockSupplierOrderCache);
		FieldUtil.setFieldValue(supplierOrderCacheManager, "supplierOrderCacheProcessor", mockSupplierOrderCacheProcessor);
		//FieldUtil.setFieldValue(supplierOrderCacheManager, "requestContext", mockRequestContext);
		return supplierOrderCacheManager;
	}

	public void registerSupplierOrderCacheManager() {
		registerSupplierOrderCacheManager(createSupplierOrderCacheManager());
	}
	
	public void registerSupplierOrderCacheManager(SupplierOrderCacheManager supplierOrderCacheManager) {
		MBeanUtil.registerMBean(supplierOrderCacheManager, SupplierOrderCacheManagerMBean.MBEAN_NAME);
	}
	
	@After
	public void tearDown() throws Exception {
		MBeanUtil.unregisterMBeans();
		supplierOrderCacheManager = null;
	}

	@Test
	//@Ignore
	public void testGetBooksInStock() throws Exception {
		//prepare context
		List<Book> expectedBooksInStock = Bookshop2Fixture.createList_Book();
		addToBooksInStock(expectedBooksInStock);
		
		MBeanOperationInfo[] operations = MBeanUtil.getOperations(SupplierOrderCacheManagerMBean.MBEAN_NAME);
		for (int i = 0; i < operations.length; i++) {
			String name = operations[i].getName();
			System.out.println(">>>"+name);
		}
		
		//execute fixture
		List<Book> actualBooksInStock = MBeanUtil.invoke(SupplierOrderCacheManagerMBean.MBEAN_NAME, "getAllBooksInStock");
		//Map<String, Book> actualBooksInStock = MBeanUtil.getAttribute(SupplierOrderCacheManagerMBean.MBEAN_NAME, "BooksInStock");

		//validate results
		validate(expectedBooksInStock, actualBooksInStock);
	}

	@Test
	//@Ignore
	public void testGetBooksInStockAsMap() throws Exception {
		//prepare context
		Map<BookKey, Book> expectedBooksInStock = Bookshop2Fixture.createMap_Book();
		addToBooksInStock(expectedBooksInStock);
		
		//execute fixture
		Map<BookKey, Book> actualBooksInStock = MBeanUtil.invoke(SupplierOrderCacheManagerMBean.MBEAN_NAME, "getAllBooksInStockAsMap");
		//Map<String, Book> actualBooksInStock = MBeanUtil.getAttribute(SupplierOrderCacheManagerMBean.MBEAN_NAME, "BooksInStock");

		//validate results
		validate(expectedBooksInStock, actualBooksInStock);
	}
	

	@Test
	public void testAddToBooksInStock() throws Exception {
		//prepare context
		Map<BookKey, Book> expectedBooksInStock = Bookshop2Fixture.createMap_Book();

		//execute fixture
		MBeanUtil.invoke(SupplierOrderCacheManagerMBean.MBEAN_NAME, "addToBooksInStock", "java.util.Map", expectedBooksInStock);
		
		//validate results
		Map<BookKey, Book> actualBooksInStock = getPendingBooksInStock();
		validate(expectedBooksInStock, actualBooksInStock);
	}

	@Test
	public void testUpdateState() throws Exception {
		//prepare context
		Map<BookKey, Book> expectedBooksInStock = Bookshop2Fixture.createMap_Book();
		addToBooksInStock(expectedBooksInStock);

		//execute fixture
		supplierOrderCacheManager.updateState();

		//validate results
		Map<BookKey, Book> actualBooksInStock = getBooksInStock();
		validate(expectedBooksInStock, actualBooksInStock);
	}

	protected Map<BookKey, Book> getBooksInStock() throws Exception {
		return supplierOrderCacheManager.getAllBooksInStockAsMap();
	}

	protected Map<BookKey, Book> getPendingBooksInStock() throws Exception {
		return supplierOrderCacheManager.getAllBooksInStockAsMap();
	}

	public void addToBooksInStock(List<Book> bookList) throws Exception {
		supplierOrderCacheManager.addToBooksInStock(bookList);
	}
	
	public void addToBooksInStock(Map<BookKey, Book> bookMap) throws Exception {
		supplierOrderCacheManager.addToBooksInStock(bookMap);
	}

	
//	@SuppressWarnings("unchecked")
//	public Map<Object, Book> getBooksInStock(ObjectName objectName) throws Exception {
//		Map<Object, Book> books = (Map<Object, Book>) MBeanUtil.invoke(objectName, "getBooksInStock");
//		return books;
//	}
//	
//	public void addToBooksInStock(ObjectName objectName, Map<Object, Book> books) throws Exception {
//		Object[] parameters = { books };
//		String[] signature = { "java.util.Map" };
//		MBeanUtil.invoke(objectName, "addToBooksInStock", parameters, signature);
//	}
	
	protected void validate(List<Book> expectedBooksInStock, List<Book> actualBooksInStock) {
	}
	
	protected void validate(Map<BookKey, Book> expectedBooksInStock, Map<BookKey, Book> actualBooksInStock) {
	}

}
