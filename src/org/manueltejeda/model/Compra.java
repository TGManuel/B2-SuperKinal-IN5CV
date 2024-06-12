/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.manueltejeda.model;

import java.sql.Date;

/**
 *
 * @author usuario
 */
public class Compra {
     private int compraId;
    private Date fechaCompra;
    private Double totalCompra;

    public Compra() {
    }

    public Compra(int compraId, Date fechaCompra, Double totalCompra) {
        this.compraId = compraId;
        this.fechaCompra = fechaCompra;
        this.totalCompra = totalCompra;
    }

    public Compra(Date fechaCompra, Double totalCompra) {
        this.fechaCompra = fechaCompra;
        this.totalCompra = totalCompra;
    }
    
    

    public int getCompraId() {
        return compraId;
    }

    public void setCompraId(int compraId) {
        this.compraId = compraId;
    }

    public Date getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(Date fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

    public Double getTotalCompra() {
        return totalCompra;
    }

    public void setTotalCompra(Double totalCompra) {
        this.totalCompra = totalCompra;
    }

    @Override
    public String toString() {
        return String.valueOf(compraId);
    }
}
