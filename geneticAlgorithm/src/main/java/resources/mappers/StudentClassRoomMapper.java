package mappers;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.solvd.laba.Model.Classes.ClassRoom;
import com.solvd.laba.Model.Student.StudentClassRoom;

public interface StudentClassRoomMapper {
	
	@Insert("INSERT INTO student_class (student_id, class_id) VALUES(#{studentId},#{classId})")
    void create(StudentClassRoom studentClassRoom);

    @Select("SELECT * FROM student_class")
    List<StudentClassRoom> selectAll();

    @Select("SELECT * FROM student_class WHERE student_id = #{studentId}")
    ClassRoom selectById(int id);

    @Delete("DELETE FROM student_class WHERE student_id= #{studentId}")
    void delete(StudentClassRoom studentClassRoom);

    @Update("UPDATE student_class SET class_id = #{classId} WHERE student_id = #{studentId}")
    void update(StudentClassRoom studentClassRoom);
    
}
