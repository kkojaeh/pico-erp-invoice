package pico.erp.invoice;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@SuppressWarnings("unused")
@Component
@Transactional
public class InvoiceEventListener {

  private static final String LISTENER_NAME = "listener.invoice-event-listener";

}
