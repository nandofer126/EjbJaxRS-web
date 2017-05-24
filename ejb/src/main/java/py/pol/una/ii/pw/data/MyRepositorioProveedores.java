package py.pol.una.ii.pw.data;

import java.util.List;
import java.util.logging.Logger;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;

import py.pol.una.ii.pw.model.Clientes;
import py.pol.una.ii.pw.model.Proveedores;

public class MyRepositorioProveedores {
	@Inject
	SessionesMyBatis sesiones;
	
	@Inject
	Logger log;
	
	public Proveedores findById(Long proveedorId) {
        SqlSession session=sesiones.getSession();
        Proveedores proveedor=null;
        try{
        	proveedor=session.selectOne("mybatis.ProveedorMapper.getProveedorById",proveedorId);
        	log.info("Se ha obtenido el proveedor; Id: "+proveedor.getId()+", nombre: "+proveedor.getNombre());
        	
        }catch (Exception e) {
			// TODO: handle exception
        	log.severe("Se ha producido un error al obtener el proveedor; Id: "+proveedorId+", error: "+e.getMessage()+ ", causa: "+e.getCause());
		}
        return proveedor;
    }
	public List<Proveedores> findAllOrderedByName(){
		SqlSession session=sesiones.getSession();
		List<Proveedores> listaProveedores=null;
		try{
        	listaProveedores=session.selectList("mybatis.ProveedorMapper.getAllProveedores");
        	log.info("Se ha obtenido la lista de Proveedores...");
        	
        }catch (Exception e) {
			// TODO: handle exception
        	log.severe("Se ha producido un error al obtener la lista de proveedores:  Error: "+e.getMessage()+ ", causa: "+e.getCause());
		}
		return listaProveedores;
	}
}
