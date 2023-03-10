package ra.model.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ra.model.entity.Users;

import java.util.List;

public interface UserService {
    List<Users> sortByStudentName(String direction);
    List<Users> findByUserName(String userName);
    List<Users> searchUserName(String userName);

    Users findByEmail(String email);

    boolean existsByUserName(String userName);

    boolean existsByEmail(String email);

    Users saveOrUpdate(Users user);

    Users findByUserId(int users);

    List<Users> findAll();
    Page<Users> pagging(Pageable pageable);
    Users updateStatus(Users users);

    List<Users> listFilter(Integer option);

}