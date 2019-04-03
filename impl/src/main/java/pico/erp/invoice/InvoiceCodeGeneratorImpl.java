package pico.erp.invoice;

import java.time.LocalDateTime;
import java.time.LocalTime;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class InvoiceCodeGeneratorImpl implements InvoiceCodeGenerator {

  @Lazy
  @Autowired
  private InvoiceRepository invoiceRepository;

  @Override
  public InvoiceCode generate(Invoice invoice) {
    val now = LocalDateTime.now();
    val begin = now.with(LocalTime.MIN);
    val end = now.with(LocalTime.MAX);
    val count = invoiceRepository.countCreatedBetween(begin, end);
    val code = String
      .format("IV%03d%02d%02d-%04d", now.getYear() % 1000, now.getMonthValue(), now.getDayOfMonth(),
        count + 1).toUpperCase();
    return InvoiceCode.from(code);
  }

}
