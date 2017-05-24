package py.pol.una.ii.pw.rest;

import java.util.List;
import java.util.logging.Logger;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import py.pol.una.ii.pw.data.MyRepositorioClientes;
import py.pol.una.ii.pw.data.Repositorio;
import py.pol.una.ii.pw.data.RepositorioClientes;
import py.pol.una.ii.pw.model.Clientes;
import py.pol.una.ii.pw.service.ControladorClientes;
import py.pol.una.ii.pw.service.MyControladorClientes;

@Path("/clientes")
public class ClientesRest {
	@Inject
	Repositorio repositorio;
	
	@Inject
	MyRepositorioClientes repositorioClientes;
	
	@Inject
	MyControladorClientes controladorCLientes;
	@Inject
    private Logger log;
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
    public Clientes listarClientePorId(@PathParam("id") long id) {
		return repositorioClientes.findById(id);
    }
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
    public List<Clientes> listarClientes() {
		return repositorioClientes.findAllOrderedByName();
    }
	 
	 @POST
	 @Consumes(MediaType.APPLICATION_JSON)
	 @Produces(MediaType.APPLICATION_JSON)
	 public Response setCliente(Clientes c){
		 Response.ResponseBuilder builder = null;
		 try{
			 controladorCLientes.register(c);
			 builder =  Response.ok();
		 }catch (Exception e) {
			// TODO: handle exception
			log.info("No se ha registrado al cliente: "+c.getNombre());
			builder = Response.notModified();
		}
		 return builder.build();
	 }
	 @DELETE
	 @Path("/{id}")
	 @Produces(MediaType.APPLICATION_JSON)
	 public Response eliminarCliente(@PathParam("id") long id){
		 Response.ResponseBuilder builder = null;
		 Clientes cliente = repositorioClientes.findById(id);
		 try{
			 controladorCLientes.eliminar(cliente);
			 builder =  Response.ok();
		 }catch(Exception e){
			 log.info("No se ha podido eliminar el cliente error: "+e.getMessage());
			 builder=Response.notModified();
		 }
		 return builder.build();
		 
	 }
	 @POST
	 @Path("{id}/actualizar")
	 @Consumes(MediaType.APPLICATION_JSON)
	 @Produces(MediaType.APPLICATION_JSON)
	 public Response actualizaarCliente(@PathParam("id") long id,Clientes cliente){
		 Response.ResponseBuilder builder = null;
		 try{
			controladorCLientes.actualizar(cliente);
			 builder  = Response.ok();
		 }catch (Exception e) {
			// TODO: handle exception
			log.info("No se actualizo en cliente: "+cliente.getNombre()+e.getMessage());
			builder = Response.notModified();
		}
		return builder.build();
	 }
	 
	
}
