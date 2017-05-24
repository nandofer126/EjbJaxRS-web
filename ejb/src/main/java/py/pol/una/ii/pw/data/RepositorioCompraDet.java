package py.pol.una.ii.pw.data;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import py.pol.una.ii.pw.model.CompraDet;

public class RepositorioCompraDet {
	@Inject
    private EntityManager em;

    public CompraDet findById(Long id) {
        return em.find(CompraDet.class, id);
    }
    
    public List<CompraDet> findAllOrderedByProducto() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<CompraDet> criteria = cb.createQuery(CompraDet.class);
        Root<CompraDet> compradet = criteria.from(CompraDet.class);
        // Swap criteria statements if you would like to try out type-safe criteria queries, a new
        // feature in JPA 2.0
        // criteria.select(member).orderBy(cb.asc(member.get(Member_.name)));
        criteria.select(compradet).orderBy(cb.asc(compradet.get("prducto")));
        return em.createQuery(criteria).getResultList();
    }

}
