package pico.erp.invoice;

import kkojaeh.spring.boot.component.SpringBootComponentReadyEvent;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import pico.erp.user.group.GroupRequests;
import pico.erp.user.group.GroupService;

@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@Configuration
public class InvoiceInitializer implements ApplicationListener<SpringBootComponentReadyEvent> {

  @Lazy
  @Autowired
  GroupService groupService;

  @Autowired
  InvoiceProperties properties;

  @Override
  public void onApplicationEvent(SpringBootComponentReadyEvent event) {
    val receiverGroup = properties.getReceiverGroup();
    if (!groupService.exists(receiverGroup.getId())) {
      groupService.create(
        GroupRequests.CreateRequest.builder()
          .id(receiverGroup.getId())
          .name(receiverGroup.getName())
          .build()
      );
    }
  }
}
