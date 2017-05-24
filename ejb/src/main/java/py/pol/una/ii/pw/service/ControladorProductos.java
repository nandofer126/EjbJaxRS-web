package py.pol.una.ii.pw.service;

import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import py.pol.una.ii.pw.data.RepositorioClientes;
import py.pol.una.ii.pw.data.RepositorioProductos;
import py.pol.una.ii.pw.model.Clientes;
import py.pol.una.ii.pw.model.Productos;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ControladorProductos {

	@PersistenceContext
    private EntityManager em;
	
	@Inject
	RepositorioProductos repositorio;
	
	@Inject
    private Logger log;
	
	@Resource
	private SessionContext session;
	
	public void register(Productos producto) throws Exception {
       
		log.info("Registering " + producto.getNombre());
        em.persist(producto);
		
    }
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void eliminar(Productos producto) throws Exception {
		Productos p = null;
	    try{
	    	p = repositorio.findById(producto.getId());
	    	if(em.contains(p)){
	    		em.remove(p);
	    	}else{
	    		p=em.merge(p);
	    		em.remove(p);
	    	}
	    }catch (Exception e) {
			// TODO: handle exception
	    	log.info("No se ha podido eliminar el cliente: "+p.getNombre()+" error: "+e.getMessage());
	    	session.setRollbackOnly();
		}
    }
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void actualizar(Productos producto) throws Exception {
		Productos p = null;
		try{
			p = repositorio.findById(producto.getId());
			if(em.contains(p)){
				p.setNombre(producto.getNombre());
			}else{
				p=em.merge(p);
				p.setNombre(producto.getNombre());
			}
		}catch (Exception e) {
			// TODO: handle exception
			log.info("No se ha podido modifiicar el cliente: "+p.getNombre()+" error: "+e.getMessage());
	    	session.setRollbackOnly();
		}
	}
}
