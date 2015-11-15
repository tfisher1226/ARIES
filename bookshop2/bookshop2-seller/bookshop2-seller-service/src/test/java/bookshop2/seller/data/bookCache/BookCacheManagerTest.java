package bookshop2.seller.data.bookCache;

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
import bookshop2.seller.SellerContext;
import bookshop2.seller.data.bookCache.BookCache;
import bookshop2.seller.data.bookCache.BookCacheManager;
import bookshop2.seller.data.bookCache.BookCacheManagerMBean;
import bookshop2.seller.data.bookCache.BookCacheProcessor;
import bookshop2.util.Bookshop2Fixture;
import common.jmx.MBeanUtil;
import common.jmx.client.JmxManager;


@RunWith(MockitoJUnitRunner.class)
public class BookCacheManagerTest {
	
	private BookCacheManager bookCacheManager;
	
	private BookCache mockBookCache;
	
	private BookCacheProcessor mockBookCacheProcessor;

	private RequestContext mockRequestContext;
	
	private ArquillianConfiguration configuration;
	
	private JmxManager jmxManager;


	@Before
	public void setUp() throws Exception {
		MBeanUtil.setMBeanServer(MBeanServerFactory.createMBeanServer());		
		
		bookCacheManager = createBookCacheManager();
		registerBookCacheManager(bookCacheManager);
		
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
	
	public BookCacheManager createBookCacheManager() {
		mockBookCache = mock(BookCache.class);
		mockBookCacheProcessor = mock(BookCacheProcessor.class);
		mockRequestContext = mock(RequestContext.class);
		BookCacheManager bookCacheManager = new BookCacheManager();
		FieldUtil.setFieldValue(bookCacheManager, "bookCache", mockBookCache);
		FieldUtil.setFieldValue(bookCacheManager, "bookCacheProcessor", mockBookCacheProcessor);
		FieldUtil.setFieldValue(bookCacheManager, "requestContext", mockRequestContext);
		return bookCacheManager;
	}

	public void registerBookCacheManager() {
		registerBookCacheManager(createBookCacheManager());
	}
	
	public void registerBookCacheManager(BookCacheManager bookCacheManager) {
		MBeanUtil.registerMBean(bookCacheManager, BookCacheManagerMBean.MBEAN_NAME);
	}
	
	@After
	public void tearDown() throws Exception {
		MBeanUtil.unregisterMBeans();
		bookCacheManager = null;
	}

	@Test
	//@Ignore
	public void testGetBookOrders() throws Exception {
		//prepare context
		List<Book> expectedBookOrders = Bookshop2Fixture.createList_Book();
		addToBookOrders(expectedBookOrders);
		
//		MBeanOperationInfo[] operations = MBeanUtil.getOperations(BookCacheManagerMBean.MBEAN_NAME);
//		for (int i = 0; i < operations.length; i++) {
//			String name = operations[i].getName();
//			System.out.println(">>>"+name);
//		}
		
		//execute fixture
		List<Book> actualBookOrders = MBeanUtil.invoke(BookCacheManagerMBean.MBEAN_NAME, "getAllBookOrders");

		//validate results
		validate(expectedBookOrders, actualBookOrders);
	}

	@Test
	//@Ignore
	public void testGetBookOrdersAsMap() throws Exception {
		//prepare context
		List<Book> expectedBookOrders = Bookshop2Fixture.createList_Book();
		addToBookOrders(expectedBookOrders);
		
		//execute fixture
		List<Book> actualBookOrders = MBeanUtil.invoke(BookCacheManagerMBean.MBEAN_NAME, "getAllBookOrders");

		//validate results
		validate(expectedBookOrders, actualBookOrders);
	}
	

	@Test
	public void testAddToBookOrders() throws Exception {
		//prepare context
		List<Book> expectedBookOrders = Bookshop2Fixture.createList_Book();

		//execute fixture
		MBeanUtil.invoke(BookCacheManagerMBean.MBEAN_NAME, "addToBookOrders", "java.util.Collection", expectedBookOrders);
		
		//validate results
		List<Book> actualBookOrders = getBookOrders();
		validate(expectedBookOrders, actualBookOrders);
	}

	@Test
	public void testUpdateState() throws Exception {
		//prepare context
		List<Book> expectedBookOrders = Bookshop2Fixture.createList_Book();
		addToBookOrders(expectedBookOrders);

		//execute fixture
		bookCacheManager.updateState();

		//validate results
		List<Book> actualBookOrders = getBookOrders();
		validate(expectedBookOrders, actualBookOrders);
	}

	protected List<Book> getBookOrders() throws Exception {
		return bookCacheManager.getAllBookOrders();
	}

	protected List<Book> getPendingBookOrders() throws Exception {
		return bookCacheManager.getAllBookOrders();
	}

	public void addToBookOrders(List<Book> bookList) throws Exception {
		bookCacheManager.addToBookOrders(bookList);
	}
	
//	@SuppressWarnings("unchecked")
//	public Map<Object, Book> getBookOrders(ObjectName objectName) throws Exception {
//		Map<Object, Book> books = (Map<Object, Book>) MBeanUtil.invoke(objectName, "getBookOrders");
//		return books;
//	}
//	
//	public void addToBookOrders(ObjectName objectName, Map<Object, Book> books) throws Exception {
//		Object[] parameters = { books };
//		String[] signature = { "java.util.Map" };
//		MBeanUtil.invoke(objectName, "addToBookOrders", parameters, signature);
//	}
	
	protected void validate(List<Book> expectedBookOrders, List<Book> actualBookOrders) {
	}
	
	protected void validate(Map<String, Book> expectedBookOrders, Map<String, Book> actualBookOrders) {
	}

}
