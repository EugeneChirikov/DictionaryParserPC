

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import com.mates120.dictionaryparser.Storage;
import com.mates120.dictionaryparser.Exceptions.DictionaryParserException;
import com.mates120.dictionaryparser.debug.Logger;

public class TestStorage implements Storage
{
	static final String TABLE_WORDS = "words_table";
	static final String COL_WORDS_ID = "_id";
	static final String COL_WORDS_SOURCE = "source";
	static final String COL_WORDS_VALUE = "value";
//	private static final int DATABASE_VERSION = 1;
	static final String DB_NAME = "dict0";
	
	private static final String DATABASE_CREATE_WORDS = "create table "
	+ TABLE_WORDS + "(" + COL_WORDS_ID
	+ " integer primary key autoincrement, " + COL_WORDS_SOURCE
	+ " text unique not null, " + COL_WORDS_VALUE
	+ " text not null);";
	
	static final String TABLE_ANDROIDMETA = "android_metadata";
	private static final String DATABASE_CREATE_ANDROIDMETA = "create table "
			+ TABLE_ANDROIDMETA + "(locale TEXT DEFAULT \'en_US\');";
	
	private static Connection connection;
	private Statement statement;
	
	public void insertWord(String word, String value) throws DictionaryParserException
	{
		try
		{
			statement = connection.createStatement();
			String sql = "INSERT INTO " + TABLE_WORDS + " (" 
					+ COL_WORDS_SOURCE + ", " + COL_WORDS_VALUE 
					+ ") VALUES (\'" + word + "\', \'" + value + "\')";
//			Logger.l().PRINT(sql);
			statement.executeUpdate(sql);
		} catch (Exception e)
		{
			e.printStackTrace();
			throw new DictionaryParserException("Failed to insert word into db");
		}
		finally
		{
			try {
				statement.close();
			} catch (SQLException e) {
				throw new DictionaryParserException("Failed to close statement");
			}
		}
	}
	
	public void establishConnection() throws Exception
	{
		Class.forName("org.sqlite.JDBC");		
		connection = DriverManager.getConnection("jdbc:sqlite:" + DB_NAME);		
		createWordsTable();
		createAndroidMetaTable();
	}
	
	private void createWordsTable() throws Exception
	{
		statement = connection.createStatement();
		statement.executeUpdate("drop table if exists " + TABLE_WORDS);
		statement.executeUpdate(DATABASE_CREATE_WORDS);
		statement.close();
	}
	
	private void createAndroidMetaTable() throws Exception
	{
		statement = connection.createStatement();
		statement.executeUpdate("drop table if exists " + TABLE_ANDROIDMETA);
		statement.executeUpdate(DATABASE_CREATE_ANDROIDMETA);		
		statement.executeUpdate("INSERT INTO " + TABLE_ANDROIDMETA
				+ " VALUES (\'en_US\')");
		statement.close();
	}

	@Override
	public void open() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
		
	}
}
