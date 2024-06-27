package fr.alib.elec_boutique.controllers;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.encrypt.BytesEncryptor;
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

import fr.alib.elec_boutique.dtos.inbound.CardInboundDTO;
import fr.alib.elec_boutique.dtos.outbound.CardOutboundDTO;
import fr.alib.elec_boutique.dtos.outbound.InvoiceOutboundDTO;
import fr.alib.elec_boutique.entities.Card;
import fr.alib.elec_boutique.entities.Invoice;
import fr.alib.elec_boutique.exceptions.IdNotFoundException;
import fr.alib.elec_boutique.exceptions.LackingAuthorizationsException;
import fr.alib.elec_boutique.services.CardService;
import fr.alib.elec_boutique.services.CustomUserDetails;
import fr.alib.elec_boutique.services.InvoiceService;
import fr.alib.elec_boutique.services.ProductService;
import fr.alib.elec_boutique.utils.ControllerUtils;
import fr.alib.elec_boutique.utils.EncryptionUtils;
import fr.alib.elec_boutique.utils.Pair;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class PaymentController {

	@Autowired
	private CardService cardService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private InvoiceService invoiceService;
	
	@Autowired
	private BytesEncryptor bytesEncryptor;
	
	@Autowired
	private EncryptionUtils encryptionUtils;
	
	@PostMapping("/users/cards")
	public ResponseEntity<?> postCardAPI( @Valid @RequestBody CardInboundDTO dto ) throws BadCredentialsException
	{
		CustomUserDetails userDetails = ControllerUtils.throwIfNotAuthenticated();
		Card card = this.cardService.addCard(dto, userDetails);
		return ResponseEntity.ok(new CardOutboundDTO(card, bytesEncryptor, encryptionUtils));
	}
	
	@GetMapping("/users/cards")
	public ResponseEntity<?> getCardAPI( ) throws BadCredentialsException
	{
		CustomUserDetails userDetails = ControllerUtils.throwIfNotAuthenticated();
		List<Card> cards = this.cardService.getUserCardsById(userDetails.getUser().getId());
		return ResponseEntity.ok( cards.stream().map(c->{
			return new CardOutboundDTO(c, bytesEncryptor, encryptionUtils);
		}).collect(Collectors.toList()) );
	}
	
	@DeleteMapping("/users/cards/{id}")
	public ResponseEntity<?> deleteCardAPI( @PathVariable("id") Long id )
	{
		CustomUserDetails userDetails = ControllerUtils.throwIfNotAuthenticated();
		Card card = this.cardService.getCardById(id);
		if (!card.getUser().getId().equals(userDetails.getUser().getId())) {
			throw new LackingAuthorizationsException("User isn't the card owner.");
		}
		this.cardService.deleteCardById(id);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value = "/users/cards/{id}", method = {RequestMethod.PATCH, RequestMethod.PUT})
	public ResponseEntity<?> editCardAPI( @PathVariable("id") Long id, @RequestBody CardInboundDTO dto )
	{
		ControllerUtils.throwIfNotAuthenticatedOrNotCardOwner(cardService, id);
		Card card = this.cardService.patchCardById(id, dto);
		return ResponseEntity.ok(new CardOutboundDTO(card, bytesEncryptor, encryptionUtils));
	}
	
	@PostMapping("/products/{id}/pay/{cardId}")
	public ResponseEntity<?> payAPI( @PathVariable("id") Long id, @PathVariable("cardId") Long cardId )
	{
		CustomUserDetails userDetails = ControllerUtils.throwIfNotAuthenticated();
		Invoice invoice = this.invoiceService.registerInvoice(cardService, productService, userDetails.getUser(), id, cardId);
		return ResponseEntity.ok( new InvoiceOutboundDTO(invoice) );
	}
	
	@GetMapping("/users/invoices")
	public ResponseEntity<?> getInvoicesAPI( @RequestParam Map<String, String> params )
	{
		CustomUserDetails userDetails = ControllerUtils.throwIfNotAuthenticated();
		return ResponseEntity.ok( 
				this.invoiceService.getUserInvoices(userDetails.getUser(), params)
				.stream()
				.map(i->{ return new InvoiceOutboundDTO(i); })
				.collect(Collectors.toList()) 
				);
	}
	
	@GetMapping("/users/invoices/{id}")
	public ResponseEntity<?> getInvoiceByIdAPI( @PathVariable("id") Long id )
	{
		Pair<CustomUserDetails, Invoice> data = ControllerUtils.throwIfNotAuthenticatedOrNotInvoiceOwner(invoiceService, id);
		return ResponseEntity.ok( new InvoiceOutboundDTO( data.getData() ) );
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
