package pico.erp.invoice;

import java.time.LocalDateTime;
import javax.validation.Valid;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pico.erp.company.CompanyId;
import pico.erp.shared.TypeDefinitions;
import pico.erp.shared.data.Address;
import pico.erp.user.UserId;

public interface InvoiceRequests {

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  class CreateRequest {

    @Valid
    @NotNull
    InvoiceId id;

    @Future
    @NotNull
    LocalDateTime dueDate;

    @Valid
    @NotNull
    CompanyId receiverId;

    @Valid
    @NotNull
    CompanyId senderId;

    @Valid
    @NotNull
    Address receiveAddress;

    @Size(max = TypeDefinitions.REMARK_LENGTH)
    String remark;
  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  class UpdateRequest {

    @Valid
    @NotNull
    InvoiceId id;

    @Future
    @NotNull
    LocalDateTime dueDate;

    @Valid
    @NotNull
    CompanyId receiverId;

    @Valid
    @NotNull
    CompanyId senderId;

    @Valid
    @NotNull
    Address receiveAddress;

    @Size(max = TypeDefinitions.REMARK_LENGTH)
    String remark;


  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  class ReceiveRequest {

    @Valid
    @NotNull
    InvoiceId id;

    @Valid
    @NotNull
    UserId confirmerId;

  }


  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  class CancelRequest {

    @Valid
    @NotNull
    InvoiceId id;

  }

}
