package admin;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.aries.Assert;
import org.aries.jaxb.JAXBReader;
import org.aries.jaxb.JAXBReaderImpl;
import org.aries.jaxb.JAXBSessionCache;
import org.aries.jaxb.JAXBWriter;
import org.aries.jaxb.JAXBWriterImpl;
import org.aries.message.Message;
import org.aries.message.util.MessageConstants;
import org.aries.runtime.BeanContext;
import org.aries.util.NameUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import admin.util.AdminFixture;


public class UserTest {

	private JAXBReader jaxbReader;

	private JAXBWriter jaxbWriter;

	private JAXBSessionCache jaxbSessionCache = new JAXBSessionCache("test");
	
	
	@Before
	public void setUp() throws Exception {
		createJAXBContext();
	}

	protected void createJAXBContext() throws Exception {
		jaxbSessionCache.addSchema("/schema/common/aries-common-1.0.xsd", org.aries.common.ObjectFactory.class);
		jaxbSessionCache.addSchema("/schema/common/aries-message-1.0.xsd", org.aries.message.ObjectFactory.class);
		jaxbSessionCache.addSchema("/schema/admin/admin_model.xsd", admin.ObjectFactory.class);
		jaxbReader = createJAXBReader();
		jaxbWriter = createJAXBWriter();
	}
	
	protected JAXBReader createJAXBReader() throws Exception {
		JAXBReaderImpl jaxbReader = new JAXBReaderImpl();
		jaxbReader.setSchema(jaxbSessionCache.getSchema());
		jaxbReader.setPackagesToLoad(jaxbSessionCache.getPackagesToLoad());
		jaxbReader.initialize();
		return jaxbReader; 
	}
	
	protected JAXBWriter createJAXBWriter() throws Exception {
		JAXBWriterImpl jaxbWriter = new JAXBWriterImpl();
		jaxbWriter.setSchema(jaxbSessionCache.getSchema());
		jaxbWriter.setPackagesToLoad(jaxbSessionCache.getPackagesToLoad());
		jaxbWriter.initialize();
		return jaxbWriter; 
	}
	
	public Message createMessage(String operationName) throws Exception {
		Message message = new Message();
		message.addPart(MessageConstants.PROPERTY_USER_NAME, "tfisher");
		message.addPart(MessageConstants.PROPERTY_SERVICE_ID, "org.aries.userService");
		message.addPart(MessageConstants.PROPERTY_OPERATION_NAME, operationName);
		return message;
	}
	

	@After
	public void tearDown() throws Exception {
		jaxbReader = null;
		jaxbWriter = null;
	}
	
	@Test
	public void testMarshalUser() throws Exception {
		User user = AdminFixture.createUser();
		String xml = jaxbWriter.marshal(user);
		Assert.notNull(xml, "XML should exist");
		User user2 = jaxbReader.unmarshal(xml);
		Assert.notNull(user2, "User should exist");
		Assert.equals(user, user2, "User records should be equal");
	}

	@Test
	public void testMarshalUserMessage() throws Exception {
		User user = AdminFixture.createUser();
		Message message = createMessage("getUserById");
		message.addPart("user", user);
		String xml = jaxbWriter.marshal(message);
		Assert.notNull(xml, "XML should exist");
		Message message2 = jaxbReader.unmarshal(xml);
		User user2 = message2.getPart("user");
		Assert.notNull(user2, "User should exist");
		Assert.equals(user, user2, "User records should be equal");
	}
	
	@Test
	public void testMarshalUserListMessage() throws Exception {
		User user = AdminFixture.createUser();
		Message message = createMessage("getUserList");
		List<User> userList = new ArrayList<User>();
		userList.add(user);
		message.addPart("userList", userList);
		String xml = jaxbWriter.marshal(message);
		Assert.notNull(xml, "XML should exist");
		Message message2 = jaxbReader.unmarshal(xml);
		List<User> userList2 = message2.getPart("userList");
		Assert.notNull(userList2, "User records should exist");
		Assert.equals(userList, userList2, "User records should be equal");
	}


}
