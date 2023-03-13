package ra.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ra.model.entity.User;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
     User findByUserName(String userName);
     List<User> findByUserNameContaining(String userName);

     User findByUserEmail(String email);
     boolean existsByUserName(String userName);
     boolean existsByUserEmail(String email);

}
