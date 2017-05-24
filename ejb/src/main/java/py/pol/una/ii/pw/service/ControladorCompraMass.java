package py.pol.una.ii.pw.service;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import py.pol.una.ii.pw.data.RepositorioClientes;
import py.pol.una.ii.pw.data.RepositorioCompraCab;
import py.pol.una.ii.pw.data.RepositorioProductos;
import py.pol.una.ii.pw.model.Clientes;
import py.pol.una.ii.pw.model.CompraCab;
import py.pol.una.ii.pw.model.Productos;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ControladorCompraMass {
	
	@PersistenceContext
    private EntityManager em;
	
	@Inject
	RepositorioClientes repositorioClientes;
	
	@Inject
	RepositorioCompraCab repositorioCompras;
	
	@Inject
	RepositorioProductos repositorioProductos;
	
	@Inject
    private Logger log;
	
	public void guardarCompras(String nombreArchivo) throws Exception{
		CompraCab compra=null;
		File file = new File(nombreArchivo);
		FileReader filewriter = new FileReader(file);
		//SE define como tamaño del buffer 1024 bytes para que solo cargue en memoria esa cantidad
		BufferedReader reader = new BufferedReader(filewriter,1024);
		String linea;
		while((linea=reader.readLine())!=null){
			compra = obtenerCompra(leerCliente(linea), leerProductos(linea));
			em.persist(compra);
			log.info("Se ha guardado la compra: "+compra.getId()+" con un total: "+compra.getTotal());	
		}
		log.info("Se han guadado todas la compras...");
	}
	public Clientes leerCliente(String linea){
		
		int posicionDivisor = linea.indexOf("-");
		System.out.println(posicionDivisor);
		String caracterId = linea.substring(0, posicionDivisor);
		System.out.println(caracterId);
		long id = Long.parseLong(caracterId);
		System.out.println(""+id);
		return repositorioClientes.findById(id);
	}
	public List<Productos> leerProductos(String linea){
		Productos producto;
		int posicionCliente = linea.indexOf("-");
		String listaProductosID = linea.substring(posicionCliente+1,linea.length());
		List<Productos> listaProductos = new LinkedList<Productos>();
		while(listaProductosID.contains("-")){
			int posicion = listaProductosID.indexOf("-");
			String caracterID = listaProductosID.substring(0,posicion);
			producto = repositorioProductos.findById(Long.parseLong(caracterID.trim()));
			listaProductos.add(producto);
			listaProductosID = listaProductosID.substring(posicion+1,listaProductosID.length());
		}
		producto = repositorioProductos.findById(Long.parseLong(listaProductosID.trim()));
		listaProductos.add(producto);
		return listaProductos;
	}
	public CompraCab obtenerCompra(Clientes cliente, List<Productos> listaProductos){
		CompraCab compra = new CompraCab();
		compra.setCliente(cliente);
		for(Productos producto:listaProductos){
			compra.setProducto(producto);
			compra.setTotal(compra.getTotal()+producto.getPrecio());
		}
		return compra;
	}

}
