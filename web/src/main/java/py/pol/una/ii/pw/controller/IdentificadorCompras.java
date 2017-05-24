package py.pol.una.ii.pw.controller;

import java.util.Map;
import java.util.TreeMap;

import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;

@Singleton
@Lock(LockType.WRITE)
public class IdentificadorCompras {
	Integer indentificador;
	Map<Integer,Object> tablaStatefull;
	
	public IdentificadorCompras(){
		this.indentificador=new Integer(0);
		this.tablaStatefull = new TreeMap<Integer,Object>();
	}
	
	public Integer setValor(Object o){
		this.indentificador++;
		this.tablaStatefull.put(indentificador, o);
		return this.indentificador;
	}
	@Lock(LockType.READ)
	public Object getValor(Integer id){
		return this.tablaStatefull.get(id);
	}
	
	@Lock(LockType.READ)
	public Integer getIndentificador() {
		return indentificador;
	}
	
	public Integer getIndentificadorNuevo() {
		return indentificador++;
	}
	public void setIndentificador(Integer indentificador) {
		this.indentificador = indentificador;
	}
	@Lock(LockType.READ)
	public Map<Integer, Object> getTablaStatefull() {
		return tablaStatefull;
	}

	public void setTablaStatefull(Map<Integer, Object> tablaStatefull) {
		this.tablaStatefull = tablaStatefull;
	}
	
}
