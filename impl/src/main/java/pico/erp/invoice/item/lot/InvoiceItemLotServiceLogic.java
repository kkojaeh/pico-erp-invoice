package pico.erp.invoice.item.lot;

import java.util.List;
import java.util.stream.Collectors;
import kkojaeh.spring.boot.component.Give;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import pico.erp.invoice.item.InvoiceItemId;
import pico.erp.invoice.item.lot.InvoiceItemLotRequests.DeleteRequest;
import pico.erp.shared.event.EventPublisher;

@SuppressWarnings("Duplicates")
@Service
@Give
@Transactional
@Validated
public class InvoiceItemLotServiceLogic implements InvoiceItemLotService {

  @Autowired
  private InvoiceItemLotRepository invoiceItemLotRepository;

  @Autowired
  private EventPublisher eventPublisher;

  @Autowired
  private InvoiceItemLotMapper mapper;

  @Override
  public InvoiceItemLotData create(InvoiceItemLotRequests.CreateRequest request) {
    val item = new InvoiceItemLot();
    val response = item.apply(mapper.map(request));
    if (invoiceItemLotRepository.exists(item.getId())) {
      throw new InvoiceItemLotExceptions.AlreadyExistsException();
    }
    val created = invoiceItemLotRepository.create(item);
    eventPublisher.publishEvents(response.getEvents());
    return mapper.map(created);
  }

  @Override
  public void delete(DeleteRequest request) {
    val item = invoiceItemLotRepository.findBy(request.getId())
      .orElseThrow(InvoiceItemLotExceptions.NotFoundException::new);
    val response = item.apply(mapper.map(request));
    invoiceItemLotRepository.deleteBy(item.getId());
    eventPublisher.publishEvents(response.getEvents());
  }


  @Override
  public boolean exists(InvoiceItemLotId id) {
    return invoiceItemLotRepository.exists(id);
  }


  @Override
  public InvoiceItemLotData get(InvoiceItemLotId id) {
    return invoiceItemLotRepository.findBy(id)
      .map(mapper::map)
      .orElseThrow(InvoiceItemLotExceptions.NotFoundException::new);
  }

  @Override
  public List<InvoiceItemLotData> getAll(InvoiceItemId invoiceItemId) {
    return invoiceItemLotRepository.findAllBy(invoiceItemId)
      .map(mapper::map)
      .collect(Collectors.toList());
  }

  @Override
  public void update(InvoiceItemLotRequests.UpdateRequest request) {
    val item = invoiceItemLotRepository.findBy(request.getId())
      .orElseThrow(InvoiceItemLotExceptions.NotFoundException::new);
    val response = item.apply(mapper.map(request));
    invoiceItemLotRepository.update(item);
    eventPublisher.publishEvents(response.getEvents());
  }

}
