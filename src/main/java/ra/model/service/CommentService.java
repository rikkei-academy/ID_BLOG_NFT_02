package ra.model.service;

import org.springframework.stereotype.Service;
import ra.model.entity.Comment;

import java.util.List;

public interface CommentService {
    List<Comment> findAll();
    Comment findByID(int commentID);
    Comment save(Comment comment);
    void delete(int commentID);
    List<Comment> sortByDateTimeComment(String direction);
    List<Comment> searchByUserID(int userID);
}
