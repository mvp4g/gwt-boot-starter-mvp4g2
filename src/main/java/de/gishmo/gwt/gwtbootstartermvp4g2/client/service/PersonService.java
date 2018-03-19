package de.gishmo.gwt.gwtbootstartermvp4g2.client.service;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.RestService;

import de.gishmo.gwt.example.mvp4g2.springboot.client.data.model.dto.Person;
import de.gishmo.gwt.example.mvp4g2.springboot.client.data.model.dto.PersonSearch;

public interface ProjectService
  extends RestService {

  @POST
  @Path("/generate")
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  void get(@QueryParam("id") String id,
           MethodCallback<Person> methodCallBack);

  @GET
  @Path("/getAll")
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  void get(MethodCallback<List<Person>> methodCallBack);

  @POST
  @Path("/search")
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  void search(PersonSearch search,
              MethodCallback<List<Person>> methodCallBack);

  @POST
  @Path("/insert")
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  void insert(Person person,
              MethodCallback<Void> methodCallBack);

  @POST
  @Path("/update")
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  void udpate(Person person,
              MethodCallback<Void> methodCallBack);

}
