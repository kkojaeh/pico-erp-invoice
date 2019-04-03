package pico.erp.invoice;

import kkojaeh.spring.boot.component.ComponentBean;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import pico.erp.invoice.InvoiceRequests.CancelRequest;
import pico.erp.invoice.InvoiceRequests.ReceiveRequest;
import pico.erp.shared.event.EventPublisher;

@SuppressWarnings("Duplicates")
@Service
@ComponentBean
@Transactional
@Validated
public class InvoiceServiceLogic implements InvoiceService {

  @Autowired
  private InvoiceRepository invoiceRepository;

  @Autowired
  private EventPublisher eventPublisher;

  @Autowired
  private InvoiceMapper mapper;

  @Override
  public void cancel(CancelRequest request) {
    val invoice = invoiceRepository.findBy(request.getId())
      .orElseThrow(InvoiceExceptions.NotFoundException::new);
    val response = invoice.apply(mapper.map(request));
    invoiceRepository.update(invoice);
    eventPublisher.publishEvents(response.getEvents());
  }

  @Override
  public InvoiceData create(InvoiceRequests.CreateRequest request) {
    val invoice = new Invoice();
    val response = invoice.apply(mapper.map(request));
    if (invoiceRepository.exists(invoice.getId())) {
      throw new InvoiceExceptions.AlreadyExistsException();
    }
    val created = invoiceRepository.create(invoice);
    eventPublisher.publishEvents(response.getEvents());
    return mapper.map(created);
  }

  @Override
  public boolean exists(InvoiceId id) {
    return invoiceRepository.exists(id);
  }

  @Override
  public InvoiceData get(InvoiceId id) {
    return invoiceRepository.findBy(id)
      .map(mapper::map)
      .orElseThrow(InvoiceExceptions.NotFoundException::new);
  }

  @Override
  public void receive(ReceiveRequest request) {
    val invoice = invoiceRepository.findBy(request.getId())
      .orElseThrow(InvoiceExceptions.NotFoundException::new);
    val response = invoice.apply(mapper.map(request));
    invoiceRepository.update(invoice);
    eventPublisher.publishEvents(response.getEvents());
  }

  @Override
  public void update(InvoiceRequests.UpdateRequest request) {
    val invoice = invoiceRepository.findBy(request.getId())
      .orElseThrow(InvoiceExceptions.NotFoundException::new);
    val response = invoice.apply(mapper.map(request));
    invoiceRepository.update(invoice);
    eventPublisher.publishEvents(response.getEvents());
  }

}
