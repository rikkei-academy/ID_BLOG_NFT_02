package ra.model.service;

import ra.model.entity.Users;

import java.util.List;

public interface UserService {
    Users findByUserName(String userName);

    Users findByEmail(String email);

    boolean existsByUserName(String userName);

    boolean existsByEmail(String email);

    Users saveOrUpdate(Users user);

    Users findByUserId(int users);

    List<Users> findAll();
}