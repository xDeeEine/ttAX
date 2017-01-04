package com.ttAX.dao;

import java.util.List;

import com.ttAX.model.Roles;
import com.ttAX.model.Users;

public interface UserDAO {

    void addUser(Users users);
    void regUser(Users users, Roles roles);
    void updateUser(Users users);
    void updateRole(Roles roles);
    List<Users> listUsers();
    Users findLogin(String login);
    Users getUserById(int id);
    Roles getRoleById(int id);
    void removeUser(int id);
}
