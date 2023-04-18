/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gt.com.aisolis.umg.dao;

import gt.com.aisolis.umg.dao.exceptions.NonexistentEntityException;
import gt.com.aisolis.umg.dao.exceptions.PreexistingEntityException;
import gt.com.aisolis.umg.model.TbCliente;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import gt.com.aisolis.umg.model.TbVenta;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author swords
 */
public class TbClienteJpaController implements Serializable {

    public TbClienteJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TbCliente tbCliente) throws PreexistingEntityException, Exception {
        if (tbCliente.getTbVentaCollection() == null) {
            tbCliente.setTbVentaCollection(new ArrayList<TbVenta>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<TbVenta> attachedTbVentaCollection = new ArrayList<TbVenta>();
            for (TbVenta tbVentaCollectionTbVentaToAttach : tbCliente.getTbVentaCollection()) {
                tbVentaCollectionTbVentaToAttach = em.getReference(tbVentaCollectionTbVentaToAttach.getClass(), tbVentaCollectionTbVentaToAttach.getFactura());
                attachedTbVentaCollection.add(tbVentaCollectionTbVentaToAttach);
            }
            tbCliente.setTbVentaCollection(attachedTbVentaCollection);
            em.persist(tbCliente);
            for (TbVenta tbVentaCollectionTbVenta : tbCliente.getTbVentaCollection()) {
                TbCliente oldNitOfTbVentaCollectionTbVenta = tbVentaCollectionTbVenta.getNit();
                tbVentaCollectionTbVenta.setNit(tbCliente);
                tbVentaCollectionTbVenta = em.merge(tbVentaCollectionTbVenta);
                if (oldNitOfTbVentaCollectionTbVenta != null) {
                    oldNitOfTbVentaCollectionTbVenta.getTbVentaCollection().remove(tbVentaCollectionTbVenta);
                    oldNitOfTbVentaCollectionTbVenta = em.merge(oldNitOfTbVentaCollectionTbVenta);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTbCliente(tbCliente.getNit()) != null) {
                throw new PreexistingEntityException("TbCliente " + tbCliente + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TbCliente tbCliente) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TbCliente persistentTbCliente = em.find(TbCliente.class, tbCliente.getNit());
            Collection<TbVenta> tbVentaCollectionOld = persistentTbCliente.getTbVentaCollection();
            Collection<TbVenta> tbVentaCollectionNew = tbCliente.getTbVentaCollection();
            Collection<TbVenta> attachedTbVentaCollectionNew = new ArrayList<TbVenta>();
            for (TbVenta tbVentaCollectionNewTbVentaToAttach : tbVentaCollectionNew) {
                tbVentaCollectionNewTbVentaToAttach = em.getReference(tbVentaCollectionNewTbVentaToAttach.getClass(), tbVentaCollectionNewTbVentaToAttach.getFactura());
                attachedTbVentaCollectionNew.add(tbVentaCollectionNewTbVentaToAttach);
            }
            tbVentaCollectionNew = attachedTbVentaCollectionNew;
            tbCliente.setTbVentaCollection(tbVentaCollectionNew);
            tbCliente = em.merge(tbCliente);
            for (TbVenta tbVentaCollectionOldTbVenta : tbVentaCollectionOld) {
                if (!tbVentaCollectionNew.contains(tbVentaCollectionOldTbVenta)) {
                    tbVentaCollectionOldTbVenta.setNit(null);
                    tbVentaCollectionOldTbVenta = em.merge(tbVentaCollectionOldTbVenta);
                }
            }
            for (TbVenta tbVentaCollectionNewTbVenta : tbVentaCollectionNew) {
                if (!tbVentaCollectionOld.contains(tbVentaCollectionNewTbVenta)) {
                    TbCliente oldNitOfTbVentaCollectionNewTbVenta = tbVentaCollectionNewTbVenta.getNit();
                    tbVentaCollectionNewTbVenta.setNit(tbCliente);
                    tbVentaCollectionNewTbVenta = em.merge(tbVentaCollectionNewTbVenta);
                    if (oldNitOfTbVentaCollectionNewTbVenta != null && !oldNitOfTbVentaCollectionNewTbVenta.equals(tbCliente)) {
                        oldNitOfTbVentaCollectionNewTbVenta.getTbVentaCollection().remove(tbVentaCollectionNewTbVenta);
                        oldNitOfTbVentaCollectionNewTbVenta = em.merge(oldNitOfTbVentaCollectionNewTbVenta);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = tbCliente.getNit();
                if (findTbCliente(id) == null) {
                    throw new NonexistentEntityException("The tbCliente with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TbCliente tbCliente;
            try {
                tbCliente = em.getReference(TbCliente.class, id);
                tbCliente.getNit();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tbCliente with id " + id + " no longer exists.", enfe);
            }
            Collection<TbVenta> tbVentaCollection = tbCliente.getTbVentaCollection();
            for (TbVenta tbVentaCollectionTbVenta : tbVentaCollection) {
                tbVentaCollectionTbVenta.setNit(null);
                tbVentaCollectionTbVenta = em.merge(tbVentaCollectionTbVenta);
            }
            em.remove(tbCliente);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TbCliente> findTbClienteEntities() {
        return findTbClienteEntities(true, -1, -1);
    }

    public List<TbCliente> findTbClienteEntities(int maxResults, int firstResult) {
        return findTbClienteEntities(false, maxResults, firstResult);
    }

    private List<TbCliente> findTbClienteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TbCliente.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public TbCliente findTbCliente(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TbCliente.class, id);
        } finally {
            em.close();
        }
    }

    public int getTbClienteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TbCliente> rt = cq.from(TbCliente.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
