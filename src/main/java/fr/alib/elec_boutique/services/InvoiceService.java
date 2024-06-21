package fr.alib.elec_boutique.services;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.alib.elec_boutique.entities.Card;
import fr.alib.elec_boutique.entities.Invoice;
import fr.alib.elec_boutique.entities.Product;
import fr.alib.elec_boutique.entities.User;
import fr.alib.elec_boutique.exceptions.IdNotFoundException;
import fr.alib.elec_boutique.repositories.InvoiceRepository;

@Service
@Transactional
public class InvoiceService {

	@Autowired
	private InvoiceRepository invoiceRepository;
	
	/**
	 * Registers an invoice.
	 * @param cardService The card service.
	 * @param productService The product service.
	 * @param user The user tied to the invoice.
	 * @param productId The id of the product tied to the invoice.
	 * @param cardId The id of the card tied to the invoice.
	 * @return The invoice entity.
	 */
	@Transactional(rollbackFor = Exception.class)
	public Invoice registerInvoice(CardService cardService, ProductService productService, User user, Long productId, Long cardId) throws
		IllegalArgumentException,
		OptimisticLockingFailureException
	{
		Product product = productService.getProductById(productId);
		Card card = cardService.getCardById(cardId);
		
		Invoice invoice = new Invoice();
		invoice.setPaymentData(card.getPaymentData());
		invoice.setProduct(product);
		invoice.setUser(user);
		invoice.setPrice(product.getPrice());
		invoice = this.invoiceRepository.save(invoice);
		return invoice;
	}
	
	/**
	 * Gets user invoices.
	 * @param user The user.
	 * @param params The query parameters.
	 * @return The page.
	 */
	public Page<Invoice> getUserInvoices(User user, Map<String, String> params) throws
		IllegalArgumentException
	{
		Integer page = params.containsKey("page") ? Integer.valueOf( params.get("page") ) : 0;
		Pageable pageable = PageRequest.of(page, 10);
		Page<Invoice> invoices = this.invoiceRepository.findAllByUserId(user.getId(), pageable);
		return invoices;
	}
	
	/**
	 * Gets an invoice by its id.
	 * @param id The invoice id.
	 * @return The invoice entity.
	 */
	public Invoice getInvoiceById(Long id) throws
		IllegalArgumentException
	{
		Optional<Invoice> result = this.invoiceRepository.findById(id);
		if (result.isEmpty()) throw new IdNotFoundException("Couldn't find invoice with id '" + id + "'.");
		return result.get();
	}
}
