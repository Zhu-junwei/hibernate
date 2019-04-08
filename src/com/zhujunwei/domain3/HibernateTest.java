package com.zhujunwei.domain3;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;

import com.zhujunwei.util.HibernateUtils;

public class HibernateTest {
	
	/**
	  *  多对多的保存：没有设置级联保存
	 */
	@Test
	public void dome1() {
		Session session = HibernateUtils.getCurrentSession();
		Transaction transaction = session.beginTransaction();
		
		Student s1 = new Student();
		s1.setS_name("朱俊伟");
		Student s2 = new Student();
		s2.setS_name("朱俊伟2");
		
		Course c1 = new Course();
		c1.setC_name("Java开发");
		Course c2 = new Course();
		c2.setC_name("Android开发");
		
		
		
		s1.getCourses().add(c1);
		s2.getCourses().add(c1);
		c1.getStudents().add(s1);
		c1.getStudents().add(s2);
		
		session.save(s1);
		session.save(s2);
		session.save(c1);
		session.save(c2);
		
		
		transaction.commit();
	}
	
	/**
	  *  多对多的保存：级联保存
	  *  保存课程时保存学生
	 */
	@Test
	public void dome2() {
		Session session = HibernateUtils.getCurrentSession();
		Transaction transaction = session.beginTransaction();
		
		Student s1 = new Student();
		s1.setS_name("朱俊伟");
		Student s2 = new Student();
		s2.setS_name("朱俊伟2");
		
		Course c1 = new Course();
		c1.setC_name("Java开发");
		
		
		s1.getCourses().add(c1);
		s2.getCourses().add(c1);
		c1.getStudents().add(s1);
		c1.getStudents().add(s2);
		
//		session.save(s1);
//		session.save(s2);
		session.save(c1);
		
		transaction.commit();
	}
	
	/**
	  * 级联删除:删除课程的时候删除学生
	  * 在Student.hbm.xml中设置cascade=delete
	  * 
	 */
	@Test
	public void dome3() {
		Session session = HibernateUtils.getCurrentSession();
		Transaction transaction = session.beginTransaction();
		
		Course course = session.get(Course.class, 1L);
		session.delete(course);
		
		transaction.commit();
	}
	
	/**
	 * 给学生添加一门新课
	 * 取得学生对象
	 * 取得课程对象
	 * 学生对象添加课程对象
	 */
	@Test
	public void dome4() {
		Session session = HibernateUtils.getCurrentSession();
		Transaction transaction = session.beginTransaction();
		
		//获取学生和课程对象
		Student student = session.get(Student.class, 1L);
		Course course = session.get(Course.class, 2L);
		
		//添加课程
		student.getCourses().add(course);
		
		transaction.commit();
	}
	
	/**
	 * 给学生改选课程（移除课程）
	 * 获得学生对象
	 * 获得课程对象（旧课和新课）
	 * 将学生对象中的课程移除
	 * 添加新课
	 */
	@Test
	public void dome5() {
		Session session = HibernateUtils.getCurrentSession();
		Transaction transaction = session.beginTransaction();
		
		//获取学生和课程对象
		Student student = session.get(Student.class, 1L);
		Course course1 = session.get(Course.class, 1L);
		Course course2 = session.get(Course.class, 2L);
		
		//删除旧课程
		student.getCourses().remove(course1);
		//添加新课程
		student.getCourses().add(course2);
		
		transaction.commit();
	}
}
