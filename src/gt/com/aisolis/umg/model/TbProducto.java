/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gt.com.aisolis.umg.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author swords
 */
@Entity
@Table(name = "TB_PRODUCTO")
@NamedQueries({
    @NamedQuery(name = "TbProducto.findAll", query = "SELECT t FROM TbProducto t"),
    @NamedQuery(name = "TbProducto.findByCodProducto", query = "SELECT t FROM TbProducto t WHERE t.codProducto = :codProducto"),
    @NamedQuery(name = "TbProducto.findByNombre", query = "SELECT t FROM TbProducto t WHERE t.nombre = :nombre"),
    @NamedQuery(name = "TbProducto.findByStock", query = "SELECT t FROM TbProducto t WHERE t.stock = :stock"),
    @NamedQuery(name = "TbProducto.findByPrecio", query = "SELECT t FROM TbProducto t WHERE t.precio = :precio"),
    @NamedQuery(name = "TbProducto.findByDescripcion", query = "SELECT t FROM TbProducto t WHERE t.descripcion = :descripcion"),
    @NamedQuery(name = "TbProducto.findByQueEs", query = "SELECT t FROM TbProducto t WHERE t.queEs = :queEs"),
    @NamedQuery(name = "TbProducto.findByParaQue", query = "SELECT t FROM TbProducto t WHERE t.paraQue = :paraQue"),
    @NamedQuery(name = "TbProducto.findByBeneficios", query = "SELECT t FROM TbProducto t WHERE t.beneficios = :beneficios"),
    @NamedQuery(name = "TbProducto.findByFormaTomar", query = "SELECT t FROM TbProducto t WHERE t.formaTomar = :formaTomar"),
    @NamedQuery(name = "TbProducto.findByIngredientes", query = "SELECT t FROM TbProducto t WHERE t.ingredientes = :ingredientes"),
    @NamedQuery(name = "TbProducto.findByPrecauciones", query = "SELECT t FROM TbProducto t WHERE t.precauciones = :precauciones"),
    @NamedQuery(name = "TbProducto.findByEstatus", query = "SELECT t FROM TbProducto t WHERE t.estatus = :estatus")})
public class TbProducto implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "COD_PRODUCTO")
    private Integer codProducto;
    @Column(name = "NOMBRE")
    private String nombre;
    @Column(name = "STOCK")
    private Integer stock;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "PRECIO")
    private BigDecimal precio;
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Column(name = "QUE_ES")
    private String queEs;
    @Column(name = "PARA_QUE")
    private String paraQue;
    @Column(name = "BENEFICIOS")
    private String beneficios;
    @Column(name = "FORMA_TOMAR")
    private String formaTomar;
    @Column(name = "INGREDIENTES")
    private String ingredientes;
    @Column(name = "PRECAUCIONES")
    private String precauciones;
    @Column(name = "ESTATUS")
    private String estatus;
    @OneToMany(mappedBy = "codProducto")
    private Collection<TbDetalleVenta> tbDetalleVentaCollection;

    public TbProducto() {
    }

    public TbProducto(Integer codProducto) {
        this.codProducto = codProducto;
    }

    public Integer getCodProducto() {
        return codProducto;
    }

    public void setCodProducto(Integer codProducto) {
        this.codProducto = codProducto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getQueEs() {
        return queEs;
    }

    public void setQueEs(String queEs) {
        this.queEs = queEs;
    }

    public String getParaQue() {
        return paraQue;
    }

    public void setParaQue(String paraQue) {
        this.paraQue = paraQue;
    }

    public String getBeneficios() {
        return beneficios;
    }

    public void setBeneficios(String beneficios) {
        this.beneficios = beneficios;
    }

    public String getFormaTomar() {
        return formaTomar;
    }

    public void setFormaTomar(String formaTomar) {
        this.formaTomar = formaTomar;
    }

    public String getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(String ingredientes) {
        this.ingredientes = ingredientes;
    }

    public String getPrecauciones() {
        return precauciones;
    }

    public void setPrecauciones(String precauciones) {
        this.precauciones = precauciones;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
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
        hash += (codProducto != null ? codProducto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TbProducto)) {
            return false;
        }
        TbProducto other = (TbProducto) object;
        if ((this.codProducto == null && other.codProducto != null) || (this.codProducto != null && !this.codProducto.equals(other.codProducto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gt.com.aisolis.umg.model.TbProducto[ codProducto=" + codProducto + " ]";
    }
    
}
