package com.mycompany.imageboard.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Post.
 */
@Entity
@Table(name = "post")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Post implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 200)
    @Column(name = "post_title", length = 200, nullable = false)
    private String postTitle;

    @NotNull
    @Column(name = "content", nullable = false)
    private String content;

    @NotNull
    @Column(name = "jhi_date", nullable = false)
    private LocalDate date;

    @Column(name = "score")
    private Integer score;

    @ManyToOne(optional = false)
    @NotNull
    private User author;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "post_upvoters",
               joinColumns = @JoinColumn(name="posts_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="upvoters_id", referencedColumnName="id"))
    private Set<User> upvoters = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "post_downvoters",
               joinColumns = @JoinColumn(name="posts_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="downvoters_id", referencedColumnName="id"))
    private Set<User> downvoters = new HashSet<>();

    @OneToMany(mappedBy = "post")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Comment> comments = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public Post postTitle(String postTitle) {
        this.postTitle = postTitle;
        return this;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }

    public String getContent() {
        return content;
    }

    public Post content(String content) {
        this.content = content;
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDate getDate() {
        return date;
    }

    public Post date(LocalDate date) {
        this.date = date;
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Integer getScore() {
        return score;
    }

    public Post score(Integer score) {
        this.score = score;
        return this;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public User getAuthor() {
        return author;
    }

    public Post author(User user) {
        this.author = user;
        return this;
    }

    public void setAuthor(User user) {
        this.author = user;
    }

    public Set<User> getUpvoters() {
        return upvoters;
    }

    public Post upvoters(Set<User> users) {
        this.upvoters = users;
        return this;
    }

    public Post addUpvoters(User user) {
        this.upvoters.add(user);
        return this;
    }

    public Post removeUpvoters(User user) {
        this.upvoters.remove(user);
        return this;
    }

    public void setUpvoters(Set<User> users) {
        this.upvoters = users;
    }

    public Set<User> getDownvoters() {
        return downvoters;
    }

    public Post downvoters(Set<User> users) {
        this.downvoters = users;
        return this;
    }

    public Post addDownvoters(User user) {
        this.downvoters.add(user);
        return this;
    }

    public Post removeDownvoters(User user) {
        this.downvoters.remove(user);
        return this;
    }

    public void setDownvoters(Set<User> users) {
        this.downvoters = users;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public Post comments(Set<Comment> comments) {
        this.comments = comments;
        return this;
    }

    public Post addComments(Comment comment) {
        this.comments.add(comment);
        comment.setPost(this);
        return this;
    }

    public Post removeComments(Comment comment) {
        this.comments.remove(comment);
        comment.setPost(null);
        return this;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Post post = (Post) o;
        if (post.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, post.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Post{" +
            "id=" + id +
            ", postTitle='" + postTitle + "'" +
            ", content='" + content + "'" +
            ", date='" + date + "'" +
            ", score='" + score + "'" +
            '}';
    }
}
