package pico.erp.invoice.item;

import java.util.Optional;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.AuditorAware;
import pico.erp.invoice.Invoice;
import pico.erp.invoice.InvoiceExceptions;
import pico.erp.invoice.InvoiceId;
import pico.erp.invoice.InvoiceMapper;
import pico.erp.item.ItemData;
import pico.erp.item.ItemId;
import pico.erp.item.ItemService;
import pico.erp.item.lot.ItemLotData;
import pico.erp.item.lot.ItemLotId;
import pico.erp.item.lot.ItemLotService;
import pico.erp.shared.data.Auditor;

@Mapper
public abstract class InvoiceItemMapper {

  @Autowired
  protected AuditorAware<Auditor> auditorAware;

  @Lazy
  @Autowired
  protected ItemService itemService;

  @Lazy
  @Autowired
  protected ItemLotService itemLotService;

  @Lazy
  @Autowired
  private InvoiceItemRepository purchaseRequestItemRepository;

  @Autowired
  private InvoiceMapper requestMapper;


  protected InvoiceItemId id(InvoiceItem purchaseRequestItem) {
    return purchaseRequestItem != null ? purchaseRequestItem.getId() : null;
  }

  @Mappings({
    @Mapping(target = "invoiceId", source = "invoice.id"),
    @Mapping(target = "itemId", source = "item.id"),
    @Mapping(target = "itemLotId", source = "itemLot.id"),
    @Mapping(target = "createdBy", ignore = true),
    @Mapping(target = "createdDate", ignore = true),
    @Mapping(target = "lastModifiedBy", ignore = true),
    @Mapping(target = "lastModifiedDate", ignore = true)
  })
  public abstract InvoiceItemEntity jpa(InvoiceItem data);

  public InvoiceItem jpa(InvoiceItemEntity entity) {
    return InvoiceItem.builder()
      .id(entity.getId())
      .invoice(map(entity.getInvoiceId()))
      .item(map(entity.getItemId()))
      .itemLot(map(entity.getItemLotId()))
      .quantity(entity.getQuantity())
      .unit(entity.getUnit())
      .remark(entity.getRemark())
      .build();
  }

  public InvoiceItem map(InvoiceItemId purchaseRequestItemId) {
    return Optional.ofNullable(purchaseRequestItemId)
      .map(id -> purchaseRequestItemRepository.findBy(id)
        .orElseThrow(InvoiceExceptions.NotFoundException::new)
      )
      .orElse(null);
  }

  protected ItemData map(ItemId itemId) {
    return Optional.ofNullable(itemId)
      .map(itemService::get)
      .orElse(null);
  }

  protected ItemLotData map(ItemLotId itemLotId) {
    return Optional.ofNullable(itemLotId)
      .map(itemLotService::get)
      .orElse(null);
  }

  protected Invoice map(InvoiceId invoiceId) {
    return requestMapper.map(invoiceId);
  }

  @Mappings({
    @Mapping(target = "invoiceId", source = "invoice.id"),
    @Mapping(target = "itemId", source = "item.id"),
    @Mapping(target = "itemLotId", source = "itemLot.id")
  })
  public abstract InvoiceItemData map(InvoiceItem item);

  @Mappings({
    @Mapping(target = "invoice", source = "invoiceId"),
    @Mapping(target = "item", source = "itemId"),
    @Mapping(target = "itemLot", source = "itemLotId")
  })
  public abstract InvoiceItemMessages.Create.Request map(
    InvoiceItemRequests.CreateRequest request);

  public abstract InvoiceItemMessages.Update.Request map(
    InvoiceItemRequests.UpdateRequest request);

  public abstract InvoiceItemMessages.Delete.Request map(
    InvoiceItemRequests.DeleteRequest request);


  public abstract void pass(
    InvoiceItemEntity from, @MappingTarget InvoiceItemEntity to);


}



