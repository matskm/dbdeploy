package net.sf.dbdeploy.database.changelog;

import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLDatabaseSchemaVersionManagerTest extends AbstractDatabaseSchemaVersionManagerTestBase {

	static final String CONNECTION_STRING = "jdbc:mysql://localhost/dbdeploy";
	static final String USERNAME = "dbdeploy_db";
	static final String PASSWORD = "dbdeploy_db";
	static final String DELTA_SET = "All";
	static final String CHANGELOG_TABLE_DOES_NOT_EXIST_MESSAGE = "Could not retrieve change log from database because: Table 'dbdeploy.changelog' doesn't exist";
	
	protected String getConnectionString() {
		return CONNECTION_STRING;
	}
	
	protected String getUsername() {
		return USERNAME;
	}
	
	protected String getPassword() {
		return PASSWORD;
	}
	
	protected String getDeltaSet() {
		return DELTA_SET;
	}
	
	protected String getChangelogTableDoesNotExistMessage() {
		return CHANGELOG_TABLE_DOES_NOT_EXIST_MESSAGE;
	}
	
	protected void createTable() throws SQLException {
		executeSql(
		"CREATE TABLE changelog ( " +
				"change_number INTEGER, " +
				"delta_set VARCHAR(10) NOT NULL, " +
				"start_dt TIMESTAMP NOT NULL, " +
				"complete_dt TIMESTAMP NOT NULL, " +
				"applied_by VARCHAR(100) NOT NULL, "+
			    "description VARCHAR(500) NOT NULL )");
		executeSql(
		"ALTER TABLE changelog " +
			" ADD CONSTRAINT PK_VERSION_MGR PRIMARY KEY (change_number, delta_set)");

	}

	protected void insertRowIntoTable(int i) throws SQLException {
		executeSql("INSERT INTO changelog " 
				+ " (change_number, delta_set, start_dt, complete_dt, applied_by, description) VALUES ( " 
				+ i + ", '" + DELTA_SET 
				+ "', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, USER(), 'Unit test')");
	}

	protected void registerDbDriver() throws SQLException {
		DriverManager.registerDriver (new com.mysql.jdbc.Driver());
	}
}