package fr.alib.elec_boutique.services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import fr.alib.elec_boutique.dtos.inbound.ProductInboundDTO;
import fr.alib.elec_boutique.entities.Product;
import fr.alib.elec_boutique.entities.User;
import fr.alib.elec_boutique.exceptions.IdNotFoundException;
import fr.alib.elec_boutique.repositories.ProductRepository;
import fr.alib.elec_boutique.entities.Comment;


@Service
@Transactional
public class ProductService {

	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private MediaService mediaService;
	
	/**
	 * Creates a product.
	 * @param dto The product DTO.
	 * @param userId The owner's user id.
	 * @param files The files uploaded with the product.
	 * @return The created product.
	 * @throws IllegalArgumentException
	 * @throws OptimisticLockingFailureException
	 */
	@Transactional(rollbackFor = Exception.class)
	public Product createProduct(ProductInboundDTO dto, UserService userService, User user, List<MultipartFile> files) throws 
		IllegalArgumentException, 
		OptimisticLockingFailureException
	{
		Product product = new Product(dto, new ArrayList<String>(), new ArrayList<Comment>());
		product.setUser(user);
		product.setMedias( this.mediaService.storeFiles(files) );
		product = this.productRepository.save(product);
		return product;
	}
	
	/**
	 * Gets a user's products by their id.
	 * @param userId The user's id.
	 * @return The products.
	 * @throws IdNotFoundException
	 * @throws IllegalArgumentException
	 */
	public List<Product> getProductsByUserId(Long userId) throws IdNotFoundException, IllegalArgumentException
	{
		List<Product> products = this.productRepository.findAllByUserId(userId);
		return products;
	}
	
	/**
	 * Gets a product by id.
	 * @param id The product id.
	 * @return The product.
	 * @throws IdNotFoundException
	 * @throws IllegalArgumentException
	 */
	public Product getProductById(Long id) throws IdNotFoundException, IllegalArgumentException
	{
		Optional<Product> result = this.productRepository.findById(id);
		if (result.isPresent()) return result.get(); else {
			throw new IdNotFoundException("Product with id '" + id + "' couldn't be found.");
		}
	}
	
	/**
	 * Deletes a product by id.
	 * @param id The product id.
	 * @throws IllegalArgumentException
	 */
	@Transactional(rollbackFor = Exception.class)
	public void deleteProductById(Long id) throws IllegalArgumentException
	{
		Optional<Product> result = this.productRepository.findById(id);
		if (result.isPresent()) {
			Product product = result.get();
			this.mediaService.deleteMedias(product.getMedias());
			this.productRepository.deleteById(id);
		}
	}
	
	/**
	 * Edits a product by id.
	 * @param id The product's id.
	 * @param dto The DTO to apply to the product.
	 * @return The edited product.
	 * @throws IdNotFoundException
	 * @throws IllegalArgumentException
	 * @throws OptimisticLockingFailureException
	 */
	@Transactional(rollbackFor = Exception.class)
	public Product editProductById(Long id, ProductInboundDTO dto) throws 
		IdNotFoundException, 
		IllegalArgumentException, 
		OptimisticLockingFailureException
	{
		Optional<Product> result = this.productRepository.findById(id);
		if (result.isPresent()) {
			Product product = result.get();
			product.applyDTO(dto);
			return this.productRepository.save(product);
		}else {
			throw new IdNotFoundException("Couldn't find product with id '" + id + "'.");
		}
	}
	
	/**
	 * Patches a product by its id.
	 * @param id The product id.
	 * @param dto The product DTO.
	 * @return The modified product entity.
	 * @throws IdNotFoundException
	 * @throws IllegalArgumentException
	 * @throws OptimisticLockingFailureException
	 */
	@Transactional(rollbackFor = Exception.class)
	public Product patchProductById(Long id, ProductInboundDTO dto) throws
		IdNotFoundException, 
		IllegalArgumentException, 
		OptimisticLockingFailureException
	{
		Optional<Product> result = this.productRepository.findById(id);
		if (result.isPresent()) {
			Product product = result.get();
			product.applyPatchDTO(dto);
			return this.productRepository.save(product);
		}else {
			throw new IdNotFoundException("Couldn't find product with id '" + id + "'.");
		}
	}
	
