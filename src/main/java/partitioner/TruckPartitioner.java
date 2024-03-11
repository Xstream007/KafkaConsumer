/**
 * 
 */
package partitioner;

import java.util.List;
import java.util.Map;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.utils.Utils;

import dto.Order;

/**
 * 
 */
public class TruckPartitioner implements Partitioner {

	@Override
	public void configure(Map<String, ?> configs) {

	}

	@Override
	public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster) {
		List<PartitionInfo> partitions = cluster.availablePartitionsForTopic(topic);
		
		Order order = (Order) value;
		
		if(order.getLatitude().equals("37.2431") && order.getLongitude().equals("115.793")) {
			return 5;
		}
		
		return Math.abs(Utils.murmur2(keyBytes)%partitions.size() - 1);
	}

	@Override
	public void close() {

	}

}
