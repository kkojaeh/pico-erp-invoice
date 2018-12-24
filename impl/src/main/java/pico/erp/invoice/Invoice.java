package pico.erp.invoice;

import java.io.Serializable;
import java.time.OffsetDateTime;
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
import pico.erp.company.CompanyData;
import pico.erp.shared.data.Address;
import pico.erp.shared.data.Auditor;

/**
 * 주문 접수
 */
@Getter
@ToString
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Builder(toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Audit(alias = "invoice")
public class Invoice implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  InvoiceId id;

  InvoiceCode code;

  CompanyData supplier;

  CompanyData receiver;

  Address receiveAddress;

  OffsetDateTime dueDate;

  OffsetDateTime receivedDate;

  OffsetDateTime canceledDate;

  InvoiceStatusKind status;

  Auditor confirmedBy;

  String remark;


  public Invoice() {

  }

  public InvoiceMessages.Create.Response apply(
    InvoiceMessages.Create.Request request) {
    this.id = request.getId();
    this.supplier = request.getSupplier();
    this.receiver = request.getReceiver();
    this.receiveAddress = request.getReceiveAddress();
    this.status = InvoiceStatusKind.WAITING;
    this.dueDate = request.getDueDate();
    this.remark = request.getRemark();
    this.code = request.getCodeGenerator().generate(this);
    return new InvoiceMessages.Create.Response(
      Arrays.asList(new InvoiceEvents.CreatedEvent(this.id))
    );
  }

  public InvoiceMessages.Update.Response apply(
    InvoiceMessages.Update.Request request) {
    if (!isUpdatable()) {
      throw new InvoiceExceptions.CannotUpdateException();
    }
    this.dueDate = request.getDueDate();
    this.supplier = request.getSupplier();
    this.receiver = request.getReceiver();
    this.receiveAddress = request.getReceiveAddress();
    this.remark = request.getRemark();
    return new InvoiceMessages.Update.Response(
      Arrays.asList(new InvoiceEvents.UpdatedEvent(this.id))
    );
  }


  public InvoiceMessages.Cancel.Response apply(
    InvoiceMessages.Cancel.Request request) {
    if (!isCancelable()) {
      throw new InvoiceExceptions.CannotCancelException();
    }
    this.status = InvoiceStatusKind.CANCELED;
    this.canceledDate = OffsetDateTime.now();
    return new InvoiceMessages.Cancel.Response(
      Arrays.asList(new InvoiceEvents.CanceledEvent(this.id))
    );
  }

  public InvoiceMessages.Receive.Response apply(
    InvoiceMessages.Receive.Request request) {
    if (!isReceivable()) {
      throw new InvoiceExceptions.CannotReceiveException();
    }
    this.confirmedBy = request.getConfirmedBy();
    this.status = InvoiceStatusKind.RECEIVED;
    this.receivedDate = OffsetDateTime.now();
    return new InvoiceMessages.Receive.Response(
      Arrays.asList(new InvoiceEvents.ReceivedEvent(this.id))
    );
  }


  public boolean isCancelable() {
    return status.isCancelable();
  }

  public boolean isReceivable() {
    return status.isReceivable();
  }

  public boolean isUpdatable() {
    return status.isUpdatable();
  }


}
