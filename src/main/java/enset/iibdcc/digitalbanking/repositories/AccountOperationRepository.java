package enset.iibdcc.digitalbanking.repositories;

import enset.iibdcc.digitalbanking.models.AccountOperationEntity;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountOperationRepository extends JpaRepository<AccountOperationEntity, UUID > {
    List<AccountOperationEntity> findByBankAccountId(UUID accountId);
    Page<AccountOperationEntity> findByBankAccountId(UUID accountId, Pageable pageable);
}
