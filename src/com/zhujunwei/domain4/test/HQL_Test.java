package com.zhujunwei.domain4.test;

import java.util.Arrays;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.junit.Test;

import com.zhujunwei.domain4.Customer;
import com.zhujunwei.domain4.LinkMan;
import com.zhujunwei.util.HibernateUtils;

/**
 * 	HQL查询
 * @author zhujunwei
 * 2019年4月8日 下午7:46:01
 */
public class HQL_Test {
	
	/**
	 * 初始化顾客和联系人
	 */
	@Test
	public void demo1() {
		
		Session session = HibernateUtils.getCurrentSession();
		Transaction transaction = session.beginTransaction();
		
		Customer customer1 = new Customer();
		customer1.setCust_name("朱俊伟");
		Customer customer2 = new Customer();
		customer2.setCust_name("小猪");
		Customer customer3 = new Customer();
		customer3.setCust_name("小小猪");
		Customer[] customers = {customer1,customer2,customer3};
		
		for (int i = 0; i <=2; i++) {
			for (int j = 1; j <=10; j++) {
				LinkMan linkMan = new LinkMan();
				linkMan.setLkm_name(i+1+"联系人"+j);
				customers[i].getLinkMans().add(linkMan);
				linkMan.setCustomer(customers[i]);
				session.save(linkMan);
			}
			session.save(customers[i]);
		}
		
		transaction.commit();
	}
	
	/**
	 * 简单查询
	 */
	@Test
	public void demo2() {
		
		Session session = HibernateUtils.getCurrentSession();
		Transaction transaction = session.beginTransaction();
		
		// 简单的查询
		@SuppressWarnings("unchecked")
		Query<Customer> query = session.createQuery("from Customer");
		List<Customer> list = query.list();

		// sql中支持*号的写法：select * from cst_customer; 但是在HQL中不支持*号的写法。
		/*
		 * Query query = session.createQuery("select * from Customer");// 报错
		 * List<Customer> list = query.list();
		 */

		for (Customer customer : list) {
			System.out.println(customer);
		}
		
		transaction.commit();
	}
	
	/**
	 * 别名查询
	 */
	@Test
	public void demo3() {
		
		Session session = HibernateUtils.getCurrentSession();
		Transaction transaction = session.beginTransaction();
		
		// 别名的查询
		/*
		 * Query query = session.createQuery("from Customer c"); List<Customer>
		 * list = query.list();
		 */
		@SuppressWarnings("unchecked")
		Query<Customer> query = session.createQuery("select c from Customer c");
		List<Customer> list = query.list();

		for (Customer customer : list) {
			System.out.println(customer);
		}
		
		transaction.commit();
	}
	
	/**
	 * 排序查询
	 */
	@Test
	public void demo4() {
		
		Session session = HibernateUtils.getCurrentSession();
		Transaction transaction = session.beginTransaction();
	
		@SuppressWarnings("unchecked")
		//默认升序
//		Query<Customer> query = session.createQuery("from Customer");
//		Query<Customer> query = session.createQuery("from Customer order by cust_id");
//		Query<Customer> query = session.createQuery("from Customer order by cust_id asc");
		
		//降序
		Query<Customer> query = session.createQuery("from Customer order by cust_id desc");
		List<Customer> list = query.list();
		
		for (Customer customer : list) {
			System.out.println(customer);
		}
		transaction.commit();
	}
	
