package org.aries.jaxws;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class JAXWSUtil {

	private static Log log = LogFactory.getLog(JAXWSUtil.class);
	
	
	public static String getMessageContentAsString(SOAPMessage message) {
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		
		try {
			message.writeTo(stream);
			String content = new String(stream.toByteArray());
			return content;

		} catch (SOAPException e) {
			log.error(e);
			throw new RuntimeException(e);

		} catch (IOException e) {
			log.error(e);
			throw new RuntimeException(e);

		} finally {
			try {
				stream.close();
			} catch (IOException e) {
				//ignore
			}
		}
	}
	
}
