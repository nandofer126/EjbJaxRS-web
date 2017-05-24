package py.pol.una.ii.pw.data;

import java.util.List;
import java.util.logging.Logger;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;

import py.pol.una.ii.pw.model.Clientes;

/*
 * Clase para obtener los datos de los clientes con MyBatis
 * */
public class MyRepositorioClientes {
	
	@Inject
	SessionesMyBatis sesiones;
	
	@Inject
	Logger log;
	
	public Clientes findById(Long clienteId) {
        SqlSession session=sesiones.getSession();
        Clientes cliente=null;
        try{
        	cliente=session.selectOne("mybatis.ClienteMapper.getClienteById",clienteId);
        	log.info("Se ha obtenido el cliente; Id: "+cliente.getId()+", nombre: "+cliente.getNombre());
        	
        }catch (Exception e) {
			// TODO: handle exception
        	log.severe("Se ha producido un error al obtener el cliente; Id: "+clienteId+", error: "+e.getMessage()+ ", causa: "+e.getCause());
		}
        return cliente;
    }
	public List<Clientes> findAllOrderedByName(){
		SqlSession session=sesiones.getSession();
		List<Clientes> listaClientes=null;
		try{
        	listaClientes=session.selectList("mybatis.ClienteMapper.selectClientes");
        	log.info("Se ha obtenido la lista de clientes...");
        	
        }catch (Exception e) {
			// TODO: handle exception
        	log.severe("Se ha producido un error al obtener la lista de clientes:  Error: "+e.getMessage()+ ", causa: "+e.getCause());
		}
		return listaClientes;
	}
}
