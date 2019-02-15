package pico.erp.invoice.item;

import java.util.Optional;
import java.util.stream.Stream;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pico.erp.invoice.InvoiceId;

@Repository
interface InvoiceItemEntityRepository extends
  CrudRepository<InvoiceItemEntity, InvoiceItemId> {

  @Query("SELECT i FROM InvoiceItem i WHERE i.invoiceId = :invoiceId ORDER BY i.createdDate")
  Stream<InvoiceItemEntity> findAllBy(@Param("invoiceId") InvoiceId invoiceId);

}

@Repository
@Transactional
public class InvoiceItemRepositoryJpa implements InvoiceItemRepository {

  @Autowired
  private InvoiceItemEntityRepository repository;

  @Autowired
  private InvoiceItemMapper mapper;

  @Override
  public InvoiceItem create(InvoiceItem item) {
    val entity = mapper.jpa(item);
    val created = repository.save(entity);
    return mapper.jpa(created);
  }

  @Override
  public void deleteBy(InvoiceItemId id) {
    repository.delete(id);
  }

  @Override
  public boolean exists(InvoiceItemId id) {
    return repository.exists(id);
  }

  @Override
  public Stream<InvoiceItem> findAllBy(InvoiceId invoiceId) {
    return repository.findAllBy(invoiceId)
      .map(mapper::jpa);
  }

  @Override
  public Optional<InvoiceItem> findBy(InvoiceItemId id) {
    return Optional.ofNullable(repository.findOne(id))
      .map(mapper::jpa);
  }

  @Override
  public void update(InvoiceItem item) {
    val entity = repository.findOne(item.getId());
    mapper.pass(mapper.jpa(item), entity);
    repository.save(entity);
  }
}
