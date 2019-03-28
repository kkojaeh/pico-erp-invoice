package pico.erp.invoice

import kkojaeh.spring.boot.component.SpringBootTestComponent
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.MessageSource
import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.test.annotation.Rollback
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional
import pico.erp.company.CompanyApplication
import pico.erp.item.ItemApplication
import pico.erp.project.ProjectApplication
import pico.erp.shared.TestParentApplication
import pico.erp.user.UserApplication
import spock.lang.Specification

import java.util.stream.Collectors
import java.util.stream.Stream

@SpringBootTest(classes = [InvoiceApplication, TestConfig])
@SpringBootTestComponent(parent = TestParentApplication, siblings = [ItemApplication, UserApplication, ProjectApplication, CompanyApplication])
@Transactional
@Rollback
@ActiveProfiles("test")
class MessageSourceSpec extends Specification {

  @Autowired
  MessageSource messageSource

  def locale = LocaleContextHolder.locale

  def "송장 상태"() {
    when:
    def messages = Stream.of(InvoiceStatusKind.values())
      .map({
      kind -> messageSource.getMessage(kind.nameCode, null, locale)
    }).collect(Collectors.toList())

    println messages

    then:
    messages.size() == 3
  }


}
