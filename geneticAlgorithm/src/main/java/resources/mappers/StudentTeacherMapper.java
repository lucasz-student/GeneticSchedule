package mappers;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.solvd.laba.Model.Classes.ClassRoom;
import com.solvd.laba.Model.Student.StudentClassRoom;
import com.solvd.laba.Model.Student.StudentTeacher;

public interface StudentTeacherMapper {
	
	@Insert("INSERT INTO student_teacher (student_id, teacher_id) VALUES(#{studentId},#{teacherId})")
    void create(StudentTeacher studentTeacher);

    @Select("SELECT * FROM student_teacher")
    List<StudentTeacher> selectAll();

    @Select("SELECT * FROM student_teacher WHERE student_id = #{studentId}")
    ClassRoom selectById(int id);

    @Delete("DELETE FROM student_teacher WHERE student_id= #{studentId}")
    void delete(StudentClassRoom studentClassRoom);

    @Update("UPDATE student_teacher SET teacher_id = #{teacherId} WHERE student_id = #{studentId}")
    void update(StudentClassRoom studentClassRoom);
    
}
