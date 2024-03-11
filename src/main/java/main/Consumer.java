/**
 * 
 */
package main;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.RoundRobinAssignor;
import org.apache.kafka.common.serialization.IntegerDeserializer;

import deserializer.OrderDeserializer;
import dto.Order;

/**
 * 
 */
public class Consumer {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Properties props = new Properties();
		props.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		props.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, IntegerDeserializer.class.getName());
		props.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, OrderDeserializer.class.getName());
		props.setProperty(ConsumerConfig.GROUP_ID_CONFIG, "TruckGroup");
		props.setProperty(ConsumerConfig.FETCH_MIN_BYTES_CONFIG, "1024");
		props.setProperty(ConsumerConfig.FETCH_MAX_WAIT_MS_CONFIG, "200");
		props.setProperty(ConsumerConfig.HEARTBEAT_INTERVAL_MS_CONFIG, "1000");
		props.setProperty(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "3000");
		props.setProperty(ConsumerConfig.MAX_PARTITION_FETCH_BYTES_CONFIG, "1MB");
		props.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
		props.setProperty(ConsumerConfig.CLIENT_ID_CONFIG, "TruckID");
		props.setProperty(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, "100");
		props.setProperty(ConsumerConfig.PARTITION_ASSIGNMENT_STRATEGY_CONFIG, RoundRobinAssignor.class.getName());
		
		try (KafkaConsumer<Integer, Order> consumer = new KafkaConsumer<>(props)) {
			consumer.subscribe(Collections.singletonList("first-topic"));
			
			ConsumerRecords<Integer, Order> records = consumer.poll(Duration.ofSeconds(60));
			
			for(ConsumerRecord<Integer, Order> record : records) {
				Order order = record.value();
				System.out.println("ID - " + order.getId());
				System.out.println("Latitude - " + order.getLatitude());
				System.out.println("Longitude - " + order.getLongitude());
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

}
