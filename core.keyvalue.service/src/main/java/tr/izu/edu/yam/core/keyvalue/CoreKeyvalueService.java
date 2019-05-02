package tr.izu.edu.yam.core.keyvalue;
import java.util.Scanner;
import java.io.File;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class CoreKeyvalueService {
	private static SessionFactory factory; 
	   public static void main(String[] args) {
	      
	      try {
	         factory = new Configuration().
	                   configure().
	                   //addPackage("com.xyz") //add package if used.
	                   addAnnotatedClass(CoreKeyvalue.class).
	                   buildSessionFactory();
	      } catch (Throwable ex) { 
	         System.err.println("Failed to create sessionFactory object." + ex);
	         throw new ExceptionInInitializerError(ex); 
	      }
	      
	      CoreKeyvalueService ME = new CoreKeyvalueService();

	      /* Add few employee records in database */
	      Long element1 = ME.addElement((long) 30715027,1,"aa");
	      Long element2 = ME.addElement((long) 56525465,2,"bb");	     
	      Long element3 = ME.addElement((long) 86465266,3,"cc");
	   }
	   
	   /* Method to CREATE an employee in the database */
	   public Long addElement(Long id , int orderNo, String code){
	      Session session = factory.openSession();
	      Transaction tx = null;
	      Long corekeyvalueid = null;
	      
	      try {
	         tx = session.beginTransaction();
	         CoreKeyvalue corekeyvalue = new CoreKeyvalue();
	         corekeyvalue.setId(id);
	         corekeyvalue.setOrderNo(orderNo);
	         corekeyvalue.setCode(code);
	         corekeyvalueid = (Long) session.save(corekeyvalue); 
	         tx.commit();
	      } catch (HibernateException e) {
	         if (tx!=null) tx.rollback();
	         e.printStackTrace(); 
	      } finally {
	         session.close(); 
	      }
	      return corekeyvalueid;
	   }
}