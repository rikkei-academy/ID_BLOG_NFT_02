package ra.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;


@Data
@Entity
@Table(name = "Comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "commentID")
    private int commentID;

    @Column(name = "commentDate")
    private LocalDate commentDate;

    @Column(name = "commentContent")
    private String commentContent;

    @Column(name = "commentStatus")
    private boolean commentStatus;

    @ManyToOne
    @JoinColumn(name = "userID")
    private User user;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "blogID")
    private Blog blog;


}
