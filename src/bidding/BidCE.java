/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bidding;

import common.bidding.Bid;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 *
 * @author sedog
 */
public class BidCE implements Bid, Serializable {
    public final static int DEFAULT_COURIER_ID = 0;
    public final static int DEFAULT_REQ_ID = 0;
    public final static float DEFAULT_FEE = Float.NaN;
    private static final long serialVersionUID = 1234L;
    private int bidID;
    private int courierID = BidCE.DEFAULT_COURIER_ID;
    private int deliveryRequestID = BidCE.DEFAULT_REQ_ID;
    private Timestamp dropOffTime;
    private Timestamp pickUpTime;
    private float fee = BidCE.DEFAULT_FEE;
    private boolean isPendingUpdate;
    private boolean isAccepted;
    
    /**
     * No-args constructor
     */
    public BidCE() {}
    
    /**
     * Handier constructor when all values are known
     * @param courierID     the userID of the courier who created the bid
     * @param reqID         the id of the delivery request this bid is on
     * @param dot           the drop off time of this bid
     * @param pot           the pick up time of this bid
     * @param fee           the fee charged on this bid
     * @param isUpdated     flag indicating this bid is undergoing an update
     * @param isAccepted    flag indicating this bid has been accepted
     */
    public BidCE(int courierID, int reqID, Timestamp dot, Timestamp pot,
            float fee, boolean isUpdated, boolean isAccepted) {
        this.courierID = courierID;
        this.deliveryRequestID = reqID;
        this.dropOffTime = dot;
        this.pickUpTime = pot;
        this.fee = fee;
        this.isPendingUpdate = isUpdated;
        this.isAccepted = isAccepted;
    }

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
     * @return the courierID
     */
    public int getCourierID() {
        return courierID;
    }

    /**
     * @param courierID the courierID to set
     */
    public void setCourierID(int courierID) {
        this.courierID = courierID;
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

    /**
     * @return the fee
     */
    public float getFee() {
        return fee;
    }

    /**
     * @param fee the fee to set
     */
    public void setFee(float fee) {
        this.fee = fee;
    }

    /**
     * @return the isPendingUpdate
     */
    public boolean isPendingUpdate() {
        return isPendingUpdate;
    }

    /**
     * @param isPendingUpdate the isPendingUpdate to set
     */
    public void setIsPendingUpdate(boolean isPendingUpdate) {
        this.isPendingUpdate = isPendingUpdate;
    }

    /**
     * @return the isAccepted
     */
    public boolean isAccepted() {
        return isAccepted;
    }

    /**
     * @param isAccepted the isAccepted to set
     */
    public void setIsAccepted(boolean isAccepted) {
        this.isAccepted = isAccepted;
    }
    
    /**
     * Duplicates the non-unique attributes of this <code>Bid</code> in a
     * new <code>Bid</code> and returns that copy.
     */
    @Override
    public Bid clone() {
        // The duplicate bid to return
        Bid castClone = null;
        // The intermediate cloned object
        Object clone = null;
        
        try {
            clone = super.clone();
            if (clone instanceof Bid) {
                castClone = (Bid) clone;
            }
            
        } catch (CloneNotSupportedException ex) {
           // Never should happen here 
        }
        
        castClone.setBidID(this.getBidID());
        castClone.setCourierID(this.getCourierID());
        castClone.setDeliveryRequestID(this.getDeliveryRequestID());
        castClone.setDropOffTime(this.getDropOffTime());
        castClone.setPickUpTime(this.getPickUpTime());
        castClone.setFee(this.getFee());
        castClone.setIsPendingUpdate(this.isPendingUpdate());
        castClone.setIsAccepted(this.isAccepted());
        
        return castClone;
    }
}
