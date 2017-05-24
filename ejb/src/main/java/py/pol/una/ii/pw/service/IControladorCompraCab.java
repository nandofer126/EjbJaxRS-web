package py.pol.una.ii.pw.service;

import javax.ejb.Local;
import javax.ejb.Remote;

import py.pol.una.ii.pw.model.CompraCab;
import py.pol.una.ii.pw.model.Productos;

@Local
public interface IControladorCompraCab {

	public void register(CompraCab compra)throws Exception;
	
	public void confirmar()throws Exception;
	
	public void cancelar()throws Exception;
	
	public void eliminar(CompraCab compra) throws Exception;
		
	public void addProducto(Productos producto) throws Exception;
}
