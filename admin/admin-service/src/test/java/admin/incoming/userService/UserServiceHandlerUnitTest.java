package admin.incoming.userService;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import org.aries.jaxb.JAXBSessionCache;
import org.aries.runtime.BeanContext;
import org.aries.tx.AbstractHandlerUnitTest;
import org.aries.tx.Transactional;
import org.aries.validate.util.CheckpointManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import admin.AdminRepository;
import admin.User;
import admin.util.AdminFixture;
import admin.util.AdminHelper;


@RunWith(MockitoJUnitRunner.class)
public class UserServiceHandlerUnitTest extends AbstractHandlerUnitTest {
	
	private UserServiceHandlerImpl fixture;
	
	private AdminRepository mockAdminRepository;
	
	
	public String getName() {
		return "UserService";
	}
	
	public String getDomain() {
		return "admin.service";
	}
	
	public Transactional getFixture() {
		return fixture;
	}
	
	@Before
	public void setUp() throws Exception {
		mockAdminRepository = mock(AdminRepository.class);
		CheckpointManager.addConfiguration("admin-service-checks.xml");
		JAXBSessionCache jaxbSessionCache = new JAXBSessionCache(getDomain());
		CheckpointManager.setJAXBSessionCache(jaxbSessionCache);
		super.setUp();
	}
	
	@After
	public void tearDown() throws Exception {
		BeanContext.clear();
		mockAdminRepository = null;
		fixture = null;
		super.tearDown();
	}
	
	protected UserServiceHandlerImpl createFixture() throws Exception {
		fixture = new UserServiceHandlerImpl();
		fixture.adminRepository = mockAdminRepository;
		initialize(fixture);
		return fixture;
	}
	
	@Test
	public void test_getUserList_Success() throws Exception {
		List<User> expectedUserRecords = AdminFixture.createUser_List();
		when(mockAdminRepository.getAllUserRecords()).thenReturn(expectedUserRecords);

		try {
			fixture = createFixture();
			List<User> actualUserRecords = fixture.getUserList();
			AdminHelper.assertUserRecordListEquals(expectedUserRecords, actualUserRecords);
			
		} catch (Throwable e) {
			validateAfterException(e);
			
		} finally {
			validateAfterExecution();
		}
	}
	
}
