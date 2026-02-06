package dao;

import java.util.List;

import model.User;

public interface UserDao {

    void add_user(String name, String password);

    List<User> select_by_id(int userID);

    List<User> select_by_name_and_password(String name, String password);

    List<User> select_by_name(String name);

    void update(User user);

    void delete_by_id(int userID);

}
