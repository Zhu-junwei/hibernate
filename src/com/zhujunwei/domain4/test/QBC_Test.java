package com.zhujunwei.domain4.test;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;

import com.zhujunwei.domain4.Customer;
import com.zhujunwei.util.HibernateUtils;

public class QBC_Test {
	@Test
	/**
	 * 简单的查询
	 */
	public void demo1(){
		Session session = HibernateUtils.getCurrentSession();
		Transaction tx = session.beginTransaction();
		
		// 获得Criteria的对象
//		Criteria criteria = session.createCriteria(Customer.class);
//		List<Customer> list = criteria.list();
		//1、创建CriteriaBuiler对象
		CriteriaBuilder builder = session.getCriteriaBuilder();
		//2、获取CriteriaQuery对象
		CriteriaQuery<Customer> criteriaQuery = builder.createQuery(Customer.class);
		//3、指定根条件
		criteriaQuery.from(Customer.class);
		//4、执行查询
		List<Customer> list = session.createQuery(criteriaQuery).getResultList();
		for (Customer customer : list) {
			System.out.println(customer);
		}
		tx.commit();
	}
}
