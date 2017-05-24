package py.pol.una.ii.pw.data;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import py.pol.una.ii.pw.model.Clientes;
import py.pol.una.ii.pw.model.Productos;

@ApplicationScoped
public class RepositorioProductos {
	@Inject
    private EntityManager em;

    public Productos findById(Long id) {
        return em.find(Productos.class, id);
    }
    
    public List<Productos> findAllOrderedByName() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Productos> criteria = cb.createQuery(Productos.class);
        Root<Productos> productos = criteria.from(Productos.class);
        // Swap criteria statements if you would like to try out type-safe criteria queries, a new
        // feature in JPA 2.0
        // criteria.select(member).orderBy(cb.asc(member.get(Member_.name)));
        criteria.select(productos).orderBy(cb.asc(productos.get("nombre")));
        return em.createQuery(criteria).getResultList();
    }
}
