package com.zhujunwei.domain2;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;

import com.zhujunwei.util.HibernateUtils;

public class HibernateTest {
	
	/**
	 * 一对多的保存：没有设置级联保存
	 */
	@Test
	public void dome1() {
		Session session = HibernateUtils.getCurrentSession();
		Transaction transaction = session.beginTransaction();
		
		//创建三个雇员
		Employee e1 = new Employee();
		e1.setE_name("朱俊伟");
		Employee e2 = new Employee();
		e2.setE_name("王二");
		Employee e3 = new Employee();
		e3.setE_name("赵四");
		
		//创建两个部门
		Department d1 = new Department();
		d1.setD_name("技术部");
		Department d2 = new Department();
		d2.setD_name("公关部");
		
		//设置关系
		e1.setDepartment(d1);
		e2.setDepartment(d2);
		e3.setDepartment(d2);
		d1.getEmployees().add(e1);
		d2.getEmployees().add(e2);
		d2.getEmployees().add(e3);
		
		//保存
		session.save(e1);
		session.save(e2);
		session.save(e3);
		session.save(d1);
		session.save(d2);
		
		transaction.commit();
	}
	
	/**
	 * 级联保存:在保存部门的时候保存雇员
	 * 需要在Department.hbm.xml设置cascade="save-update"
	 */
	@Test
	public void demo2() {
		Session session = HibernateUtils.getCurrentSession();
		Transaction transaction = session.beginTransaction();
		
		//创建三个雇员
		Employee e1 = new Employee();
		e1.setE_name("朱俊伟");
		Employee e2 = new Employee();
		e2.setE_name("王二");
		Employee e3 = new Employee();
		e3.setE_name("赵四");
		
		//创建两个部门
		Department d1 = new Department();
		d1.setD_name("技术部");
		Department d2 = new Department();
		d2.setD_name("公关部");
		
		//设置关系
		e1.setDepartment(d1);
		e2.setDepartment(d2);
		e3.setDepartment(d2);
		d1.getEmployees().add(e1);
		d2.getEmployees().add(e2);
		d2.getEmployees().add(e3);
		
		//保存
		//只需要保存部门就可以了
		session.save(d1);
		session.save(d2);
		
		transaction.commit();
	}
	
	/**
	 * 级联删除：删除部门的时候删除雇员
	 * 在Department、cascade中添加delete
	 */
	@Test
	public void demo3() {
		Session session = HibernateUtils.getCurrentSession();
		Transaction transaction = session.beginTransaction();
		
		Department department = session.get(Department.class, 1L);
		session.delete(department);
		
		transaction.commit();
	}
	
	/*
	 * 王二原本是公关部，现在调到技术部
	 * 一的一方放弃对外键的维护（Department.hbm.xml加上inverse="true"）
	 * 否则会产生多余的sql语句
	 */
	@Test
	public void demo4() {
		Session session = HibernateUtils.getCurrentSession();
		Transaction transaction = session.beginTransaction();
		
		Employee employee = session.get(Employee.class, 2L);
		Department department = session.get(Department.class, 1L);
		employee.setDepartment(department);
		
		transaction.commit();
	}
}
