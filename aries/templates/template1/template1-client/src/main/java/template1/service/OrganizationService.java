package template1.service;

import java.util.List;

import template1.model.Organization;


/**
 * Provides public interface into the <code>sample1</code> service module.
 * 
 * @author Administrator
 */
public interface OrganizationService {

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

	public String GET_ORGANIZATIONS = "org.sgiusa.getOrganizations";
	public String SAVE_ORGANIZATION = "org.sgiusa.saveOrganization";

	/*
	 * Below is the list of data items transferred to/from this service.
	 * The list below represents the data model exported by this service.
	 * The data model is basically the list of items below, the set of 
	 * potential data item types.
	 */
	public final String ORGANIZATION = "organization";
	public final String ORGANIZATIONS = "organizations";
	public final String ORGANIZATION_ID = "organizationId";


	/**
	 * Gets the root <em>Organization</em>.
	 * @return The root <em>Organization</em>.
	 */
	public Organization getOrganization();

	/**
	 * Gets all <em>Organization</em>s.
	 * @return The list of <em>Organization</em>s.
	 */
	public List<Organization> getOrganizations();

	/**
	 * Saves specified <em>Organization</em> to database.
	 * @param organization The record to save in database.
	 * @return The <em>id</em> for <em>Organization</em>.
	 */
	public Long saveOrganization(Organization organization);

	/**
	 * Removes specified <em>Organization</em> from database.
	 * @param organization The record to delete from database.
	 */
	public void deleteOrganization(Organization organization);

}
