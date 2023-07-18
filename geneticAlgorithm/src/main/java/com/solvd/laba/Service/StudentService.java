package com.solvd.laba.Service;

import java.io.IOException; 
import java.util.List;

import com.solvd.laba.Model.Student.Student;
import com.solvd.laba.Model.Teachers.Teacher;

import mappers.StudentMapper;
import mappers.TeacherMapper;

public class StudentService extends AbstractService {
	StudentMapper studentMapper = null;

	public StudentService() throws IOException {
		super(); 
		studentMapper = this.getSession().getMapper(StudentMapper.class);
	}

	public List<Student> getAllStudents(){
		return studentMapper.selectAll(); 
	}
}
