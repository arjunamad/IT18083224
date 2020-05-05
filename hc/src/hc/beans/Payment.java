package hc.beans;

import java.sql.Date;

public class Payment {
	private Date date;
	private Date sessionDate;
	private String sessionDescription;
	private String user;
	private String doctor;

	public Payment(Date date, Date sessionDate, String sessionDescription, String user, String doctor) {
		super();
		this.date = date;
		this.sessionDate = sessionDate;
		this.sessionDescription = sessionDescription;
		this.user = user;
		this.doctor = doctor;
	}

	public Payment() {
		super();
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Date getSessionDate() {
		return sessionDate;
	}

	public void setSessionDate(Date sessionDate) {
		this.sessionDate = sessionDate;
	}

	public String getSessionDescription() {
		return sessionDescription;
	}

	public void setSessionDescription(String sessionDescription) {
		this.sessionDescription = sessionDescription;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getDoctor() {
		return doctor;
	}

	public void setDoctor(String doctor) {
		this.doctor = doctor;
	}

}
