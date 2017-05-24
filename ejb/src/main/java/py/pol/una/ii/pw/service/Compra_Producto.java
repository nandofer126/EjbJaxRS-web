package py.pol.una.ii.pw.service;
/* 
 * Axiliar para la relacion ManyToMany de Compras con Productos
 * Se utiliza para almacenar una compra con un producto
 * */
public class Compra_Producto {
	private long idcompra;
	private long idproducto;
	public long getIdcompra() {
		return idcompra;
	}
	public void setIdcompra(long idcompra) {
		this.idcompra = idcompra;
	}
	public long getIdproducto() {
		return idproducto;
	}
	public void setIdproducto(long idproducto) {
		this.idproducto = idproducto;
	}
}
