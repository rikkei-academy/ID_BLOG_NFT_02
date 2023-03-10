//package ra.model.entity;
//
//import com.fasterxml.jackson.annotation.JsonFormat;
//import lombok.Data;
//
//import javax.persistence.*;
//import java.util.Date;
//
//@Entity
//@Data
//@Table(name = "Blog")
//public class Blog {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "blogId")
//    private int blogId;
//    @Column(name = "blogTitle")
//    private String blogTitle;
//    @Column(name = "blogContent")
//    private String bogContent;
//    @Column(name = "blogImage")
//    private String blogImage;
//    @Column(name = "blogCreateDate")
//    @JsonFormat(pattern = "dd/MM/yyyy")
//    private Date blogCreateDate;
//}
