package com.solvd.laba.Model.Classes;

import com.solvd.laba.Model.Teachers.Teacher;

public abstract class AbstractClass {

	private int id;
	private String name;
	private Teacher teacher;
	
	public AbstractClass(int id, String name, Teacher teacher) {
		this.id=id;
		this.name=name;
		this.teacher=teacher;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}
}
