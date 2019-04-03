package pico.erp.invoice;

import java.time.LocalDateTime;
import lombok.Data;
import pico.erp.company.CompanyId;
import pico.erp.shared.data.Address;
import pico.erp.user.UserId;

@Data
public class InvoiceData {

  InvoiceId id;

  InvoiceCode code;

  CompanyId senderId;

  CompanyId receiverId;

  Address receiveAddress;

  LocalDateTime dueDate;

  LocalDateTime receivedDate;

  LocalDateTime canceledDate;

  InvoiceStatusKind status;

  UserId confirmerId;

  String remark;

  boolean cancelable;

  boolean receivable;

  boolean updatable;

}
