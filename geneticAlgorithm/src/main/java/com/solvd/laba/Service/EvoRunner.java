package com.solvd.laba.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.jgap.IChromosome;
import org.jgap.InvalidConfigurationException;

import com.solvd.laba.DAO.StudentMapper;
import com.solvd.laba.Model.Groups.Group;
import com.solvd.laba.Model.Student.Student;

public class EvoRunner {
	
	
    public static void main(String[] args) throws InvalidConfigurationException, IOException {
    	
    	System.out.println("\r\n"
    			+ "Find your ideal schedule effortlessly with our smart program! Using the genetic algorithm, "
    			+ "\nwe create a personalized timetable that fits your needs perfectly. "
    			+ "\nSay goodbye to scheduling stress and enjoy a smooth academic experience. "
    			+ "\nExperience the convenience of our user-friendly solution tailored just for you. "
    			+ "\nGet started today and discover the perfect schedule for your success!"
    			+ "\n\nLet's start with your requirements!\n"
    			);
    	
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
				if (s.getGroupID()==1) {
					group1.addStudent(s);
				} else if (s.getGroupID()==2) {
					group2.addStudent(s);
				} else {
					group3.addStudent(s);
				}
			});
		}
  	
    	Scanner scanner = new Scanner(System.in);
        int minDays = 0;
        int maxDays = 0;
        int minPeriods = 0;
        int maxPeriods = 0;

        try {
            System.out.println("How many minimum days would you like to work?");
            minDays = Integer.parseInt(scanner.nextLine());
            System.out.println("How many maximum days would you like to work?");
            maxDays = Integer.parseInt(scanner.nextLine());
            System.out.println("How many minimum periods (classes) would you like to have on work days?");
            minPeriods = Integer.parseInt(scanner.nextLine());
            System.out.println("How many maximum periods (classes) would you like to have on work days?");
            maxPeriods = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter valid integer values.");
            System.exit(1); 
        }
		scanner.close();
    	
		System.out.println("Calculating best schedule...");

        SchedulingService schedulingService = new SchedulingService();
        IChromosome bestSolution = schedulingService.schedule(minDays, maxDays, minPeriods, maxPeriods);
        schedulingService.displaySchedule(bestSolution);
       
        System.out.println();
        System.out.println("Meet your new classmates!");
        System.out.println();
        
        System.out.println("Here's all the students that are in group 1 (your group)! :");
        group1.printStudents();
        System.out.println();
        
        System.out.println("Here's all the students that are in group 2! :");
        group2.printStudents();
        System.out.println();
        
        System.out.println("Here's all the students that are in group 3! :");
        group3.printStudents();
        System.out.println();
    }
}