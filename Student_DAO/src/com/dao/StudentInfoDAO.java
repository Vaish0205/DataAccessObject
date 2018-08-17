package com.dao;

public interface StudentInfoDAO {
	Student login(int sid, String password);
	boolean createProfile(Student s);
	Student searchStudent(int sid);
	boolean deleteStudent(int sid, String password);
}
