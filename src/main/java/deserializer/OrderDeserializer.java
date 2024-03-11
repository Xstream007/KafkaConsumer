/**
 * 
 */
package deserializer;

import org.apache.kafka.common.serialization.Deserializer;

import com.fasterxml.jackson.databind.ObjectMapper;

import dto.Order;

/**
 * 
 */
public class OrderDeserializer implements Deserializer<Order> {

	@Override
	public Order deserialize(String topic, byte[] data) {
		ObjectMapper mapper = new ObjectMapper();
		Order result = null;
		try {
			result = mapper.readValue(data, Order.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

}
