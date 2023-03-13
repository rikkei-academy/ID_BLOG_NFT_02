package ra.model.serviceImp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.model.entity.Exhibition;
import ra.model.repository.ExhibitionRespository;
import ra.model.service.ExhibitionService;

import java.util.List;

@Service
public class ExhibitionServiceImp implements ExhibitionService {
    @Autowired
    private ExhibitionRespository exhibitionRespository;

    @Override
    public List<Exhibition> findAll() {
        return exhibitionRespository.findAll();
    }

    @Override
    public Exhibition findById(int exhibitionId) {
        return exhibitionRespository.findById(exhibitionId).get();
    }

    @Override
    public Exhibition saveOfUpdate(Exhibition exhibition) {
        return exhibitionRespository.save(exhibition);
    }

    @Override
    public void delete(int exhibitionId) {
        exhibitionRespository.deleteById(exhibitionId);
    }
}
