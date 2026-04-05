package com.afidu.clientadmin.service;

import com.afidu.clientadmin.dto.AdvancedSearchCriteria;
import com.afidu.clientadmin.dto.CreateClientRequest;
import com.afidu.clientadmin.model.Client;
import com.afidu.clientadmin.repository.InMemoryClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


/**
 * En la clase ClientServiceTest se validan los casos de prueba relacionados con la lógica de negocio implementada para la gestión de clientes.
 *
 * @author hsgdevelop
 * @fechaCreacion 04/04/2026
 * @version 1.0.0
 */

class ClientServiceTest {

    private ClientService clientService;

    @BeforeEach
    void setUp() {
        InMemoryClientRepository repository = new InMemoryClientRepository();
        repository.seed(List.of(
                new Client(UUID.randomUUID(), "jgutierrez", "Juliana Gutierrez", "jgutierrez@gmail.com", "3219876543",
                        LocalDate.of(2025, 1, 1), LocalDate.of(2026, 12, 31), LocalDate.of(2019, 5, 20)),
                new Client(UUID.randomUUID(), "aruiz", "Ana Ruiz", "aruiz@gmail.com", "3001112233",
                        LocalDate.of(2025, 3, 1), LocalDate.of(2026, 12, 31), LocalDate.of(2019, 5, 20))
        ));
        clientService = new ClientService(repository);
    }

    @Test
    void shouldListClientsBySharedKey() {
        List<Client> result = clientService.listClients("jgut");
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getSharedKey()).isEqualTo("jgutierrez");
    }

    @Test
    void shouldCreateClientWithGeneratedSharedKey() {
        CreateClientRequest request = new CreateClientRequest();
        request.setName("Carlos Perez");
        request.setEmail("carlos@gmail.com");
        request.setPhone("3001234567");
        request.setStartDate(LocalDate.of(2026, 1, 1));
        request.setEndDate(LocalDate.of(2026, 12, 31));

        Client created = clientService.createClient(request);

        assertThat(created.getSharedKey()).startsWith("cperez");
        assertThat(created.getEmail()).isEqualTo("carlos@gmail.com");
    }

    @Test
    void shouldRejectInvalidDateRange() {
        CreateClientRequest request = new CreateClientRequest();
        request.setName("Carlos Perez");
        request.setEmail("carlos@gmail.com");
        request.setPhone("3001234567");
        request.setStartDate(LocalDate.of(2026, 12, 31));
        request.setEndDate(LocalDate.of(2026, 1, 1));

        assertThatThrownBy(() -> clientService.createClient(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("End date must be greater than or equal to start date");
    }

    @Test
    void shouldRunAdvancedSearchByName() {
        AdvancedSearchCriteria criteria = new AdvancedSearchCriteria();
        criteria.setName("Ana");

        List<Client> result = clientService.advancedSearch(criteria);

        assertThat(result).isNotEmpty();
        assertThat(result)
                .allMatch(client -> client.getBusinessId().toLowerCase().contains("ana"));
    }
}
