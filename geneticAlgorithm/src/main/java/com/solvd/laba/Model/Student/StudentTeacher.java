package com.solvd.laba.Model.Student;

public class StudentTeacher {
	
	private int studentId;
	private int teacherId;
	
	public StudentTeacher(int studentId, int teacherId) {
		super();
		this.setStudentId(studentId);
		this.setTeacherId(teacherId);
	}

	public int getStudentId() {
		return studentId;
	}

	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}

	public int getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(int teacherId) {
		this.teacherId = teacherId;
	}
	
}