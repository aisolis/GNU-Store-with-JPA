/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gt.com.aisolis.umg.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author swords
 */
@Entity
@Table(name = "TB_VENTA")
@NamedQueries({
    @NamedQuery(name = "TbVenta.findAll", query = "SELECT t FROM TbVenta t"),
    @NamedQuery(name = "TbVenta.findByFactura", query = "SELECT t FROM TbVenta t WHERE t.factura = :factura"),
    @NamedQuery(name = "TbVenta.findByFecha", query = "SELECT t FROM TbVenta t WHERE t.fecha = :fecha"),
    @NamedQuery(name = "TbVenta.findByMedio", query = "SELECT t FROM TbVenta t WHERE t.medio = :medio"),
    @NamedQuery(name = "TbVenta.findByEstatus", query = "SELECT t FROM TbVenta t WHERE t.estatus = :estatus")})
public class TbVenta implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "FACTURA")
    private Integer factura;
    @Column(name = "FECHA")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Column(name = "MEDIO")
    private Integer medio;
    @Column(name = "ESTATUS")
    private String estatus;
    @JoinColumn(name = "NIT", referencedColumnName = "NIT")
    @ManyToOne
    private TbCliente nit;
    @OneToMany(mappedBy = "factura")
    private Collection<TbDetalleVenta> tbDetalleVentaCollection;

    public TbVenta() {
    }

    public TbVenta(Integer factura) {
        this.factura = factura;
    }

    public Integer getFactura() {
        return factura;
    }

    public void setFactura(Integer factura) {
        this.factura = factura;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Integer getMedio() {
        return medio;
    }

    public void setMedio(Integer medio) {
        this.medio = medio;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    public TbCliente getNit() {
        return nit;
    }

    public void setNit(TbCliente nit) {
        this.nit = nit;
    }

    public Collection<TbDetalleVenta> getTbDetalleVentaCollection() {
        return tbDetalleVentaCollection;
    }

    public void setTbDetalleVentaCollection(Collection<TbDetalleVenta> tbDetalleVentaCollection) {
        this.tbDetalleVentaCollection = tbDetalleVentaCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (factura != null ? factura.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TbVenta)) {
            return false;
        }
        TbVenta other = (TbVenta) object;
        if ((this.factura == null && other.factura != null) || (this.factura != null && !this.factura.equals(other.factura))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gt.com.aisolis.umg.model.TbVenta[ factura=" + factura + " ]";
    }
    
}
