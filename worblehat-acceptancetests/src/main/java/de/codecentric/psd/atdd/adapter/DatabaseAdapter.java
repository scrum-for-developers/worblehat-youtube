package de.codecentric.psd.atdd.adapter;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.jbehave.core.annotations.BeforeStories;


public class DatabaseAdapter {

	private Connection connection;

	@BeforeStories
	public void connectToDatabase() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		this.connection = DriverManager.getConnection(Config.getDbURL(),Config.getDbUser(),Config.getDbPassword());

	}

	public Connection getConnection() {
		return connection;
	}

	public void emptyTable(String table)
			throws SQLException {
		String statement = "DELETE FROM " + table;
		execute(statement);
	}

	public void execute(String statement) throws SQLException {
		getConnection().createStatement().execute(statement);
	}

	public void shouldReturnExactlyOne(String statement) throws SQLException {
		ResultSet resultSet = getResultSet(statement);
		assertThat(resultSet.first(), is(true));
	}

	public void shouldReturnNothing(String statement) throws SQLException {
		ResultSet resultSet = getResultSet(statement);
		assertThat(resultSet.first(), is(false));
	}

	private ResultSet getResultSet(String statement) throws SQLException {
		Statement s = connection.createStatement();
		s.execute(statement);
		ResultSet resultSet = s.getResultSet();
		return resultSet;
	}

	public String getResult(String statement) throws SQLException {
		ResultSet resultSet = getResultSet(statement);
		resultSet.first();
		return resultSet.getString(1);
	}

	public String executeAndReturn(String statement, String column) throws SQLException {
		ResultSet resultSet = getResultSet(statement);
		resultSet.first();
		return resultSet.getString(column);
	}

}
