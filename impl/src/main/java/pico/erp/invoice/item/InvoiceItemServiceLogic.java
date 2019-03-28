package pico.erp.invoice.item;

import java.util.List;
import java.util.stream.Collectors;
import kkojaeh.spring.boot.component.Give;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import pico.erp.invoice.InvoiceId;
import pico.erp.invoice.item.InvoiceItemRequests.DeleteRequest;
import pico.erp.shared.event.EventPublisher;

@SuppressWarnings("Duplicates")
@Service
@Give
@Transactional
@Validated
public class InvoiceItemServiceLogic implements InvoiceItemService {

  @Autowired
  private InvoiceItemRepository invoiceItemRepository;

  @Autowired
  private EventPublisher eventPublisher;

  @Autowired
  private InvoiceItemMapper mapper;

  @Override
  public InvoiceItemData create(InvoiceItemRequests.CreateRequest request) {
    val item = new InvoiceItem();
    val response = item.apply(mapper.map(request));
    if (invoiceItemRepository.exists(item.getId())) {
      throw new InvoiceItemExceptions.AlreadyExistsException();
    }
    val created = invoiceItemRepository.create(item);
    eventPublisher.publishEvents(response.getEvents());
    return mapper.map(created);
  }

  @Override
  public void delete(DeleteRequest request) {
    val item = invoiceItemRepository.findBy(request.getId())
      .orElseThrow(InvoiceItemExceptions.NotFoundException::new);
    val response = item.apply(mapper.map(request));
    invoiceItemRepository.deleteBy(item.getId());
    eventPublisher.publishEvents(response.getEvents());
  }


  @Override
  public boolean exists(InvoiceItemId id) {
    return invoiceItemRepository.exists(id);
  }


  @Override
  public InvoiceItemData get(InvoiceItemId id) {
    return invoiceItemRepository.findBy(id)
      .map(mapper::map)
      .orElseThrow(InvoiceItemExceptions.NotFoundException::new);
  }

  @Override
  public List<InvoiceItemData> getAll(InvoiceId invoiceId) {
    return invoiceItemRepository.findAllBy(invoiceId)
      .map(mapper::map)
      .collect(Collectors.toList());
  }

  @Override
  public void update(InvoiceItemRequests.UpdateRequest request) {
    val item = invoiceItemRepository.findBy(request.getId())
      .orElseThrow(InvoiceItemExceptions.NotFoundException::new);
    val response = item.apply(mapper.map(request));
    invoiceItemRepository.update(item);
    eventPublisher.publishEvents(response.getEvents());
  }

}
