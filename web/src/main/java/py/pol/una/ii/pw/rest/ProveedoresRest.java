package py.pol.una.ii.pw.rest;

import java.util.List;
import java.util.logging.Logger;

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

import py.pol.una.ii.pw.data.MyRepositorioProveedores;
import py.pol.una.ii.pw.data.Repositorio;
import py.pol.una.ii.pw.data.RepositorioProveedores;
import py.pol.una.ii.pw.model.Proveedores;
import py.pol.una.ii.pw.service.MyControladorProveedores;

@Path("/proveedores")
public class ProveedoresRest {
	@Inject
	Repositorio repositorio;
	
	@Inject
	MyRepositorioProveedores repositorioProveedores;
	
	@Inject
	MyControladorProveedores controladorProveedores;
	@Inject
    private Logger log;
	
	
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
    public List<Proveedores> listarProveedores() {
		return repositorioProveedores.findAllOrderedByName();
    }
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
    public Proveedores getProveedoreById(@PathParam("id") long id){
		return repositorioProveedores.findById(id);
    } 
	 @POST
	 @Consumes(MediaType.APPLICATION_JSON)
	 @Produces(MediaType.APPLICATION_JSON)
	 public Response setProveedor(Proveedores pr){
		 Response.ResponseBuilder builder = null;
		 try{
			 controladorProveedores.register(pr);
			 builder =  Response.ok();
		 }catch (Exception e) {
			// TODO: handle exception
			log.info("No se ha registrado al cliente: "+pr.getNombre());
			builder = Response.notModified();
		}
		 return builder.build();
	 }
	 @DELETE
	 @Path("{id}")
	 @Produces(MediaType.APPLICATION_JSON)
	 public Response eliminarProveedor(@PathParam("id") long id){
		 Response.ResponseBuilder builder = null;
		 Proveedores pr = repositorioProveedores.findById(id);
		 try{
			 controladorProveedores.eliminar(pr);
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
	 public Response actualizarProveedor(@PathParam("id") long id,Proveedores proveedor){
		 Response.ResponseBuilder builder = null;
		 try{
			controladorProveedores.actualizar(proveedor);
			 builder  = Response.ok();
		 }catch (Exception e) {
			// TODO: handle exception
			log.info("No se actualizo en cliente: "+proveedor.getNombre()+e.getMessage());
			builder = Response.notModified();
		}
		return builder.build();
	 }

}
