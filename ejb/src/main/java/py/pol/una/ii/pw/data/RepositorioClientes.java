package py.pol.una.ii.pw.data;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import py.pol.una.ii.pw.model.Clientes;
import py.pol.una.ii.pw.model.Member;

@ApplicationScoped
public class RepositorioClientes {
	@Inject
    private EntityManager em;
	
	

    public Clientes findById(Long id) {
        return em.find(Clientes.class, id);
    }
    
    public List<Clientes> findAllOrderedByName() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Clientes> criteria = cb.createQuery(Clientes.class);
        Root<Clientes> clientes = criteria.from(Clientes.class);
        // Swap criteria statements if you would like to try out type-safe criteria queries, a new
        // feature in JPA 2.0
        // criteria.select(member).orderBy(cb.asc(member.get(Member_.name)));
        criteria.select(clientes).orderBy(cb.asc(clientes.get("nombre")));
        return em.createQuery(criteria).getResultList();
    }

}
