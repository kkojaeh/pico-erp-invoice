package pico.erp.invoice;

import java.time.LocalDateTime;
import java.util.Optional;
import javax.validation.constraints.NotNull;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceRepository {

  long countCreatedBetween(LocalDateTime begin, LocalDateTime end);

  Invoice create(@NotNull Invoice orderAcceptance);

  void deleteBy(@NotNull InvoiceId id);

  boolean exists(@NotNull InvoiceId id);

  Optional<Invoice> findBy(@NotNull InvoiceId id);

  void update(@NotNull Invoice orderAcceptance);

}
