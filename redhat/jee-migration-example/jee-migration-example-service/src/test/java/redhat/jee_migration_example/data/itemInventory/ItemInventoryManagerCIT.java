package redhat.jee_migration_example.data.itemInventory;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.Callable;

import javax.inject.Inject;

import org.aries.Assert;
import org.aries.tx.AbstractArquillianTest;
import org.aries.tx.AbstractDataUnitArquillionTest;
import org.aries.tx.BytemanRule;
import org.aries.tx.TransactionTestControl;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.TargetsContainer;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.shrinkwrap.api.spec.EnterpriseArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import redhat.jee_migration_example.EventLoggerTestEARBuilder;
import redhat.jee_migration_example.EventLoggerTestWARBuilder;
import redhat.jee_migration_example.Item;
import redhat.jee_migration_example.ItemInventoryHelper;
import redhat.jee_migration_example.util.JeeMigrationExampleFixture;

import common.tx.state.AbstractStateManager;


@RunWith(Arquillian.class)
public class ItemInventoryManagerCIT extends AbstractDataUnitArquillionTest {
	
	@Inject
	private ItemInventoryManager itemInventoryManager;
	
	@Inject
	private ItemInventoryHelper itemInventoryHelper;
	
	
	public ItemInventoryManagerCIT() {
		//nothing for now
	}
	
	
	@Override
	public String getServerName() {
		return "hornetQ01_local";
	}
	
	@Override
	public String getDomainId() {
		return "redhat.jee_migration_example";
	}
	
	@Override
	public Class<?> getTestClass() {
		return ItemInventoryManagerCIT.class;
	}
	
	@Override
	public String getTargetArchive() {
		return EventLoggerTestEARBuilder.NAME;
	}
	
	@Override
	public AbstractStateManager<?> getStateManager() {
		return itemInventoryManager;
	}
	
	@BeforeClass
	public static void beforeClass() throws Exception {
		AbstractArquillianTest.beforeClass();
	}
	
	@AfterClass
	public static void afterClass() throws Exception {
		AbstractArquillianTest.afterClass();
	}
	
	@Before
	public void setUp() throws Exception {
		if (!isServerStarted()) {
			startServer();
		} else {
			super.setUp();
			createTransactionControl();
			initializeItemInventoryHelper();
			clearItemInventoryContext();
		}
	}
	
	@After
	public void tearDown() throws Exception {
		super.tearDown();
		clearTransaction();
		clearItemInventoryContext();
	}
	
	@TargetsContainer("hornetQ01_local")
	@Deployment(name = "eventLoggerEAR", order = 1)
	public static EnterpriseArchive createEventLoggerEAR() {
		EventLoggerTestEARBuilder builder = new EventLoggerTestEARBuilder();
		builder.addAsModule(createEventLoggerWAR(), "eventLogger");
		EnterpriseArchive ear = builder.createEAR();
		return ear;
	}
	
	public static WebArchive createEventLoggerWAR() {
		EventLoggerTestWARBuilder builder = new EventLoggerTestWARBuilder("test.war");
		builder.addClass(ItemInventoryManagerCIT.class);
		WebArchive war = builder.create();
		return war;
	}
	
	protected void createTransactionControl() throws Exception {
		transactionTestControl = new TransactionTestControl();
		transactionTestControl.assureTransactionManager();
	}
	
	protected void initializeItemInventoryHelper() throws Exception {
		itemInventoryHelper.setJmxManager(jmxManager);
		itemInventoryHelper.initialize(transactionTestControl);
	}
	
