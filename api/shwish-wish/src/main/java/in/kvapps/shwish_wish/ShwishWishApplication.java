package in.kvapps.shwish_wish;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.ZonedDateTimeSerializer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@SpringBootApplication
public class ShwishWishApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShwishWishApplication.class, args);
	}

	@Bean
	public ObjectMapper getObjectMapper() {
		ObjectMapper objectMapper = new ObjectMapper();

		// Register the JavaTimeModule
		JavaTimeModule module = new JavaTimeModule();

		// Define the default ZonedDateTime format
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss a z");

		// Use the custom serializer with the defined format
		module.addSerializer(ZonedDateTime.class, new ZonedDateTimeSerializer(formatter));

		// Register the module in the ObjectMapper
		objectMapper.registerModule(module);
		objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		return objectMapper;
	}
}
