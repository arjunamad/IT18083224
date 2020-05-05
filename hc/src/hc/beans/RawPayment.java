package hc.beans;

import java.sql.Date;

public class RawPayment {
	private Date date;
	private double price;
	private Long appoId;
	private Long id;

	public RawPayment() {
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Long getAppoId() {
		return appoId;
	}

	public void setAppoId(Long appoId) {
		this.appoId = appoId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
