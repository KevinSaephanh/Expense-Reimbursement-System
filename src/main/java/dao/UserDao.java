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

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	public User createUser(User user) {
		System.out.println("Jumping around");
		try (Connection conn = ConnectionUtil.getConnection()) {
			System.out.println("Are we here?");
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
			System.out.println("Okay");
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

	public int deleteUser(User user) {
		try (Connection conn = ConnectionUtil.getConnection()) {
			return 1;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return 0;
	}
}
