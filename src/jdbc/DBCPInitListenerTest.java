package jdbc;

import java.io.IOException;
import java.io.StringReader;
import java.sql.DriverManager;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.dbcp2.ConnectionFactory;
import org.apache.commons.dbcp2.DriverManagerConnectionFactory;
import org.apache.commons.dbcp2.PoolableConnection;
import org.apache.commons.dbcp2.PoolableConnectionFactory;
import org.apache.commons.dbcp2.PoolingDriver;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

public class DBCPInitListenerTest implements ServletContextListener {
	// poolConfig 컨텍스트 초기화
	//poolConfig 컨텍스트 초기화
	@Override 
	public void contextInitialized(ServletContextEvent sce) {
		String poolConfig =     
					sce.getServletContext().getInitParameter("poolConfig");
		Properties prop = new Properties();//Properties객체생성
		//"키=값"형식의 문자열로부터 Properties를 로딩   
		//String 키명=String 값
		try {
			prop.load(new StringReader(poolConfig));//prop.load() "키=값"형식의 문자열로부터 Properties를 로딩
		} catch (IOException e) {
			throw new RuntimeException("config load fail", e);
		}
		loadJDBCDriver(prop);    
		initConnectionPool(prop);
	}
		
		//매개변수 Properties prop에 담겨온 내용을 이용하여 JDBCDriver를 로딩
		private void loadJDBCDriver(Properties prop) {
			       //"키=값"형식
			//jdbcdriver=com.mysql.jdbc.Driver
			String driverClass = prop.getProperty("jdbcdriver");
			//com.mysql.jdbc.Driver값이  String타입의 driverClass변수에 저장
			try {
				Class.forName(driverClass);
			} catch (ClassNotFoundException ex) {
				throw new RuntimeException("fail to load JDBC Driver", ex);
			}
		}

		//ConnectionPool 초기화
		private void initConnectionPool(Properties prop) {
			try {//참고 jdbc:mysql://ip주소 : port번호/DB스키마명?characterEncoding=UTF-8&serverTimezone=UTC
				/*     키명=값
				 *  jdbcUrl=jdbc:mysql://localhost:3306/board?characterEncoding=utf8&amp;serverTimezone=UTC
					dbUser=jspexam
					dbPass=jsppw
				 */
				String jdbcUrl = prop.getProperty("jdbcUrl");
				String username = prop.getProperty("dbUser");
				String pw = prop.getProperty("dbPass");

				ConnectionFactory connFactory = 
						new DriverManagerConnectionFactory(jdbcUrl, username, pw);

				PoolableConnectionFactory poolableConnFactory = 
						new PoolableConnectionFactory(connFactory, null);
				
				//풀의 커넥션 (유효성)검사		
				String validationQuery = prop.getProperty("validationQuery");
				if (validationQuery != null && !validationQuery.isEmpty()) {
					poolableConnFactory.setValidationQuery(validationQuery);
				}
				GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
				poolConfig.setTimeBetweenEvictionRunsMillis(1000L * 60L * 5L);
				poolConfig.setTestWhileIdle(true);
				
				//최소 유휴 커넥션설정
				//최소 유휴 커넥션:사용되지 않고 풀에 저장될 수 있는 최소 커넥션 개수
				int minIdle = getIntProperty(prop, "minIdle", 5);
				poolConfig.setMinIdle(minIdle);
				
				//최대 유휴 커넥션설정
				int maxTotal = getIntProperty(prop, "maxTotal", 50);
				poolConfig.setMaxTotal(maxTotal);

				GenericObjectPool<PoolableConnection> connectionPool = 
						new GenericObjectPool<>(poolableConnFactory, poolConfig);
				poolableConnFactory.setPool(connectionPool);
				
				//org.apache.commons.dbcp2패키지.PoolingDriver클래스를 불러오기
				Class.forName("org.apache.commons.dbcp2.PoolingDriver");
				PoolingDriver driver = (PoolingDriver)
					DriverManager.getDriver("jdbc:apache:commons:dbcp:");
				
				//poolName을 이용해서 가져온 board값을
				//String타입의  poolName변수에 저장한다
				//여기에서는 ->String poolName="board";
				String poolName = prop.getProperty("poolName");//poolName을 가져와 변수에 저장
				driver.registerPool(poolName, connectionPool);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}

		
		private int getIntProperty(Properties prop, String propName, int defaultValue) {
			String value = prop.getProperty(propName);
			if (value == null) return defaultValue;
			return Integer.parseInt(value);
		}

		@Override
		public void contextDestroyed(ServletContextEvent sce) {
		}
	 
}
