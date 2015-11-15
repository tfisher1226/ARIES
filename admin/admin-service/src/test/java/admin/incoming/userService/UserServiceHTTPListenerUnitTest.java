package admin.incoming.userService;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import org.aries.tx.AbstractListenerForJAXWSUnitTest;
import org.aries.util.FieldUtil;
import org.aries.validate.util.CheckpointManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import admin.User;
import admin.util.AdminFixture;
import admin.util.AdminHelper;


@RunWith(MockitoJUnitRunner.class)
public class UserServiceHTTPListenerUnitTest extends AbstractListenerForJAXWSUnitTest {
	
	private UserServiceListenerForJAXWS fixture;
	
	private UserServiceHandler mockUserServiceHandler;
	
	
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
		mockUserServiceHandler = mock(UserServiceHandler.class);
		CheckpointManager.addConfiguration("admin-service-checks.xml");
		CheckpointManager.setJAXBSessionCache(getJAXBSessionCache());
		super.setUp();
	}
	
	@After
	public void tearDown() throws Exception {
		mockUserServiceHandler = null;
		fixture = null;
		super.tearDown();
	}
	
	protected UserServiceListenerForJAXWS createFixture() throws Exception {
		fixture = new UserServiceListenerForJAXWS();
		FieldUtil.setFieldValue(fixture, "userServiceHandler", mockUserServiceHandler);
		initialize(fixture);
		return fixture;
	}
	
	@Test
	public void test_getUserList_Success() throws Exception {
		List<User> expectedUserRecords = AdminFixture.createUser_List();
		when(mockUserServiceHandler.getUserList()).thenReturn(expectedUserRecords);

		try {
			fixture = createFixture();
			List<User> actualUserRecords = fixture.getUserList();
			AdminHelper.assertUserRecordListEquals(expectedUserRecords, actualUserRecords);
			
		} catch (Throwable e) {
			e.printStackTrace();
			validateAfterException(e);
			
		} finally {
			validateAfterExecution();
		}
	}
	
}
