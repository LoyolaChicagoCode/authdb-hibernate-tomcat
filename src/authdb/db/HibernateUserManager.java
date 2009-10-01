package authdb.db;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 * This class implements a user manager that accesses User objects from the
 * database using Hibernate. Users of this class need not be aware of Hibernate
 * at all. Based on Ed Hand's Struts/Hibernate example.
 */
/*
 * This class uses the Singleton design pattern to provide an interface to the
 * Struts application allowing it to work with User objects through the
 * persistence layer.
 * 
 * No Hibernate-related code exists above this layer. This would allow us, in
 * the future, to switch to some other method of object persistence without
 * making changes to the Struts-related code in the application.
 *  
 */
public class HibernateUserManager implements UserManager {

	private static UserManager instance = null;

	/**
	 * This method returns the instance of the singleton user manager.
	 * 
	 * @return the instance
	 */
	public static synchronized UserManager getInstance() {
		if (instance == null) {
			instance = new HibernateUserManager();
		}
		return instance;
	}

	public void init() {
		ConnectionFactory.getInstance().createTables();
		create("nobody", "password", new String[]{});
		create("guest",  "password", new String[]{"guests"});
		create("laufer", "password", new String[]{"guests", "users"});
		create("user",   "password", new String[]{"guests", "users"});
		create("admin",  "password", new String[]{"guests", "users", "administrators"});
	}

	protected void create(String id, String password, String[] roles) {
		create(id, password, new HashSet(Arrays.asList(roles)));
	}

	public User create(final String id, final String password, final Set roles) {
		// Use the ConnectionFactory to retrieve an open Hibernate Session.
		Session session = ConnectionFactory.getInstance().getSession();
		Transaction tx = null;
		try {
			Principal principal = new Principal(id, password, roles);
			tx = session.beginTransaction();
			session.save(principal);
			tx.commit();
			return principal;
		} catch (/*Hibernate*/Exception e) {
			/*
			 * All Hibernate Exceptions are transformed into an unchecked
			 * RuntimeException. This will have the effect of causing the user's
			 * request to return an error.
			 */
			try {
				if (tx != null)
					tx.rollback();
			} catch (HibernateException e2) {
				e = e2;
			}
			System.err.println("Hibernate Exception " + e.getMessage());
			throw new RuntimeException(e);
		} finally {
			/*
			 * Regardless of whether the above processing resulted in an Exception or
			 * proceeded normally, we want to close the Hibernate session. When
			 * closing the session, we must allow for the possibility of a Hibernate
			 * Exception.
			 */
			if (session != null) {
				try {
					session.close();
				} catch (HibernateException e) {
					System.err.println("Hibernate Exception " + e.getMessage());
					throw new RuntimeException(e);
				}
			}
		}
	}

	public User find(final String id) {
		Session session = ConnectionFactory.getInstance().getSession();
		try {
			/*
			 * Calls the get method on the Hibernate session object to retrieve the
			 * Item with the provided id.
			 */
			return (Principal) session.get(Principal.class, id);
		} catch (HibernateException e) {
			System.err.println("Hibernate Exception " + e.getMessage());
			throw new RuntimeException(e);
		} finally {
			if (session != null) {
				try {
					session.close();
				} catch (HibernateException e) {
					System.err.println("Hibernate Exception " + e.getMessage());
					throw new RuntimeException(e);
				}
			}
		}
	}

	public Collection findAll() {
		Session session = ConnectionFactory.getInstance().getSession();
		try {
			/*
			 * Build HQL (Hibernate Query Language) query to retrieve a list of all
			 * the items currently stored by Hibernate.
			 */
			Query query = session
					.createQuery("select item from authdb.db.Principal item order by item.id");
			return query.list();
		} catch (HibernateException e) {
			System.err.println("Hibernate Exception " + e.getMessage());
			throw new RuntimeException(e);
		} finally {
			if (session != null) {
				try {
					session.close();
				} catch (HibernateException e) {
					System.err.println("Hibernate Exception " + e.getMessage());
					throw new RuntimeException(e);
				}
			}
		}
	}

	public void remove(final String id) {
		Session session = ConnectionFactory.getInstance().getSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.delete(session.load(Principal.class, id));
			tx.commit();
		} catch (HibernateException e) {
			try {
				if (tx != null)
					tx.rollback();
			} catch (HibernateException e2) {
				e = e2;
			}
			System.err.println("Hibernate Exception " + e.getMessage());
			throw new RuntimeException(e);
		} finally {
			if (session != null) {
				try {
					session.close();
				} catch (HibernateException e) {
					System.err.println("Hibernate Exception " + e.getMessage());
					throw new RuntimeException(e);
				}
			}
		}
	}

	public void update(String id, String password, Set roles) {
		Session session = ConnectionFactory.getInstance().getSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.update(new Principal(id, password, roles));
			tx.commit();
		} catch (HibernateException e) {
			try {
				if (tx != null)
					tx.rollback();
			} catch (HibernateException e2) {
				e = e2;
			}
			System.err.println("Hibernate Exception " + e.getMessage());
			throw new RuntimeException(e);
		} finally {
			if (session != null) {
				try {
					session.close();
				} catch (HibernateException e) {
					System.err.println("Hibernate Exception " + e.getMessage());
					throw new RuntimeException(e);
				}
			}
		}
	}
}