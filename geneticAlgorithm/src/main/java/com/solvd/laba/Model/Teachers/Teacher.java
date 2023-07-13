package com.solvd.laba.Model.Teachers;

public class Teacher {

	private int id; 
	private String subject;
	
	public Teacher(int id, String subject) {
		this.id = id;
		this.subject = subject;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	} 
}
