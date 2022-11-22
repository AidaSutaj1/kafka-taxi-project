package com.github.aidasutaj1.taxispringapp.kafka.consumer;

import com.github.aidasutaj1.taxispringapp.dto.Signal;
import com.github.aidasutaj1.taxispringapp.model.VehicleData;
import com.github.aidasutaj1.taxispringapp.repository.VehicleDataRepository;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.awaitility.Duration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@DirtiesContext
@EmbeddedKafka(partitions = 1, topics = {"${spring.kafka.topic1}", "${spring.kafka.topic2}"})
public class ConsumerIntegrationTest {

    private static final Logger log = LoggerFactory.getLogger(ConsumerIntegrationTest.class);

    @Value("${spring.kafka.topic1}")
    private String INPUT_TOPIC;

    @Value("${spring.kafka.topic2}")
    private String OUTPUT_TOPIC;

    @Autowired
    private EmbeddedKafkaBroker embeddedKafkaBroker;

    @Autowired
    private VehicleDataRepository vehicleDataRepository;

    public Signal mockSignal(Long id, Double latitude, Double longitude) {
        Signal testSignal = new Signal();
        testSignal.setVehicleId(id);
        testSignal.setLatitude(latitude);
        testSignal.setLongitude(longitude);
        return testSignal;
    }

    /**
     * We verify the output in the topic. But aslo in the database.
     */
    @Test
    public void itShould_ConsumeCorrectSignal_from_INPUT_TOPIC_and_should_saveCorrectSignal() throws ExecutionException, InterruptedException {
        // GIVEN
        Signal testSignal = mockSignal(8L, 23.8, 50.34);
        // simulation producer
        Map<String, Object> producerProps = KafkaTestUtils.producerProps(embeddedKafkaBroker.getBrokersAsString());
        log.info("props {}", producerProps);
        ProducerFactory producerFactory = new DefaultKafkaProducerFactory<String, Signal>(producerProps, new StringSerializer(), new JsonSerializer<Signal>());

        ProducerRecord<String, Signal> producerRecord = new ProducerRecord<String, Signal>(INPUT_TOPIC, testSignal.getVehicleId().toString(), testSignal);
        KafkaTemplate<String, Signal> template = new KafkaTemplate<>(producerFactory);
        template.setDefaultTopic(INPUT_TOPIC);
        template.send(producerRecord);


        // Wait until value is present
        await().atMost(Duration.ONE_MINUTE).untilAsserted(() -> {
            List<VehicleData> exampleEntityList = vehicleDataRepository.findAll();
            VehicleData firstEntity = exampleEntityList.get(exampleEntityList.size() - 1);
            assertEquals(testSignal.getLatitude(), firstEntity.getLastLatitude());
            assertEquals(testSignal.getLongitude(), firstEntity.getLastLongitude());
        });
    }
}
