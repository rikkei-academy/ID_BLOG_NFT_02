package ra.model.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ra.model.entity.User;

import java.util.List;

public interface UserService {
    List<User> sortByUserName(String direction);
    List<User> findByUserName(String userName);
    List<User> searchUserName(String userName);

    User findByEmail(String email);

    boolean existsByUserName(String userName);

    boolean existsByEmail(String email);

    User saveOrUpdate(User user);

    User findByUserId(int users);

    List<User> findAll();
    Page<User> pagging(Pageable pageable);
    User updateStatus(User users);

    List<User> listFilter(Integer option);

}