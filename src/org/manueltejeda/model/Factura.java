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
public class Factura {
    private int facturaId;
    private Date fecha;
    private Time hora;
    private double total;
    private int clienteId;
    private String cliente;
    private int empleadoId;
    private String empleado;

    public Factura() {
    }
    
    public Factura(int facturaId, Date fecha, Time hora, double total, String cliente, String empleado) {
        this.facturaId = facturaId;
        this.fecha = fecha;
        this.hora = hora;
        this.total = total;
        this.cliente = cliente;
        this.empleado = empleado;
    }

    public Factura(int facturaId, Date fecha, Time hora, double total, int clienteId, int empleadoId) {
        this.facturaId = facturaId;
        this.fecha = fecha;
        this.hora = hora;
        this.total = total;
        this.clienteId = clienteId;
        this.empleadoId = empleadoId;
    }

    public int getFacturaId() {
        return facturaId;
    }

    public void setFacturaId(int facturaId) {
        this.facturaId = facturaId;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Time getHora() {
        return hora;
    }

    public void setHora(Time hora) {
        this.hora = hora;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public int getClienteId() {
        return clienteId;
    }

    public void setClienteId(int clienteId) {
        this.clienteId = clienteId;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public int getEmpleadoId() {
        return empleadoId;
    }

    public void setEmpleadoId(int empleadoId) {
        this.empleadoId = empleadoId;
    }

    public String getEmpleado() {
        return empleado;
    }

    public void setEmpleado(String empleado) {
        this.empleado = empleado;
    }

    @Override
    public String toString() {
        return "Facturas{" + "facturaId=" + facturaId + ", total=" + total + '}';
    }
}
