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
import pico.erp.invoice.Invoice;
import pico.erp.item.ItemId;
import pico.erp.item.spec.ItemSpecCode;
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
public class InvoiceItem implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  InvoiceItemId id;

  Invoice invoice;

  ItemId itemId;

  ItemSpecCode itemSpecCode;

  BigDecimal quantity;

  UnitKind unit;

  String remark;

  public InvoiceItem() {

  }

  public InvoiceItemMessages.Create.Response apply(
    InvoiceItemMessages.Create.Request request) {
    this.invoice = request.getInvoice();
    if (!isUpdatable()) {
      throw new InvoiceItemExceptions.CannotCreateException();
    }
    this.id = request.getId();

    this.itemId = request.getItemId();
    this.itemSpecCode = request.getItemSpecCode();
    this.quantity = request.getQuantity();
    this.unit = request.getUnit();
    this.remark = request.getRemark();

    return new InvoiceItemMessages.Create.Response(
      Arrays.asList(new InvoiceItemEvents.CreatedEvent(this.id))
    );
  }

  public InvoiceItemMessages.Update.Response apply(
    InvoiceItemMessages.Update.Request request) {
    if (!isUpdatable()) {
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
    if (!isUpdatable()) {
      throw new InvoiceItemExceptions.CannotDeleteException();
    }
    return new InvoiceItemMessages.Delete.Response(
      Arrays.asList(new InvoiceItemEvents.DeletedEvent(this.id))
    );
  }

  public boolean isUpdatable() {
    return this.invoice.isUpdatable();
  }


}
