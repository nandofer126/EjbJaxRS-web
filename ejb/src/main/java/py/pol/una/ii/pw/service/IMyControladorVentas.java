package py.pol.una.ii.pw.service;

import javax.ejb.Local;

import py.pol.una.ii.pw.model.Clientes;
import py.pol.una.ii.pw.model.Productos;

@Local
public interface IMyControladorVentas {
	public void iniciarVenta(Clientes cliente) throws Exception;
	public void agregarProducto(long idProducto, long cantidad) throws Exception;
	public void eliminarProducto(Productos p) throws Exception;
	public void confirmar() throws Exception;
	public void cancelar() throws Exception;
}
