package fr.alib.elec_boutique.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.data.domain.Page;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import fr.alib.elec_boutique.dtos.inbound.ProductInboundDTO;
import fr.alib.elec_boutique.dtos.outbound.ProductOutboundDTO;
import fr.alib.elec_boutique.dtos.outbound.ProductPageOutboundDTO;
import fr.alib.elec_boutique.entities.Product;
import fr.alib.elec_boutique.entities.User;
import fr.alib.elec_boutique.exceptions.IdNotFoundException;
import fr.alib.elec_boutique.exceptions.LackingAuthorizationsException;
import fr.alib.elec_boutique.services.CustomUserDetails;
import fr.alib.elec_boutique.services.ProductService;
import fr.alib.elec_boutique.services.UserService;
import fr.alib.elec_boutique.utils.ControllerUtils;
import fr.alib.elec_boutique.utils.Pair;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/products")
public class ProductController {

	@Autowired
	private ProductService productService;
	
	@Autowired
	private UserService userService;


	
	@GetMapping("/users/{id}")
	public ResponseEntity<?> getProductsByUserId(@PathVariable("id") Long userId) throws
		IdNotFoundException,
		IllegalArgumentException
	{
		List<ProductOutboundDTO> dtos = this.productService.getProductsByUserId(userId).stream().map(p -> {
			return new ProductOutboundDTO(p);
		}).collect(Collectors.toList());
		return ResponseEntity.ok(dtos);
	}
	
	@PostMapping("")
	public ResponseEntity<?> postProduct(
			@Valid @RequestBody ProductInboundDTO dto
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

		return ResponseEntity.ok( new ProductOutboundDTO( this.productService.createProduct(dto, userService, user, new ArrayList<MultipartFile>()) ) );
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
		Pair<User, Product> data = ControllerUtils.throwIfNotAdminOrProductOwner(productService, id);
		Product product = data.getData();
		
		this.productService.deleteProductById(product.getId());
		
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value = "/{id}", method = { RequestMethod.PATCH, RequestMethod.PUT })
	public ResponseEntity<?> editProduct(@PathVariable("id") Long id, @RequestBody ProductInboundDTO dto) throws 
		BadCredentialsException,
		LackingAuthorizationsException,
		IllegalArgumentException,
		OptimisticLockingFailureException
	{
		ControllerUtils.throwIfNotAdminOrProductOwner(productService, id);		
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
		ControllerUtils.throwIfNotAdminOrProductOwner(productService, id);
		this.productService.addProductMedias(id, medias);
		return ResponseEntity.created(null).build();
	}
	
	@DeleteMapping("/{id}/medias")
	public ResponseEntity<?> deleteProductMedias( @PathVariable("id") Long id )
	{
		ControllerUtils.throwIfNotAdminOrProductOwner(productService, id);
		this.productService.deleteProductMedias(id);
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping("/{id}/medias/{media}")
	public ResponseEntity<?> deleteProductMedia( @PathVariable("id") Long id, @PathVariable("media") String media )
	{
		ControllerUtils.throwIfNotAdminOrProductOwner(productService, id);
		this.productService.deleteProductMedia(id, media);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("")
	public ResponseEntity<?> searchProducts( @RequestParam Map<String, String> params )
	{
		Page<Product> results = this.productService.searchProducts(params);
		return ResponseEntity.ok(new ProductPageOutboundDTO( results ));
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
