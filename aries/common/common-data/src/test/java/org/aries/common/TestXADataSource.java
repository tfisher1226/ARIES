package org.aries.common;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.sql.DataSource;
import javax.sql.XAConnection;
import javax.transaction.Transaction;
import javax.transaction.xa.XAResource;

import org.aries.tx.TransactionProvider;

import com.mysql.jdbc.jdbc2.optional.MysqlXADataSource;


public class TestXADataSource implements DataSource {
    
    private MysqlXADataSource delegate;

    private XAConnection xaConnection;
    
//	private Transaction transaction;
    
    private Object transactionId;

    private TransactionProvider transactionProvider;
    
	private List<XAResource> enlistedResources;
	
    
    public TestXADataSource() {
    }
    
	public TestXADataSource(MysqlXADataSource delegate) {
    	this.delegate = delegate;
    	enlistedResources = new ArrayList<XAResource>();
    	//TransactionSynchronizationRegistry transactionSynchronizationRegistry = jtaPropertyManager.getJTAEnvironmentBean().getTransactionSynchronizationRegistry();
    }
    
	public void setTransactionProvider(TransactionProvider transactionProvider) {
		this.transactionProvider = transactionProvider;		
	}
	
	@Override
    public Connection getConnection() throws SQLException {
//		if (transaction == null)
//			return delegate.getConnection();
		if (transactionProvider == null)
			return delegate.getConnection();
		if (xaConnection == null)
			xaConnection = delegate.getXAConnection();
		enlistResource(xaConnection.getXAResource());
		Connection connection = xaConnection.getConnection();
		return connection;
    }
	
	@Override
    public Connection getConnection(String user, String password) throws SQLException {
//		if (transaction == null)
//			return delegate.getConnection(user, password);
		if (transactionProvider == null)
			return delegate.getConnection(user, password);
		if (xaConnection == null)
			xaConnection = delegate.getXAConnection(user, password);
		enlistResource(xaConnection.getXAResource());
		Connection connection = xaConnection.getConnection();
		return connection;
    }

	protected boolean enlistResource(XAResource xaResource) {
//		if (transaction == null)
//			return false;
		if (transactionProvider == null)
			return false;
		
		try {
//			Iterator<XAResource> iterator = enlistedResources.iterator();
//			while (iterator.hasNext()) {
//				XAResource resource = iterator.next();
//				if (xaResource.isSameRM(resource))
//					return true;
//			}
			
			Transaction transaction = null;
			if (transactionProvider != null) {
				if (transactionId != null) {
					transaction = transactionProvider.getTransaction(transactionId);
				//} else {
				//	transaction = transactionProvider.getTransaction();
				}
			}
			
			if (transaction != null) {
				int status = transaction.getStatus();
				transaction.enlistResource(xaResource);
				//enlistedResources.add(xaResource);
			}
			return true;

		} catch (IllegalStateException e) {
			//ignore these
			return false;
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public PrintWriter getLogWriter() throws SQLException {
		return delegate.getLogWriter();
	}

	@Override
	public void setLogWriter(PrintWriter out) throws SQLException {
		delegate.setLogWriter(out);
	}

	@Override
	public void setLoginTimeout(int seconds) throws SQLException {
		delegate.setLoginTimeout(seconds);
	}

	@Override
	public int getLoginTimeout() throws SQLException {
		return delegate.getLoginTimeout();
	}

	@Override
	public <T> T unwrap(Class<T> iface) throws SQLException {
		return null; //delegate.unwrap(iface);
	}

	@Override
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		return false; //delegate.isWrapperFor(iface);
	}

//	public void setTransaction(Transaction transaction) {
//		this.transaction = transaction;
//	}
	
	public void setTransactionId(Object transactionId) {
		this.transactionId = transactionId;
	}

	@Override
	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		return null;
	}
	
	public void close() throws SQLException {
		try {
			if (xaConnection != null) {
				Connection connection = xaConnection.getConnection();
				if (connection != null && !connection.isClosed())
					connection.close();
			}
			
		} catch (SQLException e) {
			throw e;
			
		} finally {
			if (xaConnection != null)
				xaConnection.close();
		}
	}
	
	public void clear() throws SQLException {
		try {
			if (xaConnection != null)
				xaConnection.close();
			
		} finally {
			xaConnection = null;
//			transaction = null;
			transactionId = null;
//			transactionProvider = null;
			enlistedResources.clear();
		}
	}

}
