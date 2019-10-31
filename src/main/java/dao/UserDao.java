package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import models.User;
import utils.ConnectionUtil;

public class UserDao {
	public User getUser(String username, String password) {
		try (Connection conn = ConnectionUtil.getConnection()) {
			String sql = "SELECT * FROM ers_users WHERE user_name = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, username);
			
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				User user = extractUser(rs);
				return user;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	public User createUser(User user) {
		try (Connection conn = ConnectionUtil.getConnection()) {
			String sql = "INSERT INTO ers_users (user_name, pass_word, first_name, last_name, email, ers_user_role_id)"
					+ "VALUES (?, ?, ?, ?, ?, ?) RETURNING user_id";
			PreparedStatement ps = conn.prepareStatement(sql);

			ps.setString(1, user.getUsername());
			ps.setString(2, user.getPassword());
			ps.setString(3, user.getFirstname());
			ps.setString(4, user.getLastname());
			ps.setString(5, user.getEmail());
			ps.setInt(6, 2); // Placeholder

			ResultSet rs = ps.executeQuery();
			if (rs.next())
				user.setId(rs.getInt("user_id"));
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return user;
	}

	public User updateUser(User user) {
		try (Connection conn = ConnectionUtil.getConnection()) {
			return null;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	public int deleteUser(int id) {
		try (Connection conn = ConnectionUtil.getConnection()) {
			String sql = "DELETE FROM ers_users WHERE user_id = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			
			int deleteCount = ps.executeUpdate();
			return deleteCount;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return 0;
	}

	private User extractUser(ResultSet rs) throws SQLException {
		int id = rs.getInt("user_id");
		String username = rs.getString("user_name");
		String password = rs.getString("pass_word");
		String email = rs.getString("email");
		String firstname = rs.getString("first_name");
		String lastname = rs.getString("last_name");
		int role_id = rs.getInt("ers_user_role_id");
		String role = "";

		if (role_id == 1)
			role = "Employee";
		else if (role_id == 2)
			role = "Manager";

		User user = new User(id, username, password, email, firstname, lastname, role);
		return user;
	}
}
