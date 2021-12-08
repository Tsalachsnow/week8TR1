package week7TR3.mediator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CommentMapper {
    private Long postId;
    private Long id;
    private String username;
    private String comment;
    private String title;
    private String imageName;
    private Long userId;
}