	protected void clearItemInventoryContext() throws Exception {
		itemInventoryHelper.clearContext(ItemInventoryManager.MBEAN_NAME);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 1)
	public void runTest_ItemStore_GetAllAsMap_success() throws Exception {
		String testName = "runTest_ItemStore_GetAllAsMap_success";
		log.info(testName+": started");
		
		// prepare test context
		itemInventoryHelper.assureRemoveAll();
		
		// prepare test data
		int itemCount = 4;
		Map<String, Item> itemMapToAdd = JeeMigrationExampleFixture.createMap_Item(itemCount);
		itemInventoryHelper.addItemStore(itemMapToAdd);
		
		// execute action
		Map<String, Item> itemMap = runTest_ItemStore_GetAllAsMap();
		
		// validate persistent state
		Assert.equals(itemCount, itemMap.size(), "ItemStore record count should be correct");
		JeeMigrationExampleFixture.assertSameItem(itemMapToAdd, itemMap, "ItemStore records should be equal");
				
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 2)
	public void runTest_ItemStore_GetAllAsMap_tx_commit() throws Exception {
		String testName = "runTest_ItemStore_GetAllAsMap_tx_commit";
		log.info(testName+": started");
		
		// prepare test context
		itemInventoryHelper.assureRemoveAll();
		
		// prepare test data
		int itemCount = 4;
		Map<String, Item> itemMapToAdd = JeeMigrationExampleFixture.createMap_Item(itemCount);
		itemInventoryHelper.addItemStoreIC(itemMapToAdd);
		
		// begin transaction
		beginJTATransaction();
		
		// execute action
		Map<String, Item> itemMap = runTest_ItemStore_GetAllAsMap();

		// validate intermediate persistent state
		Assert.equals(itemCount, itemMap.size(), "ItemStore record count should be correct");
		JeeMigrationExampleFixture.assertSameItem(itemMapToAdd, itemMap, "ItemStore records should be equal");

		// commit transaction
		commitJTATransaction();

		// execute action again
		itemMap = runTest_ItemStore_GetAllAsMap();

		// validate persistent state
		Assert.equals(itemCount, itemMap.size(), "ItemStore record count should be correct");
		JeeMigrationExampleFixture.assertSameItem(itemMapToAdd, itemMap, "ItemStore records should be equal");
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 2)
	public void runTest_ItemStore_GetAllAsMap_tx_commit2() throws Exception {
		String testName = "runTest_ItemStore_GetAllAsMap_tx_commit";
		log.info(testName+": started");
		
		// prepare test context
		itemInventoryHelper.assureRemoveAll();
		
		// begin transaction
		beginJTATransaction();
		
		// prepare test data
		int itemCount = 4;
		Map<String, Item> itemMapToAdd = JeeMigrationExampleFixture.createMap_Item(itemCount);
		itemInventoryHelper.addItemStoreIC(itemMapToAdd);
		
		// execute action
		Map<String, Item> itemMap = runTest_ItemStore_GetAllAsMap();

		//TODO get results from intermediate transactional state
		itemMap = itemInventoryHelper.getAllItemStoreAsMapIC();
		
		//TODO get results from persisted state
		//TODO validate persisted state == 0

		//TODO validate intermediate transactional state
		//TODO Assert.isEmpty(itemMap, "No ItemStore records should exist");
		Assert.equals(itemCount, itemMap.size(), "ItemStore record count should be correct");
		JeeMigrationExampleFixture.assertSameItem(itemMapToAdd, itemMap, "ItemStore records should be equal");
		
		// commit transaction
		commitJTATransaction();

		// execute action again
		itemMap = runTest_ItemStore_GetAllAsMap();

		// validate persistent state
		Assert.equals(itemCount, itemMap.size(), "ItemStore record count should be correct");
		JeeMigrationExampleFixture.assertSameItem(itemMapToAdd, itemMap, "ItemStore records should be equal");
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	@Ignore
	@InSequence(value = 3)
	public void runTest_ItemStore_GetAllAsMap_tx_rollback() throws Exception {
		String testName = "runTest_ItemStore_GetAllAsMap_tx_rollback";
		log.info(testName+": started");
		
		// prepare test context
		itemInventoryHelper.assureRemoveAll();
		
		// begin transaction
		beginJTATransaction();
		
		// prepare test data
		int itemCount = 4;
		Map<String, Item> itemMapToAdd = JeeMigrationExampleFixture.createMap_Item(itemCount);
		itemInventoryHelper.addItemStoreIC(itemMapToAdd);
		
		// execute action
		Map<String, Item> itemMap = runTest_ItemStore_GetAllAsMap();
		
		// validate intermediate persistent state
		Assert.isEmpty(itemMap, "No ItemStore records should exist");
					
		// rollback transaction
		rollbackJTATransaction();
		
		// execute action again
		itemMap = runTest_ItemStore_GetAllAsMap();

		// validate persistent state
		Assert.isEmpty(itemMap, "No ItemStore records should exist");
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 4)
	public void runTest_ItemStore_GetAllAsMap_utx_commit() throws Exception {
		String testName = "runTest_ItemStore_GetAllAsMap_utx_commit";
		log.info(testName+": started");
		
		// prepare test context
		itemInventoryHelper.assureRemoveAll();
		
		// prepare test data
		int itemCount = 4;
		Map<String, Item> itemMapToAdd = JeeMigrationExampleFixture.createMap_Item(itemCount);
		itemInventoryHelper.addItemStore(itemMapToAdd);
		
		// begin transaction
		beginUserTransaction();
		
		// execute action
		Map<String, Item> itemMap = runTest_ItemStore_GetAllAsMap();
		
		// commit transaction
		commitJTATransaction();
		
		// validate intermediate persistent state
		Assert.equals(itemCount, itemMap.size(), "ItemStore record count should be correct");
		JeeMigrationExampleFixture.assertSameItem(itemMapToAdd, itemMap, "ItemStore records should be equal");
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	@Ignore
	@InSequence(value = 5)
	public void runTest_ItemStore_GetAllAsMap_utx_rollback() throws Exception {
		String testName = "runTest_ItemStore_GetAllAsMap_utx_rollback";
		log.info(testName+": started");
		
		// prepare test context
		itemInventoryHelper.assureRemoveAll();
		
		// prepare test data
		int itemCount = 4;
		Map<String, Item> itemMapToAdd = JeeMigrationExampleFixture.createMap_Item(itemCount);
		itemInventoryHelper.addItemStoreIC(itemMapToAdd);
		
		// begin transaction
		beginUserTransaction();
		
		// execute action
		Map<String, Item> itemMap = runTest_ItemStore_GetAllAsMap();
		
		// validate intermediate persistent state
		//Assert.isEmpty(itemMap, "No ItemStore records should exist");
					
		// rollback transaction
		rollbackJTATransaction();
		
		// execute action again
		itemMap = runTest_ItemStore_GetAllAsMap();

		// validate persistent state
		Assert.isEmpty(itemMap, "No ItemStore records should exist");		

		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 6)
	@BytemanRule(name = "rule6", 
			targetClass = "ItemInventoryManager", 
			targetMethod = "getAllItemStoreAsMap", 
			targetLocation = "AT EXIT", 
			action = "throw new org.aries.ApplicationFailure(\"error6\")")
	public void runTest_ItemStore_GetAllAsMap_manager_exception_rollback() throws Exception {
		String testName = "runTest_ItemStore_GetAllAsMap_manager_exception_rollback";
		log.info(testName+": started");
		
		// prepare test context
		setupByteman(testName);
		itemInventoryHelper.assureRemoveAll();
		
		// setup expectations
		isExceptionExpected = true;
		
		try {
			// execute action
			runTest_ItemStore_GetAllAsMap();
			fail("Exception should have been thrown");

		} catch (Exception e) {
			Assert.exception(e, org.aries.ApplicationFailure.class);
			Assert.exception(e, "error6");

		} finally {
			// cleanup test context
			tearDownByteman(testName);
		}
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 7)
	@BytemanRule(name = "rule7", 
			targetClass = "ItemInventory", 
			targetMethod = "getAllItemStoreAsMap", 
			targetLocation = "AT EXIT", 
			action = "throw new org.aries.ApplicationFailure(\"error7\")")
	public void runTest_ItemStore_GetAllAsMap_interface_exception_rollback() throws Exception {
		String testName = "runTest_ItemStore_GetAllAsMap_interface_exception_rollback";
		log.info(testName+": started");
		
		// prepare test context
		setupByteman(testName);
		itemInventoryHelper.assureRemoveAll();
		
		// setup expectations
		isExceptionExpected = true;
		
		try {
			// execute action
			runTest_ItemStore_GetAllAsMap();
			fail("Exception should have been thrown");

		} catch (Exception e) {
			Assert.exception(e, org.aries.ApplicationFailure.class);
			Assert.exception(e, "error7");

		} finally {
			// cleanup test context
			tearDownByteman(testName);
		}
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 8)
	@BytemanRule(name = "rule8", 
			targetClass = "ItemInventoryRepositoryImpl", 
			targetMethod = "getAllItemStoreRecords", 
			targetLocation = "AT EXIT", 
			action = "throw new org.aries.ApplicationFailure(\"error8\")")
	public void runTest_ItemStore_GetAllAsMap_repository_exception_rollback() throws Exception {
		String testName = "runTest_ItemStore_GetAllAsMap_repository_exception_rollback";
		log.info(testName+": started");
		
		// prepare test context
		setupByteman(testName);
		itemInventoryHelper.assureRemoveAll();
		
		// setup expectations
		isExceptionExpected = true;
		
		try {
			// execute action
			runTest_ItemStore_GetAllAsMap();
			fail("Exception should have been thrown");

		} catch (Exception e) {
			Assert.exception(e, org.aries.ApplicationFailure.class);
			Assert.exception(e, "error8");

		} finally {
		// cleanup test context
		tearDownByteman(testName);
		}
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	public <T> T runTest_ItemStore_GetAllAsMap() throws Exception {
		return runAction(new Callable<T>() {
			@SuppressWarnings("unchecked")
			public T call() {
				return (T) itemInventoryManager.getAllItemStoreAsMap();
			}
		});
	}
	
	@Test
	//@Ignore
	@InSequence(value = 17)
	public void runTest_ItemStore_AddAsItem_success() throws Exception {
		String testName = "runTest_ItemStore_AddAsItem_success";
		log.info(testName+": started");
		
		// prepare test context
		itemInventoryHelper.assureRemoveAll();

		// prepare test data
		Item itemToAdd = JeeMigrationExampleFixture.create_Item();
		String keyToAdd = JeeMigrationExampleFixture.create_ItemKey(itemToAdd);
		
		// execute test action
		runTest_ItemStore_AddAsItem(keyToAdd, itemToAdd);

		// validate persistent state
		Collection<Item> itemList = itemInventoryHelper.getAllItemStore();
		Assert.equals(1, itemList.size(), "Only 1 ItemStore record should exist");
		JeeMigrationExampleFixture.assertSameItem(itemToAdd, itemList.iterator().next());
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 18)
	public void runTest_ItemStore_AddAsItem_tx_commit() throws Exception {
		String testName = "runTest_ItemStore_AddAsItem_tx_commit";
		log.info(testName+": started");
		
		// prepare test context
		itemInventoryHelper.assureRemoveAll();
		
		// begin transaction
		beginJTATransaction();

		// prepare test data
		Item itemToAdd = JeeMigrationExampleFixture.create_Item();
		String keyToAdd = JeeMigrationExampleFixture.create_ItemKey(itemToAdd);
		
		// execute test action
		runTest_ItemStore_AddAsItem(keyToAdd, itemToAdd);
		
		// commit transaction
		commitJTATransaction();

		// validate persistent state
		Collection<Item> itemList = itemInventoryHelper.getAllItemStore();
		Assert.equals(1, itemList.size(), "Only 1 ItemStore record should exist");
		JeeMigrationExampleFixture.assertSameItem(itemToAdd, itemList.iterator().next());
		clearTransaction();
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 19)
	public void runTest_ItemStore_AddAsItem_tx_rollback() throws Exception {
		String testName = "runTest_ItemStore_AddAsItem_tx_rollback";
		log.info(testName+": started");
		
		// prepare test context
		itemInventoryHelper.assureRemoveAll();
		
		// begin transaction
		beginJTATransaction();

		// prepare test data
		Item itemToAdd = JeeMigrationExampleFixture.create_Item();
		String keyToAdd = JeeMigrationExampleFixture.create_ItemKey(itemToAdd);
		
		// execute test action
		runTest_ItemStore_AddAsItem(keyToAdd, itemToAdd);
		
		// rollback transaction
		rollbackJTATransaction();

		// validate persistent state
		Collection<Item> itemList = itemInventoryHelper.getAllItemStore();
		Assert.isEmpty(itemList, "No ItemStore records should exist");
		clearTransaction();
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 20)
	public void runTest_ItemStore_AddAsItem_utx_commit() throws Exception {
		String testName = "runTest_ItemStore_AddAsItem_utx_commit";
		log.info(testName+": started");
		
		// prepare test context
		itemInventoryHelper.assureRemoveAll();
		
		// begin transaction
		beginUserTransaction();

		// prepare test data
		Item itemToAdd = JeeMigrationExampleFixture.create_Item();
		String keyToAdd = JeeMigrationExampleFixture.create_ItemKey(itemToAdd);
		
		// execute test action
		runTest_ItemStore_AddAsItem(keyToAdd, itemToAdd);
		
		// commit transaction
		commitUserTransaction();

		// validate persistent state
		Collection<Item> itemList = itemInventoryHelper.getAllItemStore();
		Assert.equals(1, itemList.size(), "Only 1 ItemStore record should exist");
		JeeMigrationExampleFixture.assertSameItem(itemToAdd, itemList.iterator().next());
		clearTransaction();
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 21)
	public void runTest_ItemStore_AddAsItem_utx_rollback() throws Exception {
		String testName = "runTest_ItemStore_AddAsItem_utx_rollback";
		log.info(testName+": started");
		
		// prepare test context
		itemInventoryHelper.assureRemoveAll();
		
		// begin transaction
		beginUserTransaction();

		// prepare test data
		Item itemToAdd = JeeMigrationExampleFixture.create_Item();
		String keyToAdd = JeeMigrationExampleFixture.create_ItemKey(itemToAdd);
		
		// execute test action
		runTest_ItemStore_AddAsItem(keyToAdd, itemToAdd);
		
		// rollback transaction
		rollbackUserTransaction();

		// validate persistent state
		Collection<Item> itemList = itemInventoryHelper.getAllItemStore();
		Assert.isEmpty(itemList, "No ItemStore records should exist");
		clearTransaction();
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 22)
	@BytemanRule(name = "rule22", 
			targetClass = "ItemInventoryManager", 
			targetMethod = "addToItemStore", 
			targetLocation = "AT EXIT", 
			action = "throw new org.aries.ApplicationFailure(\"error22\")")
	public void runTest_ItemStore_AddAsItem_manager_exception_rollback() throws Exception {
		String testName = "runTest_ItemStore_AddAsItem_manager_exception_rollback";
		log.info(testName+": started");
		
		// prepare test context
		setupByteman(testName);
		itemInventoryHelper.assureRemoveAll();
		
		// setup expectations
		isExceptionExpected = true;

		// prepare test data
		Item itemToAdd = JeeMigrationExampleFixture.create_Item();
		String keyToAdd = JeeMigrationExampleFixture.create_ItemKey(itemToAdd);
		
		try {
			// execute test action
			runTest_ItemStore_AddAsItem(keyToAdd, itemToAdd);
			fail("Exception should have been thrown");

		} catch (Exception e) {
			Assert.exception(e, org.aries.ApplicationFailure.class);
			Assert.exception(e, "error22");

		// validate persistent state
		Collection<Item> itemList = itemInventoryHelper.getAllItemStore();
		Assert.isEmpty(itemList, "No ItemStore records should exist");

		} finally {
		// cleanup test context
		tearDownByteman(testName);
		}
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 23)
	@BytemanRule(name = "rule23", 
			targetClass = "ItemInventory", 
			targetMethod = "addToItemStore", 
			targetLocation = "AT EXIT", 
			action = "throw new org.aries.ApplicationFailure(\"error23\")")
	public void runTest_ItemStore_AddAsItem_interface_exception_rollback() throws Exception {
		String testName = "runTest_ItemStore_AddAsItem_interface_exception_rollback";
		log.info(testName+": started");
		
		// prepare test context
		setupByteman(testName);
		itemInventoryHelper.assureRemoveAll();
		
		// setup expectations
		isExceptionExpected = true;

		// prepare test data
		Item itemToAdd = JeeMigrationExampleFixture.create_Item();
		String keyToAdd = JeeMigrationExampleFixture.create_ItemKey(itemToAdd);
		
		try {
			// execute test action
			runTest_ItemStore_AddAsItem(keyToAdd, itemToAdd);
			fail("Exception should have been thrown");

		} catch (Exception e) {
			Assert.exception(e, org.aries.ApplicationFailure.class);
			Assert.exception(e, "error23");

		// validate persistent state
		Collection<Item> itemList = itemInventoryHelper.getAllItemStore();
		Assert.isEmpty(itemList, "No ItemStore records should exist");

		} finally {
		// cleanup test context
		tearDownByteman(testName);
		}
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 24)
	@BytemanRule(name = "rule24", 
			targetClass = "ItemInventoryRepositoryImpl", 
			targetMethod = "addItemStoreRecord", 
			targetLocation = "AT EXIT", 
			action = "throw new org.aries.ApplicationFailure(\"error24\")")
	public void runTest_ItemStore_AddAsItem_repository_exception_rollback() throws Exception {
		String testName = "runTest_ItemStore_AddAsItem_repository_exception_rollback";
		log.info(testName+": started");
		
		// prepare test context
		setupByteman(testName);
		itemInventoryHelper.assureRemoveAll();
		
		// setup expectations
		isExceptionExpected = true;

		// prepare test data
		Item itemToAdd = JeeMigrationExampleFixture.create_Item();
		String keyToAdd = JeeMigrationExampleFixture.create_ItemKey(itemToAdd);
		
		try {
			// execute test action
			runTest_ItemStore_AddAsItem(keyToAdd, itemToAdd);
			fail("Exception should have been thrown");

		} catch (Exception e) {
			Assert.exception(e, org.aries.ApplicationFailure.class);
			Assert.exception(e, "error24");

		// validate persistent state
		Collection<Item> itemList = itemInventoryHelper.getAllItemStore();
		Assert.isEmpty(itemList, "No ItemStore records should exist");

		} finally {
		// cleanup test context
		tearDownByteman(testName);
		}
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	public void runTest_ItemStore_AddAsItem(final String key, final Item item) throws Exception {
		runAction(new Runnable() {
			public void run() {
				itemInventoryManager.addToItemStore(key, item);
			}
		});
	}
	
	@Test
	//@Ignore
	@InSequence(value = 25)
	public void runTest_ItemStore_AddAsList_success() throws Exception {
		String testName = "runTest_ItemStore_AddAsList_success";
		log.info(testName+": started");
		
		// prepare test context
		itemInventoryHelper.assureRemoveAll();

		// prepare test data
		Map<String, Item> itemMapToAdd = JeeMigrationExampleFixture.createMap_Item();
		
		// execute test action
		runTest_ItemStore_AddAsList(itemMapToAdd);

		// validate persistent state
		Map<String, Item> itemMap = itemInventoryHelper.getAllItemStoreAsMap();
		JeeMigrationExampleFixture.assertSameItem(itemMapToAdd, itemMap);
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 26)
	public void runTest_ItemStore_AddAsList_tx_commit() throws Exception {
		String testName = "runTest_ItemStore_AddAsList_tx_commit";
		log.info(testName+": started");
		
		// prepare test context
		itemInventoryHelper.assureRemoveAll();
		
		// begin transaction
		beginJTATransaction();

		// prepare test data
		Map<String, Item> itemMapToAdd = JeeMigrationExampleFixture.createMap_Item();
		
		// execute test action
		runTest_ItemStore_AddAsList(itemMapToAdd);
		
		// commit transaction
		commitJTATransaction();

		// validate persistent state
		Map<String, Item> itemMap = itemInventoryHelper.getAllItemStoreAsMap();
		JeeMigrationExampleFixture.assertSameItem(itemMapToAdd, itemMap);
		clearTransaction();
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 27)
	public void runTest_ItemStore_AddAsList_tx_rollback() throws Exception {
		String testName = "runTest_ItemStore_AddAsList_tx_rollback";
		log.info(testName+": started");
		
		// prepare test context
		itemInventoryHelper.assureRemoveAll();
		
		// begin transaction
		beginJTATransaction();

		// prepare test data
		Map<String, Item> itemMapToAdd = JeeMigrationExampleFixture.createMap_Item();
		
		// execute test action
		runTest_ItemStore_AddAsList(itemMapToAdd);
		
		// rollback transaction
		rollbackJTATransaction();

		// validate persistent state
		Collection<Item> itemList = itemInventoryHelper.getAllItemStore();
		Assert.isEmpty(itemList, "No ItemStore records should exist");
		clearTransaction();
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 28)
	public void runTest_ItemStore_AddAsList_utx_commit() throws Exception {
		String testName = "runTest_ItemStore_AddAsList_utx_commit";
		log.info(testName+": started");
		
		// prepare test context
		itemInventoryHelper.assureRemoveAll();
		
		// begin transaction
		beginUserTransaction();

		// prepare test data
		Map<String, Item> itemMapToAdd = JeeMigrationExampleFixture.createMap_Item();
		
		// execute test action
		runTest_ItemStore_AddAsList(itemMapToAdd);
		
		// commit transaction
		commitUserTransaction();

		// validate persistent state
		Map<String, Item> itemMap = itemInventoryHelper.getAllItemStoreAsMap();
		JeeMigrationExampleFixture.assertSameItem(itemMapToAdd, itemMap);
		clearTransaction();
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 29)
	public void runTest_ItemStore_AddAsList_utx_rollback() throws Exception {
		String testName = "runTest_ItemStore_AddAsList_utx_rollback";
		log.info(testName+": started");
		
		// prepare test context
		itemInventoryHelper.assureRemoveAll();
		
		// begin transaction
		beginUserTransaction();

		// prepare test data
		Map<String, Item> itemMapToAdd = JeeMigrationExampleFixture.createMap_Item();
		
		// execute test action
		runTest_ItemStore_AddAsList(itemMapToAdd);
		
		// rollback transaction
		rollbackUserTransaction();

		// validate persistent state
		Collection<Item> itemList = itemInventoryHelper.getAllItemStore();
		Assert.isEmpty(itemList, "No ItemStore records should exist");
		clearTransaction();
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 30)
	@BytemanRule(name = "rule30", 
			targetClass = "ItemInventoryManager", 
			targetMethod = "addToItemStore", 
			targetLocation = "AT EXIT", 
			action = "throw new org.aries.ApplicationFailure(\"error30\")")
	public void runTest_ItemStore_AddAsList_manager_exception_rollback() throws Exception {
		String testName = "runTest_ItemStore_AddAsList_manager_exception_rollback";
		log.info(testName+": started");
		
		// prepare test context
		setupByteman(testName);
		itemInventoryHelper.assureRemoveAll();
		
		// setup expectations
		isExceptionExpected = true;

		// prepare test data
		Map<String, Item> itemMapToAdd = JeeMigrationExampleFixture.createMap_Item();
		
		try {
			// execute test action
			runTest_ItemStore_AddAsList(itemMapToAdd);
			fail("Exception should have been thrown");

		} catch (Exception e) {
			Assert.exception(e, org.aries.ApplicationFailure.class);
			Assert.exception(e, "error30");

		// validate persistent state
		Collection<Item> itemList = itemInventoryHelper.getAllItemStore();
		Assert.isEmpty(itemList, "No ItemStore records should exist");

		} finally {
		// cleanup test context
		tearDownByteman(testName);
		}
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 31)
	@BytemanRule(name = "rule31", 
			targetClass = "ItemInventory", 
			targetMethod = "addToItemStore", 
			targetLocation = "AT EXIT", 
			action = "throw new org.aries.ApplicationFailure(\"error31\")")
	public void runTest_ItemStore_AddAsList_interface_exception_rollback() throws Exception {
		String testName = "runTest_ItemStore_AddAsList_interface_exception_rollback";
		log.info(testName+": started");
		
		// prepare test context
		setupByteman(testName);
		itemInventoryHelper.assureRemoveAll();
		
		// setup expectations
		isExceptionExpected = true;

		// prepare test data
		Map<String, Item> itemMapToAdd = JeeMigrationExampleFixture.createMap_Item();
		
		try {
			// execute test action
			runTest_ItemStore_AddAsList(itemMapToAdd);
			fail("Exception should have been thrown");

		} catch (Exception e) {
			Assert.exception(e, org.aries.ApplicationFailure.class);
			Assert.exception(e, "error31");

		// validate persistent state
		Collection<Item> itemList = itemInventoryHelper.getAllItemStore();
		Assert.isEmpty(itemList, "No ItemStore records should exist");

		} finally {
		// cleanup test context
		tearDownByteman(testName);
		}
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 32)
	@BytemanRule(name = "rule32", 
			targetClass = "ItemInventoryRepositoryImpl", 
			targetMethod = "addItemStoreRecords", 
			targetLocation = "AT EXIT", 
			action = "throw new org.aries.ApplicationFailure(\"error32\")")
	public void runTest_ItemStore_AddAsList_repository_exception_rollback() throws Exception {
		String testName = "runTest_ItemStore_AddAsList_repository_exception_rollback";
		log.info(testName+": started");
		
		// prepare test context
		setupByteman(testName);
		itemInventoryHelper.assureRemoveAll();
		
		// setup expectations
		isExceptionExpected = true;

		// prepare test data
		Map<String, Item> itemMapToAdd = JeeMigrationExampleFixture.createMap_Item();
		
		try {
			// execute test action
			runTest_ItemStore_AddAsList(itemMapToAdd);
			fail("Exception should have been thrown");

		} catch (Exception e) {
			Assert.exception(e, org.aries.ApplicationFailure.class);
			Assert.exception(e, "error32");

		// validate persistent state
		Collection<Item> itemList = itemInventoryHelper.getAllItemStore();
		Assert.isEmpty(itemList, "No ItemStore records should exist");

		} finally {
		// cleanup test context
		tearDownByteman(testName);
		}
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	public void runTest_ItemStore_AddAsList(final Map<String, Item> itemMap) throws Exception {
		runAction(new Runnable() {
			public void run() {
				itemInventoryManager.addToItemStore(itemMap);
			}
		});
	}
	
	@Test
	//@Ignore
	@InSequence(value = 33)
	public void runTest_ItemStore_RemoveAll_success() throws Exception {
		String testName = "runTest_ItemStore_RemoveAll_success";
		log.info(testName+": started");
		
		// prepare test context
		itemInventoryHelper.assureRemoveAll();
		
		// prepare test data
		int itemCount = 4;
		Map<String, Item> itemMapToAdd = JeeMigrationExampleFixture.createMap_Item(itemCount);
		itemInventoryHelper.addItemStore(itemMapToAdd);
		
		// validate persistent state
		Map<String, Item> itemMap = itemInventoryHelper.getAllItemStoreAsMap();
		Assert.equals(itemCount, itemMap.size(), "ItemStore record count should be correct");
		JeeMigrationExampleFixture.assertSameItem(itemMapToAdd, itemMap, "ItemStore records should be equal");

		// execute action
		runTest_ItemStore_RemoveAll();

		// validate persistent state
		itemMap = itemInventoryHelper.getAllItemStoreAsMap();
		Assert.isEmpty(itemMap, "No ItemStore records should exist");	
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 34)
	public void runTest_ItemStore_RemoveAll_tx_commit() throws Exception {
		String testName = "runTest_ItemStore_RemoveAll_tx_commit";
		log.info(testName+": started");
		
		// prepare test context
		itemInventoryHelper.assureRemoveAll();
		
		// begin transaction
		beginJTATransaction();
		
		// prepare test data
		int itemCount = 4;
		Map<String, Item> itemMapToAdd = JeeMigrationExampleFixture.createMap_Item(itemCount);
		itemInventoryHelper.addItemStore(itemMapToAdd);
		
		// validate persistent state
		Map<String, Item> itemMap = itemInventoryHelper.getAllItemStoreAsMapIC();
		Assert.equals(itemCount, itemMap.size(), "ItemStore record count should be correct");
		JeeMigrationExampleFixture.assertSameItem(itemMapToAdd, itemMap, "ItemStore records should be equal");

		// execute action
		runTest_ItemStore_RemoveAll();

		// commit transaction
		commitJTATransaction();
		
		// validate persistent state
		itemMap = itemInventoryHelper.getAllItemStoreAsMap();
		Assert.isEmpty(itemMap, "No ItemStore records should exist");
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 35)
	public void runTest_ItemStore_RemoveAll_tx_rollback() throws Exception {
		String testName = "runTest_ItemStore_RemoveAll_tx_rollback";
		log.info(testName+": started");
		
		// prepare test context
		itemInventoryHelper.assureRemoveAll();
		
		// begin transaction
		beginJTATransaction();
		
		// prepare test data
		int itemCount = 4;
		Map<String, Item> itemMapToAdd = JeeMigrationExampleFixture.createMap_Item(itemCount);
		itemInventoryHelper.addItemStore(itemMapToAdd);
		
		// validate persistent state
		Map<String, Item> itemMap = itemInventoryHelper.getAllItemStoreAsMapIC();
		Assert.equals(itemCount, itemMap.size(), "ItemStore record count should be correct");
		JeeMigrationExampleFixture.assertSameItem(itemMapToAdd, itemMap, "ItemStore records should be equal");

		// execute action
		runTest_ItemStore_RemoveAll();
		
		// rollback transaction
		rollbackJTATransaction();

		// validate persistent state
		itemMap = itemInventoryHelper.getAllItemStoreAsMap();
		Assert.equals(itemCount, itemMap.size(), "ItemStore record count should be correct");
		JeeMigrationExampleFixture.assertSameItem(itemMapToAdd, itemMap, "ItemStore records should be equal");

		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 36)
	public void runTest_ItemStore_RemoveAll_utx_commit() throws Exception {
		String testName = "runTest_ItemStore_RemoveAll_utx_commit";
		log.info(testName+": started");
		
		// prepare test context
		itemInventoryHelper.assureRemoveAll();
		
		// begin transaction
		beginUserTransaction();
		
		// prepare test data
		int itemCount = 4;
		Map<String, Item> itemMapToAdd = JeeMigrationExampleFixture.createMap_Item(itemCount);
		itemInventoryHelper.addItemStore(itemMapToAdd);
		
		// validate persistent state
		Map<String, Item> itemMap = itemInventoryHelper.getAllItemStoreAsMapIC();
		Assert.equals(itemCount, itemMap.size(), "ItemStore record count should be correct");
		JeeMigrationExampleFixture.assertSameItem(itemMapToAdd, itemMap, "ItemStore records should be equal");

		// execute action
		runTest_ItemStore_RemoveAll();

		// commit transaction
		commitUserTransaction();
		
		// validate persistent state
		itemMap = itemInventoryHelper.getAllItemStoreAsMap();
		Assert.isEmpty(itemMap, "No ItemStore records should exist");
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 37)
	public void runTest_ItemStore_RemoveAll_utx_rollback() throws Exception {
		String testName = "runTest_ItemStore_RemoveAll_utx_rollback";
		log.info(testName+": started");
		
		// prepare test context
		itemInventoryHelper.assureRemoveAll();
		
		// begin transaction
		beginUserTransaction();
		
		// prepare test data
		int itemCount = 4;
		Map<String, Item> itemMapToAdd = JeeMigrationExampleFixture.createMap_Item(itemCount);
		itemInventoryHelper.addItemStore(itemMapToAdd);
		
		// validate persistent state
		Map<String, Item> itemMap = itemInventoryHelper.getAllItemStoreAsMapIC();
		Assert.equals(itemCount, itemMap.size(), "ItemStore record count should be correct");
		JeeMigrationExampleFixture.assertSameItem(itemMapToAdd, itemMap, "ItemStore records should be equal");

		// execute action
		runTest_ItemStore_RemoveAll();
		
		// rollback transaction
		rollbackUserTransaction();

		// validate persistent state
		itemMap = itemInventoryHelper.getAllItemStoreAsMap();
		Assert.equals(itemCount, itemMap.size(), "ItemStore record count should be correct");
		JeeMigrationExampleFixture.assertSameItem(itemMapToAdd, itemMap, "ItemStore records should be equal");
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 38)
	@BytemanRule(name = "rule38", 
			targetClass = "ItemInventoryManager", 
			targetMethod = "removeAllItemStore", 
			targetLocation = "AT EXIT", 
			action = "throw new org.aries.ApplicationFailure(\"error38\")")
	public void runTest_ItemStore_RemoveAll_manager_exception_rollback() throws Exception {
		String testName = "runTest_ItemStore_RemoveAll_manager_exception_rollback";
		log.info(testName+": started");
		
		// prepare test context
		setupByteman(testName);
		itemInventoryHelper.assureRemoveAll();
		
		// setup expectations
		isExceptionExpected = true;
		
		// prepare test data
		int itemCount = 4;
		Map<String, Item> itemMapToAdd = JeeMigrationExampleFixture.createMap_Item(itemCount);
		itemInventoryHelper.addItemStore(itemMapToAdd);
		
		// validate persistent state
		Map<String, Item> itemMap = itemInventoryHelper.getAllItemStoreAsMapIC();
		Assert.equals(itemCount, itemMap.size(), "ItemStore record count should be correct");
		JeeMigrationExampleFixture.assertSameItem(itemMapToAdd, itemMap, "ItemStore records should be equal");

		try {
			// execute action
			runTest_ItemStore_RemoveAll();
			fail("Exception should have been thrown");

		} catch (Exception e) {
			Assert.exception(e, org.aries.ApplicationFailure.class);
			Assert.exception(e, "error38");

		} finally {
			// cleanup test context
			tearDownByteman(testName);
		}
		
		// validate persistent state
		itemMap = itemInventoryHelper.getAllItemStoreAsMap();
		Assert.equals(itemCount, itemMap.size(), "ItemStore record count should be correct");
		JeeMigrationExampleFixture.assertSameItem(itemMapToAdd, itemMap, "ItemStore records should be equal");
				
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 39)
	@BytemanRule(name = "rule39", 
			targetClass = "ItemInventory", 
			targetMethod = "removeAllItemStore", 
			targetLocation = "AT EXIT", 
			action = "throw new org.aries.ApplicationFailure(\"error39\")")
	public void runTest_ItemStore_RemoveAll_interface_exception_rollback() throws Exception {
		String testName = "runTest_ItemStore_RemoveAll_interface_exception_rollback";
		log.info(testName+": started");
		
		// prepare test context
		setupByteman(testName);
		itemInventoryHelper.assureRemoveAll();
		
		// setup expectations
		isExceptionExpected = true;
		
		// prepare test data
		int itemCount = 4;
		Map<String, Item> itemMapToAdd = JeeMigrationExampleFixture.createMap_Item(itemCount);
		itemInventoryHelper.addItemStore(itemMapToAdd);
		
		// validate persistent state
		Map<String, Item> itemMap = itemInventoryHelper.getAllItemStoreAsMapIC();
		Assert.equals(itemCount, itemMap.size(), "ItemStore record count should be correct");
		JeeMigrationExampleFixture.assertSameItem(itemMapToAdd, itemMap, "ItemStore records should be equal");

		try {
			// execute action
			runTest_ItemStore_RemoveAll();
			fail("Exception should have been thrown");

		} catch (Exception e) {
			Assert.exception(e, org.aries.ApplicationFailure.class);
			Assert.exception(e, "error39");

		} finally {
			// cleanup test context
			tearDownByteman(testName);
		}
		
		// validate persistent state
		itemMap = itemInventoryHelper.getAllItemStoreAsMap();
		Assert.equals(itemCount, itemMap.size(), "ItemStore record count should be correct");
		JeeMigrationExampleFixture.assertSameItem(itemMapToAdd, itemMap, "ItemStore records should be equal");
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 40)
	@BytemanRule(name = "rule40", 
			targetClass = "ItemInventoryRepositoryImpl", 
			targetMethod = "removeAllItemStoreRecords", 
			targetLocation = "AT EXIT", 
			action = "throw new org.aries.ApplicationFailure(\"error40\")")
	public void runTest_ItemStore_RemoveAll_repository_exception_rollback() throws Exception {
		String testName = "runTest_ItemStore_RemoveAll_repository_exception_rollback";
		log.info(testName+": started");
		
		// prepare test context
		setupByteman(testName);
		itemInventoryHelper.assureRemoveAll();
		
		// setup expectations
		isExceptionExpected = true;
		
		// prepare test data
		int itemCount = 4;
		Map<String, Item> itemMapToAdd = JeeMigrationExampleFixture.createMap_Item(itemCount);
		itemInventoryHelper.addItemStore(itemMapToAdd);
		
		// validate persistent state
		Map<String, Item> itemMap = itemInventoryHelper.getAllItemStoreAsMapIC();
		Assert.equals(itemCount, itemMap.size(), "ItemStore record count should be correct");
		JeeMigrationExampleFixture.assertSameItem(itemMapToAdd, itemMap, "ItemStore records should be equal");

		try {
			// execute action
			runTest_ItemStore_RemoveAll();
			fail("Exception should have been thrown");

		} catch (Exception e) {
			Assert.exception(e, org.aries.ApplicationFailure.class);
			Assert.exception(e, "error40");

		} finally {
			// cleanup test context
			tearDownByteman(testName);
		}
		
		// validate persistent state
		itemMap = itemInventoryHelper.getAllItemStoreAsMap();
		Assert.equals(itemCount, itemMap.size(), "ItemStore record count should be correct");
		JeeMigrationExampleFixture.assertSameItem(itemMapToAdd, itemMap, "ItemStore records should be equal");
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	public void runTest_ItemStore_RemoveAll() throws Exception {
		runAction(new Runnable() {
			public void run() {
				itemInventoryManager.removeAllItemStore();
			}
		});
	}
	
	@Test
	//@Ignore
	@InSequence(value = 41)
	public void runTest_ItemStore_RemoveAsItem_success() throws Exception {
		String testName = "runTest_ItemStore_RemoveAsItem_success";
		log.info(testName+": started");
		
		// prepare test context
		itemInventoryHelper.assureRemoveAll();
		Long id = itemInventoryHelper.addItemStore();

		// prepare test data
		Item itemToRemove = itemInventoryHelper.getItemStoreById(id);
		Assert.notNull(itemToRemove, "The ItemStore record should exist");
		String itemKey = itemInventoryHelper.createItemStoreKey(itemToRemove);
		
		// execute test action
		runTest_ItemStore_RemoveAsItem(itemKey);

		// validate persistent state
		Item itemRemoved = itemInventoryHelper.getItemStoreById(id);
		Assert.isNull(itemRemoved, "The ItemStore record should be removed");
		Collection<Item> itemListRemaining = itemInventoryHelper.getAllItemStore();
		Assert.isEmpty(itemListRemaining, "No ItemStore records should exist");
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 42)
	public void runTest_ItemStore_RemoveAsItem_tx_commit() throws Exception {
		String testName = "runTest_ItemStore_RemoveAsItem_tx_commit";
		log.info(testName+": started");
		
		// prepare test context
		itemInventoryHelper.assureRemoveAll();
		Long id = itemInventoryHelper.addItemStore();
		
		// begin transaction
		beginJTATransaction();

		// prepare test data
		Item itemToRemove = itemInventoryHelper.getItemStoreById(id);
		Assert.notNull(itemToRemove, "The ItemStore record should exist");
		String itemKey = itemInventoryHelper.createItemStoreKey(itemToRemove);
		
		// execute test action
		runTest_ItemStore_RemoveAsItem(itemKey);
		
		// commit transaction
		commitJTATransaction();

		// validate persistent state
		Item itemRemoved = itemInventoryHelper.getItemStoreById(id);
		Assert.isNull(itemRemoved, "The ItemStore record should be removed");
		Collection<Item> itemListRemaining = itemInventoryHelper.getAllItemStore();
		Assert.isEmpty(itemListRemaining, "No ItemStore records should exist");
		clearTransaction();
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 43)
	public void runTest_ItemStore_RemoveAsItem_tx_rollback() throws Exception {
		String testName = "runTest_ItemStore_RemoveAsItem_tx_rollback";
		log.info(testName+": started");
		
		// prepare test context
		itemInventoryHelper.assureRemoveAll();
		Long id = itemInventoryHelper.addItemStore();
		
		// begin transaction
		beginJTATransaction();

		// prepare test data
		Item itemToRemove = itemInventoryHelper.getItemStoreById(id);
		Assert.notNull(itemToRemove, "The ItemStore record should exist");
		String itemKey = itemInventoryHelper.createItemStoreKey(itemToRemove);

		// execute test action
		runTest_ItemStore_RemoveAsItem(itemKey);
		
		// rollback transaction
		rollbackJTATransaction();

		// validate persistent state
		Item itemNotRemoved = itemInventoryHelper.getItemStoreById(id);
		Assert.notNull(itemNotRemoved, "The ItemStore record should still exist");
		Collection<Item> itemListRemaining = itemInventoryHelper.getAllItemStore();
		Assert.equals(1, itemListRemaining.size(), "Only 1 ItemStore record should exist");
		clearTransaction();
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 44)
	public void runTest_ItemStore_RemoveAsItem_utx_commit() throws Exception {
		String testName = "runTest_ItemStore_RemoveAsItem_utx_commit";
		log.info(testName+": started");
		
		// prepare test context
		itemInventoryHelper.assureRemoveAll();
		Long id = itemInventoryHelper.addItemStore();
		
		// begin transaction
		beginUserTransaction();

		// prepare test data
		Item itemToRemove = itemInventoryHelper.getItemStoreById(id);
		Assert.notNull(itemToRemove, "The ItemStore record should exist");
		String itemKey = itemInventoryHelper.createItemStoreKey(itemToRemove);

		// execute test action
		runTest_ItemStore_RemoveAsItem(itemKey);
		
		// commit transaction
		commitUserTransaction();

		// validate persistent state
		Item itemRemoved = itemInventoryHelper.getItemStoreById(id);
		Assert.isNull(itemRemoved, "The ItemStore record should be removed");
		Collection<Item> itemListRemaining = itemInventoryHelper.getAllItemStore();
		Assert.isEmpty(itemListRemaining, "No ItemStore records should exist");
		clearTransaction();
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 45)
	public void runTest_ItemStore_RemoveAsItem_utx_rollback() throws Exception {
		String testName = "runTest_ItemStore_RemoveAsItem_utx_rollback";
		log.info(testName+": started");
		
		// prepare test context
		itemInventoryHelper.assureRemoveAll();
		Long id = itemInventoryHelper.addItemStore();
		
		// begin transaction
		beginUserTransaction();

		// prepare test data
		Item itemToRemove = itemInventoryHelper.getItemStoreById(id);
		Assert.notNull(itemToRemove, "The ItemStore record should exist");
		String itemKey = itemInventoryHelper.createItemStoreKey(itemToRemove);

		// execute test action
		runTest_ItemStore_RemoveAsItem(itemKey);
		
		// rollback transaction
		rollbackUserTransaction();

		// validate persistent state
		Item itemNotRemoved = itemInventoryHelper.getItemStoreById(id);
		Assert.notNull(itemNotRemoved, "The ItemStore record should still exist");
		Collection<Item> itemListRemaining = itemInventoryHelper.getAllItemStore();
		Assert.equals(1, itemListRemaining.size(), "Only 1 ItemStore record should exist");
		clearTransaction();
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 46)
	@BytemanRule(name = "rule46", 
			targetClass = "ItemInventoryManager", 
			targetMethod = "removeFromItemStore", 
			targetLocation = "AT EXIT", 
			action = "throw new org.aries.ApplicationFailure(\"error46\")")
	public void runTest_ItemStore_RemoveAsItem_manager_exception_rollback() throws Exception {
		String testName = "runTest_ItemStore_RemoveAsItem_manager_exception_rollback";
		log.info(testName+": started");
		
		// prepare test context
		setupByteman(testName);
		itemInventoryHelper.assureRemoveAll();
		Long id = itemInventoryHelper.addItemStore();
		
		// setup expectations
		isExceptionExpected = true;

		// prepare test data
		Item itemToRemove = itemInventoryHelper.getItemStoreById(id);
		Assert.notNull(itemToRemove, "The ItemStore record should exist");
		String itemKey = itemInventoryHelper.createItemStoreKey(itemToRemove);

		try {
			// execute test action
			runTest_ItemStore_RemoveAsItem(itemKey);
			fail("Exception should have been thrown");

		} catch (Exception e) {
			Assert.exception(e, org.aries.ApplicationFailure.class);
			Assert.exception(e, "error46");

			// validate persistent state
			Item itemNotRemoved = itemInventoryHelper.getItemStoreById(id);
			Assert.notNull(itemNotRemoved, "The ItemStore record should still exist");
			Collection<Item> itemList = itemInventoryHelper.getAllItemStore();
			Assert.equals(1, itemList.size(), "Only 1 ItemStore record should exist");

		} finally {
			// cleanup test context
			tearDownByteman(testName);
		}
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 47)
	@BytemanRule(name = "rule47", 
			targetClass = "ItemInventory", 
			targetMethod = "removeFromItemStore", 
			targetLocation = "AT EXIT", 
			action = "throw new org.aries.ApplicationFailure(\"error47\")")
	public void runTest_ItemStore_RemoveAsItem_interface_exception_rollback() throws Exception {
		String testName = "runTest_ItemStore_RemoveAsItem_interface_exception_rollback";
		log.info(testName+": started");
		
		// prepare test context
		setupByteman(testName);
		itemInventoryHelper.assureRemoveAll();
		Long id = itemInventoryHelper.addItemStore();
		
		// setup expectations
		isExceptionExpected = true;

		// prepare test data
		Item itemToRemove = itemInventoryHelper.getItemStoreById(id);
		Assert.notNull(itemToRemove, "The ItemStore record should exist");
		String itemKey = itemInventoryHelper.createItemStoreKey(itemToRemove);

		try {
			// execute test action
			runTest_ItemStore_RemoveAsItem(itemKey);
			fail("Exception should have been thrown");

		} catch (Exception e) {
			Assert.exception(e, org.aries.ApplicationFailure.class);
			Assert.exception(e, "error47");

			// validate persistent state
			Item itemNotRemoved = itemInventoryHelper.getItemStoreById(id);
			Assert.notNull(itemNotRemoved, "The ItemStore record should still exist");
			Collection<Item> itemList = itemInventoryHelper.getAllItemStore();
			Assert.equals(1, itemList.size(), "Only 1 ItemStore record should exist");
			
		} finally {
			// cleanup test context
			tearDownByteman(testName);
		}
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 48)
	@BytemanRule(name = "rule48", 
			targetClass = "ItemInventoryRepositoryImpl", 
			targetMethod = "removeItemStoreRecord", 
			targetLocation = "AT EXIT", 
			action = "throw new org.aries.ApplicationFailure(\"error48\")")
	public void runTest_ItemStore_RemoveAsItem_repository_exception_rollback() throws Exception {
		String testName = "runTest_ItemStore_RemoveAsItem_repository_exception_rollback";
		log.info(testName+": started");
		
		// prepare test context
		setupByteman(testName);
		itemInventoryHelper.assureRemoveAll();
		Long id = itemInventoryHelper.addItemStore();
		
		// setup expectations
		isExceptionExpected = true;

		// prepare test data
		Item itemToRemove = itemInventoryHelper.getItemStoreById(id);
		Assert.notNull(itemToRemove, "The ItemStore record should exist");
		String itemKey = itemInventoryHelper.createItemStoreKey(itemToRemove);

		try {
			// execute test action
			runTest_ItemStore_RemoveAsItem(itemKey);
			fail("Exception should have been thrown");

		} catch (Exception e) {
			Assert.exception(e, org.aries.ApplicationFailure.class);
			Assert.exception(e, "error48");

		// validate persistent state
		Item itemNotRemoved = itemInventoryHelper.getItemStoreById(id);
		Assert.notNull(itemNotRemoved, "The ItemStore record should still exist");
		Collection<Item> itemList = itemInventoryHelper.getAllItemStore();
		Assert.equals(1, itemList.size(), "Only 1 ItemStore record should exist");

		} finally {
		// cleanup test context
		tearDownByteman(testName);
		}
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	public void runTest_ItemStore_RemoveAsItem(final String key) throws Exception {
		runAction(new Runnable() {
			public void run() {
				itemInventoryManager.removeFromItemStore(key);
			}
		});
	}
	
	@Test
	//@Ignore
	@InSequence(value = 49)
	public void runTest_ItemStore_RemoveAsList_success() throws Exception {
		String testName = "runTest_ItemStore_RemoveAsList_success";
		log.info(testName+": started");
		
		// prepare test context
		int itemStoreCount = 2;
		itemInventoryHelper.assureRemoveAll();
		itemInventoryHelper.addItemStore(itemStoreCount);

		// prepare test data
		Map<String, Item> itemMapToRemove = itemInventoryHelper.getAllItemStoreAsMap();
		Assert.equals(itemStoreCount, itemMapToRemove.size(), "The ItemStore records should exist");
		
		// execute test action
		runTest_ItemStore_RemoveAsList(itemMapToRemove);

		// validate persistent state
		Collection<Item> itemListRemaining = itemInventoryHelper.getAllItemStore();
		Assert.isEmpty(itemListRemaining, "No ItemStore records should exist");
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 50)
	public void runTest_ItemStore_RemoveAsList_tx_commit() throws Exception {
		String testName = "runTest_ItemStore_RemoveAsList_tx_commit";
		log.info(testName+": started");
		
		// prepare test context
		int itemStoreCount = 2;
		itemInventoryHelper.assureRemoveAll();
		itemInventoryHelper.addItemStore(itemStoreCount);
		
		// begin transaction
		beginJTATransaction();

		// prepare test data
		Map<String, Item> itemMapToRemove = itemInventoryHelper.getAllItemStoreAsMap();
		Assert.equals(itemStoreCount, itemMapToRemove.size(), "The ItemStore records should exist");
		
		// execute test action
		runTest_ItemStore_RemoveAsList(itemMapToRemove);
		
		// commit transaction
		commitJTATransaction();

		// validate persistent state
		Collection<Item> itemListRemaining = itemInventoryHelper.getAllItemStore();
		Assert.isEmpty(itemListRemaining, "No ItemStore records should exist");
		clearTransaction();
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 51)
	public void runTest_ItemStore_RemoveAsList_tx_rollback() throws Exception {
		String testName = "runTest_ItemStore_RemoveAsList_tx_rollback";
		log.info(testName+": started");
		
		// prepare test context
		int itemStoreCount = 2;
		itemInventoryHelper.assureRemoveAll();
		itemInventoryHelper.addItemStore(itemStoreCount);
		
		// begin transaction
		beginJTATransaction();

		// prepare test data
		Map<String, Item> itemMapToRemove = itemInventoryHelper.getAllItemStoreAsMap();
		Assert.equals(itemStoreCount, itemMapToRemove.size(), "The ItemStore records should exist");
		
		// execute test action
		runTest_ItemStore_RemoveAsList(itemMapToRemove);
		
		// rollback transaction
		rollbackJTATransaction();

		// validate persistent state
		Map<String, Item> itemMapRemaining = itemInventoryHelper.getAllItemStoreAsMap();
		Assert.equals(itemStoreCount, itemMapRemaining.size(), "The ItemStore records should still exist");
		JeeMigrationExampleFixture.assertSameItem(itemMapRemaining, itemMapToRemove);
		clearTransaction();
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 52)
	public void runTest_ItemStore_RemoveAsList_utx_commit() throws Exception {
		String testName = "runTest_ItemStore_RemoveAsList_utx_commit";
		log.info(testName+": started");
		
		// prepare test context
		int itemStoreCount = 2;
		itemInventoryHelper.assureRemoveAll();
		itemInventoryHelper.addItemStore(itemStoreCount);
		
		// begin transaction
		beginUserTransaction();

		// prepare test data
		Map<String, Item> itemMapToRemove = itemInventoryHelper.getAllItemStoreAsMap();
		Assert.equals(itemStoreCount, itemMapToRemove.size(), "The ItemStore records should exist");
		
		// execute test action
		runTest_ItemStore_RemoveAsList(itemMapToRemove);
		
		// commit transaction
		commitUserTransaction();

		// validate persistent state
		Collection<Item> itemListRemaining = itemInventoryHelper.getAllItemStore();
		Assert.isEmpty(itemListRemaining, "No ItemStore records should exist");
		clearTransaction();
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 53)
	public void runTest_ItemStore_RemoveAsList_utx_rollback() throws Exception {
		String testName = "runTest_ItemStore_RemoveAsList_utx_rollback";
		log.info(testName+": started");
		
		// prepare test context
		int itemStoreCount = 2;
		itemInventoryHelper.assureRemoveAll();
		itemInventoryHelper.addItemStore(itemStoreCount);
		
		// begin transaction
		beginUserTransaction();

		// prepare test data
		Map<String, Item> itemMapToRemove = itemInventoryHelper.getAllItemStoreAsMap();
		Assert.equals(itemStoreCount, itemMapToRemove.size(), "The ItemStore records should exist");
		
		// execute test action
		runTest_ItemStore_RemoveAsList(itemMapToRemove);
		
		// rollback transaction
		rollbackUserTransaction();

		// validate persistent state
		Map<String, Item> itemMapRemaining = itemInventoryHelper.getAllItemStoreAsMap();
		Assert.equals(itemStoreCount, itemMapRemaining.size(), "The ItemStore records should still exist");
		JeeMigrationExampleFixture.assertSameItem(itemMapRemaining, itemMapToRemove);
		clearTransaction();
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 54)
	@BytemanRule(name = "rule54", 
			targetClass = "ItemInventoryManager", 
			targetMethod = "removeFromItemStore", 
			targetLocation = "AT EXIT", 
			action = "throw new org.aries.ApplicationFailure(\"error54\")")
	public void runTest_ItemStore_RemoveAsList_manager_exception_rollback() throws Exception {
		String testName = "runTest_ItemStore_RemoveAsList_manager_exception_rollback";
		log.info(testName+": started");
		
		// prepare test context
		setupByteman(testName);
		int itemStoreCount = 2;
		itemInventoryHelper.assureRemoveAll();
		itemInventoryHelper.addItemStore(itemStoreCount);
		
		// setup expectations
		isExceptionExpected = true;

		// prepare test data
		Map<String, Item> itemMapToRemove = itemInventoryHelper.getAllItemStoreAsMap();
		Assert.equals(itemStoreCount, itemMapToRemove.size(), "The ItemStore records should exist");
		
		try {
			// execute test action
			runTest_ItemStore_RemoveAsList(itemMapToRemove);
			fail("Exception should have been thrown");

		} catch (Exception e) {
			Assert.exception(e, org.aries.ApplicationFailure.class);
			Assert.exception(e, "error54");

			// validate persistent state
			Map<String, Item> itemMapRemaining = itemInventoryHelper.getAllItemStoreAsMap();
			Assert.equals(itemStoreCount, itemMapRemaining.size(), "The ItemStore records should still exist");
			JeeMigrationExampleFixture.assertSameItem(itemMapRemaining, itemMapToRemove);

		} finally {
			// cleanup test context
			tearDownByteman(testName);
		}
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 55)
	@BytemanRule(name = "rule55", 
			targetClass = "ItemInventory", 
			targetMethod = "removeFromItemStore", 
			targetLocation = "AT EXIT", 
			action = "throw new org.aries.ApplicationFailure(\"error55\")")
	public void runTest_ItemStore_RemoveAsList_interface_exception_rollback() throws Exception {
		String testName = "runTest_ItemStore_RemoveAsList_interface_exception_rollback";
		log.info(testName+": started");
		
		// prepare test context
		setupByteman(testName);
		int itemStoreCount = 2;
		itemInventoryHelper.assureRemoveAll();
		itemInventoryHelper.addItemStore(itemStoreCount);
		
		// setup expectations
		isExceptionExpected = true;

		// prepare test data
		Map<String, Item> itemMapToRemove = itemInventoryHelper.getAllItemStoreAsMap();
		Assert.equals(itemStoreCount, itemMapToRemove.size(), "The ItemStore records should exist");
		
		try {
			// execute test action
			runTest_ItemStore_RemoveAsList(itemMapToRemove);
			fail("Exception should have been thrown");

		} catch (Exception e) {
			Assert.exception(e, org.aries.ApplicationFailure.class);
			Assert.exception(e, "error55");

			// validate persistent state
			Map<String, Item> itemMapRemaining = itemInventoryHelper.getAllItemStoreAsMap();
			Assert.equals(itemStoreCount, itemMapRemaining.size(), "The ItemStore records should still exist");
			JeeMigrationExampleFixture.assertSameItem(itemMapRemaining, itemMapToRemove);

		} finally {
			// cleanup test context
			tearDownByteman(testName);
		}
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 56)
	@BytemanRule(name = "rule56", 
			targetClass = "ItemInventoryRepositoryImpl", 
			targetMethod = "removeItemStoreRecords", 
			targetLocation = "AT EXIT", 
			action = "throw new org.aries.ApplicationFailure(\"error56\")")
	public void runTest_ItemStore_RemoveAsList_repository_exception_rollback() throws Exception {
		String testName = "runTest_ItemStore_RemoveAsList_repository_exception_rollback";
		log.info(testName+": started");
		
		// prepare test context
		setupByteman(testName);
		int itemStoreCount = 2;
		itemInventoryHelper.assureRemoveAll();
		itemInventoryHelper.addItemStore(itemStoreCount);
		
		// setup expectations
		isExceptionExpected = true;

		// prepare test data
		Map<String, Item> itemMapToRemove = itemInventoryHelper.getAllItemStoreAsMap();
		Assert.equals(itemStoreCount, itemMapToRemove.size(), "The ItemStore records should exist");
		
		try {
			// execute test action
			runTest_ItemStore_RemoveAsList(itemMapToRemove);
			fail("Exception should have been thrown");

		} catch (Exception e) {
			Assert.exception(e, org.aries.ApplicationFailure.class);
			Assert.exception(e, "error56");

		// validate persistent state
		Map<String, Item> itemMapRemaining = itemInventoryHelper.getAllItemStoreAsMap();
		Assert.equals(itemStoreCount, itemMapRemaining.size(), "The ItemStore records should still exist");
		JeeMigrationExampleFixture.assertSameItem(itemMapRemaining, itemMapToRemove);

		} finally {
			// cleanup test context
			tearDownByteman(testName);
		}
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	public void runTest_ItemStore_RemoveAsList(final Map<String, Item> itemMap) throws Exception {
		runAction(new Runnable() {
			public void run() {
				itemInventoryManager.removeFromItemStore(itemMap);
			}
		});
	}
	
	public void runAction(Runnable runnable) throws Exception {
		try {
			runnable.run();
			if (isExceptionExpected) {
				if (isTransactional())
					clearTransaction();
				errorMessage = "Exception should have been thrown";
			}
			
		} catch (Exception e) {
			throw e;
		}
	}

	public <T> T runAction(Callable<T> callable) throws Exception {
		try {
			T object = callable.call();
			if (isExceptionExpected) {
				if (isTransactional())
					clearTransaction();
				errorMessage = "Exception should have been thrown";
			}
			return object;
			
		} catch (Exception e) {
			throw e;
		}
	}
	
}
