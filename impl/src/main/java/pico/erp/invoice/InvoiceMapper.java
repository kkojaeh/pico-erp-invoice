package pico.erp.invoice;

import java.util.Optional;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.AuditorAware;
import pico.erp.company.CompanyData;
import pico.erp.company.CompanyId;
import pico.erp.company.CompanyService;
import pico.erp.invoice.InvoiceRequests.ReceiveRequest;
import pico.erp.item.ItemData;
import pico.erp.item.ItemId;
import pico.erp.item.ItemService;
import pico.erp.item.spec.ItemSpecData;
import pico.erp.item.spec.ItemSpecId;
import pico.erp.item.spec.ItemSpecService;
import pico.erp.shared.data.Auditor;
import pico.erp.user.UserData;
import pico.erp.user.UserId;
import pico.erp.user.UserService;

@Mapper
public abstract class InvoiceMapper {

  @Autowired
  protected AuditorAware<Auditor> auditorAware;

  @Lazy
  @Autowired
  protected ItemService itemService;

  @Lazy
  @Autowired
  protected ItemSpecService itemSpecService;

  @Autowired
  protected InvoiceCodeGenerator invoiceCodeGenerator;

  @Lazy
  @Autowired
  private CompanyService companyService;

  @Lazy
  @Autowired
  private UserService userService;

  @Lazy
  @Autowired
  private InvoiceRepository invoiceRepository;


  protected Auditor auditor(UserId userId) {
    return Optional.ofNullable(userId)
      .map(userService::getAuditor)
      .orElse(null);
  }

  @Mappings({
    @Mapping(target = "receiverId", source = "receiver.id"),
    @Mapping(target = "senderId", source = "sender.id"),
    @Mapping(target = "confirmerId", source = "confirmer.id"),
    @Mapping(target = "createdBy", ignore = true),
    @Mapping(target = "createdDate", ignore = true),
    @Mapping(target = "lastModifiedBy", ignore = true),
    @Mapping(target = "lastModifiedDate", ignore = true)
  })
  public abstract InvoiceEntity jpa(Invoice data);

  public Invoice jpa(InvoiceEntity entity) {
    return Invoice.builder()
      .id(entity.getId())
      .code(entity.getCode())
      .dueDate(entity.getDueDate())
      .sender(map(entity.getSenderId()))
      .receiver(map(entity.getReceiverId()))
      .receiveAddress(entity.getReceiveAddress())
      .remark(entity.getRemark())
      .confirmer(map(entity.getConfirmerId()))
      .receivedDate(entity.getReceivedDate())
      .canceledDate(entity.getCanceledDate())
      .status(entity.getStatus())
      .build();
  }

  protected UserData map(UserId userId) {
    return Optional.ofNullable(userId)
      .map(userService::get)
      .orElse(null);
  }

  protected CompanyData map(CompanyId companyId) {
    return Optional.ofNullable(companyId)
      .map(companyService::get)
      .orElse(null);
  }

  public Invoice map(InvoiceId invoiceId) {
    return Optional.ofNullable(invoiceId)
      .map(id -> invoiceRepository.findBy(id)
        .orElseThrow(InvoiceExceptions.NotFoundException::new)
      )
      .orElse(null);
  }

  protected ItemData map(ItemId itemId) {
    return Optional.ofNullable(itemId)
      .map(itemService::get)
      .orElse(null);
  }

  protected ItemSpecData map(ItemSpecId itemSpecId) {
    return Optional.ofNullable(itemSpecId)
      .map(itemSpecService::get)
      .orElse(null);
  }

  @Mappings({
    @Mapping(target = "senderId", source = "sender.id"),
    @Mapping(target = "receiverId", source = "receiver.id"),
    @Mapping(target = "confirmerId", source = "confirmer.id")
  })
  public abstract InvoiceData map(Invoice invoice);

  @Mappings({
    @Mapping(target = "sender", source = "senderId"),
    @Mapping(target = "receiver", source = "receiverId"),
    @Mapping(target = "codeGenerator", expression = "java(invoiceCodeGenerator)")
  })
  public abstract InvoiceMessages.Create.Request map(
    InvoiceRequests.CreateRequest request);

  @Mappings({
    @Mapping(target = "sender", source = "senderId"),
    @Mapping(target = "receiver", source = "receiverId")
  })
  public abstract InvoiceMessages.Update.Request map(
    InvoiceRequests.UpdateRequest request);

  @Mappings({
    @Mapping(target = "confirmer", source = "confirmerId"),
  })
  public abstract InvoiceMessages.Receive.Request map(
    ReceiveRequest request);

  public abstract InvoiceMessages.Cancel.Request map(
    InvoiceRequests.CancelRequest request);


  public abstract void pass(InvoiceEntity from, @MappingTarget InvoiceEntity to);


}


