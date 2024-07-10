package com.hellovelog.myvelog.service;

import com.hellovelog.myvelog.domain.Role;
import com.hellovelog.myvelog.domain.User;
import com.hellovelog.myvelog.dto.UserDTO;
import com.hellovelog.myvelog.repository.UserRepository;
import com.hellovelog.myvelog.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public boolean saveUser(UserDTO userDTO) {
        if (userRepository.findByUsername(userDTO.getUsername()) != null || userRepository.findByUserEmail(userDTO.getEmail()) != null) {
            return false; // 중복된 사용자 ID 또는 이메일이 있는 경우
        }

        User user = User.builder()
                .username(userDTO.getUsername())
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .userEmail(userDTO.getEmail())
                .registrationDate(LocalDateTime.now())
                .role(Role.USER) // 기본 역할 설정
                .build();

        userRepository.save(user);
        return true; // 회원가입 성공
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
}
