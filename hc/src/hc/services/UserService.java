package hc.services;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import hc.beans.DoctorSession;
import hc.beans.User;
import hc.suport.DbContext;

public class UserService {

	public static boolean IsAuthenticated(String username, String password, Set<String> roleSet) throws Exception {
		String sql = "SELECT a.id as 'id',a.email as 'email',a.name as 'name', a.password as 'password',b.name as 'role' FROM `users` a inner join roles b on a.role_id=b.id where a.email=? and a.password=?";
		PreparedStatement statement = DbContext.getConnection().prepareStatement(sql);
		statement.setString(1, username);
		statement.setString(2, toMd5(password));

		ResultSet result = statement.executeQuery();
		if (result.next() == false)
			return false;
		else {
			if (roleSet.isEmpty()) {
				return true;
			} else {

				String role = result.getString("role");
				return role.contains(role);

			}
		}

	}

	public static boolean signUp(User user) throws Exception {
		String sql = "INSERT INTO `users`( `email`, `name`, `password`, `role_id`) VALUES (?,?,?,?)";

		PreparedStatement statement = DbContext.getConnection().prepareStatement(sql);
		statement.setString(1, user.getEmail());
		statement.setString(2, user.getName());
		statement.setString(3, toMd5(user.getPassword()));

		switch (user.getRole()) {
		case "DOCTOR":
			statement.setInt(4, 2);
			break;
		default:
			statement.setInt(4, 1);
			break;
		}

		return statement.execute();

	}

	public static User getUserFromEmail(String email) throws Exception {
		String sql = "SELECT a.id as 'id',a.email as 'email',a.name as 'name', a.password as 'password',b.name as 'role' FROM `users` a inner join roles b on a.role_id=b.id where a.email=?  ";
		PreparedStatement statement = DbContext.getConnection().prepareStatement(sql);
		statement.setString(1, email);
		ResultSet result = statement.executeQuery();
		while (result.next()) {
			return new User(result.getLong("id"), result.getString("email"), result.getString("name"), null,
					result.getString("role"));
		}
		return null;
	}
	
	public static List<User> getPatients() throws Exception {
		String sql = "SELECT a.id as 'id',a.email as 'email',a.name as 'name', a.password as 'password',b.name as 'role' FROM `users` a inner join roles b on a.role_id=b.id where a.role_id=?  ";
		PreparedStatement statement = DbContext.getConnection().prepareStatement(sql);
		statement.setLong(1, 1);
		ResultSet result = statement.executeQuery();
		List<User> users=new ArrayList<>();
		while (result.next()) {
			users.add(new User(result.getLong("id"), result.getString("email"), result.getString("name"), null,
					result.getString("role")));
		}
		return users;
	}
	public static User getUserFromId(Long id) throws Exception {
		String sql = "SELECT a.id as 'id',a.email as 'email',a.name as 'name', a.password as 'password',b.name as 'role' FROM `users` a inner join roles b on a.role_id=b.id where a.id=?  ";
		PreparedStatement statement = DbContext.getConnection().prepareStatement(sql);
		statement.setLong(1, id);
		ResultSet result = statement.executeQuery();
		while (result.next()) {
			return new User(result.getLong("id"), result.getString("email"), result.getString("name"), null,
					result.getString("role"));
		}
		return null;
	}
	private static String toMd5(String text) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("MD5");
		byte[] messageDigest = md.digest(text.getBytes());
		BigInteger no = new BigInteger(1, messageDigest);
		String hashtext = no.toString(16);
		while (hashtext.length() < 32) {
			hashtext = "0" + hashtext;
		}
		return hashtext;
	}

	public static boolean delete(Long id) throws Exception {
		String sql = "DELETE FROM `users` WHERE `id`=?";
		PreparedStatement statement = DbContext.getConnection().prepareStatement(sql);
		statement.setLong(1, id);
		return statement.execute();
	}

	public static boolean update(User a, Long id) throws Exception {
		String sql = "UPDATE `users` SET  `email`=?,`name`=?,`password`=?,`role_id`=? WHERE `id`=?";
		PreparedStatement statement = DbContext.getConnection().prepareStatement(sql);
		statement.setString(1, a.getEmail());
		statement.setString(2, a.getName());
		statement.setString(3, toMd5(a.getPassword()));
		switch (a.getRole()) {
		case "DOCTOR":
			statement.setInt(4, 2);
			break;
		case "USER":
			statement.setInt(4, 1);
			break;
		case "ADMIN":
			statement.setInt(4, 3);
			break;
		}
		statement.setLong(5, a.getId());
		return statement.execute();
	}
}
