package hc.controllers;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import hc.beans.Appoinment;
import hc.beans.Payment;
import hc.beans.RawPayment;
import hc.beans.SingleResponse;
import hc.services.AppoinmentService;

@Path("payments")
public class PaymentController {

	@Path("all")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@RolesAllowed("ROLE_ADMIN")
	public List<Payment> checkPayments() throws Exception {
		return AppoinmentService.getPayments();
	}

	@Path("delete/{id}")
	@DELETE
	@RolesAllowed("ROLE_ADMIN")
	@Produces(MediaType.APPLICATION_JSON)
	public SingleResponse delete(@PathParam("id") Long id) {
		try {
			AppoinmentService.deletePayment(id);
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
	public SingleResponse update(@PathParam("id") Long id, RawPayment a) {
		try {
			AppoinmentService.updatePayment(a, id);
			return SingleResponse.ok("Success");
		} catch (Exception e) {
			return SingleResponse.error(e);
		}
	}

}
