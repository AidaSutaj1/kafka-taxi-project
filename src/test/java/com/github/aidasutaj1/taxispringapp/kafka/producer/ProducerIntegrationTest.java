package com.github.aidasutaj1.taxispringapp.kafka.producer;

import com.github.aidasutaj1.taxispringapp.dto.Signal;
import com.github.aidasutaj1.taxispringapp.model.VehicleData;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@DirtiesContext
@EmbeddedKafka(topics = {"${spring.kafka.topic1}", "${spring.kafka.topic2}"})
public class ProducerIntegrationTest {

    @Value("${spring.kafka.topic1}")
    private String INPUT_TOPIC;

    @Value("${spring.kafka.topic2}")
    private String OUTPUT_TOPIC;

    @Autowired
    private EmbeddedKafkaBroker embeddedKafkaBroker;

    @Autowired
    private KafkaProducer kafkaProducer;

    /**
     * We verify the output in the topic. With an simulated consumer.
     */

    public Signal mockSignal(Long id, Double latitude, Double longitude) {
        Signal testSignal = new Signal();
        testSignal.setVehicleId(id);
        testSignal.setLatitude(latitude);
        testSignal.setLongitude(longitude);
        return testSignal;
    }

    public VehicleData mockVehicleData(Long id, Double lastLatitude, Double lastLongitude, Double distance, LocalDateTime timeStamp) {
        return new VehicleData(id, lastLatitude, lastLongitude, distance, timeStamp);
    }

    @Test
    public void checkIf_CorrectSignal_IsProduced_to_INPUT_TOPIC() {
        // GIVEN
        Signal testSignal = mockSignal(8L, 23.8, 50.34);
        // simulation consumer
        Map<String, Object> consumerProps = KafkaTestUtils.consumerProps("myGroup", "false", embeddedKafkaBroker);
        consumerProps.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        ConsumerFactory cf = new DefaultKafkaConsumerFactory<String, Signal>(consumerProps, new StringDeserializer(), new JsonDeserializer<>(Signal.class, false));
        Consumer<String, Signal> consumerServiceTest = cf.createConsumer();

        embeddedKafkaBroker.consumeFromAnEmbeddedTopic(consumerServiceTest, INPUT_TOPIC);
        // WHEN
        kafkaProducer.publishMessage(INPUT_TOPIC, testSignal.getVehicleId().toString(), testSignal);
        // THEN
        ConsumerRecord<String, Signal> consumerRecordOfSignal = KafkaTestUtils.getSingleRecord(consumerServiceTest, INPUT_TOPIC);
        String keyReceived = consumerRecordOfSignal.key();
        Signal valueReceived = consumerRecordOfSignal.value();
        assertEquals(testSignal.getVehicleId().toString(), keyReceived);
        assertEquals(testSignal, valueReceived);
        consumerServiceTest.close();
    }

    @Test
    public void checkIf_CorrectVehicleData_IsProduced_to_OUTPUT_TOPIC() {
        // GIVEN
        VehicleData testVehicleData = mockVehicleData(3L, 78.2, 34.9, 23467.1, LocalDateTime.now());
        // simulation consumer
        Map<String, Object> consumerProps = KafkaTestUtils.consumerProps("group2", "false", embeddedKafkaBroker);
        consumerProps.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        ConsumerFactory cf = new DefaultKafkaConsumerFactory<String, VehicleData>(consumerProps, new StringDeserializer(), new JsonDeserializer<>(VehicleData.class, false));
        Consumer<String, VehicleData> consumerServiceTest = cf.createConsumer();

        embeddedKafkaBroker.consumeFromAnEmbeddedTopic(consumerServiceTest, OUTPUT_TOPIC);
        // WHEN
        kafkaProducer.publishMessage(OUTPUT_TOPIC, testVehicleData.getVehicleId().toString(), testVehicleData);
        // THEN
        ConsumerRecord<String, VehicleData> consumerRecordOfVehicleData = KafkaTestUtils.getSingleRecord(consumerServiceTest, OUTPUT_TOPIC);
        String keyReceived = consumerRecordOfVehicleData.key();
        VehicleData valueReceived = consumerRecordOfVehicleData.value();
        assertEquals(testVehicleData.getVehicleId().toString(), keyReceived);
        assertEquals(testVehicleData, valueReceived);
        consumerServiceTest.close();
    }

}
