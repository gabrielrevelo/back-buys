package com.sofka.Api_Buys.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
public class Buy {

    @Id
    private String id;

    private String date;

    private String idType;

    private String idClient;

    private String clientName;

    private String username;

    private List<Product> products;

    public Buy(String id, String date, String idType, String idClient, String clientName, String username,List<Product> products) {
        this.id = id;
        this.date = date;
        this.idType = idType;
        this.idClient = idClient;
        this.clientName = clientName;
        this.username = username;
        this.products = products;
    }

    public Buy() {
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
