package py.pol.una.ii.pw.service;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

import org.apache.ibatis.session.SqlSession;

import py.pol.una.ii.pw.data.RepositorioCompraCab;
import py.pol.una.ii.pw.data.RepositorioProductos;
import py.pol.una.ii.pw.data.SessionesMyBatis;
import py.pol.una.ii.pw.model.Clientes;
import py.pol.una.ii.pw.model.CompraCab;
import py.pol.una.ii.pw.model.Productos;

@Stateful(name="IMyControladorCompras")
@TransactionManagement(TransactionManagementType.BEAN)
public class MyControladorCompras implements IMyControladorCompras{
	
	@Inject
	SessionesMyBatis sesiones;
	
	@Inject
	RepositorioCompraCab repositorio;
	
	@Inject
	RepositorioProductos repositorioProductos;
	
	
	@Inject
    private Logger log;
	   
    @Inject
    private CompraCab compra;
	
    @Override
    public void iniciarCompra(Clientes cliente) throws Exception{
		
    	log.info("Registering compra cliente: "+cliente.getNombre());
		this.compra = new CompraCab();
		this.compra.setCliente(cliente);
    }
    
    @Override
	public void agregarProducto(long idProducto, long cantidad) throws Exception {
    	
    	SqlSession session = sesiones.getSession();
	    Productos p=repositorioProductos.findById(idProducto);
	    try{
		    if (p.getStock() >= cantidad){
		    	this.compra.setProducto(p);
		    	this.compra.setTotal(this.compra.getTotal()+(p.getPrecio()*(int)cantidad));
		    	p.setStock(p.getStock()-(int)cantidad);
		    	session.selectOne("mybatis.ProductoMapper.updateProducto", p);
			    log.info("Registrando producto: "+ p.getNombre()+ " para la compra: "+compra.getId());
		    }else{
		    	log.info("Stock agotado");
		    }
		}catch (Exception e) {
			// TODO: handle exception
			log.info("NO se Registro el producto: "+ p.getNombre()+ " para la compra: "+compra.getId());
		}finally{
	    	session.close();
	    }
    }
    
    @Override
	public void eliminarProducto(Productos p) throws Exception {
    	
    	List<Productos> productos = this.compra.getProductos();
    	productos.remove(p);
		this.compra.setProductos(productos);
	}
    
	@Remove
	@Override
	public void confirmar() throws Exception {
		Compra_Producto compraProducto= new Compra_Producto();
		List <Productos> listaProductos = this.compra.getProductos();
		long id;
		SqlSession session = sesiones.getSession();
	    try{
	    	session.insert("mybatis.CompraCabMapper.createCompraCab",compra);
	    	log.info(" Se confirmo la compra: "+compra.getId());
	    	id=session.selectOne("mybatis.CompraCabMapper.getIdCompra");
	    	id-=1; //disminuimos en uno para obtener el ultimo usado en la sequencia
	    	log.info("ID de la compra obtenido de currval('idcompra')-1 "+id);
	    	for(Productos p:listaProductos){
				compraProducto.setIdcompra(id);
				compraProducto.setIdproducto(p.getId());
				session.insert("mybatis.CompraCabMapper.createCompraProducto",compraProducto);
				log.info("Se ha guardado la compra-producto: compra "+compraProducto.getIdcompra()+" producto: "+compraProducto.getIdproducto()+"con MyBatis");
	    	}
	    }catch (Exception e) {
			// TODO: handle exception
	    	log.info(" NO se confirmo la compra: "+compra.getId()+" error: "+e.getMessage());
	    }finally{
    		session.close();
    	}
	}
	
	@Remove
	@Override
	public void cancelar() throws Exception {
		
	    try{
	    	log.info(" Se cancelo la compra ");
	    }catch (Exception e) {
			// TODO: handle exception
	    	log.info(" NO Se cancelo la compra, error: "+e.getMessage());
	    }
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void eliminar(CompraCab compra) throws Exception {
		CompraCab c = null;
	    try{
	    	c = repositorio.findById(compra.getId());
	    	
	    }catch (Exception e) {
			// TODO: handle exception
	    	log.info("No se ha podido eliminar la compra: "+c.getId()+" error: "+e.getMessage());
		}
    }


}
