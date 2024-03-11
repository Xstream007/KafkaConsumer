/**
 * 
 */
package avro;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import avro.pojo.TruckPojo;
import io.confluent.kafka.serializers.KafkaAvroDeserializer;

/**
 * 
 */
public class ConsumerAvro {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Properties props = new Properties();
		props.setProperty("bootstrap.servers", "localhost:9092");
		props.setProperty("key.deserializer", KafkaAvroDeserializer.class.getName());
		props.setProperty("value.deserializer", KafkaAvroDeserializer.class.getName());
		props.setProperty("group.id", "TruckGroup");
		props.setProperty("schema.registry.url", "http://localhost:8081");
		props.setProperty("specific.avro.reader", "true");
		
		try(KafkaConsumer<Integer, TruckPojo> consumer = new KafkaConsumer<>(props)) {
			while(true) {
				consumer.subscribe(Collections.singleton("TruckAvroTopic"));
				ConsumerRecords<Integer, TruckPojo> records = consumer.poll(Duration.ofSeconds(20));
				
				for(ConsumerRecord<Integer, TruckPojo> record : records) {
					TruckPojo truck = record.value();
					System.out.println("ID - " + truck.getId());
					System.out.println("Latitude - " + truck.getLatitude().toString());
					System.out.println("Longitude - " + truck.getLongitude().toString());
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

}
