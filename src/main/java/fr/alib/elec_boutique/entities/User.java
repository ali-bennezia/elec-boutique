package fr.alib.elec_boutique.entities;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.security.crypto.password.PasswordEncoder;

import fr.alib.elec_boutique.dtos.inbound.UserProfileInboundDTO;
import fr.alib.elec_boutique.dtos.inbound.UserRegisterInboundDTO;
import fr.alib.elec_boutique.entities.embedded.Address;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Table(name="USER")
@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false, unique = true)
	private String username;
	@Column(nullable = false, unique = true)
	private String email;
	@Column(nullable = false)
	private String firstName;
	@Column(nullable = false)
	private String lastName;
	@Column(nullable = true)
	private String profilePhotoMedia;
	@Column(nullable = false)
	private String password;
	@Column(nullable = false)
	private Boolean enabled;
	@Temporal(TemporalType.TIMESTAMP)
	private Timestamp createdAt;
	@Column(nullable = false)
	private String roles;
	@Embedded
	private fr.alib.elec_boutique.entities.embedded.Address address;
	@Column(nullable = true, unique = true)
	private String businessName;
	@AttributeOverrides({
		@AttributeOverride(name="street", column=@Column(name="businessStreet")),
		@AttributeOverride(name="city", column=@Column(name="businessCity")),
		@AttributeOverride(name="zipCode", column=@Column(name="businessZipCode")),
		@AttributeOverride(name="country", column=@Column(name="businessCountry")),
	})
	@Embedded
	private fr.alib.elec_boutique.entities.embedded.Address businessAddress;
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "user_card_id", referencedColumnName = "id")
	private List<Card> cards = new ArrayList<Card>();
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "user_product_id", referencedColumnName = "id")
	private List<Product> products = new ArrayList<Product>();
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "user_comment_id", referencedColumnName = "id")
	private List<Comment> comments = new ArrayList<Comment>();
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getProfilePhotoMedia() {
		return profilePhotoMedia;
	}
	public void setProfilePhotoMedia(String profilePhotoMedia) {
		this.profilePhotoMedia = profilePhotoMedia;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Boolean getEnabled() {
		return enabled;
	}
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
	public Timestamp getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}
	public String getRoles() {
		return roles;
	}
	public void setRoles(String roles) {
		this.roles = roles;
	}
	public fr.alib.elec_boutique.entities.embedded.Address getAddress() {
		return address;
	}
	public void setAddress(fr.alib.elec_boutique.entities.embedded.Address address) {
		this.address = address;
	}
	public String getBusinessName() {
		return businessName;
	}
	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}
	public fr.alib.elec_boutique.entities.embedded.Address getBusinessAddress() {
		return businessAddress;
	}
	public void setBusinessAddress(fr.alib.elec_boutique.entities.embedded.Address businessAddress) {
		this.businessAddress = businessAddress;
	}
	public List<Card> getCards() {
		return cards;
	}
	public void setCards(List<Card> cards) {
		this.cards = cards;
	}
	public List<Product> getProducts() {
		return products;
	}
	public void setProducts(List<Product> products) {
		this.products = products;
	}
	public List<Comment> getComments() {
		return comments;
	}
	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}
	@Override
	public int hashCode() {
		return Objects.hash(address, businessAddress, businessName, cards, comments, createdAt, email, enabled,
				firstName, id, lastName, password, products, profilePhotoMedia, roles, username);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return Objects.equals(address, other.address) && Objects.equals(businessAddress, other.businessAddress)
				&& Objects.equals(businessName, other.businessName) && Objects.equals(cards, other.cards)
				&& Objects.equals(comments, other.comments) && Objects.equals(createdAt, other.createdAt)
				&& Objects.equals(email, other.email) && Objects.equals(enabled, other.enabled)
				&& Objects.equals(firstName, other.firstName) && Objects.equals(id, other.id)
				&& Objects.equals(lastName, other.lastName) && Objects.equals(password, other.password)
				&& Objects.equals(products, other.products)
				&& Objects.equals(profilePhotoMedia, other.profilePhotoMedia) && Objects.equals(roles, other.roles)
				&& Objects.equals(username, other.username);
	}
	public void applyDTO(UserRegisterInboundDTO dto, PasswordEncoder pwdEncoder)
	{
		this.setUsername(dto.getUsername());
		this.setEmail(dto.getEmail());
		this.setFirstName(dto.getFirstName());
		this.setLastName(dto.getLastName());
		this.setPassword( pwdEncoder.encode( dto.getPassword() ));
		this.setAddress( new Address( dto.getAddress() ) );
		this.setBusinessName(dto.getBusinessName());
		this.setBusinessAddress( new Address(dto.getBusinessAddress()) );
	}
	public void applyProfilePatchDTO(UserProfileInboundDTO dto, PasswordEncoder pwdEncoder)
	{
		if (dto.getEmail() != null) this.setEmail(dto.getEmail());
		if (dto.getFirstName() != null) this.setFirstName(dto.getFirstName());
		if (dto.getLastName() != null) this.setLastName(dto.getLastName());
		if (dto.getPassword() != null) this.setPassword(pwdEncoder.encode(dto.getPassword()));
		if (dto.getAddress() != null) this.setAddress(new Address( dto.getAddress() ));
		if (dto.getBusinessName() != null) this.setBusinessName(dto.getBusinessName());
		if (dto.getBusinessAddress() != null) this.setBusinessAddress(new Address( dto.getBusinessAddress() ));
	}
	public User(Long id, String username, String email, String firstName, String lastName, String profilePhotoMedia,
			String password, Boolean enabled, Timestamp createdAt, String roles, Address address, String businessName,
			Address businessAddress, List<Card> cards, List<Product> products, List<Comment> comments) {
		super();
		this.id = id;
		this.username = username;
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.profilePhotoMedia = profilePhotoMedia;
		this.password = password;
		this.enabled = enabled;
		this.createdAt = createdAt;
		this.roles = roles;
		this.address = address;
		this.businessName = businessName;
		this.businessAddress = businessAddress;
		this.cards = cards;
		this.products = products;
		this.comments = comments;
	}
	public User(UserRegisterInboundDTO dto, PasswordEncoder pwdEncoder, String roles, String profilePhotoMedia, Boolean enabled) {
		super();
		this.applyDTO(dto, pwdEncoder);
		this.setRoles(roles);
		this.setProfilePhotoMedia(profilePhotoMedia);
		this.setEnabled(enabled);
		this.setCreatedAt(Timestamp.from(Instant.now()));
	}
	public User() {
		super();
	}
	
	
}
