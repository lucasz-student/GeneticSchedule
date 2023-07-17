package com.solvd.laba.Model.Groups;

import java.util.ArrayList;
import java.util.List;

import com.solvd.laba.Model.Classes.ClassRoom;
import com.solvd.laba.Model.Student.Student;


public class Group {

	private List<Student> students = new ArrayList<>();
	private List<ClassRoom> classes = new ArrayList<>();
	

	public Group(List<Student> students, List<ClassRoom> classes) {
		super();
		this.students = students;
		this.classes = classes;
	}
	
	public List<Student> getStudents() {
		return students;
	}
	public void setStudents(List<Student> students) {
		this.students = students;
	}
	public List<ClassRoom> getClasses() {
		return classes;
	}
	public void setClasses(List<ClassRoom> classes) {
		this.classes = classes;
	}
}