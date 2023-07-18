package com.solvd.laba.Service;

import java.io.IOException;
import java.util.List;

import org.jgap.IChromosome;
import org.jgap.InvalidConfigurationException;

import com.solvd.laba.Model.Classes.ClassRoom;
import com.solvd.laba.Model.Student.Student;
import com.solvd.laba.Model.Teachers.Teacher;

public class EvoRunner {

	public static void main(String[] args) throws InvalidConfigurationException {

//   	 try {
// 			TeacherService tService = new TeacherService();
//  			List<Teacher> teachers = tService.getAllTeachers();
// 			for(int i = 0; i < teachers.size();i++) {
//
// 	 			System.out.println(teachers.get(i).getName());	
// 			}
//    		 StudentService studentService = new StudentService();
//    		 List<Student> students = studentService.getAllStudents();
//    		 for(int i = 0; i < students.size();i++) {
//    			 
//    			  	 			System.out.println(students.get(i).getName());	
//    			  			}
// 		} catch (IOException e) {
// 			// TODO Auto-generated catch block
// 			e.printStackTrace();
// 		}
//    		 ClassRoomService classRoomService;
//		
//				classRoomService = new ClassRoomService();
//			
//    		 	List<ClassRoom> classRooms = classRoomService.getAllClassRooms();
//    		 	for(int i = 0; i < classRooms.size();i++) {
//    		 		
//    		 		System.out.print(classRooms.get(i).getName());
//    		 	}
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}

		int[] numbers = new int[args.length];
		System.out.println(
				"Please enter 'MinimumDays, MaximumDays, MinimumPeriods, MaximumPeriods' as parameters for your best school schedule");
		if (args.length > 0) {
			for (int i = 0; i < args.length; i++) {
				try {
					numbers[i] = Integer.parseInt(args[i]);
				} catch (NumberFormatException e) {
					System.err.println("Invalid input: Command line argument at index " + i + " must be an integer.");
					return;
				}
			}
		}

		SchedulingService schedulingService = new SchedulingService();
		IChromosome bestSolution = schedulingService.schedule(numbers[0], numbers[1], numbers[2], numbers[3]);
		System.out.println("best solution fitness value: " + bestSolution.getFitnessValue());
		schedulingService.displaySchedule(bestSolution);
	}
}