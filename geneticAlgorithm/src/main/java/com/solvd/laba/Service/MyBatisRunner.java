package com.solvd.laba.Service;
	
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.solvd.laba.DAO.StudentMapper;
import com.solvd.laba.DAO.TeacherMapper;
import com.solvd.laba.Model.Groups.Group;
import com.solvd.laba.Model.Student.Student;
	
	public class MyBatisRunner {
	
		public static void main(String[] args) throws IOException {
			InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");
	        InputStream dbPropertiesStream = Resources.getResourceAsStream("db.properties");
	        Properties dbProperties = new Properties();
	        dbProperties.load(dbPropertiesStream);
			
	        Properties properties = new Properties();
	        properties.setProperty("driver", dbProperties.getProperty("db.driver"));
	        properties.setProperty("url", dbProperties.getProperty("db.url"));
	        properties.setProperty("username", dbProperties.getProperty("db.user"));
	        properties.setProperty("password", dbProperties.getProperty("db.password"));
			
			SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream, properties);
			Group group1 = new Group();
			Group group2 = new Group();
			Group group3 = new Group();
			
			try (SqlSession session = sqlSessionFactory.openSession()) {
				StudentMapper studentMapper = session.getMapper(StudentMapper.class);
				List<Student> ls = studentMapper.selectAll();
				ls.forEach((s) -> {
					System.out.println(s.getGroupID());
					if (s.getGroupID()==1) {
						group1.addStudent(s);
					} else if (s.getGroupID()==2) {
						group2.addStudent(s);
					} else {
						group3.addStudent(s);
					}
				});
				
				TeacherMapper TeacherMapper = session.getMapper(TeacherMapper.class);

				TeacherMapper.selectAll().forEach((t) -> {System.out.println(t.getName());});
			}
		}	
	}