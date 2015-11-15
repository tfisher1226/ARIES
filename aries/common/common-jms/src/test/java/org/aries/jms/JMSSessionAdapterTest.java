package org.aries.jms;

import org.aries.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;


public class JMSSessionAdapterTest extends AbstractJmsTest {

	private JmsSessionAdapter fixture;
	
//    private Mock _mockSession;
//    
//    private Mock _mockMessage;
//
//    private Mock _mockTextMessage;
//
//    private Mock _mockObjectMessage;


	@Before
    public void setUp() throws Exception {
//        _mockSession = mock(Session.class);
//        _mockMessage = mock(Message.class);
//        _mockTextMessage = mock(TextMessage.class);
//        _mockObjectMessage = mock(ObjectMessage.class);
        super.setUp();
    	fixture = JMSTestFixture.createSessionAdapter("testClientId");
    	fixture.initialize();
    }

	@After
	public void tearDown() throws Exception {
		fixture.close();
        fixture = null;
//        _mockSession = null;
//        _mockMessage = null;
//        _mockTextMessage = null;
//        _mockObjectMessage = null;
        super.tearDown();
    }

    @Test
    @Ignore
    public void testCreateTextMessage() throws Exception {
    	Assert.notNull("Hello", "message should be set");
    }
    
//    public void testCreateTextMessage() throws Exception {
//    	_fixture.setSession((Session) _mockSession.proxy());
//        _mockSession.expects(once()).method("createTextMessage").will(returnValue(_mockTextMessage.proxy()));
//        _mockTextMessage.expects(once()).method("setText").with(eq("data"));
//    	TextMessage message = _fixture.createTextMessage("data");
//    	Assert.notNull(message, "message should be set");
//    	Assert.isInstanceOf(TextMessage.class, message);
//    }
//
//    public void testCreateTextMessage_Exception() throws Exception {
//    	_fixture.setSession((Session) _mockSession.proxy());
//    	Exception exception = new JMSException("test exception");
//        _mockSession.expects(atLeastOnce()).method("createTextMessage").will(throwException(exception));
//    	TextMessage message = _fixture.createTextMessage("data");
//    	Assert.notNull(message, "message should be set");
//    	Assert.isInstanceOf(TextMessage.class, message);
//    }

    @Test
    @Ignore
    public void testAssureSession() throws Exception {
        fixture.assureSession();
    }
    
}
