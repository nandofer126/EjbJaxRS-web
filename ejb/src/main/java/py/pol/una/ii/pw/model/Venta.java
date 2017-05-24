package py.pol.una.ii.pw.model;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "Ventas")
public class Venta {

	/** Default value included to remove warning. Remove or modify at will. **/
    private static final long serialVersionUID = 1L;

    @Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
  
    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "id")
    Clientes cliente;
    
    @ManyToMany(fetch = FetchType.EAGER,cascade=CascadeType.ALL)
	private List<Productos> productos = new LinkedList<Productos>();
    
    @NotNull
    private int total;
    
    public Clientes getCliente() {
		return cliente;
	}
	public void setCliente(Clientes cliente) {
		this.cliente = cliente;
	}
    public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public List getProductos() {
		return productos;
	}

	public void setProductos(List productos) {
		this.productos = productos;
	}

	public void setId(long id) {
		this.id = id;
	}
	public void setProducto(Productos p){
		this.productos.add(p);
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

}
