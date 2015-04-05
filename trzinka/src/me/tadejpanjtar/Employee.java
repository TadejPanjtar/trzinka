package me.tadejpanjtar;

/**
 * 
 * @author www.codejava.net
 * 
 */
public class Employee {
	private int index;
	private String name;
	private String job;
	private int age;

	public Employee(String name, String job, int age) {
		this.name = name;
		this.job = job;
		this.age = age;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}
}
