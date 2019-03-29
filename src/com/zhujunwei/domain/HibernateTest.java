package com.zhujunwei.domain;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.junit.jupiter.api.Test;

import com.zhujunwei.util.HibernateUtils;

public class HibernateTest {
	
	@Test
	public void demo1() {
		//1、加载Hibernate的核心配置文件：hibernate.cfg.xml
		Configuration configuration = new Configuration().configure();
		//2、创建一个SessionFactory对象：类似于JDBC中的连接池
		SessionFactory sessionFactory = configuration.buildSessionFactory();
		//3、通过SessionFactory获取Session对象：类似JDBC中的Connection
		Session session = sessionFactory.openSession();
		//4、手动开启事务
		Transaction transaction = session.beginTransaction();
		//5、编写代码
		Customer customer = new Customer();
		customer.setCust_name("love you.");
		session.save(customer);
		//6、事务提交
		transaction.commit();
		//7、资源释放
		session.close();
	}
	
	/**
	 * hql
	 * 查询所有记录
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void demo2() {
		Session session = HibernateUtils.getCurrentSession();
		Transaction transaction = session.beginTransaction();
		String hql = "from Customer";
		Query<Customer> query = session.createQuery(hql);
		List<Customer> list = query.list();
		for (Customer customer : list) {
			System.out.println(customer);
		}
		transaction.commit();
	}
	
	/**
	 * hql
	 * 条件查询
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void demo3() {
		Session session = HibernateUtils.getCurrentSession();
		Transaction transaction = session.beginTransaction();
		String hql = "from Customer where cust_name like :cust_name";//注意条件的书写格式
		Query<Customer> query = session.createQuery(hql);
		query.setParameter("cust_name", "朱%");//参数的设置
		List<Customer> list = query.list();
		for (Customer customer : list) {
			System.out.println(customer);
		}
		transaction.commit();
	}
	
	/**
	 * hql
	 * 分页查询
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void demo4() {
		Session session = HibernateUtils.getCurrentSession();
		Transaction transaction = session.beginTransaction();
		String hql = "from Customer";
		Query<Customer> query = session.createQuery(hql);
		query.setFirstResult(1);//起始记录
		query.setMaxResults(2);//每一页的记录数
		List<Customer> list = query.list();
		for (Customer customer : list) {
			System.out.println(customer);
		}
		transaction.commit();
	}
	
	/**
	 * Criteria:QBC(Query By Criteria)
	 * 查询所有记录
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void demo5() {
		Session session = HibernateUtils.getCurrentSession();
		Transaction transaction = session.beginTransaction();
		@SuppressWarnings("deprecation")
		Criteria criteria = session.createCriteria(Customer.class);
		List<Customer> list = criteria.list();
		for (Customer customer : list) {
			System.out.println(customer);
		}
		transaction.commit();
	}
	
	/**
	 * Criteria:QBC(Query By Criteria)
	 * 条件查询
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void demo6() {
		Session session = HibernateUtils.getCurrentSession();
		Transaction transaction = session.beginTransaction();
		@SuppressWarnings("deprecation")
		Criteria criteria = session.createCriteria(Customer.class);
//		criteria.add(Restrictions.ilike("cust_name", "朱%"));
		criteria.add(Restrictions.ilike("cust_name", "朱%",MatchMode.END));
		List<Customer> list = criteria.list();
		for (Customer customer : list) {
			System.out.println(customer);
		}
		transaction.commit();
	}
	
	/**
	 * Criteria:QBC(Query By Criteria)
	 * 分页查询
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void demo7() {
		Session session = HibernateUtils.getCurrentSession();
		Transaction transaction = session.beginTransaction();
		@SuppressWarnings("deprecation")
		Criteria criteria = session.createCriteria(Customer.class);
		criteria.setFirstResult(0);
		criteria.setMaxResults(2);
		List<Customer> list = criteria.list();
		for (Customer customer : list) {
			System.out.println(customer);
		}
		transaction.commit();
	}
	
	
}
