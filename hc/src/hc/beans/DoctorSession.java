package hc.beans;

public class DoctorSession {
	private Long id;
	private String day;
	private int maxCount;
	private Long doctorId;
	private String description;
	private double price;

	public DoctorSession(Long id, String day, int maxCount, Long doctorId, String description, double price) {
		super();
		this.id = id;
		this.day = day;
		this.maxCount = maxCount;
		this.doctorId = doctorId;
		this.description = description;
		this.price = price;
	}

	public DoctorSession() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public int getMaxCount() {
		return maxCount;
	}

	public void setMaxCount(int maxCount) {
		this.maxCount = maxCount;
	}

	public Long getDoctorId() {
		return doctorId;
	}

	public void setDoctorId(Long doctorId) {
		this.doctorId = doctorId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

}