	/**
	 * Adds a list of medias to a product.
	 * @param id The product id.
	 * @param files The media files.
	 * @return The modified product entity.
	 * @throws IdNotFoundException
	 * @throws IllegalArgumentException
	 * @throws OptimisticLockingFailureException
	 */
	@Transactional(rollbackFor = Exception.class)
	public Product addProductMedias(Long id, MultipartFile[] files) throws
		IdNotFoundException, 
		IllegalArgumentException, 
		OptimisticLockingFailureException	
	{
		Optional<Product> result = this.productRepository.findById(id);
		if (result.isPresent()) {
			Product product = result.get();
			List<String> medias = this.mediaService.storeFiles( Arrays.asList( files ));
			product.getMedias().addAll(medias);
			return this.productRepository.save(product);
		}else {
			throw new IdNotFoundException("Couldn't find product with id '" + id + "'.");
		}	
	}
	
	/**
	 * Deletes a product's media.
	 * @param id The product id.
	 * @param media The media's file name.
	 * @return The modified product entity.
	 * @throws IdNotFoundException
	 * @throws IllegalArgumentException
	 * @throws OptimisticLockingFailureException
	 */
	@Transactional(rollbackFor = Exception.class)
	public Product deleteProductMedia(Long id, String media) throws
		IdNotFoundException, 
		IllegalArgumentException, 
		OptimisticLockingFailureException	
	{
		Optional<Product> result = this.productRepository.findById(id);
		if (result.isPresent()) {
			Product product = result.get();
			this.mediaService.deleteMedia(media);
			product.getMedias().remove(media);
			return this.productRepository.save(product);
		}else {
			throw new IdNotFoundException("Couldn't find product with id '" + id + "'.");
		}	
	}
	
	/**
	 * Deletes a product's medias.
	 * @param id The product id.
	 * @return The modified product entity.
	 * @throws IdNotFoundException
	 * @throws IllegalArgumentException
	 * @throws OptimisticLockingFailureException
	 */
	@Transactional(rollbackFor = Exception.class)
	public Product deleteProductMedias(Long id) throws
		IdNotFoundException, 
		IllegalArgumentException, 
		OptimisticLockingFailureException	
	{
		Optional<Product> result = this.productRepository.findById(id);
		if (result.isPresent()) {
			Product product = result.get();
			this.mediaService.deleteMedias(product.getMedias());
			product.getMedias().clear();
			return this.productRepository.save(product);
		}else {
			throw new IdNotFoundException("Couldn't find product with id '" + id + "'.");
		}	
	}
	
	/**
	 * Saves a product.
	 * @param product The product entity.
	 * @return The saved product entity.
	 * @throws IllegalArgumentException
	 * @throws OptimisticLockingFailureException
	 */
	@Transactional(rollbackFor = Exception.class)
	public Product saveProduct(Product product) throws
		IllegalArgumentException, 
		OptimisticLockingFailureException
	{
		return this.productRepository.save(product);
	}
	
	/**
	 * Updates a product's average note.
	 * @param product The product.
	 * @return The updated product entity.
	 */
	public Product updateProductAverageNote(Product product)
	{
		product.setAverageNote( new BigDecimal( this.productRepository.findAverageNoteById(product.getId()) ) );
		return this.productRepository.save(product);
	}
	
	private final List<String> filterOptions = Arrays.asList( new String[] {
			"page", "query", "userId", "categories", "miprice", "mxprice", "mieval", "mxeval", "sortby", "sortorder"
	} );
	
	private final List<String> sortingOptions = Arrays.asList( new String[] {
			"name", "price", "average_note"
	} );

	
	/**
	 * Searches for products.
	 * @param params Query parameters.
	 * @return Results.
	 * @throws IllegalArgumentException
	 */
	public Page<Product> searchProducts(Map<String, String> params) throws IllegalArgumentException
	{
		for (String key : params.keySet()) {
			if (!filterOptions.contains(key)) {
				throw new IllegalArgumentException();
			}
		}
		
		String sortOption = params.get("sortby");
		if (sortOption != null && !sortingOptions.contains(sortOption)) {
			throw new IllegalArgumentException();
		}
		
		Integer page = params.containsKey("page") ? Integer.valueOf( params.get("page") ) : 0;
		Integer order = params.get("sortorder") == null ? 1 : -1;

		Pageable pageable = PageRequest.of(
				page, 
				10, 
				params.get("sortby") == null ? 
						Sort.unsorted() : 
						order == 1 ? Sort.by(params.get("sortby")).ascending() : Sort.by(params.get("sortby")).descending()
				);
		
		Page<Product> products = this.productRepository.search(
				params.get("query"),
				params.get("userId"),
				params.get("categories"),
				params.get("miprice") != null ? Float.valueOf(params.get("miprice")) : null,
				params.get("mxprice") != null ? Float.valueOf(params.get("mxprice")) : null,
				params.get("mieval") != null ? Float.valueOf(params.get("mieval")) : null,
				params.get("mxeval") != null ? Float.valueOf(params.get("mxeval")) : null,
				pageable
				);
		
		return products;
	}
}
