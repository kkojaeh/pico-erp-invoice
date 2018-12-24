package pico.erp.invoice;

import java.time.OffsetDateTime;
import java.util.Optional;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
interface InvoiceEntityRepository extends
  CrudRepository<InvoiceEntity, InvoiceId> {

  @Query("SELECT COUNT(i) FROM Invoice i WHERE i.createdDate >= :begin AND i.createdDate <= :end")
  long countCreatedBetween(@Param("begin") OffsetDateTime begin, @Param("end") OffsetDateTime end);

}

@Repository
@Transactional
public class InvoiceRepositoryJpa implements InvoiceRepository {

  @Autowired
  private InvoiceEntityRepository repository;

  @Autowired
  private InvoiceMapper mapper;

  @Override
  public long countCreatedBetween(OffsetDateTime begin, OffsetDateTime end) {
    return repository.countCreatedBetween(begin, end);
  }

  @Override
  public Invoice create(Invoice plan) {
    val entity = mapper.jpa(plan);
    val created = repository.save(entity);
    return mapper.jpa(created);
  }

  @Override
  public void deleteBy(InvoiceId id) {
    repository.delete(id);
  }

  @Override
  public boolean exists(InvoiceId id) {
    return repository.exists(id);
  }

  @Override
  public Optional<Invoice> findBy(InvoiceId id) {
    return Optional.ofNullable(repository.findOne(id))
      .map(mapper::jpa);
  }

  @Override
  public void update(Invoice plan) {
    val entity = repository.findOne(plan.getId());
    mapper.pass(mapper.jpa(plan), entity);
    repository.save(entity);
  }
}
