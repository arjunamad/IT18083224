package hc.beans;

public class SingleResponse {

	private boolean status;
	private String message;
	
	public SingleResponse() {}
	public SingleResponse(Exception e) {
		this.status=false;
		this.message=e.getMessage();
	}
	public SingleResponse (String message,boolean status) {
		this.message=message;
		this.status=status;
	}
	public SingleResponse(boolean status) {
		this.status=status;
	}
	public static SingleResponse ok(String message) {
		return new SingleResponse(message,true);		
	}
	public static SingleResponse error(String message) {
		return new SingleResponse(message,false);		
	}
	public static SingleResponse error(Exception e) {
		return new SingleResponse(e);
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	 
	
}
