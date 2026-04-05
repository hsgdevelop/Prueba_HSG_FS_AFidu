package com.afidu.clientadmin.data;

import com.afidu.clientadmin.model.Client;
import com.afidu.clientadmin.repository.InMemoryClientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;


/**
 * En la clase DataSeeder se cargan los datos iniciales de clientes al momento de iniciar la aplicación.
 *
 * @author hsgdevelop
 * @fechaCreacion 04/04/2026
 * @version 1.0.0
 */

@Configuration
public class DataSeeder {

    private static final Logger log = LoggerFactory.getLogger(DataSeeder.class);

    @Bean
    CommandLineRunner seedClients(InMemoryClientRepository repository) {
        return args -> {
            repository.seed(List.of(
                    new Client(UUID.randomUUID(), "jgutierrez", "Juliana Gutierrez", "jgutierrez@gmail.com", "3219876543",
                            LocalDate.of(2025, 1, 1), LocalDate.of(2026, 12, 31), LocalDate.of(2019, 5, 20)),
                    new Client(UUID.randomUUID(), "mmartinez", "Manuel Martinez", "mmartinez@gmail.com", "3219876543",
                            LocalDate.of(2025, 2, 1), LocalDate.of(2026, 12, 31), LocalDate.of(2019, 5, 20)),
                    new Client(UUID.randomUUID(), "aruiz", "Ana Ruiz", "aruiz@gmail.com", "3219876543",
                            LocalDate.of(2025, 3, 1), LocalDate.of(2026, 12, 31), LocalDate.of(2019, 5, 20)),
                    new Client(UUID.randomUUID(), "ogarcia", "Oscar Garcia", "ogarcia@gmail.com", "3219876543",
                            LocalDate.of(2025, 4, 1), LocalDate.of(2026, 12, 31), LocalDate.of(2019, 5, 20)),
                    new Client(UUID.randomUUID(), "tramos", "Tania Ramos", "tramos@gmail.com", "3219876543",
                            LocalDate.of(2025, 5, 1), LocalDate.of(2026, 12, 31), LocalDate.of(2019, 5, 20)),
                    new Client(UUID.randomUUID(), "cariza", "Carlos Ariza", "cariza@gmail.com", "3219876543",
                            LocalDate.of(2025, 6, 1), LocalDate.of(2026, 12, 31), LocalDate.of(2019, 5, 20)),
                    new Client(UUID.randomUUID(), "rvillaneda", "Rodrigo Villaneda", "rvillaneda@gmail.com", "3219876543",
                            LocalDate.of(2025, 7, 1), LocalDate.of(2026, 12, 31), LocalDate.of(2019, 5, 20)),
                    new Client(UUID.randomUUID(), "mfonseca", "Mauricio Fonseca", "mfonseca@gmail.com", "3219876543",
                            LocalDate.of(2025, 8, 1), LocalDate.of(2026, 12, 31), LocalDate.of(2019, 5, 20))
            ));
            log.info("Seed data loaded successfully");
        };
    }
}
