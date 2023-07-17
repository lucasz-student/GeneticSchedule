package com.solvd.laba.Model.Teachers;

import com.solvd.laba.Model.Classes.ClassRoom;

public class Teacher {

	private int id; 
	private String subject;
	private ClassRoom classRoom;
	
	public Teacher(int id, String subject, ClassRoom classRoom) {
		this.setClassRoom(classRoom);
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

	public ClassRoom getClassRoom() {
		return classRoom;
	}

	public void setClassRoom(ClassRoom classRoom) {
		this.classRoom = classRoom;
	} 
}