package fr.alib.elec_boutique.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.alib.elec_boutique.entities.Invoice;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long>{

}
