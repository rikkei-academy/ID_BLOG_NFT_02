package ra.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ra.model.entity.Blog;
import ra.model.entity.Comment;
import ra.model.entity.User;
import ra.model.service.BlogService;
import ra.model.service.CommentService;
import ra.model.service.UserService;
import ra.payload.request.CommentCreateRequest;
import ra.security.CustomUserDetail;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("api/v1/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;
    @Autowired
    private UserService userService;
    @Autowired
    private BlogService blogService;


    /////////////////////////////////////start createComment/////////////////////////////

    @PostMapping("/createComment")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> createComment(@RequestBody CommentCreateRequest comment) {
        try {
            CustomUserDetail userDetails = (CustomUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User users = userService.findByUserId(userDetails.getUserId());
            Blog blog = blogService.findByID(comment.getBlogID());
            Comment com = new Comment();
            com.setUser(users);
            com.setBlog(blog);
            com.setCommentContent(comment.getCommentContent());
            com.setCommentDate(java.time.LocalDate.now());
            com.setCommentStatus(true);
            commentService.save(com);
            return ResponseEntity.ok("Thêm mới comment thành công😘");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok("them moi comment khong thanh cong");
        }
    }
    /////////////////////////////////end createcomment/////////////////////////////////////


    //start getAllComment/////////
    @GetMapping("/getAllComment")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public List<Comment> getAllConten() {
        return commentService.findAll();
    }
    ///end getAllComment////

    ///start getCommentById////
    @GetMapping("/{commentID}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public Comment findByIdComment(@PathVariable("commentID") int commentID) {
        return commentService.findByID(commentID);
    }
    ///end get CommentByid///


    ////////start updateComment////
    @PutMapping("/updateComment/{commentID}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> updateComment(@PathVariable("commentID") int commentID, @RequestBody CommentCreateRequest comment) {
        try {
            CustomUserDetail userDetails = (CustomUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User users = userService.findByUserId(userDetails.getUserId());
            Blog blog = blogService.findByID(comment.getBlogID());
            Comment commentUp = commentService.findByID(commentID);
            commentUp.setUser(users);
            commentUp.setBlog(blog);
            commentUp.setCommentContent(comment.getCommentContent());
            commentUp.setCommentDate(java.time.LocalDate.now());
            commentUp.setCommentStatus(true);
            commentService.save(commentUp);
            return ResponseEntity.ok("Bạn đã cập nhật comment thành công😘😘");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok("có lỗi trong quá trình sử lý vui lòng thử lại");
        }
    }
    /////// end updteComment/////

    ////start deleteComment////
    @DeleteMapping("/delete/{commentID}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> deleteComment(@PathVariable("commentID") int commentID) {
        try {
            Comment comment = commentService.findByID(commentID);
            commentService.delete(commentID);
            return ResponseEntity.ok("Bạn đã xóa comment thành công 😘😘😘");
        } catch (Exception e) {
            return ResponseEntity.ok("Đã có vấn đề trong quá trình xóa");
        }
    }
    ///end deleteComment////


    /////////start commentDateTime/////

    @GetMapping("/sortByDaTimeComment")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<List<Comment>> sortByDateComment(@RequestParam("direction") String direction){
        List<Comment> listComment = commentService.sortByDateTimeComment(direction);
        return new ResponseEntity<>(listComment, HttpStatus.OK);
    }

    ////end commentDateTime//////


    ////start CommentByUserName////

    @GetMapping("/searchByUserName")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public List<Comment> searchByUserName(@RequestParam String userName){
        List<User> listUser = userService.searchUserName(userName);
        List<Comment> listComment = new ArrayList<>();
        for (User u: listUser) {
            List<Comment> listCo = commentService.searchByUserID(u.getUserID());
            listComment.addAll(listCo);
        }
        return listComment;
    }

    ////end CommentByUserName////



}
