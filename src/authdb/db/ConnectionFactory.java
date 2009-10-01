package authdb.db;

import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;

/**
 * Singleton class that creates and returns an open Hibernate <code>Session</code> to the user.
 * 
 * Copyright 2003 Edward Hand
 * 
 * @author Edward Hand
 *
 */
/* 
 * This class was created to encapsulate the Hibernate SessionFactory and assist
 * the service layer, in this example consisting of HibernateUserManager.
 * 
 */ 
class ConnectionFactory {

	private static ConnectionFactory instance = null;
	private SessionFactory sessionFactory = null;
	
	private ConnectionFactory()
	{
		// Establish SessionFactory for Hibernate
		try
		{

			/*
			 * The Hibernate Configuration will contain all the classes to be
			 * persisted by Hibernate.  For each class persisted, Hibernate will
			 * expect to find a ClassName.hbm.xml file in the same location as the
			 * class file.  This XML file will define the mapping between the Java
			 * object and the database.
			 * 
			 * To add additional classes to the configuration, you may cascade the
			 * method calls thusly:
			 * 
			 * Configuration cfg = new Configuration().
			 *                         addClass(Foo.class).
			 *                         addClass(Bar.class);
			 * 
			 */
			Configuration cfg = new Configuration().configure();
			sessionFactory = cfg.buildSessionFactory();
		}
		catch (MappingException e)
		{
			/*
			 * Upon encountering a Hibernate generated Exception, we are throwing
			 * an unchecked RuntimeExcpetion that will cause the user's
			 * request to fail.
			 * 
			 */
			System.err.println("Mapping Exception" + e.getMessage());
			throw new RuntimeException(e);
		}
		catch (HibernateException e)
		{
			/*
			 * Upon encountering a Hibernate generated Exception, we are throwing
			 * an unchecked RuntimeExcpetion that will cause the user's request to fail.
			 * 
			 */
			System.err.println("Hibernate Exception" + e.getMessage());
			throw new RuntimeException(e);
		}

	}

	/** 
	 * getInstance() returns the instance of the ConnectionFactory singleton.
	 * 
	 * Example call to retrieve session:
	 * 
	 * <pre>
	 * Session session = ConnectionFactory.getInstance().getSession();
	 * </pre>
	 * 
	 * @return Instance of the <code>ConnectionFactory</code> singleton.
	 */
	public static synchronized ConnectionFactory getInstance()
	{
		/* 
		 * If the instance of the Singleton has not been created, create and
		 * return.
		 */
		if (instance == null)
		{
			instance = new ConnectionFactory();
		}
		return instance;
	}

	/**
	 * getSession() returns a Hibernate <code>Session</code>
	 * 
	 * @return <code>Session</code> retrieved from Hibernate <Code>SessionFactory</code>
	 */
	public Session getSession()
	{
		try
		{
			/*
			 * Use the Hibernate Session Factory to return an open session to the caller.
			 */
			Session s = sessionFactory.openSession();
			return s;
		}
		catch (HibernateException e)
		{
			/*
			 * Upon encountering a Hibernate generated Exception, we are throwing
			 * an unchecked RuntimeExcpetion that will cause the user's request to fail.
			 * 
			 */
			System.err.println("Hibernate Exception" + e.getMessage());
			throw new RuntimeException(e);
		}
	}

	public void createTables() {
		try {
			Configuration cfg = new Configuration().configure();
			new SchemaExport(cfg).create(false, true);
		} catch (HibernateException e) {
			System.err.println("Hibernate Exception" + e.getMessage());
			throw new RuntimeException(e);
		}
	}
	
}
