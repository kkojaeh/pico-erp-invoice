package pico.erp.invoice;

import java.time.OffsetDateTime;
import lombok.Data;
import pico.erp.company.CompanyId;
import pico.erp.shared.data.Address;
import pico.erp.shared.data.Auditor;

@Data
public class InvoiceData {

  InvoiceId id;

  InvoiceCode code;

  CompanyId supplierId;

  CompanyId receiverId;

  Address receiveAddress;

  OffsetDateTime dueDate;

  OffsetDateTime receivedDate;

  OffsetDateTime canceledDate;

  InvoiceStatusKind status;

  Auditor confirmedBy;

  String remark;

}
