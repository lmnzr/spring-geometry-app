package id.lmnzr.geometry.demo.service.user.impl;

import id.lmnzr.geometry.demo.model.dto.LoginUser;
import id.lmnzr.geometry.demo.model.entity.user.Role;
import id.lmnzr.geometry.demo.model.entity.user.User;
import id.lmnzr.geometry.demo.model.entity.user.UserTrail;
import id.lmnzr.geometry.demo.repository.user.RoleRepository;
import id.lmnzr.geometry.demo.repository.user.UserRepository;
import id.lmnzr.geometry.demo.repository.user.UserTrailRepository;
import id.lmnzr.geometry.demo.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service(value = "userService")
public class UserServiceImpl implements UserDetailsService, UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserTrailRepository trailRepository;

    @Autowired
    private BCryptPasswordEncoder bcryptEncoder;

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if(user == null){
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), getAuthority(user));
    }

    private Set<SimpleGrantedAuthority> getAuthority(User user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
        });
        return authorities;
    }

    @Transactional
    @Override
    public void registerUser(LoginUser user) {
        Role role = roleRepository.findByName("USER");

        User newUser = new User();
        newUser.setUsername(user.getEmail());
        newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
        newUser.setName(user.getName());
        newUser.setRoles(Collections.singletonList(role));

        userRepository.save(newUser);

        UserTrail trail = new UserTrail();
        trail.setUserId(newUser.getId());
        trail.setRoleId(role.getId());
        trail.setStatus(UserTrail.Status.CREATED);

        trailRepository.save(trail);
    }

    @Override
    public User findOne(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User findById(int id) {
        return userRepository.findById(id).orElse(null);
    }
}
