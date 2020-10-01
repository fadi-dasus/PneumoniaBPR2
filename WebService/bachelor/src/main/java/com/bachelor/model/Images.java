package com.bachelor.model;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "image")
public class Images {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Id")
	private Integer Id;

	
	@Column(name = "imageblob", columnDefinition="varbinary(MAX)")
	private byte[] imageblob;
	
	
	@Column(name = "imageguid", columnDefinition = "uniqueidentifier ROWGUIDCOL NOT NULL UNIQUE DEFAULT NEWSEQUENTIALID()")
	private UUID imageguid;

	public Images() {

	}

}
