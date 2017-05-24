package py.pol.una.ii.pw.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import java.io.Serializable;

@Entity
@Table(name = "Clientes")
public class Clientes implements Serializable {
	
	private static final long serialVersionUID = 1L;
	@NotNull
	@Size(min = 1, max = 25)
	@Pattern(regexp = "[^0-9]*", message = "El nombre o debe contener numeros")
	private String nombre;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	public Clientes(){}
	///setters y getters
	public void setNombre(String n){
		this.nombre=n;
	}
	public String getNombre(){
		return this.nombre;
	}
	public long getId(){
		return this.id;
	}
}
