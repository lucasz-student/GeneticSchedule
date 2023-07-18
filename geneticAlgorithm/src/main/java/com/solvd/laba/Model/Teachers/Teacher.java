package com.solvd.laba.Model.Teachers;

public class Teacher {

	private int teacher_id; 
	private String name;
	
	public Teacher(int teacher_id, String name) {
		this.teacher_id = teacher_id;
		this.name = name;
	}

	public int getTeacherId() {
		return teacher_id;
	}

	public void setTeacherId(int teacher_id) {
		this.teacher_id = teacher_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
 
}