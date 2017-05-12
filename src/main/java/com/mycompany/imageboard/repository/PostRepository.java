package com.mycompany.imageboard.repository;

import com.mycompany.imageboard.domain.Post;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Post entity.
 */
@SuppressWarnings("unused")
public interface PostRepository extends JpaRepository<Post,Long> {

    @Query("select post from Post post where post.author.login = ?#{principal.username}")
    List<Post> findByAuthorIsCurrentUser();

    @Query("select distinct post from Post post left join fetch post.upvoters left join fetch post.downvoters")
    List<Post> findAllWithEagerRelationships();

    @Query("select post from Post post left join fetch post.upvoters left join fetch post.downvoters where post.id =:id")
    Post findOneWithEagerRelationships(@Param("id") Long id);

}
