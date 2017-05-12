package com.mycompany.imageboard.repository;

import com.mycompany.imageboard.domain.Comment;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Comment entity.
 */
@SuppressWarnings("unused")
public interface CommentRepository extends JpaRepository<Comment,Long> {

    @Query("select comment from Comment comment where comment.author.login = ?#{principal.username}")
    List<Comment> findByAuthorIsCurrentUser();

    @Query("select distinct comment from Comment comment left join fetch comment.upvoters left join fetch comment.downvoters")
    List<Comment> findAllWithEagerRelationships();

    @Query("select comment from Comment comment left join fetch comment.upvoters left join fetch comment.downvoters where comment.id =:id")
    Comment findOneWithEagerRelationships(@Param("id") Long id);

    List<Comment> findByPostId(Long id);

}
