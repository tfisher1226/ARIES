package org.aries.common;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import com.mysql.jdbc.jdbc2.optional.MysqlXADataSource;


public class DataSourceTestFixture {

	public static Log log = LogFactory.getLog(DataSourceTestFixture.class);
	
	private static MysqlDataSource dataSource;

	private static MysqlXADataSource xaDataSource;

	private static TestXADataSource testDataSource;


	public static DataSource createTestDataSource(String url) {
		return createTestDataSource(url, null, null);
	}
	
	public static DataSource createTestDataSource(String url, String userName, String password) {
		dataSource = new MysqlDataSource(); 
		dataSource.setUrl(url);  
		dataSource.setUser(userName);
		dataSource.setPassword(password);
		return dataSource;
	}
	
	public static TestXADataSource createTestXADataSource(String url) throws SQLException {
		return createTestXADataSource(url, null, null);
	}
	
	public static TestXADataSource createTestXADataSource(String url, String userName, String password) throws SQLException {
		xaDataSource = new MysqlXADataSource(); 
        xaDataSource.setUrl(url);  
        xaDataSource.setUser(userName);
        xaDataSource.setPassword(password);
        //dataSource.setAutoReconnect(true);
        xaDataSource.setConnectTimeout(50000000);
        xaDataSource.setLogXaCommands(true);
        xaDataSource.setRelaxAutoCommit(true);
        //dataSource.setRequireSSL(true);
        //dataSource.setRollbackOnPooledClose(true);
        //dataSource.setStrictUpdates(true);
        //dataSource.setSocketTimeout(property);
        xaDataSource.setPinGlobalTxToPhysicalConnection(true);
        
        testDataSource = new TestXADataSource(xaDataSource);
        return testDataSource;
	}

}
