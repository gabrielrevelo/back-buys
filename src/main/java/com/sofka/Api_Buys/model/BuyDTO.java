package com.sofka.Api_Buys.model;

import javax.validation.constraints.NotBlank;
import java.util.List;

public class BuyDTO {

    private String id;

    @NotBlank
    private String date;

    @NotBlank
    private String idType;

    @NotBlank
    private String idClient;

    @NotBlank
    private String clientName;

    private List<Product> products;

    public BuyDTO(String id, String date, String idType, String idClient, String clientName, List<Product> products) {
        this.id = id;
        this.date = date;
        this.idType = idType;
        this.idClient = idClient;
        this.clientName = clientName;
        this.products = products;
    }

    public BuyDTO() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getIdType() {
        return idType;
    }

    public void setIdType(String idType) {
        this.idType = idType;
    }

    public String getIdClient() {
        return idClient;
    }

    public void setIdClient(String idClient) {
        this.idClient = idClient;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
