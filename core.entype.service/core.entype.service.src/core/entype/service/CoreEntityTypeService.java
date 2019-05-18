package core.entype.service;

import java.util.List;
import java.util.function.Consumer;

import javax.swing.text.StyledEditorKit.BoldAction;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.spi.InFlightMetadataCollector.EntityTableXref;
import org.hibernate.query.Query;

import core.entype.service.config.Config;
import core.entype.service.config.PropertiesBuilder;
import tr.edu.izu.yam.core.entype.CoreEntityType;

public class CoreEntityTypeService {

	private static SessionFactory getSessionFactory() {
		PropertiesBuilder propertiesBuilder = null;
		SessionFactory sessionFactory = null;

		try {
			// uygun adresi giriniz.
			String url = "jdbc:postgresql://localhost:5432/postgres";
			// uygun kullanici adini giriniz.
			String user = "postgres";
			// kullanici adina ait sifreyi giriniz.
			String pass = "123";
			propertiesBuilder = new PropertiesBuilder().withPostgreSQL().url(url).user(user).pass(pass);
			sessionFactory = Config.buildSessionFactory(propertiesBuilder, CoreEntityType.class);
		} catch (Exception e) {
			e.printStackTrace();
			sessionFactory = null;
		}

		return sessionFactory;
	}

	public CoreEntityType getEntityType(SessionFactory sessionFactory, Long Id) {
		Session session = null;
		CoreEntityType entityType = null;

		try {
			session = sessionFactory.openSession();
			Query<CoreEntityType> q = session.createQuery("from CoreEntityType where id=:id", CoreEntityType.class);
			q.setParameter("id", Id);

			entityType = q.getSingleResult();

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if (null != session) {
				session.close();
				session = null;

			}
			return entityType;
		}
	}

	public List<CoreEntityType> getAllEntityType(SessionFactory sessionFactory) {
		Session session = null;
		List<CoreEntityType> entityType = null;

		try {
			session = sessionFactory.openSession();
			Query<CoreEntityType> q = session.createQuery("from CoreEntityType", CoreEntityType.class);

			entityType = q.list();

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if (null != session) {
				session.close();
				session = null;

			}
		}
		return entityType;
	}

	public int saveEntityType(SessionFactory sessionFactory, String entity_type, String class_Name, int owner_id,
			int orderno, String name, int status, String description, String string_elements,
			String search_restriction) {

		Session session = null;
		Transaction transaction = null;
		int Result = 0;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			Query query = session.createSQLQuery(
					"INSERT INTO core_entity_type ( entity_type, class_Name,  owner_id, orderno, name,  status, description,  string_elements, search_restriction) VALUES (:entity_type, :class_Name,  :owner_id, :orderno, :name,  :status, :description,  :string_elements, :search_restriction)");
			query.setParameter("entity_type", entity_type);
			query.setParameter("class_Name", class_Name);
			query.setParameter("owner_id", owner_id);
			query.setParameter("orderno", orderno);
			query.setParameter("name", name);
			query.setParameter("status", status);
			query.setParameter("description", description);
			query.setParameter("string_elements", string_elements);
			query.setParameter("search_restriction", search_restriction);

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

	public Long saveEntityType(SessionFactory sessionFactory, CoreEntityType entityType) {
		Session session = null;
		Transaction transaction = null;
		Long entityTypeId = null;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();

			entityTypeId = (Long) session.save(entityType);
			transaction.commit();
		} catch (HibernateException ex) {
			if (transaction != null)
				transaction.rollback();
		} finally {
			session.close();
		}

		return entityTypeId;
	}

	public boolean updateEntityType(SessionFactory sessionFactory, CoreEntityType entity, Long Id) {
		Session session = null;
		Transaction transaction = null;
		boolean state = false;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();

			CoreEntityType entityType = session.get(CoreEntityType.class, Id);

			entityType.setName(entity.getName());
			entityType.setDescription(entity.getDescription());
			entityType.setEntityType(entity.getDescription());
			entityType.setOrderNo(entity.getOrderNo());
			entityType.setOwnerId(entity.getOwnerId());
			entityType.setSearchRestriction(entity.getSearchRestriction());
			entityType.setStatus(entity.getStatus());
			entityType.setStringElements(entity.getStringElements());
			entityType.setClassName(entity.getClassName());

			session.update(entityType);
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

	/*
	 * public static void main(String[] args) { SessionFactory sessionFactory =
	 * getSessionFactory();
	 * 
	 * 
	 * if (null != sessionFactory) { CoreEntityType cet=new CoreEntityType();
	 * cet.setClassName("123"); cet.setDescription("123");
	 * cet.setEntityType("123123"); cet.setName("d3"); cet.setOrderNo(2);
	 * cet.setOwnerId(1); cet.setSearchRestriction("d"); cet.setStatus(1);
	 * cet.setStringElements("d5");
	 * 
	 * System.out.print(getAllEntityType(sessionFactory));
	 * System.out.print(getEntityType(sessionFactory, 2L));
	 * System.out.print(saveEntityType(sessionFactory, null, null, 0, 0, null, 0,
	 * null, null, null)); System.out.print(updateEntityType(sessionFactory,
	 * cet,1L)); System.out.print(saveEntityType(sessionFactory, cet));
	 * 
	 * 
	 * } else { System.out.println("SessionFactory is null"); }
	 * 
	 * }
	 */

}