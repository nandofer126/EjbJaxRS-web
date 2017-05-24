package py.pol.una.ii.pw.rest;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import py.pol.una.ii.pw.data.RepositorioCompraDet;
import py.pol.una.ii.pw.model.CompraDet;
import py.pol.una.ii.pw.service.ControladorCompraDet;

@Path("/compdetalle")
@ViewScoped
public class CompraDetRest {
	
@Inject
RepositorioCompraDet repositorioCompraDet;

@Inject
ControladorCompraDet controladorCompraDet;

@Inject
private Logger log;

@GET
@Produces(MediaType.APPLICATION_JSON)
public List<CompraDet> listAllDetalles() throws IOException {
    return repositorioCompraDet.findAllOrderedByProducto();
}

@GET
@Produces(MediaType.APPLICATION_JSON)
@Path("/{id}")
public CompraDet listAllDetallesById(@PathParam("id") long id) throws IOException {
    return repositorioCompraDet.findById(id);
}

@POST
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public Response setCompraDet(CompraDet c){
	 Response.ResponseBuilder builder = null;
	 try{
		 controladorCompraDet.register(c);
		 builder =  Response.ok();
	 }catch (Exception e) {
		// TODO: handle exception
		log.info("No se ha registrado el detalle: "+c.getId());
		builder = Response.notModified();
	}
	 return builder.build();
 }

}
