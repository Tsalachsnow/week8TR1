package week7TR3.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.List;
import static javax.persistence.GenerationType.SEQUENCE;


@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "person")
@Table(
        name = "person",
        uniqueConstraints = {
                @UniqueConstraint(name = "person_email_contraint", columnNames = "email")
        }
)
public class Person {
    @Id
    @SequenceGenerator(name = "person_sequence", sequenceName = "person_sequence", allocationSize = 1)
    @GeneratedValue(strategy  = SEQUENCE, generator = "person_sequence")
    @Column(name = "id")
    private Long id;

    @Column(name = "firstname", nullable = false, columnDefinition = "VARCHAR(45)")
    private String firstname;

    @Column(name = "lastname", nullable = false, columnDefinition = "VARCHAR(45)")
    private String lastname;

    @Column(name = "password", nullable = false, columnDefinition = "VARCHAR(45)")
    private String password;

    @Column(name = "email", nullable = false, columnDefinition = "VARCHAR(45)")
    private String email;

    @Column(name = "dateOfBirth", nullable = false, columnDefinition = "VARCHAR(45)")
    private String dob;

    @Column(name = "gender", nullable = false, columnDefinition = "VARCHAR(45)")
    private String gender;

    @OneToMany(mappedBy = "person", cascade = CascadeType.REMOVE)
    private List<Post> post;

    @OneToMany(mappedBy = "person", cascade = CascadeType.REMOVE)
    private List<Comment> comments;

    @OneToOne(mappedBy = "person")
    private Likes myLike;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public List<Post> getPost() {
        return post;
    }

    public void setPost(List<Post> post) {
        this.post = post;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public Likes getMyLike() {
        return myLike;
    }

    public void setMyLike(Likes myLike) {
        this.myLike = myLike;
    }

}