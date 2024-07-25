package com.ecommerce.api.user.service;

import com.ecommerce.api.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    public User findByMobileNo(@Param("mobile_no") String mobile_no);
}
