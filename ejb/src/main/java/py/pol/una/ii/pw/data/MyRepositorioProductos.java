package py.pol.una.ii.pw.data;

import java.util.List;
import java.util.logging.Logger;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;

import py.pol.una.ii.pw.model.Productos;
import py.pol.una.ii.pw.model.Proveedores;

public class MyRepositorioProductos {
	
	@Inject
	SessionesMyBatis sesiones;
	
	@Inject
	Logger log;
	
	public Productos findById(Long productoId) {
        SqlSession session=sesiones.getSession();
        Productos producto=null;
        try{
        	producto=session.selectOne("mybatis.ProductoMapper.getProductoById",productoId);
        	log.info("Se ha obtenido el producto; Id: "+producto.getId()+", nombre: "+producto.getNombre());
        	
        }catch (Exception e) {
			// TODO: handle exception
        	log.severe("Se ha producido un error al obtener el proveedor; Id: "+productoId+", error: "+e.getMessage()+ ", causa: "+e.getCause());
		}
        return producto;
    }
	public List<Productos> findAllOrderedByName(){
		SqlSession session=sesiones.getSession();
		List<Productos> listaProductos=null;
		try{
        	listaProductos=session.selectList("mybatis.ProductoMapper.getAllProductos");
        	log.info("Se ha obtenido la lista de Productos...");
        	
        }catch (Exception e) {
			// TODO: handle exception
        	log.severe("Se ha producido un error al obtener la lista de productos:  Error: "+e.getMessage()+ ", causa: "+e.getCause());
		}
		return listaProductos;
	}
}
