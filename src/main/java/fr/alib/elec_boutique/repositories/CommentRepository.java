package fr.alib.elec_boutique.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.alib.elec_boutique.entities.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
	Page<Comment> findAllByProductId(Long id, Pageable pageable);
}
