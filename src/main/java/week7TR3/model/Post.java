package week7TR3.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.List;
import static javax.persistence.GenerationType.SEQUENCE;
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Post {
    @Id
    @SequenceGenerator(
            name = "post_sequence",
            sequenceName = "post_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy  = SEQUENCE,
            generator = "post_sequence"
    )
    @Column(
            name = "postId"
    )
    private Long postId;
    @Column(
            name = "title",
            nullable = false,
            columnDefinition = "VARCHAR(45)"
    )
    private String title;
    @Column(
            name = "body",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String body;
    @Column(
            name = "imageName",
            nullable = false,
            columnDefinition = "VARCHAR(45)"
    )
    private String imageName;
    @Column(
            name = "checker",
            nullable = false,
            columnDefinition = "VARCHAR(45)"
    )
    private String checker;
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name = "userId", referencedColumnName = "id")
    private Person person;
    @OneToMany(mappedBy = "person", cascade = CascadeType.REMOVE, fetch=FetchType.EAGER)
    private List<Comment> comments;
    @OneToOne(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private Likes mylike;
    public Long getPostId() {
        return postId;
    }
    public void setPostId(Long postId) {
        this.postId = postId;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getBody() {
        return body;
    }
    public void setBody(String body) {
        this.body = body;
    }
    public String getImageName() {
        return imageName;
    }
    public void setImageName(String imageName) {
        this.imageName = imageName;
    }
    public Person getPerson() {
        return person;
    }
    public void setPerson(Person person) {
        this.person = person;
    }
    public List<Comment> getComments() {
        return comments;
    }
    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
    public Likes getMylike() {
        return mylike;
    }
    public void setMylike(Likes mylike) {
        this.mylike = mylike;
    }
    public String getChecker() {
        return checker;
    }
    public void setChecker(String checker) {
        this.checker = checker;
    }
}