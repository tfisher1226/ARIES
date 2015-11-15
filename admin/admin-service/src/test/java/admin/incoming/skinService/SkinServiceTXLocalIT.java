package admin.incoming.skinService;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.aries.runtime.BeanContext;
import org.aries.transport.TransportType;
import org.aries.tx.AbstractTransactionalServiceIT;
import org.aries.validate.util.CheckpointManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import admin.Skin;
import admin.client.skinService.SkinService;
import admin.client.skinService.SkinServiceClient;
import admin.client.skinService.SkinServiceProxyForJAXWS;
import admin.util.AdminFixture;

import common.jmx.MBeanUtil;


@RunWith(MockitoJUnitRunner.class)
public class SkinServiceTXLocalIT extends AbstractTransactionalServiceIT {
	
	private admin.ObjectFactory mockAdminObjectFactory;
	
	
	@Before
	public void setUp() throws Exception {
		super.setUp();
		CheckpointManager.addConfiguration("admin-service-checks.xml");
		mockAdminObjectFactory = mock(admin.ObjectFactory.class);
		startService();
	}
	
	@After
	public void tearDown() throws Exception {
		MBeanUtil.unregisterMBeans();
		BeanContext.clear();
		super.tearDown();
		stopService();
	}
	
	public void startService() throws Exception {
		admin.incoming.skinService.SkinServiceLauncher.INSTANCE.initialize(hostName, httpPort);
	}
	
	public void stopService() throws Exception {
		admin.incoming.skinService.SkinServiceLauncher.INSTANCE.shutdown();
	}
	
	@Test
	public void testInvoke_Commit_addSkin() throws Exception {
		Skin skin = AdminFixture.createSkin();
		when(mockAdminObjectFactory.createSkin()).thenReturn(skin);
		
		//UserTransaction userTransaction = UserTransactionFactory.getUserTransaction();
		//userTransaction.begin();
		
		try {
			SkinServiceProxyForJAXWS proxy = new SkinServiceProxyForJAXWS(hostName, httpPort);
			proxy.setCorrelationId(correlationId);
			addProxyInstance(SkinService.ID, proxy, TransportType.HTTP);
			
			SkinServiceClient client = new SkinServiceClient();
			client.setTransportType(TransportType.HTTP);
			
			//client.addSkin(skin);
		
			//TODO verify conditions for commit
			//userTransaction.commit();
		
		} finally {
			//if (userTransaction.getTransactionId() != null)
			//	userTransaction.rollback();
		}
		
//		verify(mockBooksAvailableClient).send(booksAvailableMessage);
//		verify(mockBooksAvailableClient).setCorrelationId(correlationId);
//		verifyNoMoreInteractions(mockBooksAvailableClient);
		
		System.out.println();
	}
	
	@Test
	public void testInvoke_Commit_saveSkin() throws Exception {
		Skin skin = AdminFixture.createSkin();
		when(mockAdminObjectFactory.createSkin()).thenReturn(skin);
	}
	
	@Test
	public void testInvoke_Commit_deleteSkin() throws Exception {
		Skin skin = AdminFixture.createSkin();
		when(mockAdminObjectFactory.createSkin()).thenReturn(skin);
	}
	
}
