package project.lanshan.javarpc.provider.publisher;

import project.lanshan.javarpc.model.ServiceMetadata;

public interface ProviderPublisher {
  public Boolean publish();
  public void setHost(String host);
  public void setPost(int port);
  public ServiceMetadata getMetadata();
  
}
