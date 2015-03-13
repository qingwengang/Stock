package com.ganggang.Stock.Dao;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import com.ganggang.Stock.Entity.*;
import com.ganggang.Util.HibernateUtil;

public class PlanDealDao {

	public static void AddStockInfo(PlanDeal deal){
		SessionFactory sf=HibernateUtil.getSessionFactory();
		Session session=sf.openSession();
		session.beginTransaction();
		session.save(deal);
		session.getTransaction().commit();
		session.close();
	}
}
