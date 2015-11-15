package template1.service;

import java.util.List;

import template1.model.Member;


/**
 * Provides public interface into the <code>sample1</code> service module.
 * 
 * @author Administrator
 */
public interface MemberService {

	/** The identifying (the context) portion of the service URL. */
	public String CONTEXT = "/infosgi-service";

	/* 
	 * Below is the list of operations that are provided by this service.
	 * The name of each operation includes two portions, 1) the service ID,
	 * and 2) the operation ID.
	 */

	/* 
	 * The operations provided by this service: 
	 */

	public String GET_MEMBER = "org.sgiusa.getMember";
	public String GET_MEMBERS = "org.sgiusa.getMembers";
	public String SAVE_MEMBER = "org.sgiusa.saveMember";

	/*
	 * Below is the list of data items transferred to/from this service.
	 * The list below represents the data model exported by this service.
	 * The data model is basically the list of items below, the set of 
	 * potential data item types.
	 */
	public final String MEMBER = "member";
	public final String MEMBERS = "members";
	public final String MEMBER_ID = "memberId";
	public final String ORGANIZATION = "organization";
	public final String ORGANIZATION_ID = "organizationId";
	public final String PAGE_INDEX = "pageIndex";
	public final String PAGE_SIZE = "pageSize";


	/**
	 * Gets all <em>Member</em>s.
	 * @return The list of <em>Member</em>s.
	 */
	public List<Member> getMembers();

	/**
	 * Gets all <em>MembershipRecord</em>s for specified page and count.
	 * @return The list of <em>MembershipRecord</em>s.
	 */
	public List<Member> getMembers(int pageIndex, int pageSize);

	/**
	 * Gets all <em>Member</em>s for specified organization.
	 * @return The list of <em>Member</em>s.
	 */
	public List<Member> getMembersByOrganization(Long id);

	/**
	 * Gets the <em>Member</em> associated with specified <em>id</em>.
	 * @return The <em>Member</em> for <em>id</em>.
	 */
	public Member getMemberById(Long id);

	/**
	 * Saves specified <em>Member</em> to database.
	 * @param member The record to save in database.
	 * @return The <em>id</em> for <em>Member</em>.
	 */
	public Long saveMember(Member member);

}