	/**
	 * 条件查询
	 */
	@Test
	public void demo5() {
		
		Session session = HibernateUtils.getCurrentSession();
		Transaction transaction = session.beginTransaction();
		@SuppressWarnings("unchecked")
		// 条件的查询
		
		// 一、按位置绑定：根据参数的位置进行绑定。
		// 一个条件
//		Query<Customer> query = session.createQuery("from Customer where cust_name = ?0"); 
//		query.setParameter(0, "朱俊伟"); 

		// 多个条件
//		Query<Customer> query = session.createQuery("from Customer where cust_source = ?0 and cust_name like ?1");
//		query.setParameter(0, "电视"); 
//		query.setParameter(1, "小%");
		 

		// 二、按名称绑定
		Query<Customer> query = session.createQuery("from Customer where cust_source = :aaa and cust_name like :bbb");
//		// 设置参数:
		query.setParameter("aaa", "电视");
		query.setParameter("bbb", "小%");
		
		List<Customer> list = query.list();
		for (Customer customer : list) {
			System.out.println(customer);
		}
		
		transaction.commit();
	}
	
	/**
	 * 投影查询
	 */
	@Test
	public void demo6() {
		
		Session session = HibernateUtils.getCurrentSession();
		Transaction transaction = session.beginTransaction();
		@SuppressWarnings("unchecked")
		
		//投影查询
		
		//单个属性
//		List<Object> list = session.createQuery("select c.cust_name from Customer c").list();
//		for (Object object : list) {
//			System.out.println(object);
//		}
		
		//多个属性
//		List<Object[]> list = session.createQuery("select c.cust_name,c.cust_source from Customer c").list();
//		for (Object[] objects : list) {
//			System.out.println(Arrays.toString(objects));
//		}
		
		//多个属性，结果封装到对象当中
		List<Customer> list = session.createQuery("select new Customer(cust_name,cust_source) from Customer").list();
		for (Customer customer : list) {
			System.out.println(customer);
		}
		
		transaction.commit();
	}
	
	
	/**
	 * 分页查询
	 */
	@Test
	public void demo7() {
		
		Session session = HibernateUtils.getCurrentSession();
		Transaction transaction = session.beginTransaction();
		@SuppressWarnings("unchecked")
		
		Query<LinkMan> query = session.createQuery("from LinkMan");
		//0~10条记录
//		query.setFirstResult(0);
//		query.setMaxResults(10);
		//10~20条记录
		query.setFirstResult(10);
		query.setMaxResults(10);
		
		List<LinkMan> list = query.list();
		for (LinkMan linkMan : list) {
			System.out.println(linkMan);
		}
		
		transaction.commit();
	}
	
	
	/**
	 * 分组统计查询
	 */
	@Test
	public void demo8() {
		
		Session session = HibernateUtils.getCurrentSession();
		Transaction transaction = session.beginTransaction();
		// 聚合函数的使用：count(),max(),min(),avg(),sum()
		Object object = session.createQuery("select count(*) from Customer").uniqueResult();
		System.out.println(object);
		// 分组统计：
		@SuppressWarnings("unchecked")
		List<Object[]> list = session.createQuery("select cust_source,count(*) from Customer group by cust_source").list();
		for (Object[] objects : list) {
			System.out.println(Arrays.toString(objects));
		}
		transaction.commit();
	}
	
	@Test
	/**
	 * HQL的多表查询
	 */
	public void demo9() {
		Session session = HibernateUtils.getCurrentSession();
		Transaction tx = session.beginTransaction();
		
		// SQL:SELECT * FROM cst_customer c INNER JOIN cst_linkman l ON
		// c.cust_id = l.lkm_cust_id;
		// HQL:内连接 from Customer c inner join c.linkMans
		
//		@SuppressWarnings("unchecked")
//		List<Object[]> list = session.createQuery("from Customer c inner join c.linkMans").list();
//		for (Object[] objects : list) { 
//			System.out.println(Arrays.toString(objects)); 
//		}
		 

		// HQL:迫切内连接 其实就在普通的内连接inner join后添加一个关键字fetch. from Customer c inner
		// join fetch c.linkMans
		@SuppressWarnings("unchecked")
		List<Customer> list = session.createQuery("select distinct c from Customer c inner join fetch c.linkMans").list();// 通知hibernate，将另一个对象的数据封装到该对象中

		for (Customer customer : list) {
			System.out.println(customer);
		}
		  
		tx.commit();
	}
}
