package com.bachelor.model;

import java.util.Arrays;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "image")
public class Image {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Id")
	private Integer Id;

	@Column(name = "imageblob", columnDefinition = "varbinary(MAX)")
	private byte[] imageblob;

	@Column(name = "imageguid", columnDefinition = "uniqueidentifier ROWGUIDCOL NOT NULL UNIQUE DEFAULT NEWSEQUENTIALID()")
	private UUID imageguid;

	public Image() {

	}

	public Image(byte[] imageblob, UUID imageguid) {
		this.imageblob = imageblob;
		this.imageguid = imageguid;
	}

	public Integer getId() {
		return Id;
	}

	public void setId(Integer id) {
		Id = id;
	}

	public byte[] getImageblob() {
		return imageblob;
	}

	public void setImageblob(byte[] imageblob) {
		this.imageblob = imageblob;
	}

	public UUID getImageguid() {
		return imageguid;
	}

	public void setImageguid(UUID imageguid) {
		this.imageguid = imageguid;
	}

	@Override
	public String toString() {
		return "Image [Id=" + Id + ", imageblob=" + Arrays.toString(imageblob) + ", imageguid=" + imageguid + "]";
	}

}
