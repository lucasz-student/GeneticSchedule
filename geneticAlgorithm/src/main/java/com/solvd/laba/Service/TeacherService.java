package com.solvd.laba.Service;

import java.io.IOException; 
import java.util.List;

import com.solvd.laba.Model.Teachers.Teacher;

import mappers.TeacherMapper;

public class TeacherService extends AbstractService {
	TeacherMapper teacherMapper = null;

	public TeacherService() throws IOException {
		super(); 
		teacherMapper = this.getSession().getMapper(TeacherMapper.class);
	}

	public List<Teacher> getAllTeachers(){
		return teacherMapper.selectAll(); 
	}
}
