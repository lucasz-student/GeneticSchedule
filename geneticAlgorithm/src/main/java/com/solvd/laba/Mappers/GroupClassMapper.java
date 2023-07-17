package com.solvd.laba.Mappers;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import com.solvd.laba.Model.Groups.GroupClass;

@Mapper
public interface GroupClassMapper {
	
	@Insert("INSERT INTO group_class (group_id, class_id) VALUES(#{groupId},#{classId})")
    void create(GroupClass groupClass);

    @Select("SELECT * FROM group_class")
    List<GroupClass> selectAll();

    @Select("SELECT * FROM group_class WHERE group_id = #{groupId}")
    GroupClass selectById(int id);

    @Delete("DELETE FROM group_class WHERE group_id= #{groupId}")
    void delete(GroupClass groupClass);

    @Update("UPDATE group_class SET class_id = #{classId} WHERE group_id=#{groupId}")
    void update(GroupClass groupClass);
	
}