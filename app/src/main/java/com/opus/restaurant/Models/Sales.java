package com.opus.restaurant.Models;

/**
 * Created by Admin on 12/18/2017.
 */
public class Sales {

    public int id;
    public String localId;
    public String kotNo;
    public String invoiceDate;
    public String invoiceNo;
    public String tableNo;
    public String noofPersons;
    public String billAmount;
    public String gstAmount;
    public String extraCharges;
    public String totalAmount;
    public String discountAmount;
    public String payableAmount;
    public String givenAmount;
    public String balanceAmount;
    public String modifiedBy;
    public String modifiedDate;
    public String waiter;

    public String getWaiter() { return waiter; }

    public void setWaiter(String waiter) { this.waiter = waiter; }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLocalId() {
        return localId;
    }

    public void setLocalId(String localId) {
        this.localId = localId;
    }

    public String getKotNo() {
        return kotNo;
    }

    public void setKotNo(String kotNo) {
        this.kotNo = kotNo;
    }

    public String getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(String invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public String getInvoiceNo() { return invoiceNo; }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public String getTableNo() {
        return tableNo;
    }

    public void setTableNo(String tableNo) {
        this.tableNo = tableNo;
    }

    public String getNoofPersons() {
        return noofPersons;
    }

    public void setNoofPersons(String noofPersons) {
        this.noofPersons = noofPersons;
    }

    public String getBillAmount() {
        return billAmount;
    }

    public void setBillAmount(String billAmount) {
        this.billAmount = billAmount;
    }

    public String getGstAmount() {
        return gstAmount;
    }

    public void setGstAmount(String gstAmount) {
        this.gstAmount = gstAmount;
    }

    public String getExtraCharges() {
        return extraCharges;
    }

    public void setExtraCharges(String extraCharges) {
        this.extraCharges = extraCharges;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(String discountAmount) {
        this.discountAmount = discountAmount;
    }

    public String getPayableAmount() {
        return payableAmount;
    }

    public void setPayableAmount(String payableAmount) {
        this.payableAmount = payableAmount;
    }

    public String getGivenAmount() {
        return givenAmount;
    }

    public void setGivenAmount(String givenAmount) {
        this.givenAmount = givenAmount;
    }

    public String getBalanceAmount() {
        return balanceAmount;
    }

    public void setBalanceAmount(String balanceAmount) {
        this.balanceAmount = balanceAmount;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public String getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(String modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Sales(String localId, String kotNo, String invoiceDate, String invoiceNo, String tableNo, String noofPersons, String billAmount, String gstAmount, String extraCharges, String totalAmount, String discountAmount, String payableAmount, String givenAmount, String balanceAmount,String waiter, String modifiedBy, String modifiedDate) {
        this.localId = localId;
        this.kotNo = kotNo;
        this.invoiceDate = invoiceDate;
        this.invoiceNo = invoiceNo;
        this.tableNo = tableNo;
        this.noofPersons = noofPersons;
        this.billAmount = billAmount;
        this.gstAmount = gstAmount;
        this.extraCharges = extraCharges;
        this.totalAmount = totalAmount;
        this.discountAmount = discountAmount;
        this.payableAmount = payableAmount;
        this.givenAmount = givenAmount;
        this.balanceAmount = balanceAmount;
        this.waiter = waiter;
        this.modifiedBy = modifiedBy;
        this.modifiedDate = modifiedDate;
    }

    public Sales(){  }
}
