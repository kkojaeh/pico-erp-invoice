package pico.erp.invoice.item;

import java.math.BigDecimal;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pico.erp.invoice.InvoiceId;
import pico.erp.item.ItemId;
import pico.erp.item.lot.ItemLotId;
import pico.erp.shared.TypeDefinitions;
import pico.erp.shared.data.UnitKind;

public interface InvoiceItemRequests {

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  class CreateRequest {

    @Valid
    @NotNull
    InvoiceItemId id;

    @Valid
    InvoiceId invoiceId;

    @Valid
    @NotNull
    ItemId itemId;

    @Valid
    ItemLotId itemLotId;

    @NotNull
    @Min(0)
    BigDecimal quantity;

    @NotNull
    UnitKind unit;

    @Size(max = TypeDefinitions.REMARK_LENGTH)
    String remark;

  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  class UpdateRequest {

    @Valid
    @NotNull
    InvoiceItemId id;

    BigDecimal quantity;

    String remark;

  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  class DeleteRequest {

    @Valid
    @NotNull
    InvoiceItemId id;

  }
}
