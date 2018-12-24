package pico.erp.invoice;

import pico.erp.shared.data.LocalizedNameable;

public enum InvoiceStatusKind implements LocalizedNameable {

  /**
   * 대기
   */
  WAITING,

  /**
   * 수령완료
   */
  RECEIVED,

  /**
   * 취소됨
   */
  CANCELED;

  public boolean isCancelable() {
    return this == WAITING;
  }

  public boolean isReceivable() {
    return this == WAITING;
  }

  public boolean isUpdatable() {
    return this == WAITING;
  }


}
