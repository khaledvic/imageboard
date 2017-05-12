package com.mycompany.imageboard.domain;

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
 * A Comment.
 */
@Entity
@Table(name = "comment")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Comment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 200)
    @Column(name = "content", length = 200, nullable = false)
    private String content;

    @NotNull
    @Column(name = "jhi_date", nullable = false)
    private LocalDate date;

    @Column(name = "score")
    private Integer score;

    @ManyToOne(optional = false)
    @NotNull
    private Post post;

    @ManyToOne(optional = false)
    @NotNull
    private User author;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "comment_upvoters",
               joinColumns = @JoinColumn(name="comments_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="upvoters_id", referencedColumnName="id"))
    private Set<User> upvoters = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "comment_downvoters",
               joinColumns = @JoinColumn(name="comments_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="downvoters_id", referencedColumnName="id"))
    private Set<User> downvoters = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public Comment content(String content) {
        this.content = content;
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDate getDate() {
        return date;
    }

    public Comment date(LocalDate date) {
        this.date = date;
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Integer getScore() {
        return score;
    }

    public Comment score(Integer score) {
        this.score = score;
        return this;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Post getPost() {
        return post;
    }

    public Comment post(Post post) {
        this.post = post;
        return this;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public User getAuthor() {
        return author;
    }

    public Comment author(User user) {
        this.author = user;
        return this;
    }

    public void setAuthor(User user) {
        this.author = user;
    }

    public Set<User> getUpvoters() {
        return upvoters;
    }

    public Comment upvoters(Set<User> users) {
        this.upvoters = users;
        return this;
    }

    public Comment addUpvoters(User user) {
        this.upvoters.add(user);
        return this;
    }

    public Comment removeUpvoters(User user) {
        this.upvoters.remove(user);
        return this;
    }

    public void setUpvoters(Set<User> users) {
        this.upvoters = users;
    }

    public Set<User> getDownvoters() {
        return downvoters;
    }

    public Comment downvoters(Set<User> users) {
        this.downvoters = users;
        return this;
    }

    public Comment addDownvoters(User user) {
        this.downvoters.add(user);
        return this;
    }

    public Comment removeDownvoters(User user) {
        this.downvoters.remove(user);
        return this;
    }

    public void setDownvoters(Set<User> users) {
        this.downvoters = users;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Comment comment = (Comment) o;
        if (comment.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, comment.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Comment{" +
            "id=" + id +
            ", content='" + content + "'" +
            ", date='" + date + "'" +
            ", score='" + score + "'" +
            '}';
    }
}
