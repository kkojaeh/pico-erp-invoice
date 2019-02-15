package pico.erp.invoice.item.lot;

import java.math.BigDecimal;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pico.erp.invoice.item.InvoiceItemId;
import pico.erp.item.lot.ItemLotCode;

public interface InvoiceItemLotRequests {

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  class CreateRequest {

    @Valid
    @NotNull
    InvoiceItemLotId id;

    @Valid
    @NotNull
    InvoiceItemId invoiceItemId;

    @Valid
    @NotNull
    ItemLotCode itemLotCode;

    @NotNull
    @Min(0)
    BigDecimal quantity;

  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  class UpdateRequest {

    @Valid
    @NotNull
    InvoiceItemLotId id;

    @Valid
    @NotNull
    ItemLotCode itemLotCode;

    BigDecimal quantity;

  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  class DeleteRequest {

    @Valid
    @NotNull
    InvoiceItemLotId id;

  }
}
