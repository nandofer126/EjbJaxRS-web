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
import py.pol.una.ii.pw.model.Clientes;
import py.pol.una.ii.pw.model.Member;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ControladorClientes {
	
	@PersistenceContext
    private EntityManager em;
	
	@Inject
	RepositorioClientes repositorio;
	
	@Inject
    private Logger log;
	
	@Resource
	private SessionContext session;
	
	public void register(Clientes cliente) throws Exception {
       
		log.info("Registering " + cliente.getNombre());
        em.persist(cliente);
		
    }
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void eliminar(Clientes cliente) throws Exception {
		Clientes c = null;
	    try{
	    	c = repositorio.findById(cliente.getId());
	    	if(em.contains(c)){
	    		em.remove(c);
	    	}else{
	    		c=em.merge(c);
	    		em.remove(c);
	    	}
	    }catch (Exception e) {
			// TODO: handle exception
	    	log.info("No se ha podido eliminar el cliente: "+c.getNombre()+" error: "+e.getMessage());
	    	session.setRollbackOnly();
		}
    }
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void actualizar(Clientes cliente) throws Exception {
		Clientes c = null;
		try{
			c = repositorio.findById(cliente.getId());
			if(em.contains(c)){
				c.setNombre(cliente.getNombre());
			}else{
				c=em.merge(c);
				c.setNombre(cliente.getNombre());
			}
		}catch (Exception e) {
			// TODO: handle exception
			log.info("No se ha podido modifiicar el cliente: "+c.getNombre()+" error: "+e.getMessage());
	    	session.setRollbackOnly();
		}
	}

}
