package hc.auth;

import java.io.IOException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import hc.services.UserService;

@Provider
public class AuthFilter implements javax.ws.rs.container.ContainerRequestFilter {

	@Context
	private ResourceInfo resourceInfo;

	private static final String AUTHORIZATION_PROPERTY = "Authorization";
	private static final String AUTHENTICATION_SCHEME = "Basic";

	@Override
	public void filter(ContainerRequestContext requestContext)  throws IOException{
		Method method = resourceInfo.getResourceMethod();

		if (!method.isAnnotationPresent(PermitAll.class)) {
			if (method.isAnnotationPresent(DenyAll.class)) {
				requestContext.abortWith(
						Response.status(Response.Status.FORBIDDEN).entity("Access blocked for all users !!").build());
				return;
			}
			final MultivaluedMap<String, String> headers = requestContext.getHeaders();

			final List<String> authorization = headers.get(AUTHORIZATION_PROPERTY);

			if (authorization == null || authorization.isEmpty()) {
				requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
						.entity("You cannot access this resource").build());
				return;
			}

			final String encodedUserPassword = authorization.get(0).replaceFirst(AUTHENTICATION_SCHEME + " ", "");
			String usernameAndPassword = new String(Base64.getDecoder().decode(encodedUserPassword.getBytes()));
			
			final StringTokenizer tokenizer = new StringTokenizer(usernameAndPassword, ":");
            final String username = tokenizer.nextToken();
            final String password = tokenizer.nextToken();
            requestContext.setProperty("username", username);
            Set<String> rolesSet=new HashSet<String>();
            
            if(method.isAnnotationPresent(RolesAllowed.class))
            {
            	 RolesAllowed rolesAnnotation = method.getAnnotation(RolesAllowed.class);
                 rolesSet = new HashSet<String>(Arrays.asList(rolesAnnotation.value()));
                 
            }
            try {
				if(!UserService.IsAuthenticated(username, password,rolesSet)) {
					  requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
				              .entity("You cannot access this resource").build());
				          return;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            
		}
//		// TODO Auto-generated method stub
//				List<String> authHeader = requestContext.getHeaders().get(AUTHORIZATION_PROPERTY);
//				
//				if(authHeader != null && authHeader.size() > 0 ) {
//					String authToken = authHeader.get(0);
//					authToken = authToken.replaceFirst(AUTHENTICATION_SCHEME, "");
//					
//					String decodedString = "";
//					try {
//						byte[] decodedBytes = Base64.getDecoder().decode(
//								authToken);
//					  decodedString = new String(decodedBytes, "UTF-8");
//					} catch (IOException e) {
//						e.printStackTrace();
//					}
//					final StringTokenizer tokenizer = new StringTokenizer(
//							decodedString, ":");
//					
//					final String username = tokenizer.nextToken();
//					final String password = tokenizer.nextToken();
//					
//					 if(UserService.IsAuthenticated(username, password)) {
//						 return;
//					 }
//						
//					
//				}
//				Response unauthoriazedStatus = Response
//													.status(Response.Status.UNAUTHORIZED)
//													.entity("{\"error\" : \"not authorized\"}")
//													.build();
//				requestContext.abortWith(unauthoriazedStatus);
	}

}
