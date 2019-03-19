package pico.erp.invoice;


import java.io.Serializable;
import java.time.OffsetDateTime;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Index;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import pico.erp.company.CompanyId;
import pico.erp.shared.TypeDefinitions;
import pico.erp.shared.data.Address;
import pico.erp.shared.data.Auditor;
import pico.erp.user.UserId;

@Entity(name = "Invoice")
@Table(name = "INV_INVOICE", indexes = {
  @Index(columnList = "createdDate")
})
@Data
@EqualsAndHashCode(of = "id")
@FieldDefaults(level = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InvoiceEntity implements Serializable {

  private static final long serialVersionUID = 1L;

  @EmbeddedId
  @AttributeOverrides({
    @AttributeOverride(name = "value", column = @Column(name = "ID", length = TypeDefinitions.UUID_BINARY_LENGTH))
  })
  InvoiceId id;

  @AttributeOverrides({
    @AttributeOverride(name = "value", column = @Column(name = "CODE", length = TypeDefinitions.CODE_LENGTH))
  })
  InvoiceCode code;

  OffsetDateTime dueDate;

  @AttributeOverrides({
    @AttributeOverride(name = "value", column = @Column(name = "RECEIVER_ID", length = TypeDefinitions.ID_LENGTH))
  })
  CompanyId receiverId;

  @AttributeOverrides({
    @AttributeOverride(name = "value", column = @Column(name = "SENDER_ID", length = TypeDefinitions.ID_LENGTH))
  })
  CompanyId senderId;

  @Embedded
  @AttributeOverrides({
    @AttributeOverride(name = "postalCode", column = @Column(name = "RECEIVE_ADDRESS_POSTAL_CODE", length = TypeDefinitions.ADDRESS_POSTAL_LENGTH)),
    @AttributeOverride(name = "street", column = @Column(name = "RECEIVE_ADDRESS_STREET", length = TypeDefinitions.ADDRESS_STREET_LENGTH)),
    @AttributeOverride(name = "detail", column = @Column(name = "RECEIVE_ADDRESS_DETAIL", length = TypeDefinitions.ADDRESS_DETAIL_LENGTH))
  })
  Address receiveAddress;

  @Column(length = TypeDefinitions.REMARK_LENGTH)
  String remark;

  @Embedded
  @AttributeOverrides({
    @AttributeOverride(name = "value", column = @Column(name = "CONFIRMER_ID", length = TypeDefinitions.ID_LENGTH))
  })
  UserId confirmerId;

  OffsetDateTime receivedDate;

  OffsetDateTime canceledDate;

  @Column(length = TypeDefinitions.ENUM_LENGTH)
  @Enumerated(EnumType.STRING)
  InvoiceStatusKind status;

  @Embedded
  @AttributeOverrides({
    @AttributeOverride(name = "id", column = @Column(name = "CREATED_BY_ID", updatable = false, length = TypeDefinitions.ID_LENGTH)),
    @AttributeOverride(name = "name", column = @Column(name = "CREATED_BY_NAME", updatable = false, length = TypeDefinitions.NAME_LENGTH))
  })
  @CreatedBy
  Auditor createdBy;

  @CreatedDate
  @Column(updatable = false)
  OffsetDateTime createdDate;

  @Embedded
  @AttributeOverrides({
    @AttributeOverride(name = "id", column = @Column(name = "LAST_MODIFIED_BY_ID", length = TypeDefinitions.ID_LENGTH)),
    @AttributeOverride(name = "name", column = @Column(name = "LAST_MODIFIED_BY_NAME", length = TypeDefinitions.NAME_LENGTH))
  })
  @LastModifiedBy
  Auditor lastModifiedBy;

  @LastModifiedDate
  OffsetDateTime lastModifiedDate;

}
