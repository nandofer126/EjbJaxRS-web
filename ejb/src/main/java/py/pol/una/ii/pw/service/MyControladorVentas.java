package py.pol.una.ii.pw.service;

import java.util.List;
import java.util.logging.Logger;

import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;

import py.pol.una.ii.pw.data.RepositorioVenta;
import py.pol.una.ii.pw.data.RepositorioProductos;
import py.pol.una.ii.pw.data.SessionesMyBatis;
import py.pol.una.ii.pw.model.Clientes;
import py.pol.una.ii.pw.model.Venta;
import py.pol.una.ii.pw.model.Productos;

@Stateful(name="IMyControladorVentas")
@TransactionManagement(TransactionManagementType.BEAN)
public class MyControladorVentas implements IMyControladorVentas{
	
	@Inject
	SessionesMyBatis sesiones;
	
	@Inject
	RepositorioVenta repositorio;
	
	@Inject
	RepositorioProductos repositorioProductos;
	
	
	@Inject
    private Logger log;
	   
    @Inject
    private Venta venta;
	
    @Override
    public void iniciarVenta(Clientes cliente) throws Exception{
		
    	log.info("Registering compra cliente: "+cliente.getNombre());
		this.venta = new Venta();
		this.venta.setCliente(cliente);
    }
    
    @Override
	public void agregarProducto(long idProducto, long cantidad) throws Exception {
    	
    	SqlSession session = sesiones.getSession();
	    Productos p=repositorioProductos.findById(idProducto);
	    try{
		    this.venta.setProducto(p);
		    this.venta.setTotal(this.venta.getTotal()+(p.getPrecio()*(int)cantidad));
		    p.setStock(p.getStock()+(int)cantidad);
		    session.selectOne("mybatis.ProductoMapper.updateProducto", p);
		    log.info("Registrando producto: "+ p.getNombre()+ " para la venta: "+venta.getId());
	    }catch (Exception e) {
			// TODO: handle exception
			log.info("NO se Registro el producto: "+ p.getNombre()+ " para la compra: "+venta.getId());
		}finally{
	    	session.close();
	    }
    }
    
    @Override
	public void eliminarProducto(Productos p) throws Exception {
    	
    	List<Productos> productos = this.venta.getProductos();
    	productos.remove(p);
		this.venta.setProductos(productos);
	}
    
	@Remove
	@Override
	public void confirmar() throws Exception {
		Venta_Producto ventaProducto= new Venta_Producto();
		List <Productos> listaProductos = this.venta.getProductos();
		long id;
		SqlSession session = sesiones.getSession();
	    try{
	    	session.insert("mybatis.VentaMapper.createVenta",venta);
	    	session.commit(true);
	    	log.info(" Se confirmo la venta: "+venta.getId());
	    	id=session.selectOne("mybatis.VentaMapper.getIdVenta");
	    	id-=1; //disminuimos en uno para obtener el ultimo usado en la sequencia
	    	log.info("ID de la venta obtenido de currval('idventa')-1 "+id);
	    	for(Productos p:listaProductos){
	    		ventaProducto.setIdventa(id);
	    		ventaProducto.setIdproducto(p.getId());
				session.insert("mybatis.VentaMapper.createVentaProducto",ventaProducto);
				log.info("Se ha guardado la venta-producto: venta "+ventaProducto.getIdcompra()+" producto: "+ventaProducto.getIdproducto()+"con MyBatis");
	    	}
	    }catch (Exception e) {
			// TODO: handle exception
	    	log.info(" NO se confirmo la venta: "+venta.getId()+" error: "+e.getMessage());
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
	public void eliminar(Venta v) throws Exception {
		Venta c = null;
	    try{
	    	c = repositorio.findById(venta.getId());
	    	
	    }catch (Exception e) {
			// TODO: handle exception
	    	log.info("No se ha podido eliminar la compra: "+c.getId()+" error: "+e.getMessage());
		}
    }



}
