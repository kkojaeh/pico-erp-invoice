package pico.erp.invoice.item.lot;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pico.erp.invoice.item.InvoiceItemId;
import pico.erp.item.lot.ItemLotCode;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class InvoiceItemLotData {

  InvoiceItemLotId id;

  InvoiceItemId invoiceItemId;

  ItemLotCode itemLotCode;

  BigDecimal quantity;

}
