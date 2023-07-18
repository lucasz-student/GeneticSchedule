package com.solvd.laba.DAO;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.solvd.laba.Model.Student.Student;

public interface StudentMapper {
	
	@Insert("INSERT INTO student (student_id, name, group_id) VALUES(#{id}, #{name}, #{groupId})")
	void create(Student student);

	@Select("SELECT * FROM student")
	List<Student> selectAll();

	@Select("SELECT * FROM student WHERE student_id = #{id}")
	Student selectById(int id);

	@Delete("DELETE FROM student WHERE student_id= #{id}")
	void delete(Student student);

	@Update("UPDATE student SET name=#{name} WHERE student_id=#{id}")
	void update(Student student);
}