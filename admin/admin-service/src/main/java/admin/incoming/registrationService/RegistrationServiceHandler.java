package admin.incoming.registrationService;

import java.util.List;

import org.aries.tx.Transactional;

import admin.Registration;


public interface RegistrationServiceHandler extends Transactional {
	
	public List<Registration> getRegistrationList();
	
	public Registration getRegistrationById(Long id);
	
	public Long addRegistration(Registration registration);
	
	public void saveRegistration(Registration registration);
	
	public void deleteRegistration(Registration registration);
	
}
