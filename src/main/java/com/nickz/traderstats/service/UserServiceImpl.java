package com.nickz.traderstats.service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.nickz.traderstats.dto.UserSavingDto;
import com.nickz.traderstats.exception.EmailAlreadyExistsException;
import com.nickz.traderstats.exception.ResourceNotFoundException;
import com.nickz.traderstats.model.Role;
import com.nickz.traderstats.model.User;
import com.nickz.traderstats.model.UserStatus;
import com.nickz.traderstats.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
	this.userRepository = userRepository;
	this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
	User user = userRepository.findByEmail(email);
	if (user == null) {
	    throw new ResourceNotFoundException("User with this email doesn't exist");
	}

	return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
		this.mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
	return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }

    @Override
    public List<User> findAll() {
	return userRepository.findAll();
    }

    @Override
    public User findById(int userId) throws ResourceNotFoundException {
	return userRepository.findById(userId)
		.orElseThrow(() -> new ResourceNotFoundException("User with this ID doesn't exist"));
    }

    @Override
    public User findByEmail(String email) throws ResourceNotFoundException {
	User foundUser = userRepository.findByEmail(email);
	if (foundUser == null) {
	    throw new ResourceNotFoundException("User with this email doesn't exist");
	}
	return foundUser;
    }

    @Override
    public User getOne(int userId) throws ResourceNotFoundException {
	return userRepository.getOne(userId);
    }

    @Override
    public User save(UserSavingDto userDto) throws EmailAlreadyExistsException {
	User foundUser = userRepository.findByEmail(userDto.getEmail());
	if (foundUser != null) {
	    throw new EmailAlreadyExistsException("User with this email already exists");
	}
	User newUser = new User();
	newUser.setEmail(userDto.getEmail());
	newUser.setPassword(passwordEncoder.encode(userDto.getPassword()));
	newUser.setRegisterDate(LocalDateTime.now());
	newUser.setStatus(UserStatus.EMAIL_NOT_VERIFIED);
	newUser.setRoles(Arrays.asList(new Role(userDto.getRole())));
	return userRepository.save(newUser);
    }

    @Override
    public User update(User user) throws ResourceNotFoundException {
	userRepository.findById(user.getId())
		.orElseThrow(() -> new ResourceNotFoundException("User with this ID doesn't exist"));
	return userRepository.save(user);
    }

    @Override
    public void delete(int userId) throws ResourceNotFoundException {
	userRepository.findById(userId)
		.orElseThrow(() -> new ResourceNotFoundException("User with this ID doesn't exist"));
	userRepository.deleteById(userId);
    }

}
