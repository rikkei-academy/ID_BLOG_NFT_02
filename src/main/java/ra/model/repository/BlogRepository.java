package ra.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ra.model.entity.Blog;
@Repository
public interface BlogRepository extends JpaRepository<Blog,Integer> {
}
