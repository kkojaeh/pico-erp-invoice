package pico.erp.invoice

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.test.annotation.Rollback
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional
import pico.erp.company.CompanyId
import pico.erp.shared.IntegrationConfiguration
import pico.erp.shared.data.Address
import pico.erp.user.UserId
import spock.lang.Specification

import java.time.OffsetDateTime

@SpringBootTest(classes = [IntegrationConfiguration])
@Transactional
@Rollback
@ActiveProfiles("test")
@Configuration
@ComponentScan("pico.erp.config")
class InvoiceServiceSpec extends Specification {

  @Autowired
  InvoiceService invoiceService

  def id = InvoiceId.from("invoice-test")

  def unknownId = InvoiceId.from("unknown")

  def dueDate = OffsetDateTime.now().plusDays(7)

  def remark = "요청 비고"

  def receiverId = CompanyId.from("CUST1")

  def senderId = CompanyId.from("SUPP1")

  def confirmerId = UserId.from("kjh")

  def receiverId2 = CompanyId.from("CUST2")

  def senderId2 = CompanyId.from("SUPP2")

  def dueDate2 = OffsetDateTime.now().plusDays(8)

  def remark2 = "요청 비고2"

  def receiveAddress = new Address(
    postalCode: '13496',
    street: '경기도 성남시 분당구 장미로 42',
    detail: '야탑리더스 410호'
  )

  def receiveAddress2 = new Address(
    postalCode: '13490',
    street: '경기도 성남시 분당구 장미로 40',
    detail: '야탑리더스 510호'
  )

  def setup() {
    invoiceService.create(
      new InvoiceRequests.CreateRequest(
        id: id,
        receiverId: receiverId,
        senderId: senderId,
        receiveAddress: receiveAddress,
        dueDate: dueDate,
        remark: remark,
      )
    )
  }

  def cancelInvoice() {
    invoiceService.cancel(
      new InvoiceRequests.CancelRequest(
        id: id
      )
    )
  }

  def receiveInvoice() {
    invoiceService.receive(
      new InvoiceRequests.ReceiveRequest(
        id: id,
        confirmerId: confirmerId
      )
    )
  }


  def updateInvoice() {
    invoiceService.update(
      new InvoiceRequests.UpdateRequest(
        id: id,
        receiverId: receiverId2,
        senderId: senderId2,
        receiveAddress: receiveAddress2,
        dueDate: dueDate2,
        remark: remark2
      )
    )
  }


  def "존재 - 아이디로 존재 확인"() {
    when:
    def exists = invoiceService.exists(id)

    then:
    exists == true
  }

  def "존재 - 존재하지 않는 아이디로 확인"() {
    when:
    def exists = invoiceService.exists(unknownId)

    then:
    exists == false
  }

  def "조회 - 아이디로 조회"() {
    when:
    def invoice = invoiceService.get(id)

    then:
    invoice.id == id
    invoice.receiverId == receiverId
    invoice.remark == remark
    invoice.dueDate == dueDate
    invoice.receiveAddress == receiveAddress

  }

  def "조회 - 존재하지 않는 아이디로 조회"() {
    when:
    invoiceService.get(unknownId)

    then:
    thrown(InvoiceExceptions.NotFoundException)
  }


  def "수정 - 취소 후 수정"() {
    when:
    cancelInvoice()
    updateInvoice()
    then:
    thrown(InvoiceExceptions.CannotUpdateException)
  }

  def "수정 - 수령 후 수정"() {
    when:
    receiveInvoice()
    updateInvoice()
    then:
    thrown(InvoiceExceptions.CannotUpdateException)
  }


  def "수정 - 작성 후 수정"() {
    when:
    updateInvoice()
    def invoice = invoiceService.get(id)

    then:
    invoice.receiverId == receiverId2
    invoice.senderId == senderId2
    invoice.receiveAddress == receiveAddress2
    invoice.dueDate == dueDate2
    invoice.remark == remark2
  }


  def "취소 - 취소 후 취소"() {
    when:
    cancelInvoice()
    cancelInvoice()
    then:
    thrown(InvoiceExceptions.CannotCancelException)
  }


  def "취소 - 수령 후 취소"() {
    when:
    receiveInvoice()
    cancelInvoice()
    then:
    thrown(InvoiceExceptions.CannotCancelException)
  }


  def "수령 - 작성 후 수령"() {
    when:
    receiveInvoice()
    def invoice = invoiceService.get(id)

    then:
    invoice.confirmerId == confirmerId
  }


  def "수령 - 취소 후 수령"() {
    when:
    cancelInvoice()
    receiveInvoice()
    then:
    thrown(InvoiceExceptions.CannotReceiveException)
  }


  def "수령 - 수령 후 수령"() {
    when:
    receiveInvoice()
    receiveInvoice()
    then:
    thrown(InvoiceExceptions.CannotReceiveException)
  }


}
