package pico.erp.invoice;

import java.time.LocalDateTime;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pico.erp.company.CompanyId;
import pico.erp.item.ItemId;
import pico.erp.shared.data.Address;
import pico.erp.user.UserId;

@Data
public class InvoiceView {

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

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  public static class Filter {

    String code;

    CompanyId receiverId;

    CompanyId senderId;

    UserId confirmerId;

    ItemId itemId;

    Set<InvoiceStatusKind> statuses;

    LocalDateTime startDueDate;

    LocalDateTime endDueDate;

  }

}
