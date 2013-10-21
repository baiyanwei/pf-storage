package com.secpro.platform.storage.services;

import java.sql.Connection;
import java.sql.SQLException;

import javax.xml.bind.annotation.XmlElement;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.secpro.platform.core.exception.PlatformException;
import com.secpro.platform.core.services.IService;
import com.secpro.platform.core.services.ServiceInfo;
import com.secpro.platform.log.utils.PlatformLogger;

/**
 * @author baiyanwei Oct 20, 2013
 * 
 * 
 *         DataBase storage service.
 */
@ServiceInfo(description = "The DataBase Storage Service in Platform.", configurationPath = "app/platform/services/DataBaseStorageService/")
public class DataBaseStorageService implements IService {
	final private static PlatformLogger theLogger = PlatformLogger.getLogger(DataBaseStorageService.class);
	// c3p0.driverClass
	@XmlElement(name = "driverClass", defaultValue = "oracle.jdbc.driver.OracleDriver")
	public String _driverClass = "oracle.jdbc.driver.OracleDriver";
	// c3p0.jdbcUrl
	@XmlElement(name = "jdbcUrl", defaultValue = "jdbc:oracle:thin:@192.168.18.161:1521:orcl")
	public String _jdbcUrl = "jdbc:oracle:thin:@192.168.18.161:1521:orcl";
	// c3p0.user
	@XmlElement(name = "user", defaultValue = "secpro")
	public String _user = "secpro";
	// c3p0.password
	@XmlElement(name = "password", defaultValue = "secpro")
	public String _password = "secpro";
	// c3p0.initialPoolSize
	@XmlElement(name = "initialPoolSize", type = Integer.class, defaultValue = "20")
	public Integer _initialPoolSize = new Integer(20);
	// c3p0.maxIdleTime
	@XmlElement(name = "maxIdleTime", type = Integer.class, defaultValue = "5")
	public Integer _maxIdleTime = new Integer(5);
	// c3p0.maxPoolSize
	@XmlElement(name = "maxPoolSize", type = Integer.class, defaultValue = "20")
	public Integer _maxPoolSize = new Integer(20);
	// c3p0.minPoolSize
	@XmlElement(name = "minPoolSize", type = Integer.class, defaultValue = "5")
	public Integer _minPoolSize = new Integer(5);

	// DataBase pool instance
	private ComboPooledDataSource _cpds = null;

	@Override
	public void start() throws PlatformException {
		createDataBaseConnectionPool();
		theLogger.info("startUp");
	}


	@Override
	public void stop() throws PlatformException {
		stopeDataBaseConnectionPool();
		_cpds = null;
	}

	/**
	 * create DataBase management ,And set parameter.
	 */
	private void createDataBaseConnectionPool() {
		_cpds = new ComboPooledDataSource();
		try {
			_cpds.setDriverClass(this._driverClass);
			_cpds.setJdbcUrl(this._jdbcUrl);
			_cpds.setUser(this._user);
			_cpds.setPassword(this._password);
			_cpds.setInitialPoolSize(this._initialPoolSize);
			_cpds.setMaxIdleTime(this._maxIdleTime);
			_cpds.setMaxPoolSize(this._maxPoolSize);
			_cpds.setMinPoolSize(this._minPoolSize);
			theLogger.info("databaseParameter", _driverClass, _jdbcUrl, _user, _password, String.valueOf(_initialPoolSize), String.valueOf(_maxIdleTime),
					String.valueOf(_maxPoolSize), String.valueOf(_minPoolSize));
		} catch (Exception e) {
			theLogger.exception(e);
		}
	}

	/**
	 * destroy the connection pool
	 */
	private void stopeDataBaseConnectionPool() {
		if (_cpds != null) {
			_cpds.close();
		}
	}

	/**
	 * @return
	 * @throws SQLException
	 * 
	 *             Get a DataBase connection. must close the connection when
	 *             done.
	 */
	public Connection getConnection() throws SQLException {
		return _cpds.getConnection();
	}
}
