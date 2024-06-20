package fr.alib.elec_boutique.controllers;

import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.alib.elec_boutique.dtos.inbound.CommentInboundDTO;
import fr.alib.elec_boutique.dtos.outbound.CommentOutboundDTO;
import fr.alib.elec_boutique.entities.Comment;
import fr.alib.elec_boutique.entities.Product;
import fr.alib.elec_boutique.exceptions.IdNotFoundException;
import fr.alib.elec_boutique.exceptions.LackingAuthorizationsException;
import fr.alib.elec_boutique.services.CommentService;
import fr.alib.elec_boutique.services.CustomUserDetails;
import fr.alib.elec_boutique.services.ProductService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/products/{id}/comments")
public class CommentController {

	@Autowired
	private CommentService commentService;
	
	@Autowired
	private ProductService productService;
	
	private CustomUserDetails throwIfUnauthenticated() throws BadCredentialsException
	{
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null) throw new BadCredentialsException("User isn't authenticated.");
		return (CustomUserDetails) authentication.getPrincipal();
	}
	
	@PostMapping("")
	public ResponseEntity<?> postComment( @PathVariable("id") Long id, @Valid @RequestBody CommentInboundDTO dto ) throws 
		BadCredentialsException,
		IdNotFoundException,
		IllegalArgumentException
	{
		CustomUserDetails userDetails = throwIfUnauthenticated();
		Product product = this.productService.getProductById(id);
		
		Comment comment = this.commentService.addComment(dto, userDetails.getUser(), productService, product);
		
		return ResponseEntity.ok( new CommentOutboundDTO(comment) );
	}
	
	@GetMapping("")
	public ResponseEntity<?> getComments( @PathVariable("id") Long id, @RequestParam Map<String, String> params )
	{
		return ResponseEntity.ok( 
				this.commentService.getCommentsByProductId(id, params).stream().map(c -> {
					return new CommentOutboundDTO(c);
				}).collect(Collectors.toList())
			);
	}
	
	@DeleteMapping("/{commentId}")
	public ResponseEntity<?> deleteComment( @PathVariable("id") Long productId, @PathVariable("commentId") Long commentId )
	{
		ProductController.throwIfNotAdminOrOwner(productService, productId);
		this.commentService.removeCommentById(commentId);
		return ResponseEntity.noContent().build();
	}
	
	@ExceptionHandler(OptimisticLockingFailureException.class)
	public ResponseEntity<?> handleInternalServerError()
	{
		return ResponseEntity.internalServerError().build();
	}
	
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<?> handleBadRequest()
	{
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	}
	
	@ExceptionHandler(IdNotFoundException.class)
	public ResponseEntity<?> handleNotFound()
	{
		return ResponseEntity.notFound().build();
	}
	
	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<?> handleUnauthorized()
	{
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	}
	
	@ExceptionHandler(LackingAuthorizationsException.class)
	public ResponseEntity<?> handleForbidden()
	{
		return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
	}
	
	
}
