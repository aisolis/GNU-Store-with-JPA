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
import gt.com.aisolis.umg.model.TbCliente;
import gt.com.aisolis.umg.model.TbDetalleVenta;
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
public class TbVentaJpaController implements Serializable {

    public TbVentaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TbVenta tbVenta) throws PreexistingEntityException, Exception {
        if (tbVenta.getTbDetalleVentaCollection() == null) {
            tbVenta.setTbDetalleVentaCollection(new ArrayList<TbDetalleVenta>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TbCliente nit = tbVenta.getNit();
            if (nit != null) {
                nit = em.getReference(nit.getClass(), nit.getNit());
                tbVenta.setNit(nit);
            }
            Collection<TbDetalleVenta> attachedTbDetalleVentaCollection = new ArrayList<TbDetalleVenta>();
            for (TbDetalleVenta tbDetalleVentaCollectionTbDetalleVentaToAttach : tbVenta.getTbDetalleVentaCollection()) {
                tbDetalleVentaCollectionTbDetalleVentaToAttach = em.getReference(tbDetalleVentaCollectionTbDetalleVentaToAttach.getClass(), tbDetalleVentaCollectionTbDetalleVentaToAttach.getIdDetalle());
                attachedTbDetalleVentaCollection.add(tbDetalleVentaCollectionTbDetalleVentaToAttach);
            }
            tbVenta.setTbDetalleVentaCollection(attachedTbDetalleVentaCollection);
            em.persist(tbVenta);
            if (nit != null) {
                nit.getTbVentaCollection().add(tbVenta);
                nit = em.merge(nit);
            }
            for (TbDetalleVenta tbDetalleVentaCollectionTbDetalleVenta : tbVenta.getTbDetalleVentaCollection()) {
                TbVenta oldFacturaOfTbDetalleVentaCollectionTbDetalleVenta = tbDetalleVentaCollectionTbDetalleVenta.getFactura();
                tbDetalleVentaCollectionTbDetalleVenta.setFactura(tbVenta);
                tbDetalleVentaCollectionTbDetalleVenta = em.merge(tbDetalleVentaCollectionTbDetalleVenta);
                if (oldFacturaOfTbDetalleVentaCollectionTbDetalleVenta != null) {
                    oldFacturaOfTbDetalleVentaCollectionTbDetalleVenta.getTbDetalleVentaCollection().remove(tbDetalleVentaCollectionTbDetalleVenta);
                    oldFacturaOfTbDetalleVentaCollectionTbDetalleVenta = em.merge(oldFacturaOfTbDetalleVentaCollectionTbDetalleVenta);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTbVenta(tbVenta.getFactura()) != null) {
                throw new PreexistingEntityException("TbVenta " + tbVenta + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TbVenta tbVenta) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TbVenta persistentTbVenta = em.find(TbVenta.class, tbVenta.getFactura());
            TbCliente nitOld = persistentTbVenta.getNit();
            TbCliente nitNew = tbVenta.getNit();
            Collection<TbDetalleVenta> tbDetalleVentaCollectionOld = persistentTbVenta.getTbDetalleVentaCollection();
            Collection<TbDetalleVenta> tbDetalleVentaCollectionNew = tbVenta.getTbDetalleVentaCollection();
            if (nitNew != null) {
                nitNew = em.getReference(nitNew.getClass(), nitNew.getNit());
                tbVenta.setNit(nitNew);
            }
            Collection<TbDetalleVenta> attachedTbDetalleVentaCollectionNew = new ArrayList<TbDetalleVenta>();
            for (TbDetalleVenta tbDetalleVentaCollectionNewTbDetalleVentaToAttach : tbDetalleVentaCollectionNew) {
                tbDetalleVentaCollectionNewTbDetalleVentaToAttach = em.getReference(tbDetalleVentaCollectionNewTbDetalleVentaToAttach.getClass(), tbDetalleVentaCollectionNewTbDetalleVentaToAttach.getIdDetalle());
                attachedTbDetalleVentaCollectionNew.add(tbDetalleVentaCollectionNewTbDetalleVentaToAttach);
            }
            tbDetalleVentaCollectionNew = attachedTbDetalleVentaCollectionNew;
            tbVenta.setTbDetalleVentaCollection(tbDetalleVentaCollectionNew);
            tbVenta = em.merge(tbVenta);
            if (nitOld != null && !nitOld.equals(nitNew)) {
                nitOld.getTbVentaCollection().remove(tbVenta);
                nitOld = em.merge(nitOld);
            }
            if (nitNew != null && !nitNew.equals(nitOld)) {
                nitNew.getTbVentaCollection().add(tbVenta);
                nitNew = em.merge(nitNew);
            }
            for (TbDetalleVenta tbDetalleVentaCollectionOldTbDetalleVenta : tbDetalleVentaCollectionOld) {
                if (!tbDetalleVentaCollectionNew.contains(tbDetalleVentaCollectionOldTbDetalleVenta)) {
                    tbDetalleVentaCollectionOldTbDetalleVenta.setFactura(null);
                    tbDetalleVentaCollectionOldTbDetalleVenta = em.merge(tbDetalleVentaCollectionOldTbDetalleVenta);
                }
            }
            for (TbDetalleVenta tbDetalleVentaCollectionNewTbDetalleVenta : tbDetalleVentaCollectionNew) {
                if (!tbDetalleVentaCollectionOld.contains(tbDetalleVentaCollectionNewTbDetalleVenta)) {
                    TbVenta oldFacturaOfTbDetalleVentaCollectionNewTbDetalleVenta = tbDetalleVentaCollectionNewTbDetalleVenta.getFactura();
                    tbDetalleVentaCollectionNewTbDetalleVenta.setFactura(tbVenta);
                    tbDetalleVentaCollectionNewTbDetalleVenta = em.merge(tbDetalleVentaCollectionNewTbDetalleVenta);
                    if (oldFacturaOfTbDetalleVentaCollectionNewTbDetalleVenta != null && !oldFacturaOfTbDetalleVentaCollectionNewTbDetalleVenta.equals(tbVenta)) {
                        oldFacturaOfTbDetalleVentaCollectionNewTbDetalleVenta.getTbDetalleVentaCollection().remove(tbDetalleVentaCollectionNewTbDetalleVenta);
                        oldFacturaOfTbDetalleVentaCollectionNewTbDetalleVenta = em.merge(oldFacturaOfTbDetalleVentaCollectionNewTbDetalleVenta);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tbVenta.getFactura();
                if (findTbVenta(id) == null) {
                    throw new NonexistentEntityException("The tbVenta with id " + id + " no longer exists.");
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
            TbVenta tbVenta;
            try {
                tbVenta = em.getReference(TbVenta.class, id);
                tbVenta.getFactura();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tbVenta with id " + id + " no longer exists.", enfe);
            }
            TbCliente nit = tbVenta.getNit();
            if (nit != null) {
                nit.getTbVentaCollection().remove(tbVenta);
                nit = em.merge(nit);
            }
            Collection<TbDetalleVenta> tbDetalleVentaCollection = tbVenta.getTbDetalleVentaCollection();
            for (TbDetalleVenta tbDetalleVentaCollectionTbDetalleVenta : tbDetalleVentaCollection) {
                tbDetalleVentaCollectionTbDetalleVenta.setFactura(null);
                tbDetalleVentaCollectionTbDetalleVenta = em.merge(tbDetalleVentaCollectionTbDetalleVenta);
            }
            em.remove(tbVenta);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TbVenta> findTbVentaEntities() {
        return findTbVentaEntities(true, -1, -1);
    }

    public List<TbVenta> findTbVentaEntities(int maxResults, int firstResult) {
        return findTbVentaEntities(false, maxResults, firstResult);
    }

    private List<TbVenta> findTbVentaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TbVenta.class));
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

    public TbVenta findTbVenta(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TbVenta.class, id);
        } finally {
            em.close();
        }
    }

    public int getTbVentaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TbVenta> rt = cq.from(TbVenta.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
