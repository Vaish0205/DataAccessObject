package com.dao;

import java.io.FileReader;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Scanner;

import com.mysql.jdbc.Driver;

public class DAO_JDBCImpl implements StudentInfoDAO{

	@Override
	public Student login(int sid, String password) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Student s=null;
		
		try {
			java.sql.Driver driverRef = new Driver();
			DriverManager.registerDriver(driverRef);
			System.out.println("Driver Loaded...");
			
			String dbUrl="jdbc:mysql://localhost:3307/capsV3_db";
			String filePath = "F:/Files/db.properties";
			FileReader reader = new FileReader(filePath);
			Properties prop = new Properties();
			prop.load(reader);
			con = DriverManager.getConnection(dbUrl, prop);
			System.out.println("Connected...");
			
			String sql = "select * from students_info where sid=? and password=?";
			
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1,sid);
			pstmt.setString(2, password);
			rs = pstmt.executeQuery();
			
			if(rs.next()){
				int regno = rs.getInt("sid");
				String firstname = rs.getString("firstname");
				String lastname = rs.getString("lastname");
				String isAdmin = rs.getString("isadmin");
				String passwd = rs.getString("password");

				s=new Student();
				s.setSid(regno);
				s.setFirstname(firstname);
				s.setLastname(lastname);
				s.setIsadmin(isAdmin);
				s.setPassword(passwd);
				
				System.out.println(s);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(con != null){
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(pstmt != null){
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(rs != null){
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

		}
		return s;
	}

	@Override
	public boolean createProfile(Student s) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			java.sql.Driver driverRef = new Driver();
			DriverManager.registerDriver(driverRef);
			System.out.println("Driver Loaded...");
			String dbUrl="jdbc:mysql://localhost:3307/capsV3_db";
			
			String filePath = "F:/Files/db.properties";
			FileReader reader = new FileReader(filePath);
			Properties prop = new Properties();
			prop.load(reader);
			con = DriverManager.getConnection(dbUrl, prop);
			System.out.println("Connected...");
			
			Scanner in = new Scanner(System.in);
			System.out.println("Enter regno : ");
			int regno= Integer.parseInt(in.nextLine());
			System.out.println("Enter First Name: ");
			String fname = in.nextLine();
			System.out.println("Enter Last Name: ");
			String lname = in.nextLine();
			System.out.println("Enter is Admin: ");
			String isAdmin = in.nextLine();
			System.out.println("Enter Password: ");
			String passwd = in.nextLine();
			in.close();
			
			String sql = "insert into students_info values(?,?,?,?,?)";
			pstmt = con.prepareStatement(sql);
			
			pstmt.setInt(1, regno);
			pstmt.setString(2,fname);
			pstmt.setString(3, lname);
			pstmt.setString(4, isAdmin.toUpperCase());
			pstmt.setString(5, passwd);
			
			int count = pstmt.executeUpdate();
			
			if(count > 0) {
				System.out.println("Profile Created Successfully!!!");
			}else {
				System.out.println("Profile Creation Failed!!!");
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			
			if(con != null){
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(pstmt != null){
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

		}
		return false;
	}

	@Override
	public Student searchStudent(int sid) {
		Connection con = null;
		CallableStatement cstmt = null;
		ResultSet rs = null;
		Student s=null;
		
		try {
			java.sql.Driver driverRef = new Driver();
			DriverManager.registerDriver(driverRef);
			System.out.println("Driver Loaded...");
			
			String dbUrl="jdbc:mysql://localhost:3307/capsV3_db";
			String filePath = "F:/Files/db.properties";
			FileReader reader = new FileReader(filePath);
			Properties prop = new Properties();
			prop.load(reader);
			con = DriverManager.getConnection(dbUrl, prop);
			System.out.println("Connected...");
			
			String sql = "call getStudentsInfo(?)";
			cstmt = con.prepareCall(sql);
			cstmt.setInt(1, sid);
			rs = cstmt.executeQuery();
			
			if(rs.next()){
				int regno = rs.getInt("sid");
				String firstname = rs.getString("firstname");
				String lastname = rs.getString("lastname");
				String isAdmin = rs.getString("isadmin");
				String passwd = rs.getString("password");

				System.out.println(regno);
				System.out.println(firstname);
				System.out.println(lastname);
				System.out.println(isAdmin);
				System.out.println(passwd);
				System.out.println();
			}else {
				System.out.println("No Data is Present");
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			
			if(con != null){
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(cstmt != null){
				try {
					cstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

		}
		return s;
	}

	@Override
	public boolean deleteStudent(int sid, String password) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			
			java.sql.Driver driverRef = new Driver();
			DriverManager.registerDriver(driverRef);
			System.out.println("Driver class loaded and registered...");
			String dbUrl="jdbc:mysql://localhost:3307/capsV3_db?user=root&password=root";
			con = DriverManager.getConnection(dbUrl);
			System.out.println("Connected...");
			String sql = "delete from students_info where sid=? and password=?";
			
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1,sid);
			pstmt.setString(2, password);
			
			int count = pstmt.executeUpdate();

			if(count > 0) {
				System.out.println("Data Deleted Successfully...");
			}else {
				System.out.println("No Data found....Failed!!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(con != null){
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(pstmt != null){
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(rs != null){
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

		}
		return false;
	}

}
