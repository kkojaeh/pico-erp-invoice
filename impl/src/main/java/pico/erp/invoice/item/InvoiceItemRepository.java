package pico.erp.invoice.item;

import java.util.Optional;
import java.util.stream.Stream;
import javax.validation.constraints.NotNull;
import org.springframework.stereotype.Repository;
import pico.erp.invoice.InvoiceId;

@Repository
public interface InvoiceItemRepository {

  InvoiceItem create(@NotNull InvoiceItem item);

  void deleteBy(@NotNull InvoiceItemId id);

  boolean exists(@NotNull InvoiceItemId id);

  Stream<InvoiceItem> findAllBy(@NotNull InvoiceId invoiceId);

  Optional<InvoiceItem> findBy(@NotNull InvoiceItemId id);

  void update(@NotNull InvoiceItem item);

}
