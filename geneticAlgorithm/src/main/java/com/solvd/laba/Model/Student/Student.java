package com.solvd.laba.Model.Student;

public class Student {

	private int id;
	private String name;
	private int groupID;
	
	public Student(int id, String name, int groupID) {
		this.groupID=groupID;
		this.id=id;
		this.name=name;
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
	
	@Override
	public String toString() {
		return this.name;
	}

	public int getGroupID() {
		return groupID;
	}

	public void setGroupID(int groupID) {
		this.groupID = groupID;
	}
}