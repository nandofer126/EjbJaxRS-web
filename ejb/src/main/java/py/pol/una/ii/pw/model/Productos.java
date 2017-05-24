package py.pol.una.ii.pw.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Table(name = "Productos")
public class Productos implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@NotNull
	@Size(min = 1, max = 30)
	@Pattern(regexp = "[^0-9]*", message = "No es una descripcion valida")
	private String nombre;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	@NotNull
    @Size(min = 1, max = 50)
    private String detalle;

    @NotNull
    private int precio;
    
    @NotNull
    private int stock;

	@ManyToOne
    private Proveedores proveedor;
	
    public Productos(){}
	
	public Productos(String n){
		this.nombre=n;
	}
	///setters y getters
	public void setNombre(String n){
		this.nombre=n;
	}
	public String getNombre(){
		return this.nombre;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getId(){
		return this.id;
	}
	public void setDetalle(String d){
		this.detalle=d;
	}
	public String getDetalle(){
		return this.detalle;
	}
	public void setPrecio(int p){
		this.precio=p;
	}
	public int getPrecio(){
		return this.precio;
	}
	public void setProveedor(Proveedores pr){
		this.proveedor=pr;
	}
	public Proveedores getProveedor() {
		return proveedor;
	}
	public void setStock(int s){
		this.stock=s;
	}
	public int getStock(){
		return this.stock;
	}
	
}
