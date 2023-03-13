package ra.model.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ra.model.entity.Exhibition;
import ra.model.entity.Product;

import java.util.List;

public interface ExhibitionService {
    List<Exhibition> findAll();
    Exhibition findById(int exhibitionId);
    Exhibition saveOfUpdate(Exhibition exhibition);
    void delete(int exhibitionId);
}
