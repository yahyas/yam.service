package core.field.service;
import java.util.List;
//import java.util.function.Consumer;

//import javax.swing.text.StyledEditorKit.BoldAction;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
//import org.hibernate.boot.spi.InFlightMetadataCollector.EntityTableXref;
import org.hibernate.query.Query;

import core.field.service.config.Config;
import core.field.service.config.PropertiesBuilder;
import tr.edu.izu.yam.core.field.CoreField;
//l
public class CoreFieldService {

	private static SessionFactory getSessionFactory() {
		PropertiesBuilder propertiesBuilder = null;
		SessionFactory sessionFactory = null;

		try {
			// uygun adresi giriniz.
			String url = "jdbc:postgresql://localhost:5432/postgres";
			// uygun kullanici adini giriniz.
			String user = "postgres";
			// kullanici adina ait sifreyi giriniz.
			String pass = "1234";
			propertiesBuilder = new PropertiesBuilder().withPostgreSQL().url(url).user(user).pass(pass);
			sessionFactory = Config.buildSessionFactory(propertiesBuilder, CoreField.class);
		} catch (Exception e) {
			e.printStackTrace();
			sessionFactory = null;
		}

		return sessionFactory;
	}
 
	public static CoreField getCoreField(SessionFactory sessionFactory, Long Id) {
		Session session = null;
		CoreField coreField = null;

		try {
			session = sessionFactory.openSession();
			Query<CoreField> q = session.createQuery("from CoreField where id=:id", CoreField.class);
			q.setParameter("id", Id);

			coreField = q.getSingleResult();

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if (null != session) {
				session.close();
				session = null;

			}
			return coreField;
		}
	}

	public static List<CoreField> getAllCoreField(SessionFactory sessionFactory) {
		Session session = null;
		List<CoreField> coreField = null;

		try {
			session = sessionFactory.openSession();
			Query<CoreField> q = session.createQuery("from CoreField", CoreField.class);

			coreField = q.list();

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if (null != session) {
				session.close();
				session = null;

			}
		}
		return coreField;
	}

	public int saveCoreField(SessionFactory sessionFactory, String name, Integer entity_type_id,String width,String height,Integer visible, Integer column_index, String label_text,Integer ref_type_id,
			String type,String ref_column,Integer orderno, String depends, String searchable,String not_null, String validate, String validation_regex, String validation_error_message, String detail_icon,
			String visible_as_column, String generated, String report_alias, Integer parent_type_id,String readonly, String default_value, String need_default_value ) {

		Session session = null;
		Transaction transaction = null;
		int Result = 0;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			Query query = session.createSQLQuery(
					"INSERT INTO core_field ( name, entity_type_id,  width, height, visible,  column_index, label_text,  ref_type_id, type, ref_column, orderno, depends, searchable, not_null,  validate, validation_regex,"
					+ " validation_error_message, detail_icon, visible_as_column, generated, report_alias, parent_type_id, readonly, default_value, need_default_value) VALUES (:name, :entity_type_id,  :width, :height, :visible, "
					+ " :column_index, :label_text,  :ref_type_id, :type, :ref_column, :orderno, :depends, :searchable, :not_null,  :validate, :validation_regex, :validation_error_message, :detail_icon, :visible_as_column, :generated,"
					+ " :report_alias, :parent_type_id, :readonly, :default_value, :need_default_value)");
			query.setParameter("name", name);
			query.setParameter("entity_type_id", entity_type_id);
			query.setParameter("width", width);
			query.setParameter("height", height);
			query.setParameter("visible", visible);
			query.setParameter("column_index", column_index);
			query.setParameter("label_text", label_text);
			query.setParameter("ref_type_id", ref_type_id);
			query.setParameter("type", type);
			query.setParameter("ref_column", ref_column);
			query.setParameter("orderno", orderno);
			query.setParameter("depends", depends);
			query.setParameter("searchable", searchable);
			query.setParameter("not_null", not_null);
			query.setParameter("validate", validate);
			query.setParameter("validation_regex", validation_regex);
			query.setParameter("validation_error_message", validation_error_message);
			query.setParameter("detail_icon", detail_icon);
			query.setParameter("visible_as_column", visible_as_column);
			query.setParameter("generated", generated);
			query.setParameter("report_alias", report_alias);
			query.setParameter("parent_type_id", parent_type_id);
			query.setParameter("readonly", readonly);
			query.setParameter("default_value", default_value);
			query.setParameter("need_default_value", need_default_value);
			

			Result = query.executeUpdate();

			transaction.commit();
		} catch (HibernateException ex) {
			if (transaction != null)
				transaction.rollback();

		} finally {
			session.close();
		}

