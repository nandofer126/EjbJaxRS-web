package py.pol.una.ii.pw.service;

import java.util.List;
import java.util.LinkedList;
import java.util.logging.Logger;

import javax.inject.Inject;

import py.pol.una.ii.pw.data.RepositorioClientes;
import py.pol.una.ii.pw.data.RepositorioProductos;
import py.pol.una.ii.pw.model.Clientes;
import py.pol.una.ii.pw.model.CompraCab;
import py.pol.una.ii.pw.model.Productos;

public class prueba {
	@Inject
	static ControladorCompraMass controlador;
	
	public static void main(String[] args) {
		
		
		String linea = "42-10-11-2-13-123";
		CompraCab compra = new CompraCab();
		//Clientes cliente = leerCliente(linea);
		List<Productos> listaProductos = leerProductos(linea);
		//System.out.println("EL cliente: "+cliente.getNombre());
		//System.out.println("EL producto: "+listaProductos.get(0).getNombre());
	}
public static  Clientes leerCliente(String linea){
		RepositorioClientes repositorioClientes = new RepositorioClientes();
		int posicionDivisor = linea.indexOf("-");
		String caracterId = linea.substring(0, posicionDivisor);
		System.out.println(caracterId);
		long id = Long.parseLong(caracterId);
		System.out.println(""+id);
		return repositorioClientes.findById(id);
	}
public static List<Productos> leerProductos(String linea){
		RepositorioProductos repositorioProductos = new RepositorioProductos();
		Productos producto;
		int posicionCliente = linea.indexOf("-");
		String listaProductosID = linea.substring(posicionCliente+1,linea.length());
		List<Productos> listaProductos = new LinkedList<Productos>();
		while(listaProductosID.contains("-")){
			int posicion = listaProductosID.indexOf("-");
			System.out.println(listaProductosID);
			System.out.println("posicion: "+posicion);
			String caracterID = listaProductosID.substring(0,posicion);
			System.out.println(""+caracterID);
			//producto = repositorioProductos.findById(Long.parseLong(caracterID));
			//listaProductos.add(producto);
			listaProductosID = listaProductosID.substring(posicion+1,listaProductosID.length());
		}
		System.out.println(listaProductosID.trim());
		System.out.println(Long.parseLong(listaProductosID.trim()));
		//producto = repositorioProductos.findById(Long.parseLong(listaProductosID));
		//listaProductos.add(producto);
		return listaProductos;
	}

}
