package org.bithacks.defidefender.dao;

import org.bithacks.defidefender.model.Po.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, String> {

    List<User> findUsersByStatus(int status);

    List<User> findUsersByWeidAndStatus(String weid, int status);

}
