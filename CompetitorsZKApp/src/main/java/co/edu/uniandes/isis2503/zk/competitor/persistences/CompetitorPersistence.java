/*
 * The MIT License
 *
 * Copyright 2016 Universidad de los Andes - Departamento de Ingeniería de Sistemas.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package co.edu.uniandes.isis2503.zk.competitor.persistences;

import co.edu.uniandes.isis2503.zk.competitor.models.entities.Competitor;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author Luis Felipe Mendivelso Osorio <lf.mendivelso10@uniandes.edu.co>
 */
public class CompetitorPersistence {

    private static CompetitorPersistence competitorPersistence = null;
    private EntityManagerFactory entityManagerFactory;

    private CompetitorPersistence() {
        this.entityManagerFactory = Persistence.createEntityManagerFactory("CompetitorsPU", System.getProperties());
    }

    public static CompetitorPersistence getPesistencer() {
        if (competitorPersistence == null) {
            competitorPersistence = new CompetitorPersistence();
        }
        return competitorPersistence;
    }

    public Competitor createCompetitor(Competitor entity) {
        EntityManager em = entityManagerFactory.createEntityManager();
        EntityTransaction tx = null;
        try {
            tx = em.getTransaction();
            tx.begin();
            em.persist(entity);
            tx.commit();
            em.refresh(entity);
        } catch (RuntimeException e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
        } finally {
            em.close();
        }
        return entity;
    }

    public Competitor updateCompetitor(Competitor entity) {
        EntityManager em = entityManagerFactory.createEntityManager();
        EntityTransaction tx = null;
        try {
            tx = em.getTransaction();
            tx.begin();
            em.merge(entity);
            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
        } finally {
            em.close();
        }
        return entity;
    }

    public void deleteCompetitor(Competitor entity) {
        EntityManager em = entityManagerFactory.createEntityManager();
        EntityTransaction tx = null;
        Query query = em.createQuery("Select e FROM Competitor e WHERE e.id = :id");
        query.setParameter("id", entity.getId());

        try {
            Competitor found = (Competitor) query.getSingleResult();
            if (found != null) {
                tx = em.getTransaction();
                tx.begin();
                em.remove(found);
                tx.commit();
            }
            else{
                throw new RuntimeException();
            }
        } catch (RuntimeException e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
        } finally {
            em.close();
        }
    }

    public Competitor getCompetitorById(Long id) {
        EntityManager em = entityManagerFactory.createEntityManager();
        Competitor competitor;
        Query query = em.createQuery("Select e FROM Competitor e WHERE e.id = :id");
        query.setParameter("id", id);
        try {
            competitor = (Competitor) query.getSingleResult();
        } catch (NoResultException | NonUniqueResultException e) {
            competitor = null;
        } finally {
            em.close();
        }
        return competitor;
    }

    public Competitor getCompetitorByName(String name) {
        EntityManager em = entityManagerFactory.createEntityManager();
        Competitor competitor;
        Query query = em.createQuery("Select c FROM Competitor c where c.name=:name");
        query.setParameter("name", name);
        try {
            competitor = (Competitor) query.getSingleResult();
        } catch (NoResultException | NonUniqueResultException ex) {
            competitor = null;
        } finally {
            em.close();
        }
        return competitor;
    }

    public List<Competitor> getCompetitors() {
        EntityManager em = entityManagerFactory.createEntityManager();
        List<Competitor> competitor;
        Query query = em.createQuery("Select c FROM Competitor c");
        try {
            competitor = query.getResultList();
        } catch (NoResultException | NonUniqueResultException ex) {
            competitor = null;
        } finally {
            em.close();
        }
        return competitor;
    }

}
