package template1.service;

import java.io.Serializable;
import java.util.List;

import org.aries.message.Message;
import org.aries.nam.model.TransportType;
import org.aries.registry.ServiceLocator;
import org.aries.runtime.BeanContext;
import org.aries.service.ServiceProxy;

import template1.model.Organization;


/**
 * Provides client interface to <code>infosgi</code> service.
 * 
 * @author tfisher
 */
@SuppressWarnings("serial")
public class OrganizationServiceProxy implements OrganizationService, Serializable {

//	@Override
//	public String getContext() {
//		return CONTEXT;
//	}

	/**
	 * Gets the root <em>Organization</em>.
	 * @return The root <em>Organization</em>.
	 */
	@Override
	public Organization getOrganization() {
    	Message request = new Message();
		ServiceLocator serviceLocator = BeanContext.get("org.aries.serviceLocator");
		ServiceProxy serviceProxy = serviceLocator.lookup("/infosgi-service", "0.0.1-SNAPSHOT", "org.sgiusa.getOrganization", TransportType.RMI);
    	Message response = serviceProxy.invoke(request);
    	Organization result = response.getPart(ORGANIZATION);
    	return result;
	}
	
	/**
	 * Gets all <em>Organization</em>s.
	 * @return The list of <em>Organization</em>s.
	 */
	@Override
	public List<Organization> getOrganizations() {
    	Message request = new Message();
		ServiceLocator serviceLocator = BeanContext.get("org.aries.serviceLocator");
		ServiceProxy serviceProxy = serviceLocator.lookup("/infosgi-service", "0.0.1-SNAPSHOT", "org.sgiusa.getOrganizations", TransportType.RMI);
    	Message response = serviceProxy.invoke(request);
    	List<Organization> result = response.getPart(ORGANIZATIONS);
    	return result;
	}
	
	/**
	 * Saves specified <em>Organization</em> to database.
	 * @param organization The data to save in database.
	 * @return The <em>id</em> for <em>Organization</em>.
	 */
	@Override
	public Long saveOrganization(Organization organization) {
    	Message request = new Message();
    	request.addPart(ORGANIZATION, organization);
		ServiceLocator serviceLocator = BeanContext.get("org.aries.serviceLocator");
		ServiceProxy serviceProxy = serviceLocator.lookup("/infosgi-service", "0.0.1-SNAPSHOT", "org.sgiusa.saveOrganization", TransportType.RMI);
    	Message response = serviceProxy.invoke(request);
    	Long id = response.getPart(ORGANIZATION_ID);
    	return id;
	}

	/**
	 * Removes specified <em>Organization</em> from database.
	 * @param organization The data to delete from database.
	 */
	@Override
	public void deleteOrganization(Organization organization) {
    	Message request = new Message();
    	request.addPart(ORGANIZATION, organization);
		ServiceLocator serviceLocator = BeanContext.get("org.aries.serviceLocator");
		ServiceProxy serviceProxy = serviceLocator.lookup("/infosgi-service", "0.0.1-SNAPSHOT", "org.sgiusa.deleteOrganization", TransportType.RMI);
    	serviceProxy.invoke(request);
	}

}
