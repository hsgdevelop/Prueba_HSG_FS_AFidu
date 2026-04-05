package com.afidu.clientadmin.model;

import java.time.LocalDate;
import java.util.UUID;

/**
 * En la clase Client se representa el modelo de negocio correspondiente a la información de un cliente dentro del sistema.
 *
 * @author hsgdevelop
 * @fechaCreacion 04/04/2026
 * @version 1.0.0
 */


public class Client {

    private UUID id;
    private String sharedKey;
    private String businessId;
    private String email;
    private String phone;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDate dateAdded;

    public Client() {
    }

    public Client(UUID id, String sharedKey, String businessId, String email, String phone,
                  LocalDate startDate, LocalDate endDate, LocalDate dateAdded) {
        this.id = id;
        this.sharedKey = sharedKey;
        this.businessId = businessId;
        this.email = email;
        this.phone = phone;
        this.startDate = startDate;
        this.endDate = endDate;
        this.dateAdded = dateAdded;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getSharedKey() {
        return sharedKey;
    }

    public void setSharedKey(String sharedKey) {
        this.sharedKey = sharedKey;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public LocalDate getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(LocalDate dateAdded) {
        this.dateAdded = dateAdded;
    }
}
