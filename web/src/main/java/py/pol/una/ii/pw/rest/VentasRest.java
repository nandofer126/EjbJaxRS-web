package py.pol.una.ii.pw.rest;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import py.pol.una.ii.pw.controller.IdentificadorVentas;
import py.pol.una.ii.pw.service.IMyControladorVentas;
import py.pol.una.ii.pw.data.RepositorioVenta;
import py.pol.una.ii.pw.model.Clientes;
import py.pol.una.ii.pw.model.Venta;

@Path("/ventas")
public class VentasRest {

	@Inject
	ProductosRest productoManager;
	
	@Inject
	RepositorioVenta repositorio;
	
	@Inject
	IdentificadorVentas identificador;

	@Inject
    private Logger log;
	
	@Inject
    private HttpSession session;
	
	@GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Venta> listAllCabeceras() throws IOException {
        return repositorio.findAll();
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Venta listAllCabeceraById(@PathParam("id") long id) throws IOException {
        return repositorio.findById(id);
    }

    @POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response iniciarVenta(Clientes cliente,@Context HttpServletRequest request, @Context HttpServletResponse response){
		 Response.ResponseBuilder builder = null;
		 IMyControladorVentas ventaControlador;
		 
		 Integer id = null;
		 try{
			 HttpSession session= request.getSession(true);
			 ventaControlador = (IMyControladorVentas)new InitialContext().lookup("java:global/EjbJaxRS-ear/EjbJaxRS-ejb/IMyControladorVentas");
			 id=identificador.getIndentificadorNuevo();
			 session.setAttribute(id.toString(), ventaControlador);
			 ventaControlador.iniciarVenta(cliente);
			 builder =  Response.ok(id);
			 
		 }catch (Exception e) {
			// TODO: handle exception
			log.info("No se ha registrado la venta del cliente: "+cliente.getNombre()+" error: "+e.getMessage()+ " causa "+e.getCause());
			builder = Response.notModified();
		}
		 return builder.build();
	 }


    @POST
    @Path("{idventa}/producto/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
	public Response setProducto(long cantidad, @PathParam("id") long id, @PathParam("idventa") Integer idventa,@Context HttpServletRequest request, @Context HttpServletResponse response){
		 Response.ResponseBuilder builder = null;
		 IMyControladorVentas controladorVenta;
		 
		 try{
			 HttpSession session= (HttpSession) request.getSession();
			 controladorVenta = (IMyControladorVentas)session.getAttribute(idventa.toString());
			 System.out.println("---------Valor real del controlador: "+controladorVenta);
			 controladorVenta.agregarProducto(id,cantidad);
			 builder =  Response.ok(idventa);
		 }catch (Exception e) {
			// TODO: handle exception
			log.info("No se ha registrado al producto -> idproducto: "+id+" excepcion: "+e.getMessage()+ " causa: "+e.getCause());
			builder = Response.notModified();
		}
		 return builder.build();
	 }

    @POST
	@Consumes(MediaType.APPLICATION_JSON)
    @Path("/{idventa}/confirmar")
    public Response confirmarCompra(@PathParam("idventa") Integer idventa,@Context HttpServletRequest request, @Context HttpServletResponse response){
		 Response.ResponseBuilder builder = null;
		 IMyControladorVentas controlador;
		 try{
			 HttpSession session= (HttpSession) request.getSession();
			 controlador = (IMyControladorVentas)session.getAttribute(idventa.toString());
			 controlador.confirmar();
			 builder =  Response.ok();
		 }catch (Exception e) {
			// TODO: handle exception
			log.info("No se ha confirmado la compra: "+idventa);
			builder = Response.notModified();
		}
		 return builder.build();
	 }
    
    @POST
	@Consumes(MediaType.APPLICATION_JSON)
    @Path("/{idventa}/cancelar")
    public Response cancelarCompra(@PathParam("idventa") Integer idventa,@Context HttpServletRequest request, @Context HttpServletResponse response){
		 Response.ResponseBuilder builder = null;
		 HttpSession session;
		 session = request.getSession();
		 IMyControladorVentas controlador;
		 try{
			//IControladorCompras controlador = (IControladorCompras)session.getAttribute(idcompra.toString());
			 controlador = (IMyControladorVentas)session.getAttribute(idventa.toString());
			 controlador.cancelar();
			 builder =  Response.ok();
		 }catch (Exception e) {
			// TODO: handle exception
			log.info("No se ha cancelado la compra: "+idventa);
			builder = Response.notModified();
		}
		 return builder.build();
	 }
}
