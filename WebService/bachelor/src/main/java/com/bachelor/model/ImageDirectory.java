package com.bachelor.model;

public class ImageDirectory {
	String sourceImagePath;
	String imageType;

	public ImageDirectory(String temporaryPath, String status) {
		this.sourceImagePath = temporaryPath;
		this.imageType = status;
	}

	public String getSourceImagePath() {
		return sourceImagePath;
	}

	public void setSourceImagePath(String sourceImagePath) {
		this.sourceImagePath = sourceImagePath;
	}

	public String getStatus() {
		return imageType;
	}

	public void setStatus(String status) {
		this.imageType = status;
	}

	@Override
	public String toString() {
		return "Path [path=" + sourceImagePath + ", imageType=" + imageType + "]";
	}

}
