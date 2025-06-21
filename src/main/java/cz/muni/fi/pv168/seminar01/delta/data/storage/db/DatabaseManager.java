package cz.muni.fi.pv168.seminar01.delta.data.storage.db;

import cz.muni.fi.pv168.seminar01.delta.data.manipulation.importer.DatabaseCredentialsImporter;
import cz.muni.fi.pv168.seminar01.delta.data.storage.dao.AutoDao;
import cz.muni.fi.pv168.seminar01.delta.data.storage.dao.CategoryDao;
import cz.muni.fi.pv168.seminar01.delta.data.storage.dao.DestinationDao;
import cz.muni.fi.pv168.seminar01.delta.data.storage.dao.RideCategoryDao;
import cz.muni.fi.pv168.seminar01.delta.data.storage.dao.RideDao;
import cz.muni.fi.pv168.seminar01.delta.error.ConnectionError;
import cz.muni.fi.pv168.seminar01.delta.error.DatabaseError;

import javax.security.auth.login.CredentialException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


/**
 * Class for managing ShareCarRider's database.
 * Connects to database server, creates database, creates tables and drops the database.
 *
 * @author Martin Vrzon
 */
public class DatabaseManager {

    private static final String PROJECT_NAME = "share_car_rider";
    private static final String TEST_PROJECT_NAME = "test_share_car_rider";

    private static void createDatabase(Connection connection) throws DatabaseError {
        try {
            var statement = connection.createStatement();
            statement.executeUpdate("CREATE DATABASE IF NOT EXISTS " + PROJECT_NAME + ";");
        } catch (SQLException e) {
            throw new DatabaseError("Could not create database", e);
        }
    }

    private static void createTables(ConnectionHandler connectionHandler) throws DatabaseError {
        DestinationDao.createTable(connectionHandler);
        AutoDao.createTable(connectionHandler);
        CategoryDao.createTable(connectionHandler);
        RideDao.createTable(connectionHandler);
        RideCategoryDao.createTable(connectionHandler);
    }

    public static ConnectionHandler initializeDatabase() throws ConnectionError {
        try {
            var credentials = DatabaseCredentialsImporter.loadCredentials("src/main/java/cz/muni/fi/pv168/seminar01/delta/data/storage/db/db_credentials.txt");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/", credentials.get("username"), credentials.get("password"));
            createDatabase(connection);
            connection.close();

            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + PROJECT_NAME, credentials.get("username"), credentials.get("password"));
            var connectionHandler = new ConnectionHandlerImpl(connection);
            createTables(connectionHandler);

            return connectionHandler;
        } catch (SQLException e) {
            throw new ConnectionError("Could not connect to the database server." +
                    " This could be due to MySQL server 1) is not running or 2) is not installed." +
                    " Please, install MySQL server. In case of any questions contact support." +
                    " For installation on Windows use link: https://dev.mysql.com/downloads/windows/installer/" +
                    " It is completely sufficient to install just MySQL server." +
                    " For further insight into the database Workbench installation is highly recommended.", e);
        } catch (CredentialException e) {
            throw new ConnectionError("Invalid credentials!", e);
        }
    }

    public static void dropDatabase(ConnectionHandler connectionHandler) throws DatabaseError {
        try {
            var statement = connectionHandler.use().prepareStatement(
                    "DROP DATABASE " + PROJECT_NAME + ";");
            statement.execute();
        } catch (SQLException e) {
            throw new DatabaseError("Could not drop database '" + PROJECT_NAME + "'", e);
        }
    }
}
