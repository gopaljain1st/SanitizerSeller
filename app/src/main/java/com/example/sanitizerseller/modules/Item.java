package com.example.sanitizerseller.modules;

public class Item
{
    private String id,itemName,itemMRP,itemOutPrice,itemWeight,itemStock,itemDescription,itemDiscount,itemCategory,itemQuantity,orderId;

    public Item(String id, String itemName, String itemMRP, String itemQuantity, String orderId) {
        this.id = id;
        this.itemName = itemName;
        this.itemMRP = itemMRP;
        this.itemQuantity = itemQuantity;
        this.orderId = orderId;
    }

    public String getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(String itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    private int image;
    public Item(String id, String itemName, String itemMRP, String itemOutPrice, String itemWeight, String itemStock, String itemDescription, String itemCategory, String itemImage) {
        this.id = id;
        this.itemName = itemName;
        this.itemMRP = itemMRP;
        this.itemOutPrice = itemOutPrice;
        this.itemWeight = itemWeight;
        this.itemStock = itemStock;
        this.itemDescription = itemDescription;
        this.itemCategory = itemCategory;
        this.itemImage = itemImage;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public Item(String id, String itemName, String itemMRP, String itemOutPrice, String itemWeight, String itemCategory, int image) {
        this.id = id;
        this.itemName = itemName;
        this.itemMRP = itemMRP;
        this.itemOutPrice = itemOutPrice;
        this.itemWeight = itemWeight;
        this.itemCategory = itemCategory;
        this.image = image;
    }

    private String itemImage;

    public String getItemCategory() {
        return itemCategory;
    }

    public void setItemCategory(String itemCategory) {
        this.itemCategory = itemCategory;
    }

    public String getItemImage() {
        return itemImage;
    }

    public void setItemImage(String itemImage) {
        this.itemImage = itemImage;
    }

    public Item(String id, String itemName, String itemMRP, String itemOutPrice, String itemWeight, String itemStock, String itemDescription, String itemDiscount) {
        this.id = id;
        this.itemName = itemName;
        this.itemMRP = itemMRP;
        this.itemOutPrice = itemOutPrice;
        this.itemWeight = itemWeight;
        this.itemStock = itemStock;
        this.itemDescription = itemDescription;
        this.itemDiscount = itemDiscount;
    }

    public Item(String itemName, String itemMRP, String itemOutPrice, String itemWeight) {
        this.itemName = itemName;
        this.itemMRP = itemMRP;
        this.itemOutPrice = itemOutPrice;
        this.itemWeight = itemWeight;
    }

    public Item(String id, String itemName, String itemMRP, String itemOutPrice, String itemWeight, String itemStock, String itemDescription) {
        this.id = id;
        this.itemName = itemName;
        this.itemMRP = itemMRP;
        this.itemOutPrice = itemOutPrice;
        this.itemWeight = itemWeight;
        this.itemStock = itemStock;
        this.itemDescription = itemDescription;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemMRP() {
        return itemMRP;
    }

    public void setItemMRP(String itemMRP) {
        this.itemMRP = itemMRP;
    }

    public String getItemOutPrice() {
        return itemOutPrice;
    }

    public void setItemOutPrice(String itemOutPrice) {
        this.itemOutPrice = itemOutPrice;
    }

    public String getItemWeight() {
        return itemWeight;
    }

    public void setItemWeight(String itemWeight) {
        this.itemWeight = itemWeight;
    }

    public String getItemStock() {
        return itemStock;
    }

    public void setItemStock(String itemStock) {
        this.itemStock = itemStock;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public String getItemDiscount() {
        return itemDiscount;
    }

    public void setItemDiscount(String itemDiscount) {
        this.itemDiscount = itemDiscount;
    }
}
