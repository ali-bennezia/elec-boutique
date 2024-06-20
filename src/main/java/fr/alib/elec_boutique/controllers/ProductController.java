package fr.alib.elec_boutique.controllers;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import fr.alib.elec_boutique.dtos.inbound.ProductInboundDTO;
import fr.alib.elec_boutique.dtos.outbound.ProductOutboundDTO;
import fr.alib.elec_boutique.entities.Product;
import fr.alib.elec_boutique.entities.User;
import fr.alib.elec_boutique.exceptions.IdNotFoundException;
import fr.alib.elec_boutique.exceptions.LackingAuthorizationsException;
import fr.alib.elec_boutique.services.CustomUserDetails;
import fr.alib.elec_boutique.services.ProductService;
import fr.alib.elec_boutique.services.UserService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/products")
public class ProductController {

	private class Pair<KeyType, DataType>
	{
		private KeyType key;
		private DataType data;
		public KeyType getKey() {
			return key;
		}
		public void setKey(KeyType key) {
			this.key = key;
		}
		public DataType getData() {
			return data;
		}
		public void setData(DataType data) {
			this.data = data;
		}
		
		public Pair(KeyType key, DataType data) {
			super();
			this.key = key;
			this.data = data;
		}
	}
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private UserService userService;

	private Pair<User,Product> throwIfNotAdminOrOwner(Long productId) throws 
		IdNotFoundException,
		IllegalArgumentException,
		LackingAuthorizationsException
	{
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null) throw new BadCredentialsException("User isn't authenticated.");
		CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
		User user = userDetails.getUser();
		Product product = this.productService.getProductById(productId);
		Boolean isProvider = user.getRoles().contains("ROLE_PROVIDER");
		Boolean isAdmin = user.getRoles().contains("ROLE_ADMIN");
		Boolean isOwner = product.getUser().getId().equals(user.getId());
		if ( !isAdmin && !( isProvider && isOwner ) ) 
			throw new LackingAuthorizationsException("User doesn't posess necessary authorizations.");
		return new Pair<User, Product>(user, product);
	}
	
	@PostMapping("")
	public ResponseEntity<?> postProduct(
			@Valid @RequestBody ProductInboundDTO dto,
			@RequestParam(name = "media", required = false) MultipartFile[] files
			) throws 
		BadCredentialsException,
		LackingAuthorizationsException,
		IllegalArgumentException,
		OptimisticLockingFailureException
	{
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null) throw new BadCredentialsException("User isn't authenticated.");
		CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
		User user = userDetails.getUser();
		if (!user.getRoles().contains("ROLE_PROVIDER") && !user.getRoles().contains("ROLE_ADMIN")) 
			throw new LackingAuthorizationsException("User doesn't posess necessary authorizations.");

		return ResponseEntity.ok( new ProductOutboundDTO( this.productService.createProduct(dto, userService, user, Arrays.asList(files)) ) );
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getProduct(@PathVariable("id") Long id) throws
		IdNotFoundException,
		IllegalArgumentException
	{
		return ResponseEntity.ok(new ProductOutboundDTO(this.productService.getProductById(id)));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteProduct(@PathVariable("id") Long id) throws 
		BadCredentialsException,
		LackingAuthorizationsException,
		IllegalArgumentException,
		OptimisticLockingFailureException
	{
		Pair<User, Product> data = throwIfNotAdminOrOwner(id);
		Product product = data.getData();
		
		this.productService.deleteProductById(product.getId());
		
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping("/{id}")
	@PatchMapping("/{id}")
	public ResponseEntity<?> editProduct(@PathVariable("id") Long id, ProductInboundDTO dto) throws 
		BadCredentialsException,
		LackingAuthorizationsException,
		IllegalArgumentException,
		OptimisticLockingFailureException
	{
		throwIfNotAdminOrOwner(id);		
		Product product = this.productService.patchProductById(id, dto);
		
		return ResponseEntity.ok( new ProductOutboundDTO(product) );
	}
	
	@GetMapping("/{id}/medias")
	public ResponseEntity<?> getProductMedias( @PathVariable("id") Long id )
	{
		Product product = this.productService.getProductById(id);
		return ResponseEntity.ok(product.getMedias());
	}
	
	@PostMapping("/{id}/medias")
	public ResponseEntity<?> postProductMedias( 
			@PathVariable("id") Long id,
			@RequestParam(name = "medias", required = true) MultipartFile[] medias
			)
	{
		throwIfNotAdminOrOwner(id);
		this.productService.addProductMedias(id, medias);
		return ResponseEntity.created(null).build();
	}
	
	@DeleteMapping("/{id}/medias")
	public ResponseEntity<?> deleteProductMedias( @PathVariable("id") Long id )
	{
		throwIfNotAdminOrOwner(id);
		this.productService.deleteProductMedias(id);
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping("/{id}/medias/{media}")
	public ResponseEntity<?> deleteProductMedia( @PathVariable("id") Long id, @PathVariable("media") String media )
	{
		throwIfNotAdminOrOwner(id);
		this.productService.deleteProductMedia(id, media);
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
		return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
	}
	
	@ExceptionHandler(IdNotFoundException.class)
	public ResponseEntity<?> handleNotFound()
	{
		return ResponseEntity.notFound().build();
	}
	
	@ExceptionHandler(LackingAuthorizationsException.class)
	public ResponseEntity<?> handleForbidden()
	{
		return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
	}
	
}
