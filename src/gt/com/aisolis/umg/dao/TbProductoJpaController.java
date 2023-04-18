/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gt.com.aisolis.umg.dao;

import gt.com.aisolis.umg.dao.exceptions.NonexistentEntityException;
import gt.com.aisolis.umg.dao.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import gt.com.aisolis.umg.model.TbDetalleVenta;
import gt.com.aisolis.umg.model.TbProducto;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author swords
 */
public class TbProductoJpaController implements Serializable {

    public TbProductoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TbProducto tbProducto) throws PreexistingEntityException, Exception {
        if (tbProducto.getTbDetalleVentaCollection() == null) {
            tbProducto.setTbDetalleVentaCollection(new ArrayList<TbDetalleVenta>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<TbDetalleVenta> attachedTbDetalleVentaCollection = new ArrayList<TbDetalleVenta>();
            for (TbDetalleVenta tbDetalleVentaCollectionTbDetalleVentaToAttach : tbProducto.getTbDetalleVentaCollection()) {
                tbDetalleVentaCollectionTbDetalleVentaToAttach = em.getReference(tbDetalleVentaCollectionTbDetalleVentaToAttach.getClass(), tbDetalleVentaCollectionTbDetalleVentaToAttach.getIdDetalle());
                attachedTbDetalleVentaCollection.add(tbDetalleVentaCollectionTbDetalleVentaToAttach);
            }
            tbProducto.setTbDetalleVentaCollection(attachedTbDetalleVentaCollection);
            em.persist(tbProducto);
            for (TbDetalleVenta tbDetalleVentaCollectionTbDetalleVenta : tbProducto.getTbDetalleVentaCollection()) {
                TbProducto oldCodProductoOfTbDetalleVentaCollectionTbDetalleVenta = tbDetalleVentaCollectionTbDetalleVenta.getCodProducto();
                tbDetalleVentaCollectionTbDetalleVenta.setCodProducto(tbProducto);
                tbDetalleVentaCollectionTbDetalleVenta = em.merge(tbDetalleVentaCollectionTbDetalleVenta);
                if (oldCodProductoOfTbDetalleVentaCollectionTbDetalleVenta != null) {
                    oldCodProductoOfTbDetalleVentaCollectionTbDetalleVenta.getTbDetalleVentaCollection().remove(tbDetalleVentaCollectionTbDetalleVenta);
                    oldCodProductoOfTbDetalleVentaCollectionTbDetalleVenta = em.merge(oldCodProductoOfTbDetalleVentaCollectionTbDetalleVenta);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTbProducto(tbProducto.getCodProducto()) != null) {
                throw new PreexistingEntityException("TbProducto " + tbProducto + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TbProducto tbProducto) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TbProducto persistentTbProducto = em.find(TbProducto.class, tbProducto.getCodProducto());
            Collection<TbDetalleVenta> tbDetalleVentaCollectionOld = persistentTbProducto.getTbDetalleVentaCollection();
            Collection<TbDetalleVenta> tbDetalleVentaCollectionNew = tbProducto.getTbDetalleVentaCollection();
            Collection<TbDetalleVenta> attachedTbDetalleVentaCollectionNew = new ArrayList<TbDetalleVenta>();
            for (TbDetalleVenta tbDetalleVentaCollectionNewTbDetalleVentaToAttach : tbDetalleVentaCollectionNew) {
                tbDetalleVentaCollectionNewTbDetalleVentaToAttach = em.getReference(tbDetalleVentaCollectionNewTbDetalleVentaToAttach.getClass(), tbDetalleVentaCollectionNewTbDetalleVentaToAttach.getIdDetalle());
                attachedTbDetalleVentaCollectionNew.add(tbDetalleVentaCollectionNewTbDetalleVentaToAttach);
            }
            tbDetalleVentaCollectionNew = attachedTbDetalleVentaCollectionNew;
            tbProducto.setTbDetalleVentaCollection(tbDetalleVentaCollectionNew);
            tbProducto = em.merge(tbProducto);
            for (TbDetalleVenta tbDetalleVentaCollectionOldTbDetalleVenta : tbDetalleVentaCollectionOld) {
                if (!tbDetalleVentaCollectionNew.contains(tbDetalleVentaCollectionOldTbDetalleVenta)) {
                    tbDetalleVentaCollectionOldTbDetalleVenta.setCodProducto(null);
                    tbDetalleVentaCollectionOldTbDetalleVenta = em.merge(tbDetalleVentaCollectionOldTbDetalleVenta);
                }
            }
            for (TbDetalleVenta tbDetalleVentaCollectionNewTbDetalleVenta : tbDetalleVentaCollectionNew) {
                if (!tbDetalleVentaCollectionOld.contains(tbDetalleVentaCollectionNewTbDetalleVenta)) {
                    TbProducto oldCodProductoOfTbDetalleVentaCollectionNewTbDetalleVenta = tbDetalleVentaCollectionNewTbDetalleVenta.getCodProducto();
                    tbDetalleVentaCollectionNewTbDetalleVenta.setCodProducto(tbProducto);
                    tbDetalleVentaCollectionNewTbDetalleVenta = em.merge(tbDetalleVentaCollectionNewTbDetalleVenta);
                    if (oldCodProductoOfTbDetalleVentaCollectionNewTbDetalleVenta != null && !oldCodProductoOfTbDetalleVentaCollectionNewTbDetalleVenta.equals(tbProducto)) {
                        oldCodProductoOfTbDetalleVentaCollectionNewTbDetalleVenta.getTbDetalleVentaCollection().remove(tbDetalleVentaCollectionNewTbDetalleVenta);
                        oldCodProductoOfTbDetalleVentaCollectionNewTbDetalleVenta = em.merge(oldCodProductoOfTbDetalleVentaCollectionNewTbDetalleVenta);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tbProducto.getCodProducto();
                if (findTbProducto(id) == null) {
                    throw new NonexistentEntityException("The tbProducto with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TbProducto tbProducto;
            try {
                tbProducto = em.getReference(TbProducto.class, id);
                tbProducto.getCodProducto();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tbProducto with id " + id + " no longer exists.", enfe);
            }
            Collection<TbDetalleVenta> tbDetalleVentaCollection = tbProducto.getTbDetalleVentaCollection();
            for (TbDetalleVenta tbDetalleVentaCollectionTbDetalleVenta : tbDetalleVentaCollection) {
                tbDetalleVentaCollectionTbDetalleVenta.setCodProducto(null);
                tbDetalleVentaCollectionTbDetalleVenta = em.merge(tbDetalleVentaCollectionTbDetalleVenta);
            }
            em.remove(tbProducto);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TbProducto> findTbProductoEntities() {
        return findTbProductoEntities(true, -1, -1);
    }

    public List<TbProducto> findTbProductoEntities(int maxResults, int firstResult) {
        return findTbProductoEntities(false, maxResults, firstResult);
    }

    private List<TbProducto> findTbProductoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TbProducto.class));
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

    public TbProducto findTbProducto(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TbProducto.class, id);
        } finally {
            em.close();
        }
    }

    public int getTbProductoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TbProducto> rt = cq.from(TbProducto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
