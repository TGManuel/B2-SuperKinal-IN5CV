/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.manueltejeda.model;

/**
 *
 * @author informatica
 */
public class Cargo {
    private int cargoId;
    private String nombre;
    private String descripcion;

    public Cargo() {
    }

    public Cargo(int cargoId, String nombre, String descripcion) {
        this.cargoId = cargoId;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public int getCargoId() {
        return cargoId;
    }

    public void setCargoId(int cargoId) {
        this.cargoId = cargoId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "ID: " + cargoId + " | " + nombre + " | " + descripcion ;
    }
    
    
}
