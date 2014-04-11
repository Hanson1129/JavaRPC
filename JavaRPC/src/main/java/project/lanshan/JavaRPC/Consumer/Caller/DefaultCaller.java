package project.lanshan.javarpc.consumer.caller;

import project.lanshan.javarpc.consumer.netty.Consumer;
import project.lanshan.javarpc.model.Request;
import project.lanshan.javarpc.model.ServiceMetadata;



public class DefaultCaller implements ConsumerCaller {

  private Consumer consumer;

  public DefaultCaller() {}

  @Override
  public boolean call() {
    try {
      consumer.startCall();
    } catch (InterruptedException e) {
      return false;
    }
    if(consumer.getObject() != null)
      return true;
    else
      return false;
  }

  @Override
  public Object getObject() {
    return consumer.getObject();
  }

  public Consumer getConsumer() {
    return consumer;
  }

  public void setConsumer(Consumer consumer) {
    this.consumer = consumer;
  }

  @Override
  public void setRequest(Request request) {
    consumer.setRequest(request);
  }

  @Override
  public Class<?> getObjectType() {
    return consumer.getObjectType();
  }

  @Override
  public ServiceMetadata getMetadata() {
    return consumer.getMetadata();
  }

  @Override
  public void setMetadata(ServiceMetadata metadata) {
    consumer.setMetadata(metadata);
  }


}
