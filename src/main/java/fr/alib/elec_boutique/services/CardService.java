package fr.alib.elec_boutique.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.security.crypto.encrypt.BytesEncryptor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.alib.elec_boutique.dtos.inbound.CardInboundDTO;
import fr.alib.elec_boutique.dtos.outbound.CardOutboundDTO;
import fr.alib.elec_boutique.entities.Card;
import fr.alib.elec_boutique.exceptions.IdNotFoundException;
import fr.alib.elec_boutique.repositories.CardRepository;
import fr.alib.elec_boutique.repositories.UserRepository;
import fr.alib.elec_boutique.utils.EncryptionUtils;

@Service
@Transactional
public class CardService {
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private BytesEncryptor bytesEncryptor;
	
	@Autowired
	private EncryptionUtils encryptionUtils;
	
	@Autowired
	private CardRepository cardRepository;
	
	/**
	 * Fetches a user's cards in the form of outbound DTOs by the user's id.
	 * @param id The user's id.
	 * @return The card DTO list.
	 * @throws IdNotFoundException If user with specified id couldn't be found.
	 */
	public List<Card> getUserCardsById(Long id) throws IdNotFoundException
	{
		CustomUserDetails user = (CustomUserDetails) userService.loadUserById(id);
		return user.getUser().getCards();
	}
	
	/**
	 * Adds a card.
	 * @param dto The card DTO.
	 * @param user The user to add it to.
	 * @return The added card entity.
	 * @throws IllegalArgumentException If argument is invalid.
	 * @throws OptimisticLockingFailureException
	 */
	@Transactional(rollbackFor = Exception.class)
	public Card addCard(CardInboundDTO dto, CustomUserDetails user) throws
		IllegalArgumentException,
		OptimisticLockingFailureException
	{
		Card card = new Card(dto, bytesEncryptor, encryptionUtils, user.getUser());
		user.getUser().getCards().add(card);
		this.userRepository.save(user.getUser());
		return card;
	}
	
	/**
	 * Edits a card by its id.
	 * @param id The card's id.
	 * @param dto The card DTO.
	 * @return The modified card entity.
	 * @throws IdNotFoundException If card with id wasn't  found.
	 * @throws IllegalArgumentException If argument is invalid.
	 * @throws OptimisticLockingFailureException
	 */
	@Transactional(rollbackFor = Exception.class)
	public Card editCardById(Long id, CardInboundDTO dto) throws 
		IdNotFoundException,
		IllegalArgumentException,
		OptimisticLockingFailureException
	{
		Optional<Card> result = this.cardRepository.findById(id);
		if (result.isEmpty()) throw new IdNotFoundException("Couldn't find card with id '" + id + "'.");
		Card card = result.get();
		card.applyDTO(dto, bytesEncryptor, encryptionUtils);
		return this.cardRepository.save(card);
	}
	
	/**
	 * Fetches a card by its id.
	 * @param id The card's id.
	 * @return The card entity.
	 * @throws IllegalArgumentException
	 */
	public Card getCardById(Long id) throws IllegalArgumentException
	{
		Optional<Card> result = this.cardRepository.findById(id);
		if (result.isEmpty()) throw new IdNotFoundException("Couldn't find card with id '" + id + "'.");
		return result.get();
	}
	
	/**
	 * Deletes a card by its id.
	 * @param id The card's id.
	 * @throws IllegalArgumentException If argument is invalid.
	 */
	@Transactional(rollbackFor = Exception.class)
	public void deleteCardById(Long id) throws IllegalArgumentException
	{
		this.cardRepository.deleteById(id);
	}
	
	/**
	 * Patches a card by its id.
	 * @param id The card id.
	 * @param dto The DTO.
	 * @return The modified card entity.
	 */
	@Transactional(rollbackFor = Exception.class)
	public Card patchCardById(Long id, CardInboundDTO dto)
	{
		Card card = this.getCardById(id);
		card.applyPatchDTO(dto, bytesEncryptor, encryptionUtils);
		card = this.cardRepository.save(card);
		return card;
	}
}
