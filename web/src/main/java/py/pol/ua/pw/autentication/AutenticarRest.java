package py.pol.ua.pw.autentication;

import java.util.List;
import java.util.LinkedList;
import java.util.logging.Logger;

import javax.annotation.security.PermitAll;
import javax.inject.Inject;
import javax.security.auth.login.LoginException;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

@Path("/auth")
public class AutenticarRest {


	@Inject
	Autenticador autenticador;
	
	@Inject
	Logger log;
	
	@POST
	@PermitAll
    @Path("/login" )
    public Response login(@Context HttpHeaders httpHeaders ){
		Response.ResponseBuilder builder = null;
		MultivaluedMap<String,String> header= httpHeaders.getRequestHeaders();
		//log.info("Cabezera User: "+header.get("username"));
		List<String> listUser = header.get("username");
		List<String> listPwd = header.get("password");
        try {
            String authToken = autenticador.login(listUser.get(0), listPwd.get(0));
            log.info("Se ha autenticado el  usuario: "+listUser.get(0)+ " con session ID: "+authToken);
            builder= Response.ok("Su SessionID es: "+authToken).header("Auth", authToken);
 
        } catch (Exception e) {
            
            log.severe("Se ha producido un error: "+e.getMessage()+" causa: "+e.getCause() );
            builder=Response.serverError();
        }
        return builder.build();
	}
	@POST
    @Path("/logout" )
    @Produces( MediaType.APPLICATION_JSON)
	public Response logout(@Context HttpHeaders httpHeaders ) {
		Response.ResponseBuilder builder = null;
			try {
	        	List<String> authTokenList = httpHeaders.getRequestHeader(HttpHeaderNames.AUTH_TOKEN);
	        	for(String authToken: authTokenList){
	        		autenticador.logout(authToken);
	        		builder=Response.ok("Ha cerrado su session con SessionID: "+authToken);
	        	}
	            log.info("Ha cerrado su session el usuario con SessionID: "+authTokenList.get(0));
	        } catch ( Exception e ) {
	            builder = Response.status(Response.Status.INTERNAL_SERVER_ERROR);
	            log.severe("Se ha producido un error al intentar cerrar la session.");
	        }
			return builder.build();
	    }
	
	
}
