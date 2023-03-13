package ra.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name = "Blog")
public class Blog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "blogID")
    private int blogID;

    @Column(name = "blogTitle")
    private String blogTitle;

    @Column(name = "blogContent")
    private String blogContent;

    @Column(name = "blogImage")
    private String blogImage;

    @Column(name = "blogCreateDate")
    private LocalDate blogCreateDate;

    @Column(name = "blogStatus")
    private boolean blogStatus;

    @ManyToOne
    @JoinColumn(name = "userID")
    private User user;

    @ManyToMany
    @JoinTable(name= "Blog_Tag", joinColumns = @JoinColumn(name = "blogID"), inverseJoinColumns = @JoinColumn(name="tagID"))
    private List<Tag> listTag;

    @OneToMany(mappedBy = "blog")
    private List<Comment> listComment;
}
