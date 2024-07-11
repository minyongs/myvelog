package com.hellovelog.myvelog.repository;

import com.hellovelog.myvelog.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    User findByUsername(String username);
    User findByUserEmail(String email);
    @Query("SELECT u FROM User u LEFT JOIN FETCH u.blog WHERE u.username = :username")
    User findByUsernameWithBlog(@Param("username") String username);

    @Query("SELECT COUNT(u) > 0 FROM User u WHERE u.username = :username OR u.userEmail = :email")
    boolean existsByUsernameOrEmail(@Param("username") String username, @Param("email") String email);


}
