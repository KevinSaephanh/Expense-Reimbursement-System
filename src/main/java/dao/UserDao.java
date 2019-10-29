package dao;

import java.sql.Connection;
import java.sql.SQLException;

import models.User;
import utils.ConnectionUtil;

public class UserDao {
	public User login(User user) {
		try (Connection conn = ConnectionUtil.getConnection()) {
			return null;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
