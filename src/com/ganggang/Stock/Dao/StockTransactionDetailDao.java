package com.ganggang.Stock.Dao;

import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.ganggang.Stock.Entity.StockTransactionDetail;
import com.ganggang.Util.HibernateUtil;

public class StockTransactionDetailDao {

	public static void UpdateCategory(StockTransactionDetail detail) {
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = sf.openSession();
		session.beginTransaction();
		session.update(detail);
		session.getTransaction().commit();
		session.close();
	}

	public static void UpdateCategory(List<StockTransactionDetail> details) {
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = sf.openSession();
		session.beginTransaction();
		for (StockTransactionDetail detail : details) {
			session.update(detail);
		}
		session.getTransaction().commit();
		session.close();
	}

	public static List<StockTransactionDetail> Query(String sql) {
		List l;
		List<StockTransactionDetail> result = new LinkedList<StockTransactionDetail>();
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = sf.openSession();
		session.beginTransaction();
		l = session.createSQLQuery(sql).addEntity(StockTransactionDetail.class)
				.list();
		for (Object o : l) {
			result.add((StockTransactionDetail) o);
		}
		session.getTransaction().commit();
		session.close();
		return result;
	}

	public static StockTransactionDetail QueryUnique(String sql) {
		Object o;
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = sf.openSession();
		session.beginTransaction();
		o = session.createSQLQuery(sql).addEntity(StockTransactionDetail.class)
				.uniqueResult();
		session.getTransaction().commit();
		session.close();
		return (StockTransactionDetail) o;
	}

	public static List QuerySql(String sql) {
		List result;
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = sf.openSession();
		session.beginTransaction();
		result = session.createSQLQuery(sql).list();
		session.getTransaction().commit();
		session.close();
		return result;
	}
	public static void AddStockTransactionDetail(StockTransactionDetail detail) {
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = sf.openSession();
		session.beginTransaction();
		session.save(detail);
		session.getTransaction().commit();
		session.close();
	}
	public static void AddStockTransactionDetail(List<StockTransactionDetail> details) {
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = sf.openSession();
		session.beginTransaction();
		details.forEach(x->session.save(x));
		session.getTransaction().commit();
		session.close();
	}

}
