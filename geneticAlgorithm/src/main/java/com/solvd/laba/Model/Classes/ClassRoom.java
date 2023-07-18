package com.solvd.laba.Model.Classes;

public abstract class ClassRoom {

	private int class_id;
	private String class_name;
	private int teacher_id;

	public ClassRoom(int class_id, String class_name, int teacher_id) {
		this.class_id = class_id;
		this.class_name = class_name;
		this.teacher_id = teacher_id;
	}

	public int getId() {
		return class_id;
	}

	public void setId(int class_id) {
		this.class_id = class_id;
	}

	public String getName() {
		return class_name;
	}

	public void setName(String class_name) {
		this.class_name = class_name;
	}

	public int getTeacherId() {
		return teacher_id;
	}

	public void setTeacherId(int teacher_id) {
		this.teacher_id = teacher_id;
	}
}