package sbd.adam.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="prizes")
public class Prizes {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="value1")
	private int value1;
	
	@Column(name="value2")
	private int value2;
	
	@Column(name="value3")
	private int value3;

	public int getId() {
		return id;
	}

	@Override
	public String toString() {
		return "Prizes [id=" + id + ", value1=" + value1 + ", value2=" + value2 + ", value3=" + value3 + "]";
	}

	public int getValue1() {
		return value1;
	}

	public void setValue1(int value1) {
		this.value1 = value1;
	}

	public int getValue2() {
		return value2;
	}

	public void setValue2(int value2) {
		this.value2 = value2;
	}

	public int getValue3() {
		return value3;
	}

	public void setValue3(int value3) {
		this.value3 = value3;
	}

	public Prizes(int value1, int value2, int value3) {
		super();
		this.value1 = value1;
		this.value2 = value2;
		this.value3 = value3;
	}

}
