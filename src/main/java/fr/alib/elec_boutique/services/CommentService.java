package fr.alib.elec_boutique.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.alib.elec_boutique.dtos.inbound.CommentInboundDTO;
import fr.alib.elec_boutique.entities.Comment;
import fr.alib.elec_boutique.entities.Product;
import fr.alib.elec_boutique.entities.User;
import fr.alib.elec_boutique.exceptions.IdNotFoundException;
import fr.alib.elec_boutique.repositories.CommentRepository;
import io.jsonwebtoken.lang.Arrays;

@Service
@Transactional
public class CommentService {

	@Autowired
	private CommentRepository commentRepository;
	
	/**
	 * Adds a comment.
	 * @param dto
	 * @param user
	 * @param product
	 * @return
	 * @throws IllegalArgumentException
	 * @throws OptimisticLockingFailureException
	 */
	@Transactional(rollbackFor = Exception.class)
	public Comment addComment(CommentInboundDTO dto, User user, ProductService productService, Product product) throws
		IllegalArgumentException,
		OptimisticLockingFailureException
	{
		Comment comment = new Comment(dto, user, product);
		product.getComments().add(comment);
		product = productService.saveProduct(product);
		productService.updateProductAverageNote(product);
		return comment;
	}
	
	/**
	 * Removes a comment.
	 * @param id The comment's id.
	 * @throws IllegalArgumentException
	 */
	@Transactional(rollbackFor = Exception.class)
	public void removeCommentById(Long id) throws IllegalArgumentException
	{
		this.commentRepository.deleteById(id);
	}
	
	/**
	 * Gets a comment by it's id.
	 * @param id The comment's id.
	 * @return The comment.
	 * @throws IdNotFoundException
	 */
	public Comment getCommentById(Long id) throws
		IdNotFoundException
	{
		Optional<Comment> result = this.commentRepository.findById(id);
		if (result.isEmpty()) throw new IdNotFoundException("Couldn't find comment with id '" + id + "'.");
		return result.get();
	}
	
	private final List<String> filterOptions = Arrays.asList( new String[] {
			"page"
	} );

	/**
	 * Gets a list of product comments by page.
	 * @param productId The product's id.
	 * @param params The query parameters.
	 * @return The list of comments.
	 * @throws IllegalArgumentException
	 */
	public List<Comment> getCommentsByProductId(Long productId, Map<String, String> params) throws IllegalArgumentException
	{
		for (String key : params.keySet()) {
			if (!filterOptions.contains(key)) {
				throw new IllegalArgumentException();
			}
		}
		
		Integer page = params.containsKey("page") ? Integer.valueOf( params.get("page") ) : 0;

		if (page < 0) throw new IllegalArgumentException("Incorrect page, value can't be negative.");
		Pageable pageable = PageRequest.of(page, 10, Sort.unsorted());
		Page<Comment> comments = this.commentRepository.findAllByProductId(productId, pageable);
		return comments.getContent();
	}
		
}
