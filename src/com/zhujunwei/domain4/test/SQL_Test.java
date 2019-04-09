package com.zhujunwei.domain4.test;

import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;

import com.zhujunwei.domain4.Customer;
import com.zhujunwei.util.HibernateUtils;

@SuppressWarnings("deprecation")
public class SQL_Test {
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public void demo1() {
		Session session = HibernateUtils.getCurrentSession();
		Transaction tx = session.beginTransaction();
		
		/*SQLQuery sqlQuery = session.createSQLQuery("select * from cst_customer");
		List<Object[]> list = sqlQuery.list();
		for (Object[] objects : list) {
			System.out.println(Arrays.toString(objects));
		}*/
		
		SQLQuery sqlQuery = session.createSQLQuery("select * from cst_customer");
		sqlQuery.addEntity(Customer.class);
		List<Customer> list = sqlQuery.list();
		for (Customer customer : list) {
			System.out.println(customer);
		}
		tx.commit();
	}
}
