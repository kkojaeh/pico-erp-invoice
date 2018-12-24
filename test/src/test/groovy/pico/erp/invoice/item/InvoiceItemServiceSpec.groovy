package pico.erp.invoice.item

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.test.annotation.Rollback
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional
import pico.erp.invoice.InvoiceId
import pico.erp.invoice.InvoiceRequests
import pico.erp.invoice.InvoiceService
import pico.erp.item.ItemId
import pico.erp.shared.IntegrationConfiguration
import pico.erp.shared.data.UnitKind
import pico.erp.user.UserId
import spock.lang.Specification

@SpringBootTest(classes = [IntegrationConfiguration])
@Transactional
@Rollback
@ActiveProfiles("test")
@Configuration
@ComponentScan("pico.erp.config")
class InvoiceItemServiceSpec extends Specification {

  @Autowired
  InvoiceService invoiceService

  @Autowired
  InvoiceItemService invoiceItemService

  def invoiceId = InvoiceId.from("invoice-1")

  def id = InvoiceItemId.from("invoice-item-1")

  def unknownId = InvoiceItemId.from("unknown")

  def itemId = ItemId.from("item-1")

  def confirmerId = UserId.from("kjh")

  def setup() {

  }

  def cancelOrder() {
    invoiceService.cancel(
      new InvoiceRequests.CancelRequest(
        id: invoiceId
      )
    )
  }

  def receiveOrder() {
    invoiceService.receive(
      new InvoiceRequests.ReceiveRequest(
        id: invoiceId,
        confirmerId: confirmerId
      )
    )
  }

  def createItem() {
    invoiceItemService.create(
      new InvoiceItemRequests.CreateRequest(
        id: id,
        invoiceId: invoiceId,
        itemId: itemId,
        quantity: 100,
        unit: UnitKind.EA,
        remark: "품목 비고"
      )
    )
  }

  def createItem2() {
    invoiceItemService.create(
      new InvoiceItemRequests.CreateRequest(
        id: InvoiceItemId.from("invoice-item-2"),
        invoiceId: invoiceId,
        itemId: itemId,
        quantity: 100,
        unit: UnitKind.EA,
        remark: "품목 비고"
      )
    )
  }

  def updateItem() {
    invoiceItemService.update(
      new InvoiceItemRequests.UpdateRequest(
        id: id,
        quantity: 200,
        remark: "품목 비고2"
      )
    )
  }

  def deleteItem() {
    invoiceItemService.delete(
      new InvoiceItemRequests.DeleteRequest(
        id: id
      )
    )
  }


  def "존재 - 아이디로 확인"() {
    when:
    createItem()
    def exists = invoiceItemService.exists(id)

    then:
    exists == true
  }

  def "존재 - 존재하지 않는 아이디로 확인"() {
    when:
    def exists = invoiceItemService.exists(unknownId)

    then:
    exists == false
  }

  def "조회 - 아이디로 조회"() {
    when:
    createItem()
    def item = invoiceItemService.get(id)
    then:
    item.id == id
    item.itemId == itemId
    item.invoiceId == invoiceId
    item.quantity == 100
    item.remark == "품목 비고"

  }

  def "조회 - 존재하지 않는 아이디로 조회"() {
    when:
    invoiceItemService.get(unknownId)

    then:
    thrown(InvoiceItemExceptions.NotFoundException)
  }

  def "생성 - 작성 후 생성"() {
    when:
    createItem()
    def items = invoiceItemService.getAll(invoiceId)
    then:
    items.size() > 0
  }


  def "생성 - 취소 후 생성"() {
    when:
    createItem()
    cancelOrder()
    createItem2()
    then:
    thrown(InvoiceItemExceptions.CannotCreateException)
  }


  def "생성 - 수령 후 생성"() {
    when:
    createItem()
    receiveOrder()
    createItem2()
    then:
    thrown(InvoiceItemExceptions.CannotCreateException)
  }

  def "수정 - 작성 후 수정"() {
    when:
    createItem()
    updateItem()
    def items = invoiceItemService.getAll(invoiceId)
    then:
    items.size() > 0
  }

  def "수정 - 취소 후 수정"() {
    when:
    createItem()
    cancelOrder()
    updateItem()
    then:
    thrown(InvoiceItemExceptions.CannotUpdateException)
  }


  def "수정 - 수령 후 수정"() {
    when:
    createItem()
    receiveOrder()
    updateItem()
    then:
    thrown(InvoiceItemExceptions.CannotUpdateException)
  }


  def "삭제 - 작성 후 삭제"() {
    when:
    createItem()
    deleteItem()
    def items = invoiceItemService.getAll(invoiceId)
    then:
    items.size() == 0
  }


  def "삭제 - 취소 후 삭제"() {
    when:
    createItem()
    cancelOrder()
    deleteItem()
    then:
    thrown(InvoiceItemExceptions.CannotDeleteException)
  }


  def "삭제 - 수령 후 삭제"() {
    when:
    createItem()
    receiveOrder()
    deleteItem()
    then:
    thrown(InvoiceItemExceptions.CannotDeleteException)
  }


}
