
package py.pol.una.ii.pw.rest;

import java.io.File;

import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.naming.InitialContext;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpSession;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import javax.validation.Validator;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Context;


import py.pol.una.ii.pw.data.RepositorioCompraCab;
import py.pol.una.ii.pw.data.RepositorioProveedores;
import py.pol.una.ii.pw.controller.IdentificadorCompras;
import py.pol.una.ii.pw.data.RepositorioClientes;
import py.pol.una.ii.pw.model.Clientes;
import py.pol.una.ii.pw.model.CompraCab;
import py.pol.una.ii.pw.model.CompraDet;
import py.pol.una.ii.pw.model.Productos;
import py.pol.una.ii.pw.model.Proveedores;
import py.pol.una.ii.pw.service.ControladorCompraCab;
import py.pol.una.ii.pw.service.IControladorCompras;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Path("/compras")
public class CompraCabRest {
		
	@Inject
	ProductosRest productoManager;
	
	@Inject
	RepositorioCompraCab repositorioCompraCab;
    
	//@Inject
	ControladorCompraCab controladorCompraCab;
	
	@Inject
	IdentificadorCompras identificador;

	@Inject
    private Logger log;
	
	@Inject
    private HttpSession session;
	
	
	
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<CompraCab> listAllCabeceras() throws IOException {
        return repositorioCompraCab.findAll();
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public CompraCab listAllCabeceraById(@PathParam("id") long id) throws IOException {
        return repositorioCompraCab.findById(id);
    }

    @POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response iniciarCompra(Clientes cliente,@Context HttpServletRequest request, @Context HttpServletResponse response){
		 Response.ResponseBuilder builder = null;
		 IControladorCompras compraControlador;
		 
		 Integer id = null;
		 try{
			HttpSession session= request.getSession(true);
			 compraControlador = (IControladorCompras)new InitialContext().lookup("java:global/EjbJaxRS-ear/EjbJaxRS-ejb/IControladorCompras");
			 id=identificador.getIndentificadorNuevo();
			 session.setAttribute(id.toString(), compraControlador);
			 compraControlador.iniciarCompra(cliente);
			 builder =  Response.ok(id);
			 
		 }catch (Exception e) {
			// TODO: handle exception
			log.info("No se ha registrado la compra del cliente: "+cliente.getNombre()+" error: "+e.getMessage()+ " causa "+e.getCause());
			builder = Response.notModified();
		}
		 return builder.build();
	 }


    @POST
    @Path("{idcompra}/producto/{id}")
	@Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
	public Response setProducto(@PathParam("id") long id,@PathParam("idcompra") Integer idcompra,@Context HttpServletRequest request, @Context HttpServletResponse response){
		 Response.ResponseBuilder builder = null;
		 IControladorCompras controladorCompra;
		 
		 try{
			// controladorCompraCab.agregarProducto(id);
			 HttpSession session= (HttpSession) request.getSession();
			 controladorCompra = (IControladorCompras)session.getAttribute(idcompra.toString());
			 System.out.println("---------Valor real del controlador: "+controladorCompra);
			 controladorCompra.agregarProducto(id);
			 builder =  Response.ok(idcompra);
		 }catch (Exception e) {
			// TODO: handle exception
			log.info("No se ha registrado al producto -> idproducto: "+id+" excepcion: "+e.getMessage()+ " causa: "+e.getCause());
			builder = Response.notModified();
		}
		 return builder.build();
	 }

    
    
    
    
    @POST
	@Consumes(MediaType.APPLICATION_JSON)
    @Path("/{idcompra}/confirmar")
    public Response confirmarCompra(@PathParam("idcompra") Integer idcompra,@Context HttpServletRequest request, @Context HttpServletResponse response){
		 Response.ResponseBuilder builder = null;

		 try{
			 HttpSession session= (HttpSession) request.getSession();
			 IControladorCompras controlador = (IControladorCompras)session.getAttribute(idcompra.toString());
			 controlador.confirmar();
			 builder =  Response.ok();
		 }catch (Exception e) {
			// TODO: handle exception
			log.info("No se ha confirmado la compra: "+idcompra);
			builder = Response.notModified();
		}
		 return builder.build();
	 }
    @POST
	@Consumes(MediaType.APPLICATION_JSON)
    @Path("/{idcompra}/cancelar")
    public Response cancelarCompra(@PathParam("idcompra") Integer idcompra,@Context HttpServletRequest request, @Context HttpServletResponse response){
		 Response.ResponseBuilder builder = null;
		 HttpSession session;
		 session = request.getSession();
		 try{
			 IControladorCompras controlador = (IControladorCompras)session.getAttribute(idcompra.toString());
			 controlador.cancelar();
			 builder =  Response.ok();
		 }catch (Exception e) {
			// TODO: handle exception
			log.info("No se ha cancelado la compra: "+idcompra);
			builder = Response.notModified();
		}
		 return builder.build();
	 }
    
}