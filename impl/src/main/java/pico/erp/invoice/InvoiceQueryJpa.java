package pico.erp.invoice;

import static org.springframework.util.StringUtils.isEmpty;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pico.erp.invoice.InvoiceView.Filter;
import pico.erp.invoice.item.QInvoiceItemEntity;
import pico.erp.shared.jpa.QueryDslJpaSupport;

public class InvoiceQueryJpa implements InvoiceQuery {


  private final QInvoiceEntity invoice = QInvoiceEntity.invoiceEntity;

  private final QInvoiceItemEntity invoiceItem = QInvoiceItemEntity.invoiceItemEntity;

  @PersistenceContext
  private EntityManager entityManager;

  @Autowired
  private QueryDslJpaSupport queryDslJpaSupport;

  @Override
  public Page<InvoiceView> retrieve(Filter filter, Pageable pageable) {
    val query = new JPAQuery<InvoiceView>(entityManager);
    val select = Projections.bean(InvoiceView.class,
      invoice.id,
      invoice.code,
      invoice.receiverId,
      invoice.supplierId,
      invoice.receiveAddress,
      invoice.dueDate,
      invoice.receivedDate,
      invoice.canceledDate,
      invoice.status
    );

    query.select(select);
    query.from(invoice);

    val builder = new BooleanBuilder();

    if (!isEmpty(filter.getCode())) {
      builder.and(invoice.code.value
        .likeIgnoreCase(queryDslJpaSupport.toLikeKeyword("%", filter.getCode(), "%")));
    }

    if (filter.getReceiverId() != null) {
      builder.and(invoice.receiverId.eq(filter.getReceiverId()));
    }

    if (filter.getConfirmerId() != null) {
      builder.and(invoice.confirmedBy.id.eq(filter.getConfirmerId().getValue()));
    }

    if (filter.getItemId() != null) {
      builder.and(
        invoice.id.in(
          JPAExpressions.select(invoiceItem.invoiceId)
            .from(invoiceItem)
            .where(invoiceItem.itemId.eq(filter.getItemId()))
        )
      );
    }

    if (filter.getStatuses() != null && !filter.getStatuses().isEmpty()) {
      builder.and(invoice.status.in(filter.getStatuses()));
    }

    if (filter.getStartDueDate() != null) {
      builder.and(invoice.dueDate.goe(filter.getStartDueDate()));
    }
    if (filter.getEndDueDate() != null) {
      builder.and(invoice.dueDate.loe(filter.getEndDueDate()));
    }

    query.where(builder);
    return queryDslJpaSupport.paging(query, pageable, select);
  }
}
