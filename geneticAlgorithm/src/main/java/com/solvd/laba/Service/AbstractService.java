package com.solvd.laba.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public  abstract class AbstractService {
	private SqlSession session; 

	public AbstractService() throws IOException {
		String resource = "myBatis-conf.xml";
		
		InputStream inputStream = Resources.getResourceAsStream(resource);

		InputStream dbPropertiesStream = Resources.getResourceAsStream("db.properties");
        Properties dbProperties = new Properties();
        dbProperties.load(dbPropertiesStream);

        Properties properties = new Properties();
        properties.setProperty("driver", dbProperties.getProperty("db.driver"));
        properties.setProperty("url", dbProperties.getProperty("db.url"));
        properties.setProperty("username", dbProperties.getProperty("db.user"));
        properties.setProperty("password", dbProperties.getProperty("db.password"));
		
		// Used while testing the services at local machine
		
        /*Properties properties = new Properties();
        properties.setProperty("driver", "com.mysql.cj.jdbc.Driver");
        properties.setProperty("url", "jdbc:mysql://localhost:3306/geneticschedulerdb");
        properties.setProperty("username", "root");
        properties.setProperty("password", "password");*/
	
		
		
		
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream, properties);
		this.setSession(sqlSessionFactory.openSession());
	}

	public SqlSession getSession() {
		return session;
	}

	public void setSession(SqlSession session) {
		this.session = session;
	}
}
