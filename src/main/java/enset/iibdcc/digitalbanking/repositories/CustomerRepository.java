package enset.iibdcc.digitalbanking.repositories;

import enset.iibdcc.digitalbanking.models.CustomerEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, UUID> { }
