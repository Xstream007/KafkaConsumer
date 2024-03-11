/**
 * 
 */
package main;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.IntegerDeserializer;

import deserializer.OrderDeserializer;
import dto.Order;

/**
 * 
 */
public class SimpleConsumer {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Properties props = new Properties();
		props.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		props.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, IntegerDeserializer.class.getName());
		props.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, OrderDeserializer.class.getName());
		props.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
		
		try (KafkaConsumer<Integer, Order> consumer = new KafkaConsumer<>(props)) {
			List<PartitionInfo> partitionInfo = consumer.partitionsFor("first-topic");
			
			List<TopicPartition> partitions = new ArrayList<>();
			for(PartitionInfo partition : partitionInfo) {
				partitions.add(new TopicPartition("first-topic", partition.partition()));
			}
			
			consumer.assign(new ArrayList<>());
			
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
