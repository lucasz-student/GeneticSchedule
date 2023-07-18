package com.solvd.laba.Service;

import java.io.IOException;
import java.util.List;

import com.solvd.laba.Model.Classes.ClassRoom;

import mappers.ClassRoomMapper;

public class ClassRoomService extends AbstractService{
	
	ClassRoomMapper classRoomMapper = null;

	public ClassRoomService() throws IOException {
		super(); 
		classRoomMapper = this.getSession().getMapper(ClassRoomMapper.class);
	}
	public List<ClassRoom> getAllClassRooms(){
			return classRoomMapper.selectAll(); 
	}
}
