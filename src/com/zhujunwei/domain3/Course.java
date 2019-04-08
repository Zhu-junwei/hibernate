package com.zhujunwei.domain3;

import java.util.HashSet;
import java.util.Set;

public class Course {
	
	private Long c_id ;
	private String c_name ;
	private Set<Student> students = new HashSet<Student>();
	
	public Long getC_id() {
		return c_id;
	}
	public void setC_id(Long c_id) {
		this.c_id = c_id;
	}
	public String getC_name() {
		return c_name;
	}
	public void setC_name(String c_name) {
		this.c_name = c_name;
	}
	public Set<Student> getStudents() {
		return students;
	}
	public void setStudents(Set<Student> students) {
		this.students = students;
	}
	
	
	
}
