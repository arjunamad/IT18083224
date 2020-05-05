package hc.beans;

import java.sql.Date;

public class Appoinment {
	private Date date;
	private int number;
	private int paid;
	private Long patientId;
	private Long sessionId;
	private String patientName;
	private String sessionDescription;
	private Long id;

	public Appoinment() {
		super();
	}

	public Appoinment(Date date, int number, int paid, Long patientId, Long sessionId) {
		super();
		this.date = date;
		this.number = number;
		this.paid = paid;
		this.patientId = patientId;
		this.sessionId = sessionId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPatientName() {
		return patientName;
	}

	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}

	public String getSessionDescription() {
		return sessionDescription;
	}

	public void setSessionDescription(String sessionDescription) {
		this.sessionDescription = sessionDescription;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public int getPaid() {
		return paid;
	}

	public void setPaid(int paid) {
		this.paid = paid;
	}

	public Long getPatientId() {
		return patientId;
	}

	public void setPatientId(Long patientId) {
		this.patientId = patientId;
	}

	public Long getSessionId() {
		return sessionId;
	}

	public void setSessionId(Long sessionId) {
		this.sessionId = sessionId;
	}

}
