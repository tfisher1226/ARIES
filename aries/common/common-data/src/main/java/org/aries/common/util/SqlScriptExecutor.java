package org.aries.common.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;
import org.hibernate.internal.util.ConfigHelper;
import org.hibernate.internal.util.StringHelper;
import org.hibernate.tool.hbm2ddl.ImportScriptException;
import org.hibernate.tool.hbm2ddl.ImportSqlCommandExtractor;
import org.hibernate.tool.hbm2ddl.ImportSqlCommandExtractorInitiator;


public class SqlScriptExecutor {

	private static Log log = LogFactory.getLog(SqlScriptExecutor.class);
	
	private DataSource dataSource;
	
	
	public SqlScriptExecutor(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	public void executeScript(String fileName) throws Exception {
		final List<NamedReader> importFileReaders = new ArrayList<NamedReader>();
		try {
			String resourceName = fileName.trim();
			log.info("Executing: "+resourceName);
			InputStream stream = ConfigHelper.getResourceAsStream(resourceName);
			NamedReader namedReader = new NamedReader(resourceName, stream);
			executeScript(namedReader);
		}
		catch ( HibernateException e ) {
			log.warn("SQL file not found: "+fileName);
		}
	}
	
	protected void executeScript(NamedReader namedReader) throws Exception {
		BufferedReader reader = new BufferedReader(namedReader.getReader());
		
		SqlExceptionHelper sqlExceptionHelper = new SqlExceptionHelper();
		DatabaseExporter databaseExporter = new DatabaseExporter(sqlExceptionHelper);
		
		ImportSqlCommandExtractor importSqlCommandExtractor = ImportSqlCommandExtractorInitiator.DEFAULT_EXTRACTOR;
		String[] statements = importSqlCommandExtractor.extractCommands(reader);
		if (statements != null) {
			for (String statement : statements) {
				if (statement != null ) {
					String trimmedSql = statement.trim();
					if (trimmedSql.endsWith(";")) {
						trimmedSql = trimmedSql.substring(0, statement.length() - 1);
					}
					if (!StringHelper.isEmpty(trimmedSql)) {
						try {
							databaseExporter.export(trimmedSql);
						} catch (Exception e) {
							throw new ImportScriptException("Error during statement execution (file: '" + namedReader.getName() + "'): " + trimmedSql, e);
						}
					}
				}
			}
		}
	}
	
	private static class NamedReader {
		private final Reader reader;
		private final String name;

		public NamedReader(String name, InputStream stream) {
			this.name = name;
			this.reader = new InputStreamReader(stream);
		}

		public Reader getReader() {
			return reader;
		}

		public String getName() {
			return name;
		}
	}
	
	interface Exporter {
		public boolean acceptsImportScripts();
		public void export(String string) throws Exception;
		public void release() throws Exception;
	}
	
	class DatabaseExporter implements Exporter {

		private final Connection connection;
		
		private final Statement statement;

		private final SqlExceptionHelper sqlExceptionHelper;

		public DatabaseExporter(SqlExceptionHelper sqlExceptionHelper) throws SQLException {
			this.sqlExceptionHelper = sqlExceptionHelper;

			connection = getConnection(true);
			statement = connection.createStatement();
		}

		@Override
		public boolean acceptsImportScripts() {
			return true;
		}

		@Override
		public void export(String string) throws Exception {
			statement.executeUpdate(string);
			try {
				SQLWarning warnings = statement.getWarnings();
				if (warnings != null) {
					sqlExceptionHelper.logAndClearWarnings(connection);
				}
			}
			catch( SQLException e ) {
				log.error( e );
			}
		}

		@Override
		public void release() throws Exception {
			try {
				statement.close();
			}
			finally {
				connection.close();
			}
		}
	}
	
	public Connection getConnection(boolean needsAutoCommit) throws SQLException {
		Connection connection = dataSource.getConnection("root", "");
		boolean toggleAutoCommit = needsAutoCommit && !connection.getAutoCommit();
		if (toggleAutoCommit) {
			try {
				connection.commit();
			} catch (Throwable ignore) {
				// might happen with a managed connection
			}
			connection.setAutoCommit(true);
		}
		return connection;
	}
	
	
}
