package pico.erp.invoice.item.lot;

import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import pico.erp.invoice.item.InvoiceItemId;

public interface InvoiceItemLotService {

  InvoiceItemLotData create(
    @Valid @NotNull InvoiceItemLotRequests.CreateRequest request);

  void delete(@Valid @NotNull InvoiceItemLotRequests.DeleteRequest request);

  boolean exists(@Valid @NotNull InvoiceItemLotId id);

  InvoiceItemLotData get(@Valid @NotNull InvoiceItemLotId id);

  List<InvoiceItemLotData> getAll(InvoiceItemId invoiceItemId);

  void update(@Valid @NotNull InvoiceItemLotRequests.UpdateRequest request);


}
