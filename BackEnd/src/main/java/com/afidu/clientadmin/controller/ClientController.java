package com.afidu.clientadmin.controller;

import com.afidu.clientadmin.dto.AdvancedSearchCriteria;
import com.afidu.clientadmin.dto.CreateClientRequest;
import com.afidu.clientadmin.model.Client;
import com.afidu.clientadmin.service.ClientService;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * En la clase ClientController se exponen los endpoints REST para listar, buscar, crear y exportar clientes.
 *
 * @author hsgdevelop
 * @fechaCreacion 04/04/2026
 * @version 1.0.0
 */

@RestController
@RequestMapping("/api/clients")
@CrossOrigin(origins = "http://localhost:4200")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping
    public List<Client> getClients(@RequestParam(required = false) String sharedKey) {
        return clientService.listClients(sharedKey);
    }

    @GetMapping("/advanced-search")
    public List<Client> advancedSearch(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String phone,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        AdvancedSearchCriteria criteria = new AdvancedSearchCriteria();
        criteria.setName(name);
        criteria.setEmail(email);
        criteria.setPhone(phone);
        criteria.setStartDate(startDate);
        criteria.setEndDate(endDate);
        return clientService.advancedSearch(criteria);
    }

    @PostMapping
    public ResponseEntity<Client> createClient(@Valid @RequestBody CreateClientRequest request) {
        return ResponseEntity.ok(clientService.createClient(request));
    }

    @GetMapping("/export")
    public ResponseEntity<byte[]> exportClients(
            @RequestParam(required = false) String sharedKey,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String phone,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        AdvancedSearchCriteria criteria = new AdvancedSearchCriteria();
        criteria.setName(name);
        criteria.setEmail(email);
        criteria.setPhone(phone);
        criteria.setStartDate(startDate);
        criteria.setEndDate(endDate);

        byte[] csv = clientService.exportCsv(sharedKey, criteria);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=clients.csv")
                .contentType(MediaType.parseMediaType("text/csv"))
                .body(csv);
    }
}
