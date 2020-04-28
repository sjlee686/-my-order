package com.example.demo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.persistence.*;


@Entity
@Table(name="ORDER_TABLE")
public class Order {
    @Id @GeneratedValue
    Long id;
    int qty;
    String productName;
    Long productId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    @PostPersist
    public void eventPublish(){
        OrderPlaced orderplaced = new OrderPlaced();
        orderplaced.setOrderId(this.getId());
        orderplaced.setQty(this.getQty());
        orderplaced.setProductId(this.getProductId());
        orderplaced.setProductName(this.getProductName());
        ObjectMapper objectMapper = new ObjectMapper();
        String json = null;
        try {
            json = objectMapper.writeValueAsString(orderplaced);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("JSON format exception", e);
        }
        System.out.println(json);
    }


}
