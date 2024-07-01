package com.example.JDBC;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;



public class App {

	// connection is responsible to make connection with database
	Connection conn = null;
	// statement is used to execute sql queries from in mysql server
	Statement stm = null;
	// useful to insert alot of data
	PreparedStatement preStatement = null;


int num=0;
	App() {// this is a checked exception
		try {

			Class.forName("com.mysql.cj.jdbc.Driver"); // using here driver to connect to database
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/shop", "root", "jattdeol96");
			System.out.println("connected");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e.getMessage());
		}







		
	}

	void fetchAllData() throws Exception {

		String query = "select * from records";

		stm = conn.createStatement();
		ResultSet set = stm.executeQuery(query); // execute select query and return resultset (data in interface )

		while (set.next()) {
			System.out.println(set.getInt(1) + set.getString(2) + set.getString(3)+set.getString(4)+set.getString(5)+set.getString(6));
		}

	}

	void insertData(String name, String purchase, String email, String password, String phone) throws Exception {
		String query = "insert into records(name,purchase,email,password,phone) values(?,?,?,?,?)";

		preStatement = conn.prepareStatement(query);
		preStatement.setString(1, name);
		preStatement.setString(2, purchase);
		preStatement.setString(3, email);
		preStatement.setString(4, password);
		preStatement.setString(5, phone);
		preStatement.executeUpdate();

	}

	void fetchSingleData(String email) throws Exception {

		preStatement = conn.prepareStatement("select * from records where email = ?");
		preStatement.setString(1, email);
		ResultSet set = preStatement.executeQuery();
		set.next();
		System.out.println(set.getInt(1) + set.getString(2) + set.getString(3) + set.getString(4));

	}

	Record fetchSingleData2(String email) throws Exception {

		preStatement = conn.prepareStatement("select * from records where email = ?");
		preStatement.setString(1, email);
		ResultSet set = preStatement.executeQuery();
		set.next();
		// System.out.println(set.getInt(1) + set.getString(2)
		// +set.getString(3)+set.getString(4));
		return new Record(set.getInt(1), set.getString(2), set.getString(3), set.getString(4), set.getString(5),
				set.getString(6));

	}

	void updateRecord(String email) throws Exception {

		Record record = fetchSingleData2(email);

		record.setPhone("3456786868"); // new phone updated into record class

		String query = "update records set phone = ? where email = ?";
		preStatement = conn.prepareStatement(query);
		preStatement.setString(1, record.getPhone()); // setting latest phone from record

		preStatement.setString(2, record.getEmail());

		preStatement.execute();

	}

	public static void main(String[] args) throws Exception {
		App app = new App();

		// app.fetchAllData();

		// app.insertData("ravi","50000","ravi@gmail.com","asd","3452335949");

		// app.fetchAllData();
		// app.fetchSingleData("ravi@gmail.com");
		app.updateRecord("ravi@gmail.com");
		app.fetchAllData();

	}
}
