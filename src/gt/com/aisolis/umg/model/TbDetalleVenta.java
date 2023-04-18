/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gt.com.aisolis.umg.model;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author swords
 */
@Entity
@Table(name = "TB_DETALLE_VENTA")
@NamedQueries({
    @NamedQuery(name = "TbDetalleVenta.findAll", query = "SELECT t FROM TbDetalleVenta t"),
    @NamedQuery(name = "TbDetalleVenta.findByIdDetalle", query = "SELECT t FROM TbDetalleVenta t WHERE t.idDetalle = :idDetalle"),
    @NamedQuery(name = "TbDetalleVenta.findByCantidad", query = "SELECT t FROM TbDetalleVenta t WHERE t.cantidad = :cantidad"),
    @NamedQuery(name = "TbDetalleVenta.findByPrecio", query = "SELECT t FROM TbDetalleVenta t WHERE t.precio = :precio")})
public class TbDetalleVenta implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_DETALLE")
    private Integer idDetalle;
    @Column(name = "CANTIDAD")
    private Integer cantidad;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "PRECIO")
    private BigDecimal precio;
    @JoinColumn(name = "COD_PRODUCTO", referencedColumnName = "COD_PRODUCTO")
    @ManyToOne
    private TbProducto codProducto;
    @JoinColumn(name = "FACTURA", referencedColumnName = "FACTURA")
    @ManyToOne
    private TbVenta factura;

    public TbDetalleVenta() {
    }

    public TbDetalleVenta(Integer idDetalle) {
        this.idDetalle = idDetalle;
    }

    public Integer getIdDetalle() {
        return idDetalle;
    }

    public void setIdDetalle(Integer idDetalle) {
        this.idDetalle = idDetalle;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public TbProducto getCodProducto() {
        return codProducto;
    }

    public void setCodProducto(TbProducto codProducto) {
        this.codProducto = codProducto;
    }

    public TbVenta getFactura() {
        return factura;
    }

    public void setFactura(TbVenta factura) {
        this.factura = factura;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDetalle != null ? idDetalle.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TbDetalleVenta)) {
            return false;
        }
        TbDetalleVenta other = (TbDetalleVenta) object;
        if ((this.idDetalle == null && other.idDetalle != null) || (this.idDetalle != null && !this.idDetalle.equals(other.idDetalle))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gt.com.aisolis.umg.model.TbDetalleVenta[ idDetalle=" + idDetalle + " ]";
    }
    
}
