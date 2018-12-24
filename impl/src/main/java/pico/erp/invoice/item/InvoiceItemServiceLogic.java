package pico.erp.invoice.item;

import java.util.List;
import java.util.stream.Collectors;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import pico.erp.audit.AuditService;
import pico.erp.invoice.InvoiceId;
import pico.erp.invoice.item.InvoiceItemRequests.DeleteRequest;
import pico.erp.shared.Public;
import pico.erp.shared.event.EventPublisher;

@SuppressWarnings("Duplicates")
@Service
@Public
@Transactional
@Validated
public class InvoiceItemServiceLogic implements InvoiceItemService {

  @Autowired
  private InvoiceItemRepository planDetailRepository;

  @Autowired
  private EventPublisher eventPublisher;

  @Autowired
  private InvoiceItemMapper mapper;

  @Lazy
  @Autowired
  private AuditService auditService;


  @Override
  public InvoiceItemData create(InvoiceItemRequests.CreateRequest request) {
    val item = new InvoiceItem();
    val response = item.apply(mapper.map(request));
    if (planDetailRepository.exists(item.getId())) {
      throw new InvoiceItemExceptions.AlreadyExistsException();
    }
    val created = planDetailRepository.create(item);
    auditService.commit(created);
    eventPublisher.publishEvents(response.getEvents());
    return mapper.map(created);
  }

  @Override
  public void delete(DeleteRequest request) {
    val item = planDetailRepository.findBy(request.getId())
      .orElseThrow(InvoiceItemExceptions.NotFoundException::new);
    val response = item.apply(mapper.map(request));
    planDetailRepository.deleteBy(item.getId());
    auditService.commit(item);
    eventPublisher.publishEvents(response.getEvents());
  }


  @Override
  public boolean exists(InvoiceItemId id) {
    return planDetailRepository.exists(id);
  }


  @Override
  public InvoiceItemData get(InvoiceItemId id) {
    return planDetailRepository.findBy(id)
      .map(mapper::map)
      .orElseThrow(InvoiceItemExceptions.NotFoundException::new);
  }

  @Override
  public List<InvoiceItemData> getAll(InvoiceId planId) {
    return planDetailRepository.findAllBy(planId)
      .map(mapper::map)
      .collect(Collectors.toList());
  }

  @Override
  public void update(InvoiceItemRequests.UpdateRequest request) {
    val item = planDetailRepository.findBy(request.getId())
      .orElseThrow(InvoiceItemExceptions.NotFoundException::new);
    val response = item.apply(mapper.map(request));
    planDetailRepository.update(item);
    auditService.commit(item);
    eventPublisher.publishEvents(response.getEvents());
  }

}
