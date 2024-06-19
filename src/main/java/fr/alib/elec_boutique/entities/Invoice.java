package fr.alib.elec_boutique.entities;

import java.util.Objects;

import fr.alib.elec_boutique.entities.embedded.PaymentData;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Table
@Entity
public class Invoice {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Embedded
	private PaymentData paymentData;
	@ManyToOne(targetEntity = User.class)
	private User user;
	@ManyToOne(targetEntity = Product.class)
	private Product product;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public PaymentData getPaymentData() {
		return paymentData;
	}
	public void setPaymentData(PaymentData paymentData) {
		this.paymentData = paymentData;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}

	public Invoice(Long id, PaymentData paymentData, User user, Product product) {
		super();
		this.id = id;
		this.paymentData = paymentData;
		this.user = user;
		this.product = product;
	}
	public Invoice() {
		super();
	}
	@Override
	public int hashCode() {
		return Objects.hash(id, paymentData, product, user);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Invoice other = (Invoice) obj;
		return Objects.equals(id, other.id) && Objects.equals(paymentData, other.paymentData)
				&& Objects.equals(product, other.product) && Objects.equals(user, other.user);
	}	
}
