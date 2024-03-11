/**
 * 
 */
package dto;

/**
 * 
 */
public class Order {
	private int id;
	private String latitude;
	private String longitude;

	public Order() {
		super();
	}

	public Order(int id, String latitude, String longitude) {
		super();
		this.id = id;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

}
