package pico.erp.invoice.item;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Arrays;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import pico.erp.audit.annotation.Audit;
import pico.erp.invoice.Invoice;
import pico.erp.item.ItemData;
import pico.erp.item.lot.ItemLotData;
import pico.erp.shared.data.UnitKind;

/**
 * 주문 접수
 */
@Getter
@ToString
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Builder(toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Audit(alias = "invoice-item")
public class InvoiceItem implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  InvoiceItemId id;

  Invoice invoice;

  ItemData item;

  ItemLotData itemLot;

  BigDecimal quantity;

  String remark;

  UnitKind unit;

  public InvoiceItem() {

  }

  public InvoiceItemMessages.Create.Response apply(
    InvoiceItemMessages.Create.Request request) {
    if (!request.getInvoice().isUpdatable()) {
      throw new InvoiceItemExceptions.CannotCreateException();
    }
    this.id = request.getId();
    this.invoice = request.getInvoice();
    this.item = request.getItem();
    this.itemLot = request.getItemLot();
    this.quantity = request.getQuantity();
    this.unit = request.getUnit();
    this.remark = request.getRemark();

    return new InvoiceItemMessages.Create.Response(
      Arrays.asList(new InvoiceItemEvents.CreatedEvent(this.id))
    );
  }

  public InvoiceItemMessages.Update.Response apply(
    InvoiceItemMessages.Update.Request request) {
    if (!this.invoice.isUpdatable()) {
      throw new InvoiceItemExceptions.CannotUpdateException();
    }
    this.quantity = request.getQuantity();
    this.remark = request.getRemark();
    return new InvoiceItemMessages.Update.Response(
      Arrays.asList(new InvoiceItemEvents.UpdatedEvent(this.id))
    );
  }

  public InvoiceItemMessages.Delete.Response apply(
    InvoiceItemMessages.Delete.Request request) {
    if (!this.invoice.isUpdatable()) {
      throw new InvoiceItemExceptions.CannotDeleteException();
    }
    return new InvoiceItemMessages.Delete.Response(
      Arrays.asList(new InvoiceItemEvents.DeletedEvent(this.id))
    );
  }


}
