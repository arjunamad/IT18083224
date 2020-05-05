package hc.services;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import hc.beans.Appoinment;
import hc.beans.DoctorSession;
import hc.beans.User;
import hc.suport.DbContext;

public class DoctorService {

	public static List<DoctorSession> getDessions(Long doctorId, String day) throws Exception {
		String sql = "select * from doctor_session where doctor_id=? and day=?";
		PreparedStatement statement = DbContext.getConnection().prepareStatement(sql);
		statement.setLong(1, doctorId);
		statement.setString(2, day);

		ResultSet r = statement.executeQuery();
		List<DoctorSession> session = new ArrayList<>();
		while (r.next()) {
			session.add(new DoctorSession(r.getLong("id"), r.getString("day"), r.getInt("max_count"), doctorId,
					r.getString("description"), r.getDouble("price")));
		}
		return session;
	}
	public static  DoctorSession  getDessionById(Long id) throws Exception {
		String sql = "select * from doctor_session where id=?";
		PreparedStatement statement = DbContext.getConnection().prepareStatement(sql);
		statement.setLong(1, id);
		ResultSet r = statement.executeQuery();
		 
		while (r.next()) {
			return new DoctorSession(r.getLong("id"), r.getString("day"), r.getInt("max_count"), r.getLong("doctor_id"),
					r.getString("description"), r.getDouble("price")) ;
		}
		return null;
	 
	}

	public static List<User> all() throws Exception {
		String sql = "SELECT * FROM `users` where role_id=?";

		PreparedStatement statement = DbContext.getConnection().prepareStatement(sql);
		statement.setInt(1, 2);
		ResultSet r = statement.executeQuery();
		List<User> docs = new ArrayList<>();
		while (r.next()) {
			docs.add(new User(r.getLong("id"), r.getString("email"), r.getString("name"), "", "DOCTOR"));
		}
		return docs;

	}

	public static List<Appoinment> getApposOfDate(int session, Date date, String email) throws Exception {
		User user = UserService.getUserFromEmail(email);
		String sql = "SELECT * from `appointment` where day(`date`)=? and month(`date`)=? and year(`date`)=?  and session_id=?";
		PreparedStatement statement = DbContext.getConnection().prepareStatement(sql);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		statement.setInt(1, cal.get(Calendar.DAY_OF_MONTH));
		statement.setInt(2, cal.get(Calendar.MONTH)+1);
		statement.setInt(3, cal.get(Calendar.YEAR));
		statement.setInt(4, session);
		ResultSet r = statement.executeQuery();
		List<Appoinment> appos = new ArrayList<>();
		while (r.next()) {
			appos.add(new Appoinment(r.getDate("date"), r.getInt("number"), r.getInt("paid"), r.getLong("patient_id"),
					r.getLong("session_id")));
		}
		return appos;
	}

	public static boolean createSession(DoctorSession session, String email) throws Exception {
		String sql = "INSERT INTO `doctor_session`( `day`, `max_count`, `doctor_id`, `description`, `price`) VALUES (?,?,?,?,? )";

		PreparedStatement statement = DbContext.getConnection().prepareStatement(sql);
		statement.setString(1, session.getDay());
		statement.setInt(2, session.getMaxCount());
		statement.setLong(3, UserService.getUserFromEmail(email).getId());
		statement.setString(4, session.getDescription());
		statement.setDouble(5, session.getPrice());
		return statement.execute();
	}
	
	public static boolean delete (Long id) throws Exception {
		String sql = "DELETE FROM `doctor_session` WHERE `id`=?";
		PreparedStatement statement = DbContext.getConnection().prepareStatement(sql);
		statement.setLong(1, id);
		return statement.execute();
	}

	public static boolean update (DoctorSession a, Long id) throws Exception {
		String sql = "UPDATE `doctor_session` SET  `day`=?,`max_count`=?,`doctor_id`=?,`description`=?,`price`=? WHERE `id`=?";
		PreparedStatement statement = DbContext.getConnection().prepareStatement(sql);
		 statement.setString(1, a.getDay());
		 statement.setInt(2, a.getMaxCount());
		 statement.setLong(3, a.getDoctorId());
		 statement.setString(4, a.getDescription());
		 statement.setDouble(5, a.getPrice());
		 statement.setLong(6, a.getId());
		return statement.execute();
	}
}
