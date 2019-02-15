package pico.erp.invoice.item.lot;

import java.util.Optional;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.AuditorAware;
import pico.erp.invoice.InvoiceExceptions;
import pico.erp.invoice.item.InvoiceItem;
import pico.erp.invoice.item.InvoiceItemId;
import pico.erp.invoice.item.InvoiceItemMapper;
import pico.erp.item.ItemData;
import pico.erp.item.ItemId;
import pico.erp.item.ItemService;
import pico.erp.item.lot.ItemLotData;
import pico.erp.item.lot.ItemLotId;
import pico.erp.item.lot.ItemLotService;
import pico.erp.shared.data.Auditor;

@Mapper
public abstract class InvoiceItemLotMapper {

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
  private InvoiceItemLotRepository purchaseRequestItemRepository;

  @Autowired
  private InvoiceItemMapper invoiceItemMapper;


  protected InvoiceItemLotId id(InvoiceItemLot invoiceItemLot) {
    return invoiceItemLot != null ? invoiceItemLot.getId() : null;
  }

  @Mappings({
    @Mapping(target = "invoiceItemId", source = "invoiceItem.id"),
    @Mapping(target = "createdBy", ignore = true),
    @Mapping(target = "createdDate", ignore = true),
    @Mapping(target = "lastModifiedBy", ignore = true),
    @Mapping(target = "lastModifiedDate", ignore = true)
  })
  public abstract InvoiceItemLotEntity jpa(InvoiceItemLot data);

  public InvoiceItemLot jpa(InvoiceItemLotEntity entity) {
    return InvoiceItemLot.builder()
      .id(entity.getId())
      .invoiceItem(map(entity.getInvoiceItemId()))
      .itemLotCode(entity.getItemLotCode())
      .quantity(entity.getQuantity())
      .build();
  }

  public InvoiceItemLot map(InvoiceItemLotId itemLotId) {
    return Optional.ofNullable(itemLotId)
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

  protected InvoiceItem map(InvoiceItemId invoiceItemId) {
    return invoiceItemMapper.map(invoiceItemId);
  }

  @Mappings({
    @Mapping(target = "invoiceItemId", source = "invoiceItem.id")
  })
  public abstract InvoiceItemLotData map(InvoiceItemLot item);

  @Mappings({
    @Mapping(target = "invoiceItem", source = "invoiceItemId")
  })
  public abstract InvoiceItemLotMessages.Create.Request map(
    InvoiceItemLotRequests.CreateRequest request);

  public abstract InvoiceItemLotMessages.Update.Request map(
    InvoiceItemLotRequests.UpdateRequest request);

  public abstract InvoiceItemLotMessages.Delete.Request map(
    InvoiceItemLotRequests.DeleteRequest request);


  public abstract void pass(
    InvoiceItemLotEntity from, @MappingTarget InvoiceItemLotEntity to);


}



