package com.hellovelog.myvelog.service;

import com.hellovelog.myvelog.domain.Role;
import com.hellovelog.myvelog.domain.User;
import com.hellovelog.myvelog.dto.UserDTO;
import com.hellovelog.myvelog.exception.customException.DuplicateUserException;
import com.hellovelog.myvelog.repository.UserRepository;
import com.hellovelog.myvelog.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public boolean saveUser(UserDTO userDTO) {
       try {
           if(userRepository.existsByUsernameOrEmail(userDTO.getUsername(),userDTO.getEmail())){
               throw new DuplicateUserException("중복된 사용자 이름 또는 이메일이 존재합니다.");
           }
           User user = User.builder()
                   .username(userDTO.getUsername())
                   .password(passwordEncoder.encode(userDTO.getPassword()))
                   .userEmail(userDTO.getEmail())
                   .registrationDate(LocalDateTime.now())
                   .role(Role.USER) //admin 은 따로 지정
                   .build();

           userRepository.save(user);
           return true;
       }catch (DataIntegrityViolationException e){
           return false;
       }
    }
    @Transactional(readOnly = true)
    public User findByUserName(String username){
        return userRepository.findByUsername(username);
    }

    @Transactional(readOnly = true)
    public boolean checkUsernameExists(String username) {
        return userRepository.findByUsername(username) != null;
    }
    @Transactional(readOnly = true)
    public boolean checkEmailExists(String email) {
        return userRepository.findByUserEmail(email) != null;
    }


    @Transactional(readOnly = true)
    public Optional<User> getCurrentUser() {
        String username = SecurityUtil.getCurrentUsername();
        if (username != null) {
            return Optional.ofNullable(userRepository.findByUsername(username));
        }
        return Optional.empty();
    }

    @Transactional
    public  void setAuthentication(Model model, Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            model.addAttribute("loggedIn", false);
        } else {
            model.addAttribute("loggedIn", true);
            Optional<User> optionalUser = getCurrentUser();
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                model.addAttribute("username", user.getUsername());
            }
        }
    }


}
