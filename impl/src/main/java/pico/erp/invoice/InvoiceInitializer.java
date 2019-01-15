package pico.erp.invoice;

import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import pico.erp.shared.ApplicationInitializer;
import pico.erp.user.group.GroupRequests;
import pico.erp.user.group.GroupService;

@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@Configuration
public class InvoiceInitializer implements ApplicationInitializer {

  @Lazy
  @Autowired
  GroupService groupService;

  @Autowired
  InvoiceProperties properties;

  @Override
  public void initialize() {
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
