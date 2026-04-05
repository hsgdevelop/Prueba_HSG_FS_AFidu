package com.afidu.clientadmin.repository;

import com.afidu.clientadmin.dto.AdvancedSearchCriteria;
import com.afidu.clientadmin.model.Client;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;


/**
 * En la clase InMemoryClientRepository se administra el almacenamiento temporal en memoria para la consulta y registro de clientes.
 *
 * @author hsgdevelop
 * @fechaCreacion 04/04/2026
 * @version 1.0.0
 */

@Repository
public class InMemoryClientRepository {

    private final ConcurrentHashMap<UUID, Client> storage = new ConcurrentHashMap<>();

    public List<Client> findAll() {
        return storage.values().stream()
                .sorted(Comparator.comparing(Client::getDateAdded).reversed()
                        .thenComparing(Client::getBusinessId))
                .collect(Collectors.toList());
    }

    public Client save(Client client) {
        storage.put(client.getId(), client);
        return client;
    }

    public List<Client> findBySharedKey(String sharedKey) {
        if (sharedKey == null || sharedKey.isBlank()) {
            return findAll();
        }

        String query = sharedKey.trim().toLowerCase();
        return storage.values().stream()
                .filter(client -> client.getSharedKey() != null && client.getSharedKey().toLowerCase().contains(query))
                .sorted(Comparator.comparing(Client::getDateAdded).reversed())
                .collect(Collectors.toList());
    }

    public List<Client> advancedSearch(AdvancedSearchCriteria criteria) {
        return storage.values().stream()
                .filter(client -> containsIgnoreCase(client.getBusinessId(), criteria.getName()))
                .filter(client -> containsIgnoreCase(client.getEmail(), criteria.getEmail()))
                .filter(client -> containsIgnoreCase(client.getPhone(), criteria.getPhone()))
                .filter(client -> criteria.getStartDate() == null || !client.getStartDate().isBefore(criteria.getStartDate()))
                .filter(client -> criteria.getEndDate() == null || !client.getEndDate().isAfter(criteria.getEndDate()))
                .sorted(Comparator.comparing(Client::getDateAdded).reversed())
                .collect(Collectors.toList());
    }

    public boolean existsBySharedKey(String sharedKey) {
        return storage.values().stream()
                .anyMatch(client -> client.getSharedKey().equalsIgnoreCase(sharedKey));
    }

    public int countByBaseSharedKeyPrefix(String baseSharedKey) {
        return (int) storage.values().stream()
                .filter(client -> client.getSharedKey() != null && client.getSharedKey().startsWith(baseSharedKey))
                .count();
    }

    public void seed(List<Client> clients) {
        List<Client> current = new ArrayList<>(clients);
        current.forEach(client -> storage.put(client.getId(), client));
    }

    private boolean containsIgnoreCase(String source, String query) {
        if (query == null || query.isBlank()) {
            return true;
        }
        if (source == null) {
            return false;
        }
        return source.toLowerCase().contains(query.trim().toLowerCase());
    }
}
