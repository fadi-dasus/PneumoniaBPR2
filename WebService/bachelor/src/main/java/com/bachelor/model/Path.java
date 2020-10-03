package com.bachelor.model;

public class Path {
	String path;
	String status;

	public Path(String path, String status) {
		this.path = path;
		this.status = status;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Path [path=" + path + ", status=" + status + "]";
	}

}
