/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.manueltejeda.model;

import java.sql.Date;
import java.sql.Time;

/**
 *
 * @author usuario
 */
public class DetalleFactura extends Factura{
     private int detalleFacturaId;
    private String factura;
    private int productoId;
    private String producto;

    public DetalleFactura() {
    }

    public DetalleFactura(String factura, String producto, int facturaId, Date fecha, Time hora, double total, String cliente, String empleado) {
        super(facturaId, fecha, hora, total, cliente, empleado);
        this.factura = factura;
        this.producto = producto;
    }

    public DetalleFactura( int productoId, int facturaId, Date fecha, Time hora, double total, int clienteId, int empleadoId) {
        super(facturaId, fecha, hora, total, clienteId, empleadoId);
        this.productoId = productoId;
    }

    public DetalleFactura(String producto, int facturaId, Date fecha, Time hora, double total, String cliente, String empleado) {
        super(facturaId, fecha, hora, total, cliente, empleado);
        this.producto = producto;
    }

    public int getDetalleFacturaId() {
        return detalleFacturaId;
    }

    public void setDetalleFacturaId(int detalleFacturaId) {
        this.detalleFacturaId = detalleFacturaId;
    }

    public String getFactura() {
        return factura;
    }

    public void setFactura(String factura) {
        this.factura = factura;
    }

    public int getProductoId() {
        return productoId;
    }

    public void setProductoId(int productoId) {
        this.productoId = productoId;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    @Override
    public String toString() {
        return  "Id |" + detalleFacturaId + "| factura |" + factura ;
    }
}
