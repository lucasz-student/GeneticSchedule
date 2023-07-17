package com.solvd.laba.Model.Student;

public class StudentClassRoom {
	
	private int studentId;
	private int classId;
	
	public StudentClassRoom(int studentId, int classId) {
		super();
		this.setStudentId(studentId);
		this.setClassId(classId);
	}

	public int getStudentId() {
		return studentId;
	}

	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}

	public int getClassId() {
		return classId;
	}

	public void setClassId(int classId) {
		this.classId = classId;
	}
	
	
	
	
}