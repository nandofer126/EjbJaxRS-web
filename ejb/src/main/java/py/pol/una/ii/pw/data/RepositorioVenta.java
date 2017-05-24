package py.pol.una.ii.pw.data;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import py.pol.una.ii.pw.model.Venta;

public class RepositorioVenta {
	
	@Inject
    private EntityManager em;

    public Venta findById(Long id) {
        return em.find(Venta.class, id);
    }
    
    public List<Venta> findAllOrderedByFecha() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Venta> criteria = cb.createQuery(Venta.class);
        Root<Venta> compracab = criteria.from(Venta.class);
        // Swap criteria statements if you would like to try out type-safe criteria queries, a new
        // feature in JPA 2.0
        // criteria.select(member).orderBy(cb.asc(member.get(Member_.name)));
        criteria.select(compracab).orderBy(cb.asc(compracab.get("fecha")));
        return em.createQuery(criteria).getResultList();
    }
    public List<Venta> findAll() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Venta> criteria = cb.createQuery(Venta.class);
        Root<Venta> venta = criteria.from(Venta.class);
        // Swap criteria statements if you would like to try out type-safe criteria queries, a new
        // feature in JPA 2.0
        // criteria.select(member).orderBy(cb.asc(member.get(Member_.name)));
        criteria.select(venta).orderBy(cb.asc(venta.get("id")));
        return em.createQuery(criteria).getResultList();
    }


}
