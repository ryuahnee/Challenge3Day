package notice.service;

import java.sql.SQLException;

import javax.naming.NamingException;

public class ServiceException extends Exception {

	public ServiceException(String string, SQLException e1) {
	}

	public ServiceException(String string, NamingException e2) {
	}

	public ServiceException(String string, Exception e) {
	}

}
