package anny.oct.helpdesk.service.security;

import anny.oct.helpdesk.model.User;
import anny.oct.helpdesk.dao.UserDao;
import anny.oct.helpdesk.model.security.CustomUserDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserDao userDao;

    public UserDetailsServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userDao.findUserByName(s);
        if (user == null) {
            throw new UsernameNotFoundException("User with username: " + s + " not found");
        }
        return new CustomUserDetails(user);
    }

}