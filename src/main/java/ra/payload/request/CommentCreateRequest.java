package ra.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentCreateRequest {
    private LocalDate commentDate;
    private String commentContent;
    private boolean commentStatus;
    private Integer blogID;

}