package hc.controllers;

import java.sql.Date;
import java.util.List;

import javax.annotation.security.PermitAll;
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
import hc.beans.DoctorSession;
import hc.beans.SingleResponse;
import hc.beans.User;
import hc.services.AppoinmentService;
import hc.services.DoctorService;

@Path("doctors")
public class DoctorCntroller {

	@Path("sessions/{doctorId}/{day}")
	@GET
	@RolesAllowed("ROLE_DOCTOR,ROLE_USER")
	@Produces(MediaType.APPLICATION_JSON)
	public List<DoctorSession> sessions(@Context ContainerRequestContext crc, @PathParam("doctorId") Long doctorId,
			@PathParam("day") String day) throws Exception {
		return DoctorService.getDessions(doctorId, day);
	}

	@Path("all")
	@GET
	@PermitAll
	@Produces(MediaType.APPLICATION_JSON)
	public List<User> all() throws Exception {
		return DoctorService.all();
	}

	@Path("appos/{date}/{session}")
	@GET
	@RolesAllowed("ROLE_DOCTOR,ROLE_USER")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Appoinment> appos(@PathParam("date") String date, @PathParam("session") int session,
			@Context ContainerRequestContext crc) throws Exception {
		Date dt = Date.valueOf(date);
		return DoctorService.getApposOfDate(session, dt, crc.getProperty("username").toString());
	}

	@Path("crete-session")
	@POST
	@RolesAllowed("ROLE_DOCTOR")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public SingleResponse creteSession(DoctorSession session, @Context ContainerRequestContext crc) throws Exception {
		try {
			DoctorService.createSession(session, crc.getProperty("username").toString());
			return SingleResponse.ok("Success");
		} catch (Exception e) {
			return SingleResponse.error(e);
		}
	}

	@Path("delete/{id}")
	@DELETE
	@RolesAllowed("ROLE_ADMIN")
	@Produces(MediaType.APPLICATION_JSON)
	public SingleResponse delete(@PathParam("id") Long id) {
		try {
			DoctorService.delete(id);
			return SingleResponse.ok("Success");
		} catch (Exception e) {
			return SingleResponse.error(e);
		}
	}

	@Path("update/{id}")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@RolesAllowed("ROLE_ADMIN")
	@Produces(MediaType.APPLICATION_JSON)
	public SingleResponse update(@PathParam("id") Long id, DoctorSession a) {
		try {
			DoctorService.update(a, id);
			return SingleResponse.ok("Success");
		} catch (Exception e) {
			return SingleResponse.error(e);
		}
	}
}
