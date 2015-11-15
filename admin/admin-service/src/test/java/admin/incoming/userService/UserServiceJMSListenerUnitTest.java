package admin.incoming.userService;

import static org.mockito.Mockito.mock;

import org.aries.tx.AbstractListenerForJMSUnitTest;
import org.aries.util.FieldUtil;
import org.aries.validate.util.CheckpointManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;


@RunWith(MockitoJUnitRunner.class)
public class UserServiceJMSListenerUnitTest extends AbstractListenerForJMSUnitTest {
	
	private UserServiceListenerForJMS fixture;
	
	private UserServiceInterceptor mockUserServiceInterceptor;
	

	@Override
	public String getServiceId() {
		return UserService.ID;
	}
	
	@Override
	public String getDomain() {
		return "admin";
	}
	
	@Override
	public String getModule() {
		return "admin-service";
	}
	
	@Before
	public void setUp() throws Exception {
		mockUserServiceInterceptor = mock(UserServiceInterceptor.class);
		CheckpointManager.addConfiguration("admin-service-checks.xml");
		CheckpointManager.setJAXBSessionCache(getJAXBSessionCache());
		super.setUp();
	}
	
	@After
	public void tearDown() throws Exception {
		mockUserServiceInterceptor = null;
		fixture = null;
		super.tearDown();
	}
	
	protected UserServiceListenerForJMS createFixture() throws Exception {
		fixture = new UserServiceListenerForJMS();
		FieldUtil.setFieldValue(fixture, "userServiceInterceptor", mockUserServiceInterceptor);
		initialize(fixture);
		return fixture;
	}
	
	@Test
	public void test_processUserList_Success() throws Exception {
//		List<User> expectedUserRecords = AdminFixture.createUser_List();
//		when(mockUserServiceListener.getUserList(inputMessage)).thenReturn(expectedUserRecords);
//
//		try {
//			fixture = createFixture();
//			Message inputMessage = new Message();
//			Message outputMessage = fixture.processGetUserList(inputMessage);
//			List<User> actualUserRecords = outputMessage.getPart("userList");
//			AdminHelper.assertUserRecordListEquals(expectedUserRecords, actualUserRecords);
//			
//		} catch (Throwable e) {
//			validateAfterException(e);
//			
//		} finally {
//			validateAfterExecution();
//		}
	}
	
}
