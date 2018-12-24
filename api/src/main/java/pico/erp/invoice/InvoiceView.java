package pico.erp.invoice;

import java.time.OffsetDateTime;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pico.erp.company.CompanyId;
import pico.erp.item.ItemId;
import pico.erp.shared.data.Address;
import pico.erp.shared.data.Auditor;
import pico.erp.user.UserId;

@Data
public class InvoiceView {

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

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  public static class Filter {

    String code;

    CompanyId receiverId;

    CompanyId supplierId;

    UserId confirmerId;

    ItemId itemId;

    Set<InvoiceStatusKind> statuses;

    OffsetDateTime startDueDate;

    OffsetDateTime endDueDate;

  }

}
