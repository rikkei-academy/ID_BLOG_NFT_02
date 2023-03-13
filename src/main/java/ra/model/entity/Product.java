package ra.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name = "Product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "productID")
    private int productID;

    @Column(name = "productName")
    private String productName;

    @Column(name = "productAuthor")
    private String productAuthor;

    @Column(name = "productPrice")
    private int productPrice;

    @Column(name = "ProductImage")
    private String productImage;

    @Column(name = "productDescription")
    private String productDescription;

    @Column(name = "productCreateDate")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date productCreateDate;

    @Column(name = "productStatus")
    private boolean productStatus;

    @ManyToOne
    @JoinColumn(name = "userID")
    private User users;

    @ManyToMany
    @JoinTable(name= "Product_Tag", joinColumns = @JoinColumn(name = "productID"), inverseJoinColumns = @JoinColumn(name="tagID"))
    private List<Tag> listTag;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "exhibitionId")
    private Exhibition exhibition;
}
