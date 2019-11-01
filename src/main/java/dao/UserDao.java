package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import models.User;
import utils.ConnectionUtil;

public class UserDao {
	public List<User> getAllUsers() {
		try (Connection conn = ConnectionUtil.getConnection()) {
			String sql = "SELECT * FROM ers_users";
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			// Extract all users and add to list
			List<User> users = new ArrayList<>();
			while (rs.next()) {
				User user = extractUser(rs);
				users.add(user);
			}
			return users;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	public User getUserById(int id) {
		try (Connection conn = ConnectionUtil.getConnection()) {
			String sql = "SELECT * FROM ers_users WHERE user_id = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);

			// Check if user was found
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

	// Used for login
	public User getUser(String username) {
		try (Connection conn = ConnectionUtil.getConnection()) {
			String sql = "SELECT * FROM ers_users WHERE user_name = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, username);

			// Check if user was found
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
			ps.setInt(6, user.getroleId());

			ResultSet rs = ps.executeQuery();
			if (rs.next())
				user.setId(rs.getInt("user_id"));
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return user;
	}

	public User updateUser(User user, int id) {
		try (Connection conn = ConnectionUtil.getConnection()) {
			String sql = "UPDATE ers_users "
					+ "SET user_name = ?, pass_word = ?, first_name = ?, last_name = ?, email = ?, ers_user_role_id = ? "
					+ "WHERE user_id = ? RETURNING user_name, ers_user_role_id";
			PreparedStatement ps = conn.prepareStatement(sql);

			ps.setString(1, user.getUsername());
			ps.setString(2, user.getPassword());
			ps.setString(3, user.getFirstname());
			ps.setString(4, user.getLastname());
			ps.setString(5, user.getEmail());
			ps.setInt(6, user.getroleId());
			ps.setInt(7, id);

			// Check if user was updated
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				String username = rs.getString("user_name");
				int roleId = rs.getInt("ers_user_role_id");
				
				user.setUsername(username);
				user.setroleId(roleId);
				return user;
			}
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

		User user = new User(id, username, password, email, firstname, lastname, role_id);
		return user;
	}
}
