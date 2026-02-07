package service;

import java.util.List;

import model.User;

public interface UserService {

    Boolean register(String name, String password);

    Boolean login(String name, String password);

    List<User> find_by_name(String name);

    Boolean update(User user);

    Boolean delete_by_id(int userID);
}
