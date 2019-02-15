package pico.erp.invoice.item;

import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import pico.erp.invoice.InvoiceId;

public interface InvoiceItemService {

  InvoiceItemData create(
    @Valid @NotNull InvoiceItemRequests.CreateRequest request);

  void delete(@Valid @NotNull InvoiceItemRequests.DeleteRequest request);

  boolean exists(@Valid @NotNull InvoiceItemId id);

  InvoiceItemData get(@Valid @NotNull InvoiceItemId id);

  List<InvoiceItemData> getAll(InvoiceId invoiceId);

  void update(@Valid @NotNull InvoiceItemRequests.UpdateRequest request);


}
