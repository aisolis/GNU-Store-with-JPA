/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gt.com.aisolis.umg.dao;

import gt.com.aisolis.umg.dao.exceptions.NonexistentEntityException;
import gt.com.aisolis.umg.model.TbDetalleVenta;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import gt.com.aisolis.umg.model.TbProducto;
import gt.com.aisolis.umg.model.TbVenta;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author swords
 */
public class TbDetalleVentaJpaController implements Serializable {

    public TbDetalleVentaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TbDetalleVenta tbDetalleVenta) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TbProducto codProducto = tbDetalleVenta.getCodProducto();
            if (codProducto != null) {
                codProducto = em.getReference(codProducto.getClass(), codProducto.getCodProducto());
                tbDetalleVenta.setCodProducto(codProducto);
            }
            TbVenta factura = tbDetalleVenta.getFactura();
            if (factura != null) {
                factura = em.getReference(factura.getClass(), factura.getFactura());
                tbDetalleVenta.setFactura(factura);
            }
            em.persist(tbDetalleVenta);
            if (codProducto != null) {
                codProducto.getTbDetalleVentaCollection().add(tbDetalleVenta);
                codProducto = em.merge(codProducto);
            }
            if (factura != null) {
                factura.getTbDetalleVentaCollection().add(tbDetalleVenta);
                factura = em.merge(factura);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TbDetalleVenta tbDetalleVenta) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TbDetalleVenta persistentTbDetalleVenta = em.find(TbDetalleVenta.class, tbDetalleVenta.getIdDetalle());
            TbProducto codProductoOld = persistentTbDetalleVenta.getCodProducto();
            TbProducto codProductoNew = tbDetalleVenta.getCodProducto();
            TbVenta facturaOld = persistentTbDetalleVenta.getFactura();
            TbVenta facturaNew = tbDetalleVenta.getFactura();
            if (codProductoNew != null) {
                codProductoNew = em.getReference(codProductoNew.getClass(), codProductoNew.getCodProducto());
                tbDetalleVenta.setCodProducto(codProductoNew);
            }
            if (facturaNew != null) {
                facturaNew = em.getReference(facturaNew.getClass(), facturaNew.getFactura());
                tbDetalleVenta.setFactura(facturaNew);
            }
            tbDetalleVenta = em.merge(tbDetalleVenta);
            if (codProductoOld != null && !codProductoOld.equals(codProductoNew)) {
                codProductoOld.getTbDetalleVentaCollection().remove(tbDetalleVenta);
                codProductoOld = em.merge(codProductoOld);
            }
            if (codProductoNew != null && !codProductoNew.equals(codProductoOld)) {
                codProductoNew.getTbDetalleVentaCollection().add(tbDetalleVenta);
                codProductoNew = em.merge(codProductoNew);
            }
            if (facturaOld != null && !facturaOld.equals(facturaNew)) {
                facturaOld.getTbDetalleVentaCollection().remove(tbDetalleVenta);
                facturaOld = em.merge(facturaOld);
            }
            if (facturaNew != null && !facturaNew.equals(facturaOld)) {
                facturaNew.getTbDetalleVentaCollection().add(tbDetalleVenta);
                facturaNew = em.merge(facturaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tbDetalleVenta.getIdDetalle();
                if (findTbDetalleVenta(id) == null) {
                    throw new NonexistentEntityException("The tbDetalleVenta with id " + id + " no longer exists.");
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
            TbDetalleVenta tbDetalleVenta;
            try {
                tbDetalleVenta = em.getReference(TbDetalleVenta.class, id);
                tbDetalleVenta.getIdDetalle();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tbDetalleVenta with id " + id + " no longer exists.", enfe);
            }
            TbProducto codProducto = tbDetalleVenta.getCodProducto();
            if (codProducto != null) {
                codProducto.getTbDetalleVentaCollection().remove(tbDetalleVenta);
                codProducto = em.merge(codProducto);
            }
            TbVenta factura = tbDetalleVenta.getFactura();
            if (factura != null) {
                factura.getTbDetalleVentaCollection().remove(tbDetalleVenta);
                factura = em.merge(factura);
            }
            em.remove(tbDetalleVenta);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TbDetalleVenta> findTbDetalleVentaEntities() {
        return findTbDetalleVentaEntities(true, -1, -1);
    }

    public List<TbDetalleVenta> findTbDetalleVentaEntities(int maxResults, int firstResult) {
        return findTbDetalleVentaEntities(false, maxResults, firstResult);
    }

    private List<TbDetalleVenta> findTbDetalleVentaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TbDetalleVenta.class));
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

    public TbDetalleVenta findTbDetalleVenta(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TbDetalleVenta.class, id);
        } finally {
            em.close();
        }
    }

    public int getTbDetalleVentaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TbDetalleVenta> rt = cq.from(TbDetalleVenta.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
