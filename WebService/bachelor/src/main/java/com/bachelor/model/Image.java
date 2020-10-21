package com.bachelor.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

@Entity
@Table(name = "Image")
public class Image {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Id")
	private Integer Id;

	@Column(name = "physicalPath", columnDefinition = "varchar(MAX)")
	private String physicalPath;

	@Column(name = "status")
	private String status;

	@Version
	@Column(name = "version")
	private Integer version;

	public Image() {

	}

	public Image(Integer id, String physicalPath, String status, Integer version) {
		this.Id = id;
		this.physicalPath = physicalPath;
		this.status = status;
		this.version = version;
	}

	public Image(String physicalPath, String status, Integer version) {
		this.physicalPath = physicalPath;
		this.status = status;
		this.version = version;
	}

	public Image(String physicalPath, String status) {
		this.status = status;
		this.physicalPath = physicalPath;

	}

	public Image(Integer id, String physicalPath, String status) {
		this.Id = id;

		this.status = status;
		this.physicalPath = physicalPath;

	}

	public Integer getId() {
		return Id;
	}

	public void setId(Integer id) {
		Id = id;
	}

	public String getPhysicalPath() {
		return physicalPath;
	}

	public void setPhysicalPath(String physicalPath) {
		this.physicalPath = physicalPath;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	@Override
	public String toString() {
		return "Image [Id=" + Id + ", physicalPath=" + physicalPath + ", status=" + status + ", version=" + version
				+ "]";
	}

}
