package rating;

import java.io.Serializable;


public interface Rating extends Serializable {
	
	public int getRatingID();	
	public void setRatingID(int ratingID);
	
	public int getDeliveryRequestID();
	public void setDeliveryRequestID(int deliveryRequestID);
	
	public int getCustomerOverallRating();
	public void setCustomerOverallRating(int customerOverallRating);
	
	public int getCustomerProfesionalismRating();
	public void setCustomerProfesionalismRating(int customerProfesionalismRating);
	
	public int getCustomerDeliveryPersonRating();
	public void setCustomerDeliveryPersonRating(int customerDeliveryPersonRating);
}
