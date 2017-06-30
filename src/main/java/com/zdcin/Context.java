package com.zdcin;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Context {

	// -- begin hibernate
	private static SessionFactory sessionFactory = null;

	public static Session getHSession() {
		return getSessionFactory().getCurrentSession();
	}

	static final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure() // 使用hibernate.cfg.xml进行配置
			.build();

	public static SessionFactory getSessionFactory() {

		if (sessionFactory == null || sessionFactory.isClosed()) {
			synchronized (Context.class) {
				if (sessionFactory == null || sessionFactory.isClosed()) {
					try {
						sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
					} catch (Exception e1) {
						log.error(e1.getMessage(), e1);
						StandardServiceRegistryBuilder.destroy(registry);
					}
				}
			}
		}
		try {
			sessionFactory.getCurrentSession();
			return sessionFactory;
		} catch (Exception e) {
			log.error("error in getCurrentSession, " + e.getMessage(), e);
			sessionFactory.close();
			sessionFactory = null;
			synchronized (Context.class) {
				if (sessionFactory == null || sessionFactory.isClosed()) {
					try {
						sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
					} catch (Exception e1) {
						log.error(e1.getMessage(), e1);
						StandardServiceRegistryBuilder.destroy(registry);
					}
				}
			}
			return sessionFactory;
		}
	}

}
