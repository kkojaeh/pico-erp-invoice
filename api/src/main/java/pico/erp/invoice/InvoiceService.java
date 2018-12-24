package pico.erp.invoice;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public interface InvoiceService {

  void cancel(@Valid @NotNull InvoiceRequests.CancelRequest request);

  InvoiceData create(@Valid @NotNull InvoiceRequests.CreateRequest request);

  boolean exists(@Valid @NotNull InvoiceId id);

  InvoiceData get(@Valid @NotNull InvoiceId id);

  void receive(@Valid @NotNull InvoiceRequests.ReceiveRequest request);

  void update(@Valid @NotNull InvoiceRequests.UpdateRequest request);

}
