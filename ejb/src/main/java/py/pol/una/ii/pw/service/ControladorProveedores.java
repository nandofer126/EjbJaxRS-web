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
import py.pol.una.ii.pw.data.RepositorioProveedores;
import py.pol.una.ii.pw.model.Clientes;
import py.pol.una.ii.pw.model.Proveedores;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ControladorProveedores {

	@PersistenceContext
    private EntityManager em;
	
	@Inject
	RepositorioProveedores repositorio;
	
	@Inject
    private Logger log;
	
	@Resource
	private SessionContext session;
	
	public void register(Proveedores proveedor) throws Exception {
       
		log.info("Registering " + proveedor.getNombre());
        em.persist(proveedor);
		
    }
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void eliminar(Proveedores proveedor) throws Exception {
		Proveedores pr = null;
	    try{
	    	pr = repositorio.findById(proveedor.getId());
	    	if(em.contains(pr)){
	    		em.remove(pr);
	    	}else{
	    		pr=em.merge(pr);
	    		em.remove(pr);
	    	}
	    }catch (Exception e) {
			// TODO: handle exception
	    	log.info("No se ha podido eliminar el cliente: "+pr.getNombre()+" error: "+e.getMessage());
	    	session.setRollbackOnly();
		}
    }
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void actualizar(Proveedores proveedor) throws Exception {
		Proveedores pr = null;
		try{
			pr = repositorio.findById(proveedor.getId());
			if(em.contains(pr)){
				pr.setNombre(proveedor.getNombre());
			}else{
				pr=em.merge(pr);
				pr.setNombre(proveedor.getNombre());
			}
		}catch (Exception e) {
			// TODO: handle exception
			log.info("No se ha podido modifiicar el cliente: "+pr.getNombre()+" error: "+e.getMessage());
	    	session.setRollbackOnly();
		}
	}

}
