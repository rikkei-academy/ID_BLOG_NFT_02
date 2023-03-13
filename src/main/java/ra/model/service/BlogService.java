package ra.model.service;

import ra.model.entity.Blog;

import java.time.LocalDate;
import java.util.List;

public interface BlogService {
    List<Blog> findAll();

    Blog findByID(int blogID);

    Blog saveOrUpdate(Blog blog);

    void delete(int blogID);

    List<Blog> searchByTitle(String blogTitle);
    List<Blog> sortByCreatedDate(String direction);
    List<Blog> findByUserID(int userID);
}
