package com.bachelor.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

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

	public Image() {

	}

	public Image(String physicalPath, String status) {
		this.physicalPath = physicalPath;
		this.status = status;
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

	@Override
	public String toString() {
		return "Image [Id=" + Id + ", physicalPath=" + physicalPath + ", imageguid=" + status + "]";
	}

}
