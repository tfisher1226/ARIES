package admin.incoming.skinService;

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
import admin.Skin;
import admin.util.AdminFixture;
import admin.util.AdminHelper;


@RunWith(MockitoJUnitRunner.class)
public class SkinServiceHandlerUnitTest extends AbstractHandlerUnitTest {
	
	private SkinServiceHandlerImpl fixture;
	
	private AdminRepository mockAdminRepository;
	
	
	public String getName() {
		return "SkinService";
	}
	
	public String getDomain() {
		return "org.aries";
	}
	
	public Transactional getFixture() {
		return fixture;
	}
	
	@Before
	public void setUp() throws Exception {
		mockAdminRepository = mock(AdminRepository.class);
		CheckpointManager.addConfiguration("admin-service-checks.xml");
		CheckpointManager.setJAXBSessionCache(getJAXBSessionCache());
		super.setUp();
	}
	
	@After
	public void tearDown() throws Exception {
		BeanContext.clear();
		mockAdminRepository = null;
		fixture = null;
		super.tearDown();
	}
	
	protected SkinServiceHandlerImpl createFixture() throws Exception {
		fixture = new SkinServiceHandlerImpl();
		fixture.adminRepository = mockAdminRepository;
		initialize(fixture);
		return fixture;
	}
	
	@Test
	public void test_getAllSkinRecords_Success() throws Exception {
		List<Skin> expectedSkinRecords = AdminFixture.createSkin_List();
		when(mockAdminRepository.getAllSkinRecords()).thenReturn(expectedSkinRecords);

		fixture = createFixture();
		List<Skin> actualSkinRecords = fixture.getSkinList();
		AdminHelper.assertSkinRecordListEquals(expectedSkinRecords, actualSkinRecords);
	}
	
}
