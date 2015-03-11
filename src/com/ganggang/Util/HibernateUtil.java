package com.ganggang.Util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

public class HibernateUtil {
	private static final SessionFactory sessionFactory = buildSessionFactory(); 
	
	private static SessionFactory buildSessionFactory() {  
        try {   
             Configuration cfg = new Configuration();    
             cfg.configure();            
             ServiceRegistry  sr = new ServiceRegistryBuilder().applySettings(cfg.getProperties()).buildServiceRegistry();             
             return cfg.buildSessionFactory(sr);
        }  
        catch (Throwable ex) {  
            System.err.println("Initial SessionFactory creation failed." + ex);  
            throw new ExceptionInInitializerError(ex);  
        }  
    }  
	public static SessionFactory getSessionFactory() {  
        return sessionFactory;  
    }  
}