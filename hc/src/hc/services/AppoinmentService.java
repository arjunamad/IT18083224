package hc.services;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import java.util.List;

import hc.beans.Appoinment;
import hc.beans.DoctorSession;
import hc.beans.Payment;
import hc.beans.RawPayment;
import hc.beans.User;
import hc.suport.DbContext;

public class AppoinmentService {

	public static boolean book(Appoinment appoinment, String email) throws Exception {
		User user = UserService.getUserFromEmail(email);

		String sql = "INSERT INTO `appointment`(  `date`, `number`, `paid`, `patient_id`, `session_id`) VALUES (?,?,?,?,?)";
		PreparedStatement statement = DbContext.getConnection().prepareStatement(sql);
		statement.setDate(1, appoinment.getDate());
		statement.setInt(2, appoinment.getNumber());
		statement.setBoolean(3, false);
		statement.setLong(4, user.getId());
		statement.setLong(5, appoinment.getSessionId());
		return statement.execute();
	}

	public static List<Appoinment> userAppoinment(String email) throws Exception {

		User user = UserService.getUserFromEmail(email);

		String sql = "SELECT * FROM appointment where patient_id=?";
		PreparedStatement statement = DbContext.getConnection().prepareStatement(sql);
		statement.setLong(1, user.getId());
		ResultSet r = statement.executeQuery();
		List<Appoinment> appos = new ArrayList<>();
		while (r.next()) {
			Appoinment a = new Appoinment(r.getDate("date"), r.getInt("number"), r.getInt("paid"),
					r.getLong("patient_id"), r.getLong("session_id"));
			a.setPatientName(UserService.getUserFromId(a.getPatientId()).getName());
			a.setSessionDescription(DoctorService.getDessionById(a.getSessionId()).getDescription());
			a.setId(r.getLong("id"));
			appos.add(a);

		}

		return appos;
	}

	public static boolean pay(Long appoId) throws Exception {
		String sql = "UPDATE appointment set paid=? where id=?";
		PreparedStatement statement = DbContext.getConnection().prepareStatement(sql);
		statement.setBoolean(1, true);
		statement.setLong(2, appoId);
		statement.execute();
		String sql2 = "INSERT INTO `payments`( `date`, `price`, `appinment_id`) VALUES (?,?,?)";
		PreparedStatement statement2 = DbContext.getConnection().prepareStatement(sql2);
		statement2.setDate(1, new Date(new java.util.Date().getTime()));
		statement2.setDouble(2, getAppoimentPrice(appoId));
		statement2.setLong(3, appoId);
		return statement2.execute();
	}

	public static double getAppoimentPrice(Long appoId) throws Exception {
		String sql = "select b.price as price from appointment a inner join doctor_session b on a.session_id=b.id where a.id=? ";
		PreparedStatement statement = DbContext.getConnection().prepareStatement(sql);
		statement.setLong(1, appoId);

		ResultSet r = statement.executeQuery();
		while (r.next()) {
			return r.getDouble("price");
		}
		return 0;

	}

	public static List<Payment> getPayments() throws Exception {
		String sql = "SELECT p.date as 'date', a.date as 'session_date',s.description as 'description',a.patient_id as 'pid',s.doctor_id as 'did' from appointment a inner join doctor_session s on a.session_id=s.id    inner join payments p on a.id=p.appinment_id WHERE a.paid=?";
		PreparedStatement statement = DbContext.getConnection().prepareStatement(sql);
		statement.setInt(1, 1);
		ResultSet r = statement.executeQuery();
		List<Payment> payments = new ArrayList<Payment>();
		while (r.next()) {
			payments.add(new Payment(r.getDate("date"), r.getDate("session_date"), r.getString("description"),
					getNameOfUser(r.getLong("pid")), getNameOfUser(r.getLong("did"))));
		}
		return payments;
	}

	public static String getNameOfUser(Long id) throws Exception {
		String sql = "SELECT * from users where id=?";
		PreparedStatement statement = DbContext.getConnection().prepareStatement(sql);
		statement.setLong(1, id);
		ResultSet r = statement.executeQuery();

		while (r.next()) {
			return r.getString("name");
		}
		return "";
	}

	public static boolean deleteAppo(Long id) throws Exception {
		String sql = "DELETE FROM `appointment` WHERE `id`=?";
		PreparedStatement statement = DbContext.getConnection().prepareStatement(sql);
		statement.setLong(1, id);
		return statement.execute();
	}

	public static boolean updateAppo(Appoinment a, Long id) throws Exception {
		String sql = "UPDATE `appointment` SET `date`=?,`number`=?,`paid`=?,`patient_id`=?,`session_id`=? WHERE `id`=?";
		PreparedStatement statement = DbContext.getConnection().prepareStatement(sql);
		statement.setDate(1, a.getDate());
		statement.setInt(2, a.getNumber());
		statement.setInt(3, a.getPaid());
		statement.setLong(4, a.getPatientId());
		statement.setLong(5, a.getSessionId());
		statement.setLong(6, id);
		return statement.execute();
	}

	public static boolean deletePayment(Long id) throws Exception {
		String sql = "DELETE FROM `payments` WHERE `id`=?";
		PreparedStatement statement = DbContext.getConnection().prepareStatement(sql);
		statement.setLong(1, id);
		return statement.execute();
	}

	public static boolean updatePayment(RawPayment a, Long id) throws Exception {
		String sql = "UPDATE `payments` SET  `date`=?,`price`=?,`appinment_id`=? WHERE `id`=?";
		PreparedStatement statement = DbContext.getConnection().prepareStatement(sql);
		statement.setDate(1, a.getDate());
		statement.setDouble(2, a.getPrice());
		statement.setLong(3, a.getAppoId());
		statement.setLong(4, a.getId());
		return statement.execute();
	}
}
