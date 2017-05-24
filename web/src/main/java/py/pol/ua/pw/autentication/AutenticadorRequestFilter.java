package py.pol.ua.pw.autentication;


import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.annotation.security.PermitAll;
import javax.annotation.security.DenyAll;
import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;


import org.jboss.resteasy.core.ResourceMethodInvoker;
@Provider
@PreMatching
public class AutenticadorRequestFilter implements ContainerRequestFilter {
	
	@Inject
	Logger log;
	
	@Inject
	Autenticador autenticador;
	
	@Override
    public void filter( ContainerRequestContext requestCtx ) throws IOException {
 
        String path = requestCtx.getUriInfo().getPath();
        log.info( "Fitrando la url: " + path );
        log.info("Contiene Login?: "+path.contains("/login"));
        if ( !path.contains("/login")) {
            String authToken = requestCtx.getHeaderString( "Auth");
 
            // if it isn't valid, just kick them out.
            if ( !autenticador.isAuthTokenValid( authToken ) ) {
                requestCtx.abortWith( Response.status( Response.Status.UNAUTHORIZED ).build() );
            }
        }
    }
}
