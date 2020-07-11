package com.example.sanitizerseller.modules;

public class Order
{
   private String id,orderId,itemId,userId,name,address,mobile,itemName,deliveryTime,itemCount,totalAmount,orderStatus,timeOfOrder,dateOfOrder;

    public Order() {
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Order(String orderId, String userId, String name, String address, String mobile, String deliveryTime, String itemCount, String totalAmount, String orderStatus, String timeOfOrder, String dateOfOrder) {
        this.orderId = orderId;
        this.userId = userId;
        this.name = name;
        this.address = address;
        this.mobile = mobile;
        this.deliveryTime = deliveryTime;
        this.itemCount = itemCount;
        this.totalAmount = totalAmount;
        this.orderStatus = orderStatus;
        this.timeOfOrder = timeOfOrder;
        this.dateOfOrder = dateOfOrder;
    }

    public Order(String orderId, String name, String address, String mobile, String deliveryTime, String itemCount, String totalAmount, String orderStatus, String timeOfOrder, String dateOfOrder) {
        this.orderId = orderId;
        this.name = name;
        this.address = address;
        this.mobile = mobile;
        this.deliveryTime = deliveryTime;
        this.itemCount = itemCount;
        this.totalAmount = totalAmount;
        this.orderStatus = orderStatus;
        this.timeOfOrder = timeOfOrder;
        this.dateOfOrder = dateOfOrder;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public String getItemCount() {
        return itemCount;
    }

    public void setItemCount(String itemCount) {
        this.itemCount = itemCount;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getTimeOfOrder() {
        return timeOfOrder;
    }

    public void setTimeOfOrder(String timeOfOrder) {
        this.timeOfOrder = timeOfOrder;
    }

    public String getDateOfOrder() {
        return dateOfOrder;
    }

    public void setDateOfOrder(String dateOfOrder) {
        this.dateOfOrder = dateOfOrder;
    }
}
