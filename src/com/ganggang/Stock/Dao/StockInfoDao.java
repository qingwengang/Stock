package com.ganggang.Stock.Dao;

import java.util.LinkedList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

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
	public static void AddStockInfo(StockInfo info){
		SessionFactory sf=HibernateUtil.getSessionFactory();
		Session session=sf.openSession();
		session.beginTransaction();
		Criteria c=session.createCriteria(StockInfo.class);
		c.add(Restrictions.eq("code", info.getCode()));
		if(c.list().size()==0){
			session.save(info);
		}
		session.getTransaction().commit();
		session.close();
	}
	
	public static StockInfo GetUnSpiderHistoryStock(){
		SessionFactory sf=HibernateUtil.getSessionFactory();
		Session session=sf.openSession();
		Criteria c=session.createCriteria(StockInfo.class);
		c.add(Restrictions.eq("ifGetHistory", 0));
		c.setFirstResult(0);
		c.setMaxResults(1);
		StockInfo ca= (StockInfo) (c.list().size()>0?c.list().get(0):null);
		ca.setIfGetHistory(2);
		UpdateCategory(ca);
		session.close();
		return ca;
	}
	
	public static void UpdateCategory(StockInfo info){
		SessionFactory sf=HibernateUtil.getSessionFactory();
		Session session=sf.openSession();
		session.beginTransaction();
		session.update(info);
		session.getTransaction().commit();
		session.close();
	}
}
