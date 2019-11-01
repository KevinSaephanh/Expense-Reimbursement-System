package services;

import dao.UserDao;
import models.User;
import utils.AuthUtil;
import utils.InputUtil;

public class UserService {
	private UserDao userDao = new UserDao();

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
	
	public User updateUser(User user) {
		user = userDao.updateUser(user);
		return user;
	}
	
	public int deleteUser(int id) {
		int deleteCount = userDao.deleteUser(id);
		return deleteCount;
	}
}
