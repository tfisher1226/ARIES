package template1.service;

import java.io.Serializable;
import java.util.List;

import org.aries.message.Message;
import org.aries.nam.model.TransportType;
import org.aries.registry.ServiceLocator;
import org.aries.runtime.BeanContext;
import org.aries.service.ServiceProxy;

import template1.model.Member;
import template1.model.Members;


/**
 * Provides client interface to <code>infosgi</code> service.
 * 
 * @author tfisher
 */
@SuppressWarnings("serial")
public class MemberServiceProxy implements MemberService, Serializable {

//	@Override
//	public String getContext() {
//		return CONTEXT;
//	}

	/**
	 * Gets all <em>Member</em>s.
	 * @return The list of <em>Member</em>s.
	 */
	@Override
	public List<Member> getMembers() {
    	Message request = new Message();
		ServiceLocator serviceLocator = BeanContext.get("org.aries.serviceLocator");
		ServiceProxy serviceProxy = serviceLocator.lookup("/infosgi-service", "0.0.1-SNAPSHOT", "org.sgiusa.getMembers", TransportType.RMI);
    	Message response = serviceProxy.invoke(request);
		Members members = response.getPart(MEMBERS);
		List<Member> result = members.getRecords();
    	return result;
	}

	/**
	 * Gets all <em>Member</em>s.
	 * @return The list of <em>Member</em>s.
	 */
	@Override
	public List<Member> getMembers(int pageIndex, int pageSize) {
    	Message request = new Message();
    	request.addPart(PAGE_INDEX, pageIndex);
    	request.addPart(PAGE_SIZE, pageSize);
		ServiceLocator serviceLocator = BeanContext.get("org.aries.serviceLocator");
		ServiceProxy serviceProxy = serviceLocator.lookup("/infosgi-service", "0.0.1-SNAPSHOT", "org.sgiusa.getMembers", TransportType.RMI);
    	Message response = serviceProxy.invoke(request);
		Members members = response.getPart(MEMBERS);
		List<Member> result = members.getRecords();
    	return result;
	}

	@Override
	public List<Member> getMembersByOrganization(Long id) {
    	Message request = new Message();
    	request.addPart(ORGANIZATION_ID, id);
		ServiceLocator serviceLocator = BeanContext.get("org.aries.serviceLocator");
		ServiceProxy serviceProxy = serviceLocator.lookup("/infosgi-service", "0.0.1-SNAPSHOT", "org.sgiusa.getMembersByOrganization", TransportType.RMI);
    	Message response = serviceProxy.invoke(request);
		Members members = response.getPart(MEMBERS);
		List<Member> result = members.getRecords();
    	return result;
	}

	/**
	 * Gets the <em>Member</em> associated with <em>id</em>.
	 * @return The <em>Member</em> for <em>id</em>.
	 */
	@Override
	public Member getMemberById(Long id) {
    	Message request = new Message();
    	request.addPart(MEMBER_ID, id);
		ServiceLocator serviceLocator = BeanContext.get("org.aries.serviceLocator");
		ServiceProxy serviceProxy = serviceLocator.lookup("/infosgi-service", "0.0.1-SNAPSHOT", "org.sgiusa.getMemberById", TransportType.RMI);
    	Message response = serviceProxy.invoke(request);
    	Member result = response.getPart(MEMBER);
    	return result;
	}
	
	/**
	 * Saves specified <em>Member</em> to database.
	 * @param member The data to save in database.
	 * @return The <em>id</em> for <em>Member</em>.
	 */
	@Override
	public Long saveMember(Member member) {
    	Message request = new Message();
    	request.addPart(MEMBER, member);
		ServiceLocator serviceLocator = BeanContext.get("org.aries.serviceLocator");
		ServiceProxy serviceProxy = serviceLocator.lookup("/infosgi-service", "0.0.1-SNAPSHOT", "org.sgiusa.saveMember", TransportType.RMI);
    	Message response = serviceProxy.invoke(request);
    	Long id = response.getPart(MEMBER_ID);
    	return id;

	}

}
