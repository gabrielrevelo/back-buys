package com.sofka.Api_Buys.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class ProductDTO {

    private String id;
    @NotBlank
    private String name;
    @NotNull
    private Integer inventory;

    @NotBlank
    private String imageUrl;

    private Boolean enabled;

    private Integer min;

    private Integer max;

    public ProductDTO(String id, String name, Integer inventory, String imageUrl, Boolean enabled, Integer min, Integer max) {
        this.id = id;
        this.name = name;
        this.inventory = inventory;
        this.imageUrl = imageUrl;
        this.enabled = enabled;
        this.min = min;
        this.max = max;
    }

    public ProductDTO() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getInventory() {
        return inventory;
    }

    public void setInventory(Integer inventory) {
        this.inventory = inventory;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Integer getMin() {
        return min;
    }

    public void setMin(Integer min) {
        this.min = min;
    }

    public Integer getMax() {
        return max;
    }

    public void setMax(Integer max) {
        this.max = max;
    }
}

