package fr.alib.elec_boutique.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import fr.alib.elec_boutique.entities.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
	@Query(value = "SELECT * FROM PRODUCT p WHERE "
			+ "( :query IS NULL OR "
			+ "("
			+ " ( p.name LIKE CONCAT('%', :query, '%') ) OR "
			+ " ( p.description LIKE CONCAT('%', :query, '%') ) OR "
			+ " ( p.tags LIKE CONCAT('%', :query, '%') )"
			+ ")"
			+ ") AND "
			+ "("
			+ "( :userId IS NULL OR p.user_product_id = :userId ) AND "
			+ "( :categories IS NULL OR p.tags LIKE CONCAT('%', :categories, '%') ) AND "
			+ "( :miprc IS NULL OR p.price >= :miprc ) AND "
			+ "( :mxprc IS NULL OR p.price <= :mxprc ) AND "
			+ "( :mieval IS NULL OR p.average_note >= :mieval ) AND "
			+ "( :mxeval IS NULL OR p.average_note <= :mxeval )"
			+ ")"
			+ "", nativeQuery = true)
	Page<Product> search(
			@Param("query") String query, 
			@Param("userId") String userId,
			@Param("categories") String categories,
			@Param("miprc") Float minPrice,
			@Param("mxprc") Float maxPrice,
			@Param("mieval") Float minEval,
			@Param("mxeval") Float maxEval,
			Pageable pageeable
	);
	
	List<Product> findAllByUserId(Long userId);
	@Query("select avg(c.note) from Comment c WHERE c.product.id = :id")
	Double findAverageNoteById(@Param("id") Long id);
}
