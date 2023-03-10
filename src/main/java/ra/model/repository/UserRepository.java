package ra.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ra.model.entity.Users;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<Users,Integer> {
     Users findByUserName(String userName);
     List<Users> findByUserNameContaining(String userName);

     Users findByEmail(String email);
     boolean existsByUserName(String userName);
     boolean existsByEmail(String email);

}
