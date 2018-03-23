package de.gishmo.gwtbootstartermvp4g2.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.core.env.Environment;

@SpringBootApplication(scanBasePackages = "de.gishmo.gwtbootstartermvp4g2.server.resource")
public class ServerApplication
  extends SpringBootServletInitializer {

  final static Logger logger = LoggerFactory.getLogger(ServerApplication.class);

  @Autowired
  Environment environment;

  @Override
  protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
    return application.sources(ServerApplication.class);
  }


  public static void main(String[] args) {
    SpringApplication.run(ServerApplication.class, args);
  }
}
