package pico.erp.invoice;

import javax.persistence.Id;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import pico.erp.shared.ApplicationId;
import pico.erp.shared.data.Role;

public final class InvoiceApi {

  public final static ApplicationId ID = ApplicationId.from("invoice");

  @RequiredArgsConstructor
  public enum Roles implements Role {

    INVOICE_RECEIVER,
    INVOICE_MANAGER;

    @Id
    @Getter
    private final String id = name();

  }
}
