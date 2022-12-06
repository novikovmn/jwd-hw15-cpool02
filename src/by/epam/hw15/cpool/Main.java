package by.epam.hw15.cpool;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {

	public static final String SQL = "SELECT * FROM users";

	public static void main(String[] args) throws ConnectionPoolException, SQLException {

		// create cpool-instance
		ConnectionPool connectionPool = ConnectionPool.getInstance();
		// create connections of the cpool
		connectionPool.initPoolData();

		// retrieve connection
		Connection connection = connectionPool.takeConnection();

		// create stmnt
		Statement statement = connection.createStatement();

		// execute sql and get result
		ResultSet resultSet = statement.executeQuery(SQL);

		while (resultSet.next()) {
			int id = resultSet.getInt("user_id");
			String firstName = resultSet.getString("first_name");
			String lastName = resultSet.getString("last_name");
			String login = resultSet.getString("login");
			String password = resultSet.getString("password");
			String email = resultSet.getString("email");
			System.out.printf("%d, %s, %s, %s, %s, %s\n", id, firstName, lastName, login, password, email);
		}
		
		/*
		 * now we need to close all resouces and put the connection back
		 * into pool
		 * */

		// closing and putting back
		connectionPool.closeConnection(connection, statement, resultSet);
		// clear the main and the reserve queues
		connectionPool.dispose();

	}

}
