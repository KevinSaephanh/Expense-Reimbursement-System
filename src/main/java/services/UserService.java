package services;

import java.util.List;

import dao.UserDao;
import models.User;
import utils.AuthUtil;
import utils.InputUtil;

public class UserService {
	private UserDao userDao = new UserDao();

	public List<User> getAllUsers() {
		List<User> users = userDao.getAllUsers();
		return users;
	}

	public User getUser(int id) {
		User user = userDao.getUserById(id);
		return user;
	}

	public User signup(User user) {
		// Validate username and password
		if (!InputUtil.isValidUsername(user.getUsername()) || !InputUtil.isValidPassword(user.getPassword())) {
			return null;
		}

		// Hash password
		String hashedPassword = AuthUtil.hashPassword(user.getPassword());
		user.setPassword(hashedPassword);

		user = userDao.createUser(user);
		return user;
	}

	public User login(String username, String password) {
		User user = userDao.getUser(username);
		if (AuthUtil.comparePasswords(password, user.getPassword()))
			return user;
		return null;
	}

	public User updateUser(User user, int id) {
		// Validate username and password
		if (!InputUtil.isValidUsername(user.getUsername()) || !InputUtil.isValidPassword(user.getPassword())) {
			return null;
		}

		// Hash password
		String hashedPassword = AuthUtil.hashPassword(user.getPassword());
		user.setPassword(hashedPassword);

		user = userDao.updateUser(user, id);
		return user;
	}

	public int deleteUser(int id) {
		int deleteCount = userDao.deleteUser(id);
		return deleteCount;
	}
}
