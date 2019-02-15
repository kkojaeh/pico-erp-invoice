package pico.erp.invoice.item.lot;

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
import pico.erp.invoice.item.InvoiceItem;
import pico.erp.item.lot.ItemLotCode;

/**
 * 주문 접수
 */
@Getter
@ToString
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Builder(toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InvoiceItemLot implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  InvoiceItemLotId id;

  InvoiceItem invoiceItem;

  ItemLotCode itemLotCode;

  BigDecimal quantity;


  public InvoiceItemLot() {

  }

  public InvoiceItemLotMessages.Create.Response apply(
    InvoiceItemLotMessages.Create.Request request) {
    this.invoiceItem = request.getInvoiceItem();
    if (!isUpdatable()) {
      throw new InvoiceItemLotExceptions.CannotCreateException();
    }
    this.id = request.getId();
    this.itemLotCode = request.getItemLotCode();
    this.quantity = request.getQuantity();
    return new InvoiceItemLotMessages.Create.Response(
      Arrays.asList(new InvoiceItemLotEvents.CreatedEvent(this.id))
    );
  }

  public InvoiceItemLotMessages.Update.Response apply(
    InvoiceItemLotMessages.Update.Request request) {
    if (!isUpdatable()) {
      throw new InvoiceItemLotExceptions.CannotUpdateException();
    }
    this.itemLotCode = request.getItemLotCode();
    this.quantity = request.getQuantity();
    return new InvoiceItemLotMessages.Update.Response(
      Arrays.asList(new InvoiceItemLotEvents.UpdatedEvent(this.id))
    );
  }

  public InvoiceItemLotMessages.Delete.Response apply(
    InvoiceItemLotMessages.Delete.Request request) {
    if (!isUpdatable()) {
      throw new InvoiceItemLotExceptions.CannotDeleteException();
    }
    return new InvoiceItemLotMessages.Delete.Response(
      Arrays.asList(new InvoiceItemLotEvents.DeletedEvent(this.id))
    );
  }

  public boolean isUpdatable() {
    return this.invoiceItem.isUpdatable();
  }


}
