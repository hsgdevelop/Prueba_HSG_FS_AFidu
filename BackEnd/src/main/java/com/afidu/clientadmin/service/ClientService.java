package com.afidu.clientadmin.service;

import com.afidu.clientadmin.dto.AdvancedSearchCriteria;
import com.afidu.clientadmin.dto.CreateClientRequest;
import com.afidu.clientadmin.model.Client;
import com.afidu.clientadmin.repository.InMemoryClientRepository;
import com.afidu.clientadmin.util.CsvExporter;
import com.afidu.clientadmin.util.SharedKeyGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;


/**
 * En la clase ClientService se implementa la lógica de negocio para listar, buscar, crear y exportar clientes.
 *
 * @author hsgdevelop
 * @fechaCreacion 04/04/2026
 * @version 1.0.0
 */

@Service
public class ClientService {

    private static final Logger log = LoggerFactory.getLogger(ClientService.class);

    private final InMemoryClientRepository repository;

    public ClientService(InMemoryClientRepository repository) {
        this.repository = repository;
    }

    public List<Client> listClients(String sharedKey) {
        log.info("Listing clients with sharedKey filter [{}]", sharedKey);
        return repository.findBySharedKey(sharedKey);
    }

    public List<Client> advancedSearch(AdvancedSearchCriteria criteria) {
        validateDateRange(criteria.getStartDate(), criteria.getEndDate());
        log.info("Advanced search name=[{}], email=[{}], phone=[{}], startDate=[{}], endDate=[{}]",
                criteria.getName(), criteria.getEmail(), criteria.getPhone(), criteria.getStartDate(), criteria.getEndDate());
        return repository.advancedSearch(criteria);
    }

    public Client createClient(CreateClientRequest request) {
        validateDateRange(request.getStartDate(), request.getEndDate());

        String baseSharedKey = SharedKeyGenerator.generateBaseSharedKey(request.getName());
        int sequence = repository.countByBaseSharedKeyPrefix(baseSharedKey) + 1;
        String candidate = SharedKeyGenerator.appendSequence(baseSharedKey, sequence == 1 && !repository.existsBySharedKey(baseSharedKey) ? 1 : sequence);

        while (repository.existsBySharedKey(candidate)) {
            sequence++;
            candidate = SharedKeyGenerator.appendSequence(baseSharedKey, sequence);
        }

        Client client = new Client(
                UUID.randomUUID(),
                candidate,
                request.getName().trim(),
                request.getEmail().trim().toLowerCase(),
                request.getPhone().trim(),
                request.getStartDate(),
                request.getEndDate(),
                LocalDate.now()
        );

        repository.save(client);
        log.info("Client created with id [{}] and sharedKey [{}]", client.getId(), client.getSharedKey());
        return client;
    }

    public byte[] exportCsv(String sharedKey, AdvancedSearchCriteria criteria) {
        List<Client> clients = (sharedKey != null && !sharedKey.isBlank())
                ? listClients(sharedKey)
                : advancedSearch(criteria);
        log.info("Exporting [{}] clients to CSV", clients.size());
        return CsvExporter.exportClients(clients);
    }

    private void validateDateRange(LocalDate startDate, LocalDate endDate) {
        if (startDate != null && endDate != null && endDate.isBefore(startDate)) {
            throw new IllegalArgumentException("End date must be greater than or equal to start date");
        }
    }
}
