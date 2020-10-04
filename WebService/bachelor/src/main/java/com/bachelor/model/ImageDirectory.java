package com.bachelor.model;

public class ImageDirectory {
	String path;
	String imageType;

	public ImageDirectory(String path, String status) {
		this.path = path;
		this.imageType = status;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getStatus() {
		return imageType;
	}

	public void setStatus(String status) {
		this.imageType = status;
	}

	@Override
	public String toString() {
		return "Path [path=" + path + ", imageType=" + imageType + "]";
	}

}
