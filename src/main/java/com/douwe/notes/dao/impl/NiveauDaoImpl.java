package com.douwe.notes.dao.impl;

import com.douwe.generic.dao.DataAccessException;
import com.douwe.generic.dao.impl.GenericDao;
import com.douwe.notes.dao.INiveauDao;
import com.douwe.notes.entities.Cycle;
import com.douwe.notes.entities.Niveau;
import com.douwe.notes.entities.Niveau_;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

/**
 *
 * @author Vincent Douwe <douwevincent@yahoo.fr>
 */
public class NiveauDaoImpl extends GenericDao<Niveau, Long> implements INiveauDao {

    @Override
    public Niveau findByCode(String code) throws DataAccessException {
        return (Niveau) (getManager().createNamedQuery("Niveau.findByCode").setParameter("param", code).getSingleResult());
    }

    @Override
    public Niveau findNiveauTerminalParCycle(Cycle cycle) throws DataAccessException {
        CriteriaBuilder cb = getManager().getCriteriaBuilder();
        CriteriaQuery<Niveau> cq = cb.createQuery(Niveau.class);
        Root<Niveau> niveauRoot = cq.from(Niveau.class);
        Path<Cycle> pathCycle = niveauRoot.get(Niveau_.cycle);
        cq.where(cb.and(cb.equal(pathCycle, cycle), cb.equal(niveauRoot.get(Niveau_.active),true),
                cb.equal(niveauRoot.get(Niveau_.terminal), true)));
        return getManager().createQuery(cq).getSingleResult();
    }

}
