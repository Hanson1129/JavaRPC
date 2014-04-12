package project.lanshan.javarpc.consumer.caller;

import project.lanshan.javarpc.model.AddressHolder;
import project.lanshan.javarpc.model.Request;
import project.lanshan.javarpc.model.ServiceMetadata;


public interface ConsumerCaller {
  public boolean call(AddressHolder addresses);

  public Object getObject(AddressHolder addresses);

  public void setRequest(Request request);

  public Class<?> getObjectType();
  
  public ServiceMetadata getMetadata();
  
  public void setMetadata(ServiceMetadata metadata);
}
