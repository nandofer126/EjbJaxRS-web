package py.pol.una.ii.pw.rest;

import java.util.List;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.persistence.ElementCollection;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import py.pol.una.ii.pw.data.MyRepositorioProductos;
import py.pol.una.ii.pw.data.Repositorio;
import py.pol.una.ii.pw.data.RepositorioClientes;
import py.pol.una.ii.pw.data.RepositorioProductos;
import py.pol.una.ii.pw.model.Clientes;
import py.pol.una.ii.pw.model.Productos;
import py.pol.una.ii.pw.service.ControladorClientes;
import py.pol.una.ii.pw.service.ControladorProductos;
import py.pol.una.ii.pw.service.MyControladorProductos;

@Path("/productos")
public class ProductosRest {

	
	@Inject
	MyRepositorioProductos repositorioProductos;
	
	@Inject
	MyControladorProductos controladorProductos;
	@Inject
    private Logger log;
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
    public Productos listarProductoById(@PathParam("id") long productoId) {
		return repositorioProductos.findById(productoId);
    }
	@GET
	@Produces(MediaType.APPLICATION_JSON)
    public List<Productos> listarProductos() {
		return repositorioProductos.findAllOrderedByName();
    }
	 
	 @POST
	 @Consumes(MediaType.APPLICATION_JSON)
	 @Produces(MediaType.APPLICATION_JSON)
	 public Response setProducto(Productos p){
		 Response.ResponseBuilder builder = null;
		 try{
			 controladorProductos.register(p);
			 builder =  Response.ok();
		 }catch (Exception e) {
			// TODO: handle exception
			log.info("No se ha registrado el producto: "+p.getNombre());
			builder = Response.notModified();
		}
		 return builder.build();
	 }
	 @DELETE
	 @Path("{id}")
	 @Produces(MediaType.APPLICATION_JSON)
	 public Response eliminarProducto(@PathParam("id") long id){
		 Response.ResponseBuilder builder = null;
		 Productos producto = repositorioProductos.findById(id);
		 try{
			 controladorProductos.eliminar(producto);
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
	 public Response actualizarProducto(@PathParam("id") long id,Productos producto){
		 Response.ResponseBuilder builder = null;
		 try{
			controladorProductos.actualizar(producto);
			 builder  = Response.ok();
		 }catch (Exception e) {
			// TODO: handle exception
			log.info("No se actualizo en cliente: "+producto.getNombre()+e.getMessage());
			builder = Response.notModified();
		}
		return builder.build();
	 }
	 
	
}
