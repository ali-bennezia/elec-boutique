package fr.alib.elec_boutique.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import fr.alib.elec_boutique.dtos.inbound.ProductInboundDTO;
import fr.alib.elec_boutique.entities.Product;
import fr.alib.elec_boutique.exceptions.IdNotFoundException;
import fr.alib.elec_boutique.repositories.ProductRepository;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private MediaService mediaService;
	
	/**
	 * Creates a product.
	 * @param dto The product DTO.
	 * @return The created product.
	 * @throws IllegalArgumentException
	 * @throws OptimisticLockingFailureException
	 */
	public Product createProduct(ProductInboundDTO dto, List<String> medias) throws IllegalArgumentException, OptimisticLockingFailureException
	{
		Product product = new Product(dto, medias);
		product = this.productRepository.save(product);
		return product;
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
	
}
