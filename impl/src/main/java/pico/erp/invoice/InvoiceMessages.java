package pico.erp.invoice;

import java.time.LocalDateTime;
import java.util.Collection;
import javax.validation.Valid;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;
import lombok.Value;
import pico.erp.company.CompanyId;
import pico.erp.shared.TypeDefinitions;
import pico.erp.shared.data.Address;
import pico.erp.shared.event.Event;
import pico.erp.user.UserId;

public interface InvoiceMessages {

  interface Create {

    @Data
    class Request {

      @Valid
      @NotNull
      InvoiceId id;

      @Future
      @NotNull
      LocalDateTime dueDate;

      @NotNull
      CompanyId senderId;

      @NotNull
      CompanyId receiverId;

      @NotNull
      Address receiveAddress;

      @Size(max = TypeDefinitions.REMARK_LENGTH)
      String remark;

      @NotNull
      InvoiceCodeGenerator codeGenerator;

    }

    @Value
    class Response {

      Collection<Event> events;

    }
  }


  interface Update {

    @Data
    class Request {

      @Future
      @NotNull
      LocalDateTime dueDate;

      @NotNull
      CompanyId senderId;

      @NotNull
      CompanyId receiverId;

      @NotNull
      Address receiveAddress;

      @Size(max = TypeDefinitions.REMARK_LENGTH)
      String remark;

    }

    @Value
    class Response {

      Collection<Event> events;

    }
  }

  interface Receive {

    @Data
    class Request {

      UserId confirmerId;

    }

    @Value
    class Response {

      Collection<Event> events;

    }

  }

  interface Cancel {

    @Data
    class Request {

    }

    @Value
    class Response {

      Collection<Event> events;

    }

  }


}
