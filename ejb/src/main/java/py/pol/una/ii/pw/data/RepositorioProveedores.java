package py.pol.una.ii.pw.data;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import py.pol.una.ii.pw.model.Proveedores;

@ApplicationScoped
public class RepositorioProveedores {
	@Inject
    private EntityManager em;

    public Proveedores findById(Long id) {
        return em.find(Proveedores.class, id);
    }
    
    public List<Proveedores> findAllOrderedByName() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Proveedores> criteria = cb.createQuery(Proveedores.class);
        Root<Proveedores> proveedores = criteria.from(Proveedores.class);
        // Swap criteria statements if you would like to try out type-safe criteria queries, a new
        // feature in JPA 2.0
        // criteria.select(member).orderBy(cb.asc(member.get(Member_.name)));
        criteria.select(proveedores).orderBy(cb.asc(proveedores.get("nombre")));
        return em.createQuery(criteria).getResultList();
    }

}
