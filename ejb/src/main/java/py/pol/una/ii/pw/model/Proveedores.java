package py.pol.una.ii.pw.model;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Table(name ="Proveedores")
public class Proveedores implements Serializable {
	
	private static final long serialVersionUID = 1L;
	@NotNull
	@Size(min = 1, max = 25)
	@Pattern(regexp = "[^0-9]*", message = "El nombre o debe contener numeros")
	private String nombre;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	public Proveedores(){}
	
	///setters y gettters 
	public String getNombre(){
		return this.nombre;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}

	public void setNombre(String n){
		this.nombre=n;
	}
	
}
