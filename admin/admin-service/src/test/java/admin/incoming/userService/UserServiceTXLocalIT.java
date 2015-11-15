package admin.incoming.userService;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.aries.runtime.BeanContext;
import org.aries.tx.AbstractTransactionalServiceIT;
import org.aries.validate.util.CheckpointManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import admin.User;
import admin.util.AdminFixture;
import common.jmx.MBeanUtil;


@RunWith(MockitoJUnitRunner.class)
public class UserServiceTXLocalIT extends AbstractTransactionalServiceIT {
	
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
		admin.incoming.userService.UserServiceLauncher.INSTANCE.initialize(hostName, httpPort);
	}
	
	public void stopService() throws Exception {
		admin.incoming.userService.UserServiceLauncher.INSTANCE.shutdown();
	}
	
	@Test
	public void testInvoke_Commit_addUser() throws Exception {
		User user = AdminFixture.createUser();
		when(mockAdminObjectFactory.createUser()).thenReturn(user);
	}
	
	@Test
	public void testInvoke_Commit_saveUser() throws Exception {
		User user = AdminFixture.createUser();
		when(mockAdminObjectFactory.createUser()).thenReturn(user);
	}
	
	@Test
	public void testInvoke_Commit_deleteUser() throws Exception {
		User user = AdminFixture.createUser();
		when(mockAdminObjectFactory.createUser()).thenReturn(user);
	}
	
}
