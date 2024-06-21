package fr.alib.elec_boutique.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.alib.elec_boutique.entities.Card;
import fr.alib.elec_boutique.entities.Invoice;
import fr.alib.elec_boutique.entities.Product;
import fr.alib.elec_boutique.entities.User;
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
	public Invoice registerInvoice(CardService cardService, ProductService productService, User user, Long productId, Long cardId)
	{
		Product product = productService.getProductById(productId);
		Card card = cardService.getCardById(cardId);
		
		Invoice invoice = new Invoice();
		invoice.setPaymentData(card.getPaymentData());
		invoice.setProduct(product);
		invoice.setUser(user);
		invoice = this.invoiceRepository.save(invoice);
		return invoice;
	}
}
