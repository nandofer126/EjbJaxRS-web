package py.pol.una.ii.pw.service;

import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import py.pol.una.ii.pw.data.RepositorioCompraDet;
import py.pol.una.ii.pw.model.CompraDet;

public class ControladorCompraDet {
	
	@PersistenceContext
    private EntityManager em;
	
	@Inject
	RepositorioCompraDet repositorio;
	
	@Inject
    private Logger log;
	
	@Resource
	private SessionContext session;
	
	public void register(CompraDet compra) throws Exception {
    	log.info("Registering " + compra.getId());
        em.persist(compra);
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void eliminar(CompraDet compra) throws Exception {
		CompraDet c = null;
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
	    	session.setRollbackOnly();
		}
    }

}
