package ra.model.serviceImp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.model.entity.Blog;
import ra.model.repository.BlogRepository;
import ra.model.service.BlogService;

import java.util.List;

@Service
public class BlogServiceImp implements BlogService {
    @Autowired
    private BlogRepository blogRepository;

    @Override
    public List<Blog> findAll() {
        return blogRepository.findAll();
    }

    @Override
    public Blog findByID(int blogID) {
        return blogRepository.findById(blogID).get();
    }

    @Override
    public Blog saveOrUpdate(Blog blog) {
        return blogRepository.save(blog);
    }

    @Override
    public void delete(int blogID) {
        blogRepository.deleteById(blogID);
    }

    @Override
    public List<Blog> searchByTitle(String blogTitle) {
        return null;
    }

    @Override
    public List<Blog> sortByCreatedDate(String direction) {
        return null;
    }

    @Override
    public List<Blog> findByUserID(int userID) {
        return null;
    }
}
