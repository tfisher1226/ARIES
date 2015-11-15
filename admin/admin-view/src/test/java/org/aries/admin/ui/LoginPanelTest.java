package org.aries.admin.ui;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.test.ArquillianConfiguration;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


//@RunWith(Arquillian.class)
public class LoginPanelTest {

	private static Log log = LogFactory.getLog(LoginPanelTest.class);

	private static ArquillianConfiguration configuration;

	private static final String WEBAPP_SRC = "source/main/webapp";

//	@Drone
//	private WebDriver browser;

//	@ArquillianResource
//	private URL deploymentUrl;
//
//
//	@FindBy(id = "pageForm:LoginDialogLoginButton")
//	private WebElement loginButton;
//
//	@FindBy(id = "pageForm:LoginDialogMessages")
//	private WebElement facesMessage;
//
//	@FindBy(id = "pageForm:LoginDialogUserName")
//	private WebElement userName;
//
//	@FindBy(id = "pageForm:LoginDialogPassword")
//	private WebElement password;


	public LoginPanelTest() {
	}

	@BeforeClass
	public static void beforeClass() throws Exception {
		configuration = new ArquillianConfiguration();
		configuration.setJndiPropertyFileName("provider/hornetq/node1-jndi-remote.properties");
		configuration.setJmsPropertyFileName("provider/hornetq/node1-jms-remote.properties");
		configuration.initialize();
		log.info("beforeClass");
	}
	
	@Before
	public void setUp() throws Exception {
//		browser.manage().deleteAllCookies();
//		CheckpointManager.addConfiguration("buyer-checks.xml");
//		MBeanServer mbeanServer = MBeanServerFactory.createMBeanServer();
//		MBeanUtil.setMBeanServer(mbeanServer);
		log.info("setUp");
	}
	
//	
//	@Deployment(testable = false)
//	public static WebArchive createDeployment() {
//		WebArchive archive = ShrinkWrap.create(WebArchive.class, "login.war");
//		archive.addClass(Credentials.class);
//		archive.addClass(User.class);
//		archive.addClass(AuthenticationManager.class);
//		archive.addAsWebResource(new File(WEBAPP_SRC, "login2.xhtml"));
//		//archive.addAsWebResource(new File(WEBAPP_SRC, "home.xhtml"));
//		//archive.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
//		archive.addAsWebInfResource(new StringAsset("<faces-config version=\"2.0\"/>"), "faces-config.xml");
//		return archive;
//	}

	@Test
	public void should_login_successfully() {
		// open the page under test
//		browser.get(deploymentUrl.toExternalForm() + "/login.seam");

		// control the page
//		userName.sendKeys("demo");                                      
//		password.sendKeys("demo");

//		guardHttp(loginButton).click();
//
//		assertEquals("Welcome", facesMessage.getText().trim());
//		whoAmI.click();
//		waitAjax().until().element(signedAs).is().present();
//		assertTrue(signedAs.getText().contains("demo"));
	}

}
