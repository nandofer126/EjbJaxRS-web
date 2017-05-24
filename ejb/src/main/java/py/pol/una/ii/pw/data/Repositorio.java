package py.pol.una.ii.pw.data;

import java.util.LinkedList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import py.pol.una.ii.pw.model.Clientes;
import py.pol.una.ii.pw.model.Member;
import py.pol.una.ii.pw.model.Productos;
import py.pol.una.ii.pw.model.Proveedores;
@ApplicationScoped
public class Repositorio {
	//lista de datos precargados
	static String [] nombresClientes = {"a","b","c","d","f","g","h"};
	String [] nombresProductos = {"pa","pb","pc","pd","pf","pg","ph"};
	String [] nombresProveedores = {"pra","prb","prc","prd","prf","prg","prh"};
	//lista de las entidades
	
	
	//static List lClientes=generarClientesIniciales();
	List lClientes;
	List lProductos=generarProdutosIniciales();
	List lProveedores= generarProveedoresIniciales();
	
	
	public Repositorio(){
		this.lClientes = generarClientesIniciales();
		this.lProductos = generarProdutosIniciales();
		this.lProveedores = generarProveedoresIniciales();
	}
	public  List getClientes(){
		return lClientes;
	}
	/////cargar datos iniciales en los atributos
	public List generarClientesIniciales(){
		List clientes = new LinkedList();
		for(int i=0;i<nombresClientes.length;i++){
			Clientes c = new Clientes();
			c.setNombre(nombresClientes[i]);
			clientes.add(c);
		}
		lClientes=clientes;
		return lClientes;
	}
	public List generarProdutosIniciales(){
		List p = new LinkedList();
		for(int i=0;i<nombresProductos.length;i++){
			Productos prd = new Productos(nombresProductos[i]);
			p.add(prd);
		}
		this.lProductos=p;
		return p;
	}
	
	public List generarProveedoresIniciales(){
		List pr = new LinkedList();
		for(int i=0;i<nombresProveedores.length;i++){
			Proveedores proveedores = new Proveedores();
			proveedores.setNombre(nombresProveedores[i]);
			pr.add(proveedores);
		}
		this.lProveedores=pr;
		return pr;
	}
	/////setters y getters mas metodos adicionales
	public List<Clientes> getlistaClientes(){
		if(this.lClientes!=null){
			return lClientes;
		}
		this.lClientes=generarClientesIniciales();
		return lClientes;
	}
	public List<Productos> getlistaProductos(){
		if(this.lProductos!=null){
			return lProductos;
		}
		this.lProductos=generarProdutosIniciales();
		return lProductos;
	}
	public List getlistaProveedores(){
		if(this.lProveedores!=null){
			return lProveedores;
		}
		this.lProveedores=generarProveedoresIniciales();
		return lProveedores;
	}
	public Clientes getCliente(int i){
		return (Clientes)this.lClientes.get(i);
	}
	public Clientes getClienteId(long id){
		Clientes c= null;
		for(int i=0;i<this.lClientes.size();i++){
			c =(Clientes)this.lClientes.get(i);
			if(c.getId()==id)
				return c;
		}
		return c;
		
	}
	public Productos getProductosId(long id){
		Productos p= null;
		for(int i=0;i<this.lProductos.size();i++){
			p =(Productos)this.lProductos.get(i);
			if(p.getId()==id)
				return p;
		}
		return p;
		
	}
	public Proveedores getProveedoresId(long id){
		Proveedores pr= null;
		for(int i=0;i<this.lProveedores.size();i++){
			pr =(Proveedores)this.lProveedores.get(i);
			if(pr.getId()==id)
				return pr;
		}
		return pr;
		
	}
	public Productos getProducto(int i){
		return (Productos)this.lProductos.get(i);
	}
	public Proveedores getProveedores(int i){
		return (Proveedores)this.lProductos.get(i);
	}
	
	public void setCliente(Clientes c){
		lClientes.add(c);
	}
	public void setProducto(Productos p){
		this.lProductos.add(p);
	}
	public void setProveedores(Proveedores p){
		this.lProveedores.add(p);
	}
	public  void eliminarCliente(long id){
		for(int i=0;i<lClientes.size();i++){
			if(((Clientes)lClientes.get(i)).getId()==id){
				lClientes.remove(i);
				break;
			}
				
		}
	}
	public  void eliminarProducto(long id){
		for(int i=0;i<lProductos.size();i++){
			if(((Productos)lProductos.get(i)).getId()==id){
				lProductos.remove(i);
				break;
			}
		}
	}
	public  void eliminarProveedores(long id){
		for(int i=0;i<lProveedores.size();i++){
			if(((Proveedores)lProveedores.get(i)).getId()==id){
				lProveedores.remove(i);
				break;
			}
		}
	}
	public  void actualizarProveedores(long id){
		for(int i=0;i<lProveedores.size();i++){
			if(((Proveedores)lProveedores.get(i)).getId()==id){
				lProveedores.remove(i);
				break;
			}
		}
	}
}
