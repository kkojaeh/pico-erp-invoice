package pico.erp.invoice.item.lot;

import java.util.Optional;
import java.util.stream.Stream;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pico.erp.invoice.item.InvoiceItemId;

@Repository
interface InvoiceItemLotEntityRepository extends
  CrudRepository<InvoiceItemLotEntity, InvoiceItemLotId> {

  @Query("SELECT i FROM InvoiceItemLot i WHERE i.invoiceItemId = :invoiceItemId ORDER BY i.createdDate")
  Stream<InvoiceItemLotEntity> findAllBy(@Param("invoiceItemId") InvoiceItemId invoiceItemId);

}

@Repository
@Transactional
public class InvoiceItemLotRepositoryJpa implements InvoiceItemLotRepository {

  @Autowired
  private InvoiceItemLotEntityRepository repository;

  @Autowired
  private InvoiceItemLotMapper mapper;

  @Override
  public InvoiceItemLot create(InvoiceItemLot itemLot) {
    val entity = mapper.jpa(itemLot);
    val created = repository.save(entity);
    return mapper.jpa(created);
  }

  @Override
  public void deleteBy(InvoiceItemLotId id) {
    repository.delete(id);
  }

  @Override
  public boolean exists(InvoiceItemLotId id) {
    return repository.exists(id);
  }

  @Override
  public Stream<InvoiceItemLot> findAllBy(InvoiceItemId invoiceItemId) {
    return repository.findAllBy(invoiceItemId)
      .map(mapper::jpa);
  }

  @Override
  public Optional<InvoiceItemLot> findBy(InvoiceItemLotId id) {
    return Optional.ofNullable(repository.findOne(id))
      .map(mapper::jpa);
  }

  @Override
  public void update(InvoiceItemLot itemLot) {
    val entity = repository.findOne(itemLot.getId());
    mapper.pass(mapper.jpa(itemLot), entity);
    repository.save(entity);
  }
}
