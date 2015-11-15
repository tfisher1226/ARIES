package admin.incoming.skinService;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import org.aries.jaxb.JAXBSessionCache;
import org.aries.tx.AbstractListenerForJAXWSUnitTest;
import org.aries.util.FieldUtil;
import org.aries.validate.util.CheckpointManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import admin.Skin;
import admin.util.AdminFixture;
import admin.util.AdminHelper;


@RunWith(MockitoJUnitRunner.class)
public class SkinServiceHTTPListenerUnitTest extends AbstractListenerForJAXWSUnitTest {
	
	private SkinServiceListenerForJAXWS fixture;
	
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
	
	protected SkinServiceListenerForJAXWS createFixture() throws Exception {
		fixture = new SkinServiceListenerForJAXWS();
		FieldUtil.setFieldValue(fixture, "skinServiceHandler", mockSkinServiceHandler);
		initialize(fixture);
		return fixture;
	}
	
	@Test
	public void test_getSkinList_Success() throws Exception {
		List<Skin> expectedSkinRecords = AdminFixture.createSkin_List();
		when(mockSkinServiceHandler.getSkinList()).thenReturn(expectedSkinRecords);

		try {
			fixture = createFixture();
			List<Skin> actualSkinRecords = fixture.getSkinList();
			AdminHelper.assertSkinRecordListEquals(expectedSkinRecords, actualSkinRecords);
			
		} catch (Throwable e) {
			validateAfterException(e);
			
		} finally {
			validateAfterExecution();
		}
	}
	
}
