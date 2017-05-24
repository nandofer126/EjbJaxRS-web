package py.pol.una.ii.pw.service;

import javax.ejb.Local;

@Local
public interface IControladorComprasMass {
	
	public boolean iniciarGuardado() throws  Exception;

}
