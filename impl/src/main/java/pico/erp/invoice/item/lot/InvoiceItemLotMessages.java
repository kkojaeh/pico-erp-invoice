package pico.erp.invoice.item.lot;

import java.math.BigDecimal;
import java.util.Collection;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;
import pico.erp.invoice.item.InvoiceItem;
import pico.erp.item.lot.ItemLotCode;
import pico.erp.shared.event.Event;

public interface InvoiceItemLotMessages {

  interface Create {

    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    class Request {

      @Valid
      @NotNull
      InvoiceItemLotId id;

      @NotNull
      InvoiceItem invoiceItem;

      @NotNull
      ItemLotCode itemLotCode;

      @NotNull
      @Min(0)
      BigDecimal quantity;

    }

    @Value
    class Response {

      Collection<Event> events;

    }
  }


  interface Update {

    @Data
    class Request {

      @NotNull
      ItemLotCode itemLotCode;

      @NotNull
      @Min(0)
      BigDecimal quantity;

    }

    @Value
    class Response {

      Collection<Event> events;

    }
  }

  interface Delete {

    @Data
    class Request {

    }

    @Value
    class Response {

      Collection<Event> events;

    }

  }

}
