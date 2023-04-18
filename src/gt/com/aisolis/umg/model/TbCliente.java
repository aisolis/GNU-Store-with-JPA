/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gt.com.aisolis.umg.model;

import java.io.Serializable;
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
@Table(name = "TB_CLIENTE")
@NamedQueries({
    @NamedQuery(name = "TbCliente.findAll", query = "SELECT t FROM TbCliente t"),
    @NamedQuery(name = "TbCliente.findByDbid", query = "SELECT t FROM TbCliente t WHERE t.dbid = :dbid"),
    @NamedQuery(name = "TbCliente.findByNit", query = "SELECT t FROM TbCliente t WHERE t.nit = :nit"),
    @NamedQuery(name = "TbCliente.findByNombre", query = "SELECT t FROM TbCliente t WHERE t.nombre = :nombre"),
    @NamedQuery(name = "TbCliente.findByDireccion", query = "SELECT t FROM TbCliente t WHERE t.direccion = :direccion"),
    @NamedQuery(name = "TbCliente.findByTelefono", query = "SELECT t FROM TbCliente t WHERE t.telefono = :telefono"),
    @NamedQuery(name = "TbCliente.findByCorreo", query = "SELECT t FROM TbCliente t WHERE t.correo = :correo")})
public class TbCliente implements Serializable {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @Column(name = "DBID")
    private int dbid;
    @Id
    @Basic(optional = false)
    @Column(name = "NIT")
    private String nit;
    @Column(name = "NOMBRE")
    private String nombre;
    @Column(name = "DIRECCION")
    private String direccion;
    @Column(name = "TELEFONO")
    private Integer telefono;
    @Column(name = "CORREO")
    private String correo;
    @OneToMany(mappedBy = "nit")
    private Collection<TbVenta> tbVentaCollection;

    public TbCliente() {
    }

    public TbCliente(String nit) {
        this.nit = nit;
    }

    public TbCliente(String nit, int dbid) {
        this.nit = nit;
        this.dbid = dbid;
    }

    public int getDbid() {
        return dbid;
    }

    public void setDbid(int dbid) {
        this.dbid = dbid;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Integer getTelefono() {
        return telefono;
    }

    public void setTelefono(Integer telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public Collection<TbVenta> getTbVentaCollection() {
        return tbVentaCollection;
    }

    public void setTbVentaCollection(Collection<TbVenta> tbVentaCollection) {
        this.tbVentaCollection = tbVentaCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (nit != null ? nit.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TbCliente)) {
            return false;
        }
        TbCliente other = (TbCliente) object;
        if ((this.nit == null && other.nit != null) || (this.nit != null && !this.nit.equals(other.nit))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gt.com.aisolis.umg.model.TbCliente[ nit=" + nit + " ]";
    }
    
}
