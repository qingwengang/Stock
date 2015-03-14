package com.ganggang.Stock.Dao;

import java.util.LinkedList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.ganggang.Stock.Entity.StockInfo;
import com.ganggang.Stock.Entity.StockTransactionDetail;
import com.ganggang.Util.HibernateUtil;

public class StockInfoDao {

	public static List<StockInfo> Query(String sql){
		List l;
		List<StockInfo> result=new LinkedList<StockInfo>();
		SessionFactory sf=HibernateUtil.getSessionFactory();
		Session session=sf.openSession();
		session.beginTransaction();
		l=session.createSQLQuery(sql).addEntity(StockInfo.class).list();
		for (Object o : l) {
			result.add((StockInfo)o);
		}
		session.getTransaction().commit();
		session.close();
		return result;
	}
	public static StockInfo QueryUnique(String sql){
		Object o;
		SessionFactory sf=HibernateUtil.getSessionFactory();
		Session session=sf.openSession();
		session.beginTransaction();
		o=session.createSQLQuery(sql).addEntity(StockInfo.class).uniqueResult();
		session.getTransaction().commit();
		session.close();
		if(o==null){
			return null;
		}
		return (StockInfo)o;
	}
	public static void Update(StockInfo info){
		SessionFactory sf=HibernateUtil.getSessionFactory();
		Session session=sf.openSession();
		session.beginTransaction();
		session.update(info);
		session.getTransaction().commit();
		session.close();
	}
}
