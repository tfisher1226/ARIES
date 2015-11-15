package org.sgiusa.model.entity;

import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.ForeignKey;
import org.sgiusa.model.entity.StudyDeptExamEntity;

/**
 * Generated by Nam.
 *
 */
@Entity(name = "StudyDeptInfo")
@Table(name = "study_dept_info")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class StudyDeptInfoEntity {

	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long id;

	@Column(name = "last_update")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastUpdate;

	@JoinColumn(name = "id")
	@OneToMany(cascade = CascadeType.ALL, targetEntity = StudyDeptExamEntity.class)
	@ForeignKey(name = "study_dept_info_entity_study_dept_exams_fk")
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	private List<StudyDeptExamEntity> studyDeptExams;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public List<StudyDeptExamEntity> getStudyDeptExams() {
		return studyDeptExams;
	}

	public void setStudyDeptExams(List<StudyDeptExamEntity> studyDeptExams) {
		this.studyDeptExams = studyDeptExams;
	}

}