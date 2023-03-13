package ra.payload.response;

import lombok.Data;
import java.util.Date;

@Data
public class ProductResponse {
    private int productId;
    private String productName;
    private String productAuthor;
    private int productPrice;
    private String productImage;
    private String productDecription;
    private Date productCreateDate;
    private String userName;
//    private int exhibitionId;


}
