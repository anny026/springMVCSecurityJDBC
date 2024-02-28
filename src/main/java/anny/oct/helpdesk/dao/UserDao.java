package anny.oct.helpdesk.dao;

import anny.oct.helpdesk.model.User;

public interface UserDao {
    User findUserByName(String name);
}
