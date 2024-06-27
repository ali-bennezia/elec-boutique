package fr.alib.elec_boutique.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.alib.elec_boutique.entities.Card;
import fr.alib.elec_boutique.entities.Comment;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {
	List<Card> findAllByUserId(Long id);
}
