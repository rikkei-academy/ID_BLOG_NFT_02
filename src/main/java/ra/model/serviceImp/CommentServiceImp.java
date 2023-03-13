package ra.model.serviceImp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ra.model.entity.Comment;
import ra.model.repository.CommentRepository;
import ra.model.service.CommentService;

import java.util.List;

@Service
public class CommentServiceImp implements CommentService {
    @Autowired
    private CommentRepository commentRepository;

    @Override
    public List<Comment> findAll() {
        return commentRepository.findAll();
    }

    @Override
    public Comment findByID(int commentID) {
        return commentRepository.findById(commentID).get();
    }

    @Override
    public Comment save(Comment comment) {
        return commentRepository.save(comment);
    }

//    @Override
//    public Comment saveOrUpdate(Comment comment) {
//        return commentRepository.save(comment);
//    }

    @Override
    public void delete(int commentID) {
        commentRepository.deleteById(commentID);
    }

    @Override
    public List<Comment> sortByDateTimeComment(String direction) {
        if(direction.equals("asc")){
            return commentRepository.findAll(Sort.by("commentDate").ascending());
        }else {
            return commentRepository.findAll(Sort.by("commentDate").descending());
        }
    }

    @Override
    public List<Comment> searchByUserID(int userID) {
        return commentRepository.findByUser_UserID(userID);
    }
}
