package fr.alib.elec_boutique.utils;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import fr.alib.elec_boutique.entities.Card;
import fr.alib.elec_boutique.entities.Product;
import fr.alib.elec_boutique.entities.User;
import fr.alib.elec_boutique.exceptions.IdNotFoundException;
import fr.alib.elec_boutique.exceptions.LackingAuthorizationsException;
import fr.alib.elec_boutique.services.CardService;
import fr.alib.elec_boutique.services.CustomUserDetails;
import fr.alib.elec_boutique.services.ProductService;

public class ControllerUtils {

	public static Pair<User,Product> throwIfNotAdminOrProductOwner(ProductService productService, Long productId) throws 
	IdNotFoundException,
	IllegalArgumentException,
	LackingAuthorizationsException
	{
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null) throw new BadCredentialsException("User isn't authenticated.");
		CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
		User user = userDetails.getUser();
		Product product = productService.getProductById(productId);
		Boolean isProvider = user.getRoles().contains("ROLE_PROVIDER");
		Boolean isAdmin = user.getRoles().contains("ROLE_ADMIN");
		Boolean isOwner = product.getUser().getId().equals(user.getId());
		if ( !isAdmin && !( isProvider && isOwner ) ) 
			throw new LackingAuthorizationsException("User doesn't posess necessary authorizations.");
		return new Pair<User, Product>(user, product);
	}
	
	public static CustomUserDetails throwIfNotAuthenticated() throws BadCredentialsException
	{
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null) throw new BadCredentialsException("User isn't authenticated.");
		CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
		return userDetails;
	}
	
	public static Pair<CustomUserDetails, Card> throwIfNotAuthenticatedOrNotCardOwner(CardService cardService, Long cardId) throws 
		BadCredentialsException,
		LackingAuthorizationsException
	{
		CustomUserDetails userDetails = throwIfNotAuthenticated();
		Card card = cardService.getCardById(cardId);
		if (!card.getUser().getId().equals(userDetails.getUser().getId())) {
			throw new LackingAuthorizationsException("User isn't the card owner.");
		}
		return new Pair<CustomUserDetails, Card>(userDetails, card);
	}
	
}
