package pico.erp.invoice;

import javax.persistence.Id;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import pico.erp.shared.data.Role;

public final class InvoiceApi {

  @RequiredArgsConstructor
  public enum Roles implements Role {

    INVOICE_RECEIVER,

    INVOICE_MANAGER;

    @Id
    @Getter
    private final String id = name();

  }
}