		return Result;
	}

	public static Long saveCoreField(SessionFactory sessionFactory, CoreField coreField) {
		Session session = null;
		Transaction transaction = null;
		Long coreFieldId = null;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();

			coreFieldId = (Long) session.save(coreField);
			transaction.commit();
		} catch (HibernateException ex) {
			if (transaction != null)
				transaction.rollback();
		} finally {
			session.close();
		}

		return coreFieldId;
	}

	public static boolean updateCoreField(SessionFactory sessionFactory, CoreField field, Long Id) {
		Session session = null;
		Transaction transaction = null;
		boolean state = false;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();

			CoreField coreField = session.get(CoreField.class, Id);

			coreField.setName(field.getName());
			coreField.setEntity_type_id(field.getEntity_type_id());
			coreField.setWidth(field.getWidth());
			coreField.setHeight(field.getHeight());
			coreField.setVisible(field.getVisible());
			coreField.setColumn_index(field.getColumn_index());
			coreField.setLabel_text(field.getLabel_text());
			coreField.setRef_type_id(field.getRef_type_id());
			coreField.setType(field.getType());
			coreField.setRef_column(field.getRef_column());
			coreField.setOrderno(field.getOrderno());
			coreField.setDepends(field.getDepends());
			coreField.setSearchable(field.getSearchable());
			coreField.setNot_null(field.getNot_null());
			coreField.setValidate(field.getValidate());
			coreField.setValidation_regex(field.getValidation_regex());
			coreField.setValidation_error_message(field.getValidation_error_message());
			coreField.setDetail_icon(field.getDetail_icon());
			coreField.setVisible_as_column(field.getVisible_as_column());
			coreField.setGenerated(field.getGenerated());
			coreField.setReport_alias(field.getReport_alias());
			coreField.setParent_type_id(field.getParent_type_id());
			coreField.setReadonly(field.getReadonly());
			coreField.setDefault_value(field.getDefault_value());
			coreField.setNeed_default_value(field.getNeed_default_value());
			
			session.update(coreField);
			transaction.commit();
			state = true;

		} catch (HibernateException ex) {
			if (transaction != null)
				transaction.rollback();
			return state;
		} finally {
			session.close();
		}

		return state;

	}

	// Test için methodlarý static yapýp çalýþtýrýn.

	
	  public static void main(String[] args) { 
		  SessionFactory sessionFactory =	  getSessionFactory();
	  
	  
	  if (null != sessionFactory) { 
	  CoreField cF=new CoreField();
	  cF.setName("aaa"); 
	  cF.setEntity_type_id(3);
	  cF.setWidth("45");
	  cF.setHeight("55");; 
	  cF.setVisible(1);
	  cF.setColumn_index(4);
	  cF.setLabel_text("LABEL");
	  cF.setRef_type_id(58);
	  cF.setType("tttt");
	  cF.setRef_column("9");
	  cF.setOrderno(98);
	  cF.setDepends("D");
	  cF.setSearchable("SS");
	  cF.setNot_null("A");
	  cF.setValidate("VALI");
	  cF.setValidation_regex("RGX");
	  cF.setValidation_error_message("ERROR");
	  cF.setDetail_icon("ICOn");
	  cF.setVisible_as_column("CLM");
	  cF.setGenerated("GENERATE");
	  cF.setReport_alias("ALIAS");
	  cF.setParent_type_id(4);
	  cF.setReadonly("RO");
	  cF.setDefault_value("DEF");
	  cF.setNeed_default_value("need");
	  
	  
	  System.out.print(getAllCoreField(sessionFactory));
	  System.out.print(getCoreField(sessionFactory, 2L));
	  //System.out.print(saveEntityType(sessionFactory, null, null, 0, 0, null, 0,null, null, null)); 
	  System.out.print(updateCoreField(sessionFactory, cF,1L)); 
	  System.out.print(saveCoreField(sessionFactory, cF));
	  
	  
	  } else { System.out.println("SessionFactory is null"); }
	  
	  }
	 

}