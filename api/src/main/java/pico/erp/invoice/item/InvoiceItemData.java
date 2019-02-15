package pico.erp.invoice.item;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pico.erp.invoice.InvoiceId;
import pico.erp.item.ItemId;
import pico.erp.item.spec.ItemSpecCode;
import pico.erp.shared.data.UnitKind;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class InvoiceItemData {

  InvoiceItemId id;

  InvoiceId invoiceId;

  ItemId itemId;

  ItemSpecCode itemSpecCode;

  BigDecimal quantity;

  String remark;

  UnitKind unit;

}
