package py.pol.una.ii.pw.data;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import py.pol.una.ii.pw.model.CompraCab;

public class RepositorioCompraCab {
	@Inject
    private EntityManager em;

    public CompraCab findById(Long id) {
        return em.find(CompraCab.class, id);
    }
    
    public List<CompraCab> findAllOrderedByFecha() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<CompraCab> criteria = cb.createQuery(CompraCab.class);
        Root<CompraCab> compracab = criteria.from(CompraCab.class);
        // Swap criteria statements if you would like to try out type-safe criteria queries, a new
        // feature in JPA 2.0
        // criteria.select(member).orderBy(cb.asc(member.get(Member_.name)));
        criteria.select(compracab).orderBy(cb.asc(compracab.get("fecha")));
        return em.createQuery(criteria).getResultList();
    }
    public List<CompraCab> findAll() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<CompraCab> criteria = cb.createQuery(CompraCab.class);
        Root<CompraCab> compracab = criteria.from(CompraCab.class);
        // Swap criteria statements if you would like to try out type-safe criteria queries, a new
        // feature in JPA 2.0
        // criteria.select(member).orderBy(cb.asc(member.get(Member_.name)));
        criteria.select(compracab).orderBy(cb.asc(compracab.get("id")));
        return em.createQuery(criteria).getResultList();
    }

}
