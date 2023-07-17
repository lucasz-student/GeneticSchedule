package com.solvd.laba.Mappers;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.solvd.laba.Model.Teachers.Teacher;

public interface TeacherMapper {
	
	 @Insert("INSERT INTO teacher (name) VALUES(#{name})")
	    void create(Teacher teacher);

	    @Select("SELECT * FROM teacher")
	    List<Teacher> selectAll();

	    @Select("SELECT * FROM teacher WHERE teacher_id = #{id}")
	    Teacher selectById(int id);

	    @Delete("DELETE FROM teacher WHERE teacher_id= #{id}")
	    void delete(Teacher teacher);

	    @Update("UPDATE teacher SET name=#{name} WHERE teacher_id = #{id}")
	    void update(Teacher teacher);
}