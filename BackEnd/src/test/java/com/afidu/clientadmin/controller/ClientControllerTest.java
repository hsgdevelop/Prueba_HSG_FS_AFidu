package com.afidu.clientadmin.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 * En la clase ClientControllerTest se validan los casos de prueba asociados al comportamiento de los endpoints expuestos por el controlador de clientes.
 *
 * @author hsgdevelop
 * @fechaCreacion 04/04/2026
 * @version 1.0.0
 */

@SpringBootTest
@AutoConfigureMockMvc
class ClientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturnClientList() throws Exception {
        mockMvc.perform(get("/api/clients"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].sharedKey").exists());
    }

    @Test
    void shouldCreateClient() throws Exception {
        String payload = """
                {
                  "name": "Carlos Perez",
                  "phone": "3001234567",
                  "email": "carlos@gmail.com",
                  "startDate": "2026-01-01",
                  "endDate": "2026-12-31"
                }
                """;

        mockMvc.perform(post("/api/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sharedKey").exists())
                .andExpect(jsonPath("$.email").value("carlos@gmail.com"));
    }

    @Test
    void shouldExportCsv() throws Exception {
        mockMvc.perform(get("/api/clients/export"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/csv"));
    }
}
