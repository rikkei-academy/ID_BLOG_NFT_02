package ra.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ra.model.entity.Exhibition;
import ra.model.entity.Product;
@Repository
public interface ExhibitionRespository extends JpaRepository<Exhibition,Integer> {
}
