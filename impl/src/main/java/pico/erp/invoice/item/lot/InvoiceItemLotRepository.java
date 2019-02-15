package pico.erp.invoice.item.lot;

import java.util.Optional;
import java.util.stream.Stream;
import javax.validation.constraints.NotNull;
import org.springframework.stereotype.Repository;
import pico.erp.invoice.item.InvoiceItemId;

@Repository
public interface InvoiceItemLotRepository {

  InvoiceItemLot create(@NotNull InvoiceItemLot item);

  void deleteBy(@NotNull InvoiceItemLotId id);

  boolean exists(@NotNull InvoiceItemLotId id);

  Stream<InvoiceItemLot> findAllBy(@NotNull InvoiceItemId invoiceItemId);

  Optional<InvoiceItemLot> findBy(@NotNull InvoiceItemLotId id);

  void update(@NotNull InvoiceItemLot item);

}
