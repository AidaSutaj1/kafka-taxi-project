package com.github.aidasutaj1.taxispringapp;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@ExtendWith(SpringExtension.class)
@DirtiesContext
@SpringBootTest
@EmbeddedKafka
class TaxiSpringAppApplicationTests {

	@Test
	void contextLoads() {
		assertDoesNotThrow(() -> TaxiSpringAppApplication.main(new String[]{}));
	}

}
