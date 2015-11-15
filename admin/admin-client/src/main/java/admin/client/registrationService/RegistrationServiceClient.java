package admin.client.registrationService;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.client.AbstractDelegate;
import org.aries.util.ExceptionUtil;

import admin.Registration;


public class RegistrationServiceClient extends AbstractDelegate implements RegistrationService {
	
	private static final Log log = LogFactory.getLog(RegistrationServiceClient.class);

	
	@Override
	public String getDomain() {
		return "admin";
	}
	
	@Override
	public String getServiceId() {
		return RegistrationService.ID;
	}
	
	@SuppressWarnings("unchecked")
	public RegistrationService getProxy() throws Exception {
		return getProxy(RegistrationService.ID);
	}
	
	@Override
	public List<Registration> getRegistrationList() {
		try {
			List<Registration> registrationList = getProxy().getRegistrationList();
			return registrationList;
		} catch (Exception e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public Registration getRegistrationById(Long id) {
		try {
		Registration registration = getProxy().getRegistrationById(id);
			return registration;
		} catch (Exception e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public Long addRegistration(Registration registration) {
		try {
		Long id = getProxy().addRegistration(registration);
			return id;
		} catch (Exception e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public void saveRegistration(Registration registration) {
		try {
			getProxy().saveRegistration(registration);
		} catch (Exception e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public void deleteRegistration(Registration registration) {
		try {
			getProxy().deleteRegistration(registration);
		} catch (Exception e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
}
