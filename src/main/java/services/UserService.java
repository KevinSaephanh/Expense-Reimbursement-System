package services;

import dao.UserDao;
import models.User;
import utils.InputValidation;

public class UserService {
	private UserDao userDao = new UserDao();
	
	public User signup(User user) {
		System.out.println("We are here");
		if (!InputValidation.isValidUsername(user.getUsername()) || 
				!InputValidation.isValidPassword(user.getPassword())) {
			return null;
		}
		
		System.out.println("Now we are here");
		user = userDao.createUser(user);
		return user;
	}
	
	public User login(String username, String password) {
		User user = userDao.getUser(username, password);
		
		return user;
	}
}
