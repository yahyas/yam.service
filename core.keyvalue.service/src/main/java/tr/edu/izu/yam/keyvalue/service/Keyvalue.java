package tr.edu.izu.yam.keyvalue.service;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;


import  tr.edu.izu.yam.keyvalue.service.config.Config;
import tr.edu.izu.yam.keyvalue.service.config.PropertiesBuilder;
import  tr.edu.izu.yam.core.keyvalue.CoreKeyValue;


public class Keyvalue {

	private static SessionFactory getSessionFactory() {
		PropertiesBuilder propertiesBuilder = null;
		SessionFactory sessionFactory = null;

		try {
			// uygun adresi giriniz.
			String url = "jdbc:postgresql://localhost:5432/yam";
			// uygun kullanici adini giriniz.
			String user = "yamuser";
			// kullanici adina ait sifreyi giriniz.
			String pass = "123456";
			propertiesBuilder = new PropertiesBuilder().withPostgreSQL().url(url).user(user).pass(pass);
			sessionFactory = Config.buildSessionFactory(propertiesBuilder, CoreKeyValue.class);
		} catch (Exception e) {
			e.printStackTrace();
			sessionFactory = null;
		}

		return sessionFactory;
	}

	private static void query(SessionFactory sessionFactory) {
		Session session = null;

		try {	
			session = sessionFactory.openSession();
			Query<CoreKeyValue> q = session.createQuery("from core_keyvalue", CoreKeyValue.class);
            List<CoreKeyValue> users = q.list();


            for (CoreKeyValue user : users) 
                    System.out.println(String.format("User:%s", user.getId()));
            
					
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != session) {
				session.close();
				session = null;
			}
		}
	}

	public static void transact(SessionFactory sessionFactory) {
		Session session = null;
		Transaction transaction = null;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
		    Long corekeyvalueid = null;
		    CoreKeyValue corekeyvalue = new CoreKeyValue();
	        corekeyvalue.setId((long)123432432);
	        corekeyvalue.setOrderNo(1);
	        corekeyvalue.setCode("code1");
	        corekeyvalueid = (Long) session.save(corekeyvalue);      
			transaction.commit();
			
		} catch (Exception e) {
			e.printStackTrace();
			transaction.rollback();
		} finally {
			if (null != session) {
				session.close();
				session = null;
			}
		}
	}

	public static void main(String[] args) {
		SessionFactory sessionFactory = getSessionFactory();
transact(sessionFactory);
		if (null != sessionFactory) {
			query(sessionFactory);
			transact(sessionFactory);
		} else {
			System.out.println("SessionFactory is null");
		}

	}

}