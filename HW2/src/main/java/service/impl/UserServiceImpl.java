package service.impl;

import java.util.List;

import dao.UserDao;
import dao.impl.UserDaoImpl;
import model.User;
import service.UserService;

public class UserServiceImpl implements UserService {
    UserDao userDao = new UserDaoImpl();

    @Override
    public Boolean register(String name, String password) {
        if (userDao.select_by_name(name).size() != 0) {
            return false;
        }
        userDao.add_user(name, password);
        return true;
    }

    @Override
    public Boolean login(String name, String password) {
        if (userDao.select_by_name_and_password(name, password).size() != 0) {
            return true;
        }
        return false;
    }

    @Override
    public List<User> find_by_name(String name) {
        return userDao.select_by_name(name);
    }

    @Override
    public Boolean update(User user) {
        List<User> list = this.find_by_name(user.getName());
        if (list.size() > 0) {
            // If name exists, check if it belongs to the same user (ID check)
            User existing = list.get(0);
            if (existing.getUserID() != user.getUserID()) {
                return false; // Name taken by another user
            }
        }
        userDao.update(user);
        return true;
    }

    @Override
    public Boolean delete_by_id(int userID) {
        if (userDao.select_by_id(userID).size() != 0) {
            userDao.delete_by_id(userID);
            return true;
        }
        return false;
    }

}
