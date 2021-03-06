package org.sgiusa.model;

import java.io.Serializable;
import java.lang.String;
import java.util.Date;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.aries.common.EmailAddress;
import org.aries.common.PhoneNumber;
import org.aries.common.StreetAddress;
import org.sgiusa.model.ActivityGroup;
import org.sgiusa.model.Division;
import org.sgiusa.model.FamilyMember;
import org.sgiusa.model.LeadershipInfo;
import org.sgiusa.model.Note;
import org.sgiusa.model.Organization;
import org.sgiusa.model.StudyDeptInfo;
import org.sgiusa.model.SubDivision;

/**
 * Generated by Nam.
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Member", propOrder = {
	"id",
	"firstName",
	"lastName",
	"middleInitial",
	"division",
	"subDivision",
	"activityGroups",
	"statusProfile",
	"interests",
	"languages",
	"joinDate",
	"birthDate",
	"employer",
	"occupation",
	"extraField1",
	"extraField2",
	"archived",
	"visible",
	"locatable",
	"creationDate",
	"lastUpdate",
	"emailAddress",
	"streetAddress",
	"homePhone",
	"cellPhone",
	"workPhone",
	"otherPhone",
	"organization",
	"leadershipInfo",
	"studyDeptInfo",
	"familyInfo",
	"notes"
})
@XmlRootElement(name = "member")
public class Member implements Serializable {

	public static final long serialVersionUID = 1;

	@XmlAttribute(name = "id")
	private Long id;

	@XmlAttribute(name = "first-name")
	private String firstName;

	@XmlAttribute(name = "last-name")
	private String lastName;

	@XmlAttribute(name = "middle-initial")
	private String middleInitial;

	@XmlAttribute(name = "division")
	private Division division;

	@XmlAttribute(name = "sub-division")
	private SubDivision subDivision;

	@XmlAttribute(name = "activity-groups")
	private List<ActivityGroup> activityGroups;

	@XmlAttribute(name = "status-profile")
	private Integer statusProfile;

	@XmlAttribute(name = "interests")
	private String interests;

	@XmlAttribute(name = "languages")
	private String languages;

	@XmlAttribute(name = "join-date")
	private Date joinDate;

	@XmlAttribute(name = "birth-date")
	private Date birthDate;

	@XmlAttribute(name = "employer")
	private String employer;

	@XmlAttribute(name = "occupation")
	private String occupation;

	@XmlAttribute(name = "extra-field1")
	private String extraField1;

	@XmlAttribute(name = "extra-field2")
	private String extraField2;

	@XmlAttribute(name = "archived")
	private Boolean archived;

	@XmlAttribute(name = "visible")
	private Boolean visible;

	@XmlAttribute(name = "locatable")
	private Boolean locatable;

	@XmlAttribute(name = "creation-date")
	private Date creationDate;

	@XmlAttribute(name = "last-update")
	private Date lastUpdate;

	@XmlElement(name = "email-address")
	private EmailAddress emailAddress;

	@XmlElement(name = "street-address")
	private StreetAddress streetAddress;

	@XmlElement(name = "home-phone")
	private PhoneNumber homePhone;

	@XmlElement(name = "cell-phone")
	private PhoneNumber cellPhone;

	@XmlElement(name = "work-phone")
	private PhoneNumber workPhone;

	@XmlElement(name = "other-phone")
	private PhoneNumber otherPhone;

	@XmlElement(name = "organization")
	private Organization organization;

	@XmlElement(name = "leadership-info")
	private LeadershipInfo leadershipInfo;

	@XmlElement(name = "study-dept-info")
	private StudyDeptInfo studyDeptInfo;

	@XmlElement(name = "family-info")
	private FamilyMember familyInfo;

	@XmlElement(name = "notes")
	private List<Note> notes;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getMiddleInitial() {
		return middleInitial;
	}

	public void setMiddleInitial(String middleInitial) {
		this.middleInitial = middleInitial;
	}

	public Division getDivision() {
		return division;
	}

	public void setDivision(Division division) {
		this.division = division;
	}

	public SubDivision getSubDivision() {
		return subDivision;
	}

	public void setSubDivision(SubDivision subDivision) {
		this.subDivision = subDivision;
	}

	public List<ActivityGroup> getActivityGroups() {
		return activityGroups;
	}

	public void setActivityGroups(List<ActivityGroup> activityGroups) {
		this.activityGroups = activityGroups;
	}

	public Integer getStatusProfile() {
		return statusProfile;
	}

	public void setStatusProfile(Integer statusProfile) {
		this.statusProfile = statusProfile;
	}

	public String getInterests() {
		return interests;
	}

	public void setInterests(String interests) {
		this.interests = interests;
	}

	public String getLanguages() {
		return languages;
	}

	public void setLanguages(String languages) {
		this.languages = languages;
	}

	public Date getJoinDate() {
		return joinDate;
	}

	public void setJoinDate(Date joinDate) {
		this.joinDate = joinDate;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public String getEmployer() {
		return employer;
	}

	public void setEmployer(String employer) {
		this.employer = employer;
	}

	public String getOccupation() {
		return occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	public String getExtraField1() {
		return extraField1;
	}

	public void setExtraField1(String extraField1) {
		this.extraField1 = extraField1;
	}

	public String getExtraField2() {
		return extraField2;
	}

	public void setExtraField2(String extraField2) {
		this.extraField2 = extraField2;
	}

	public Boolean getArchived() {
		return archived;
	}

	public void setArchived(Boolean archived) {
		this.archived = archived;
	}

	public Boolean getVisible() {
		return visible;
	}

	public void setVisible(Boolean visible) {
		this.visible = visible;
	}

	public Boolean getLocatable() {
		return locatable;
	}

	public void setLocatable(Boolean locatable) {
		this.locatable = locatable;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public EmailAddress getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(EmailAddress emailAddress) {
		this.emailAddress = emailAddress;
	}

	public StreetAddress getStreetAddress() {
		return streetAddress;
	}

	public void setStreetAddress(StreetAddress streetAddress) {
		this.streetAddress = streetAddress;
	}

	public PhoneNumber getHomePhone() {
		return homePhone;
	}

	public void setHomePhone(PhoneNumber homePhone) {
		this.homePhone = homePhone;
	}

	public PhoneNumber getCellPhone() {
		return cellPhone;
	}

	public void setCellPhone(PhoneNumber cellPhone) {
		this.cellPhone = cellPhone;
	}

	public PhoneNumber getWorkPhone() {
		return workPhone;
	}

	public void setWorkPhone(PhoneNumber workPhone) {
		this.workPhone = workPhone;
	}

	public PhoneNumber getOtherPhone() {
		return otherPhone;
	}

	public void setOtherPhone(PhoneNumber otherPhone) {
		this.otherPhone = otherPhone;
	}

	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	public LeadershipInfo getLeadershipInfo() {
		return leadershipInfo;
	}

	public void setLeadershipInfo(LeadershipInfo leadershipInfo) {
		this.leadershipInfo = leadershipInfo;
	}

	public StudyDeptInfo getStudyDeptInfo() {
		return studyDeptInfo;
	}

	public void setStudyDeptInfo(StudyDeptInfo studyDeptInfo) {
		this.studyDeptInfo = studyDeptInfo;
	}

	public FamilyMember getFamilyInfo() {
		return familyInfo;
	}

	public void setFamilyInfo(FamilyMember familyInfo) {
		this.familyInfo = familyInfo;
	}

	public List<Note> getNotes() {
		return notes;
	}

	public void setNotes(List<Note> notes) {
		this.notes = notes;
	}

	public boolean equals(Object object) {
		if (object == null)
			return false;
		if (!object.getClass().isAssignableFrom(this.getClass()))
			return false;
		Member other = (Member) object;
		if (this.getId() == null || other.getId() == null)
			return this == other;
		if (this.getId().equals(other.getId()))
				return false;
		return true;
	}

	public int hashCode() {
		if (getId() != null)
			return getId().hashCode();
		return 0;
	}

}