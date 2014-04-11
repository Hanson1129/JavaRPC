package project.lanshan.javarpc.provider.netty;

import project.lanshan.javarpc.model.ServiceMetadata;

public interface Provider {
  public boolean startPublish();

  public void setHost(String host);

  public void setPort(int port);

  public ServiceMetadata getMeatadata();
}
