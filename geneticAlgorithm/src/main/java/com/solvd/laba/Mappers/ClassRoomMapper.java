package com.solvd.laba.Mappers;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.solvd.laba.Model.Classes.ClassRoom;


public interface ClassRoomMapper {
	
	 @Insert("INSERT INTO class (class_id, class_name, teacher_id) VALUES(#{id},#{name}, #{Teacher.id})")
	    void create(ClassRoom classRoom);

	    @Select("SELECT * FROM class")
	    List<ClassRoom> selectAll();

	    @Select("SELECT * FROM class WHERE class_id = #{id}")
	    ClassRoom selectById(int id);

	    @Delete("DELETE FROM class WHERE class_id= #{id}")
	    void delete(ClassRoom classRoom);

	    @Update("UPDATE class SET class_name = #{name} WHERE class_id = #{id}")
	    void update(ClassRoom classRoom);
	    
}