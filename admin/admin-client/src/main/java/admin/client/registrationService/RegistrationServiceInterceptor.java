package admin.client.registrationService;

import java.util.List;

import org.aries.message.Message;
import org.aries.message.MessageInterceptor;
import org.aries.util.ExceptionUtil;

import admin.Registration;


@SuppressWarnings("serial")
public class RegistrationServiceInterceptor extends MessageInterceptor<RegistrationService> implements RegistrationService {
	
	@Override
	public List<Registration> getRegistrationList() {
		try {
			log.info("#### [admin]: getRegistrationList() sending...");
			Message request = createMessage("getRegistrationList");
			Message response = getProxy().invoke(request);
			List<Registration> registrationList = response.getPart("registrationList");
			return registrationList;
		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
	}

	@Override
	public Registration getRegistrationById(Long id) {
		try {
			log.info("#### [admin]: getRegistrationById() sending...");
			Message request = createMessage("getRegistrationById");
			request.addPart("id", id);
			Message response = getProxy().invoke(request);
		Registration registration = response.getPart("registration");
		return registration;
		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public Long addRegistration(Registration registration) {
		try {
			log.info("#### [admin]: addRegistration() sending...");
			Message request = createMessage("addRegistration");
			request.addPart("registration", registration);
			Message response = getProxy().invoke(request);
			Long id = response.getPart("id");
			return id;
		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public void saveRegistration(Registration registration) {
		try {
			log.info("#### [admin]: saveRegistration() sending...");
			Message request = createMessage("saveRegistration");
			request.addPart("registration", registration);
			getProxy().invoke(request);
		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public void deleteRegistration(Registration registration) {
		try {
			log.info("#### [admin]: deleteRegistration() sending...");
			Message request = createMessage("deleteRegistration");
			request.addPart("registration", registration);
			getProxy().invoke(request);
		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
	}

}
