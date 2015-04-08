/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package delivery;

import common.DeliveryRequest;
import common.Status;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 *
 * @author sedog
 */
public class DeliveryRequestCE implements DeliveryRequest, Serializable {
    private static final long serialVersionUID = 123L;
    public static final int DEFAULT_BID_ID = 0;
    public static final int DEFAULT_CUST_ID = 0;
    public static final float DEFAULT_WEIGHT = Float.NaN;
    private int bidID = DEFAULT_BID_ID;
    private int customerID = DEFAULT_CUST_ID;
    private int deliveryRequestID;
    private String description;
    private String dropOffAddress;
    private Timestamp dropOffTime;
    private String pickUpAddress;
    private Timestamp pickUpTime;
    private Timestamp postTime;
    private Timestamp realDropOffTime;
    private Timestamp realPickUpTime;
    private Status status;
    private float weight = DEFAULT_WEIGHT;

    /**
     * @return the bidID
     */
    public int getBidID() {
        return bidID;
    }

    /**
     * @param bidID the bidID to set
     */
    public void setBidID(int bidID) {
        this.bidID = bidID;
    }

    /**
     * @return the customerID
     */
    public int getCustomerID() {
        return customerID;
    }

    /**
     * @param customerID the customerID to set
     */
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    /**
     * @return the deliveryRequestID
     */
    public int getDeliveryRequestID() {
        return deliveryRequestID;
    }

    /**
     * @param deliveryRequestID the deliveryRequestID to set
     */
    public void setDeliveryRequestID(int deliveryRequestID) {
        this.deliveryRequestID = deliveryRequestID;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the dropOffAddress
     */
    public String getDropOffAddress() {
        return dropOffAddress;
    }

    /**
     * @param dropOffAddress the dropOffAddress to set
     */
    public void setDropOffAddress(String dropOffAddress) {
        this.dropOffAddress = dropOffAddress;
    }

    /**
     * @return the dropOffTime
     */
    public Timestamp getDropOffTime() {
        return dropOffTime;
    }

    /**
     * @param dropOffTime the dropOffTime to set
     */
    public void setDropOffTime(Timestamp dropOffTime) {
        this.dropOffTime = dropOffTime;
    }

    /**
     * @return the pickUpAddress
     */
    public String getPickUpAddress() {
        return pickUpAddress;
    }

    /**
     * @param pickUpAddress the pickUpAddress to set
     */
    public void setPickUpAddress(String pickUpAddress) {
        this.pickUpAddress = pickUpAddress;
    }

    /**
     * @return the postTime
     */
    public Timestamp getPostTime() {
        return postTime;
    }

    /**
     * @param postTime the postTime to set
     */
    public void setPostTime(Timestamp postTime) {
        this.postTime = postTime;
    }

    /**
     * @return the realDropOffTime
     */
    public Timestamp getRealDropOffTime() {
        return realDropOffTime;
    }

    /**
     * @param realDropOffTime the realDropOffTime to set
     */
    public void setRealDropOffTime(Timestamp realDropOffTime) {
        this.realDropOffTime = realDropOffTime;
    }

    /**
     * @return the realPickUpTime
     */
    public Timestamp getRealPickUpTime() {
        return realPickUpTime;
    }

    /**
     * @param realPickUpTime the realPickUpTime to set
     */
    public void setRealPickUpTime(Timestamp realPickUpTime) {
        this.realPickUpTime = realPickUpTime;
    }
    
    /**
     * @return the status
     */
    public Status getStatus() {
        return status;
    }

    /**
     * @param weight the weight to set
     */
    public void setStatus(Status status) {
        this.status = status;
    }

    /**
     * @return the weight
     */
    public float getWeight() {
        return weight;
    }

    /**
     * @param weight the weight to set
     */
    public void setWeight(float weight) {
        this.weight = weight;
    }

    /**
     * @return the pickUpTime
     */
    public Timestamp getPickUpTime() {
        return pickUpTime;
    }

    /**
     * @param pickUpTime the pickUpTime to set
     */
    public void setPickUpTime(Timestamp pickUpTime) {
        this.pickUpTime = pickUpTime;
    }
}
