package py.pol.una.ii.pw.service;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;



import javax.transaction.UserTransaction;
import javax.ejb.Stateful;
import javax.ejb.Remove;
import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import py.pol.una.ii.pw.data.RepositorioCompraCab;
import py.pol.una.ii.pw.data.RepositorioProductos;
import py.pol.una.ii.pw.model.Clientes;
import py.pol.una.ii.pw.model.CompraCab;
import py.pol.una.ii.pw.model.Productos;


@Stateful(name="IControladorCompras")
@TransactionManagement(TransactionManagementType.BEAN)
public class ControladorCompraCab implements IControladorCompras{
	
	@PersistenceContext
    private EntityManager em;
	
	@Inject
	RepositorioCompraCab repositorio;
	
	@Inject
	RepositorioProductos repositorioProductos;
	
	
	@Inject
    private Logger log;
	
    @Resource
    private UserTransaction userTransaction;
	
    private LinkedList productos;
    private Clientes cliente;
    @Inject
    private CompraCab compra;
	
    @Override
    public void iniciarCompra(Clientes cliente) throws Exception{
		userTransaction.begin();
		log.info("Registering compra cliente: "+cliente.getNombre());
		this.compra = new CompraCab();
		this.compra.setCliente(cliente);
        em.persist(compra);
	}
    
    @Override
	public void agregarProducto(long idProducto) throws Exception {
		
		Productos p=repositorioProductos.findById(idProducto);
		log.info("Registrando producto: "+ p.getNombre()+ " para la compra: "+compra.getId());
		this.compra.setProducto(p);
		this.compra.setTotal(this.compra.getTotal()+p.getPrecio());
        em.merge(compra);
	}
    @Override
	public void eliminarProducto(Productos p) throws Exception {
		productos.remove(p);
		
        //em.persist(compra);
	}
	@Remove
	@Override
	public void confirmar() throws Exception {

	    try{
			//compra.setProductos(productos);
			/*int total=0;
			for(int i=0;i<productos.size();i++){
				total= total + ((Productos)productos.get(i)).getPrecio();
			}
			
			compra.setTotal(total);*/
	    	em.merge(compra);
	    	em.persist(compra);
	    	log.info(" Se confirmo la compra: "+compra.getId());
	    	userTransaction.commit();
	    }catch (Exception e) {
			// TODO: handle exception
	    	log.info(" NO se confirmo la compra: "+compra.getId()+" error: "+e.getMessage());
	    }
	}
	
	@Remove
	@Override
	public void cancelar() throws Exception {
		
	    try{
	    	userTransaction.rollback();;
	    }catch (Exception e) {
			// TODO: handle exception
	    	log.info(" Se cancelo la compra, error: "+e.getMessage());
	    }
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void eliminar(CompraCab compra) throws Exception {
		CompraCab c = null;
	    try{
	    	c = repositorio.findById(compra.getId());
	    	if(em.contains(c)){
	    		em.remove(c);
	    	}else{
	    		c=em.merge(c);
	    		em.remove(c);
	    	}
	    }catch (Exception e) {
			// TODO: handle exception
	    	log.info("No se ha podido eliminar la compra: "+c.getId()+" error: "+e.getMessage());
	    	//session.setRollbackOnly();
		}
    }

}
