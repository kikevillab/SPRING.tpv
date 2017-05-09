package services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import daos.users.AuthorizationDao;
import daos.users.UserDao;
import entities.users.Role;
import entities.users.User;

@Service
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private AuthorizationDao authorizationDao;

    @Override
    public UserDetails loadUserByUsername(final String mobileOrTokenValue) throws UsernameNotFoundException {
        User user = userDao.findByTokenValue(mobileOrTokenValue);
        if (user != null) {
            List<Role> roleList = authorizationDao.findRoleByUser(user);
            return this.userBuilder(Long.toString(user.getMobile()), new BCryptPasswordEncoder().encode(""), roleList);
        } else {
            try {
                user = userDao.findByMobile(Long.valueOf(mobileOrTokenValue));
            } catch (NumberFormatException nfe) {
                throw new UsernameNotFoundException("Usuario no encontrado");
            }
            if (user != null) {
                return this.userBuilder(String.valueOf(user.getMobile()), user.getPassword(), Arrays.asList(Role.AUTHENTICATED));
            } else {
                throw new UsernameNotFoundException("Usuario no encontrado");
            }
        }
    }

    private org.springframework.security.core.userdetails.User userBuilder(String mobile, String password, List<Role> roles) {
        boolean enabled = true;
        boolean accountNonExpired = true;
        boolean credentialsNonExpired = true;
        boolean accountNonLocked = true;
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.roleName()));
        }
        return new org.springframework.security.core.userdetails.User(mobile, password, enabled, accountNonExpired, credentialsNonExpired,
                accountNonLocked, authorities);
    }
}
