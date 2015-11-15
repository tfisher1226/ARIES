package org.sgiusa.model.entity;

import java.lang.String;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.aries.common.entity.EmailAddressEntity;
import org.aries.common.entity.PhoneNumberEntity;
import org.aries.common.entity.StreetAddressEntity;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.ForeignKey;
import org.sgiusa.model.ActivityGroup;
import org.sgiusa.model.Division;
import org.sgiusa.model.SubDivision;
import org.sgiusa.model.entity.FamilyMemberEntity;
import org.sgiusa.model.entity.LeadershipInfoEntity;
import org.sgiusa.model.entity.NoteEntity;
import org.sgiusa.model.entity.OrganizationEntity;
import org.sgiusa.model.entity.StudyDeptInfoEntity;

/**
 * Generated by Nam.
 *
 */
@Entity(name = "Member")
@Table(name = "member")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class MemberEntity {

	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long id;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	@Column(name = "middle_initial")
	private String middleInitial;

	@Column(name = "division")
	private Division division;

	@Column(name = "sub_division")
	private SubDivision subDivision;

	@Column(name = "activity_groups")
	@Enumerated(EnumType.STRING)
	@ElementCollection(targetClass = ActivityGroup.class)
	@CollectionTable(name = "member_activity_group")
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	private List<ActivityGroup> activityGroups;

	@Column(name = "status_profile")
	private Integer statusProfile;

	@Column(name = "interests")
	private String interests;

	@Column(name = "languages")
	private String languages;

	@Column(name = "join_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date joinDate;

	@Column(name = "birth_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date birthDate;

	@Column(name = "employer")
	private String employer;

	@Column(name = "occupation")
	private String occupation;

	@Column(name = "extra_field_1")
	private String extraField1;

	@Column(name = "extra_field_2")
	private String extraField2;

	@Column(name = "archived")
	private Boolean archived;

	@Column(name = "visible")
	private Boolean visible;

	@Column(name = "locatable")
	private Boolean locatable;

	@Column(name = "creation_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date creationDate;

	@Column(name = "last_update")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastUpdate;

	@JoinColumn(name = "email_address", referencedColumnName = "id")
	@OneToOne(cascade = CascadeType.ALL)
	@ForeignKey(name = "member_entity_email_address_fk")
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	private EmailAddressEntity emailAddress;

	@JoinColumn(name = "street_address", referencedColumnName = "id")
	@OneToOne(cascade = CascadeType.ALL)
	@ForeignKey(name = "member_entity_street_address_fk")
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	private StreetAddressEntity streetAddress;

	@JoinColumn(name = "home_phone", referencedColumnName = "id")
	@OneToOne(cascade = CascadeType.ALL)
	@ForeignKey(name = "member_entity_home_phone_fk")
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	private PhoneNumberEntity homePhone;

	@JoinColumn(name = "cell_phone", referencedColumnName = "id")
	@OneToOne(cascade = CascadeType.ALL)
	@ForeignKey(name = "member_entity_cell_phone_fk")
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	private PhoneNumberEntity cellPhone;

	@JoinColumn(name = "work_phone", referencedColumnName = "id")
	@OneToOne(cascade = CascadeType.ALL)
	@ForeignKey(name = "member_entity_work_phone_fk")
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	private PhoneNumberEntity workPhone;

	@JoinColumn(name = "other_phone", referencedColumnName = "id")
	@OneToOne(cascade = CascadeType.ALL)
	@ForeignKey(name = "member_entity_other_phone_fk")
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	private PhoneNumberEntity otherPhone;

	@JoinColumn(name = "organization", referencedColumnName = "id")
	@OneToOne(cascade = CascadeType.ALL)
	@ForeignKey(name = "member_entity_organization_fk")
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	private OrganizationEntity organization;

	@JoinColumn(name = "leadership_info", referencedColumnName = "id")
	@OneToOne(cascade = CascadeType.ALL)
	@ForeignKey(name = "member_entity_leadership_info_fk")
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	private LeadershipInfoEntity leadershipInfo;

	@JoinColumn(name = "study_dept_info", referencedColumnName = "id")
	@OneToOne(cascade = CascadeType.ALL)
	@ForeignKey(name = "member_entity_study_dept_info_fk")
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	private StudyDeptInfoEntity studyDeptInfo;

	@JoinColumn(name = "family_info", referencedColumnName = "id")
	@OneToOne(cascade = CascadeType.ALL)
	@ForeignKey(name = "member_entity_family_info_fk")
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	private FamilyMemberEntity familyInfo;

	@JoinColumn(name = "id")
	@OneToMany(cascade = CascadeType.ALL, targetEntity = NoteEntity.class)
	@ForeignKey(name = "member_entity_notes_fk")
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	private List<NoteEntity> notes;

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

	public EmailAddressEntity getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(EmailAddressEntity emailAddress) {
		this.emailAddress = emailAddress;
	}

	public StreetAddressEntity getStreetAddress() {
		return streetAddress;
	}

	public void setStreetAddress(StreetAddressEntity streetAddress) {
		this.streetAddress = streetAddress;
	}

	public PhoneNumberEntity getHomePhone() {
		return homePhone;
	}

	public void setHomePhone(PhoneNumberEntity homePhone) {
		this.homePhone = homePhone;
	}

	public PhoneNumberEntity getCellPhone() {
		return cellPhone;
	}

	public void setCellPhone(PhoneNumberEntity cellPhone) {
		this.cellPhone = cellPhone;
	}

	public PhoneNumberEntity getWorkPhone() {
		return workPhone;
	}

	public void setWorkPhone(PhoneNumberEntity workPhone) {
		this.workPhone = workPhone;
	}

	public PhoneNumberEntity getOtherPhone() {
		return otherPhone;
	}

	public void setOtherPhone(PhoneNumberEntity otherPhone) {
		this.otherPhone = otherPhone;
	}

	public OrganizationEntity getOrganization() {
		return organization;
	}

	public void setOrganization(OrganizationEntity organization) {
		this.organization = organization;
	}

	public LeadershipInfoEntity getLeadershipInfo() {
		return leadershipInfo;
	}

	public void setLeadershipInfo(LeadershipInfoEntity leadershipInfo) {
		this.leadershipInfo = leadershipInfo;
	}

	public StudyDeptInfoEntity getStudyDeptInfo() {
		return studyDeptInfo;
	}

	public void setStudyDeptInfo(StudyDeptInfoEntity studyDeptInfo) {
		this.studyDeptInfo = studyDeptInfo;
	}

	public FamilyMemberEntity getFamilyInfo() {
		return familyInfo;
	}

	public void setFamilyInfo(FamilyMemberEntity familyInfo) {
		this.familyInfo = familyInfo;
	}

	public List<NoteEntity> getNotes() {
		return notes;
	}

	public void setNotes(List<NoteEntity> notes) {
		this.notes = notes;
	}

}