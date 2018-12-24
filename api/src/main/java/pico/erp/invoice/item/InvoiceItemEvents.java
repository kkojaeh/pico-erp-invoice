package pico.erp.invoice.item;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pico.erp.shared.event.Event;

public interface InvoiceItemEvents {

  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  class CreatedEvent implements Event {

    public final static String CHANNEL = "event.invoice-item.created";

    private InvoiceItemId purchaseRequestItemId;

    public String channel() {
      return CHANNEL;
    }

  }

  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  class UpdatedEvent implements Event {

    public final static String CHANNEL = "event.invoice-item.updated";

    private InvoiceItemId purchaseRequestItemId;

    public String channel() {
      return CHANNEL;
    }

  }

  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  class DeletedEvent implements Event {

    public final static String CHANNEL = "event.invoice-item.deleted";

    private InvoiceItemId purchaseRequestItemId;

    public String channel() {
      return CHANNEL;
    }

  }
}
