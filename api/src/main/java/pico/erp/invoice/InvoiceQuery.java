package pico.erp.invoice;

import javax.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface InvoiceQuery {

  Page<InvoiceView> retrieve(@NotNull InvoiceView.Filter filter,
    @NotNull Pageable pageable);

}
