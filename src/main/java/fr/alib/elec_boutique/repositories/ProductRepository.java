package fr.alib.elec_boutique.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.alib.elec_boutique.entities.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
	List<Product> findAllByUserId(Long userId);
}
