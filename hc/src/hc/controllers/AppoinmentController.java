package hc.controllers;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import hc.beans.Appoinment;
import hc.beans.SingleResponse;
import hc.services.AppoinmentService;

@Path("appoinment")
public class AppoinmentController {

	@Path("book")
	@POST
	@RolesAllowed("ROLE_USER")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public SingleResponse book(Appoinment appoinment, @Context ContainerRequestContext crc) {
		try {
			AppoinmentService.book(appoinment, crc.getProperty("username").toString());
			return SingleResponse.ok("Success");
		} catch (Exception e) {
			return SingleResponse.error(e);
		}
	}

	@Path("my")
	@GET
	@RolesAllowed("ROLE_USER")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Appoinment> userAppoinments(@Context ContainerRequestContext crc) throws Exception {
		return AppoinmentService.userAppoinment(crc.getProperty("username").toString());
	}

	@Path("pay/{appoId}")
	@GET
	@RolesAllowed("ROLE_USER")
	@Produces(MediaType.APPLICATION_JSON)
	public SingleResponse pay(@PathParam("appoId") Long appoId) {
		try {
			AppoinmentService.pay(appoId);
			return SingleResponse.ok("Success");
		} catch (Exception e) {
			return SingleResponse.error(e);
		}
	}

	@Path("delete/{id}")
	@DELETE
	@RolesAllowed("ROLE_ADMIN,ROLE_USER")
	@Produces(MediaType.APPLICATION_JSON)
	public SingleResponse delete(@PathParam("id") Long id) {
		try {
			AppoinmentService.deleteAppo(id);
			return SingleResponse.ok("Success");
		} catch (Exception e) {
			return SingleResponse.error(e);
		}
	}

	@Path("update/{id}")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@RolesAllowed("ROLE_ADMIN,ROLE_USER")
	@Produces(MediaType.APPLICATION_JSON)
	public SingleResponse update(@PathParam("id") Long id, Appoinment a) {
		try {
			AppoinmentService.updateAppo(a, id);
			return SingleResponse.ok("Success");
		} catch (Exception e) {
			return SingleResponse.error(e);
		}
	}

}
