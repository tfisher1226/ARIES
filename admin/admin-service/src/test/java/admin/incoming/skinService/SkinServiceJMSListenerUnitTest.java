package admin.incoming.skinService;

import static org.mockito.Mockito.mock;

import org.aries.jaxb.JAXBSessionCache;
import org.aries.tx.AbstractListenerForJMSUnitTest;
import org.aries.validate.util.CheckpointManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;


@RunWith(MockitoJUnitRunner.class)
public class SkinServiceJMSListenerUnitTest extends AbstractListenerForJMSUnitTest {
	
	private SkinServiceListenerForJMS fixture;
	
	private SkinServiceHandler mockSkinServiceHandler;
	

	@Override
	public String getServiceId() {
		return SkinService.ID;
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
		mockSkinServiceHandler = mock(SkinServiceHandler.class);
		CheckpointManager.addConfiguration("admin-service-checks.xml");
		JAXBSessionCache jaxbSessionCache = new JAXBSessionCache(getDomain());
		CheckpointManager.setJAXBSessionCache(jaxbSessionCache);
		super.setUp();
	}
	
	@After
	public void tearDown() throws Exception {
		mockSkinServiceHandler = null;
		fixture = null;
		super.tearDown();
	}
	
	protected SkinServiceListenerForJMS createFixture() throws Exception {
		fixture = new SkinServiceListenerForJMS();
		//fixture.skinServiceHandler = mockSkinServiceHandler;
		initialize(fixture);
		return fixture;
	}
	
	@Test
	public void test_processSkinList_Success() throws Exception {
//		List<Skin> expectedSkinRecords = AdminFixture.createSkin_List();
//		when(mockSkinServiceHandler.getSkinList()).thenReturn(expectedSkinRecords);
//
//		try {
//			fixture = createFixture();
//			Message inputMessage = new Message();
//			Message outputMessage = fixture.getSkinList(inputMessage);
//			List<Skin> actualSkinRecords = outputMessage.getPart("skinList");
//			AdminHelper.assertSkinRecordListEquals(expectedSkinRecords, actualSkinRecords);
//			
//		} catch (Throwable e) {
//			validateAfterException(e);
//			
//		} finally {
//			validateAfterExecution();
//		}
	}
	
}
