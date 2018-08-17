package com.dao;

public class Dao_main {
	public static void main(String[] args) {
		StudentInfoDAO db=new DAO_JDBCImpl();
		Student s = null;
		db.login(1, "root");
		System.out.println("****************************************");
		db.searchStudent(5);
		System.out.println("****************************************");
		db.createProfile(s);
		System.out.println("****************************************");
		db.deleteStudent(4, "root");
	}
}
