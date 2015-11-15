package admin.client.registrationService;

import java.util.List;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import admin.Registration;


@WebService(name = "registrationService", targetNamespace = "http://admin")
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface RegistrationService {
	
	public String ID = "admin.registrationService";
	
	public List<Registration> getRegistrationList();
	
	public Registration getRegistrationById(Long id);
	
	public Long addRegistration(Registration registration);
	
	public void saveRegistration(Registration registration);
	
	public void deleteRegistration(Registration registration);
	
}
