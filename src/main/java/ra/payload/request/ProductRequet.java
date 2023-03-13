package ra.payload.request;

import lombok.Data;

import java.util.Date;

@Data

public class ProductRequet {
    private String productName;
    private String productAuthor;
    private int productPrice;
    private String productImage;
    private String productDecription;
    private Date productCreateDate;
    private int exhibition;
}
