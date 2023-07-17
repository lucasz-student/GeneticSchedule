package com.solvd.laba.Model.Groups;

public class GroupClass {
	
	private int groupId;
	private int classId;
	
	public GroupClass(int groupId, int classId) {
		super();
		this.setGroupId(groupId);
		this.setClassId(classId);
	}

	public int getClassId() {
		return classId;
	}

	public void setClassId(int classId) {
		this.classId = classId;
	}

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}
}