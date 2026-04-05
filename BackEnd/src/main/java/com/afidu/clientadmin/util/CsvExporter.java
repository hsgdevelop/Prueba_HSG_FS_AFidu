package com.afidu.clientadmin.util;

import com.afidu.clientadmin.model.Client;

import java.nio.charset.StandardCharsets;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * En la clase CsvExporter se construye la exportación de clientes en formato CSV.
 *
 * @author hsgdevelop
 * @fechaCreacion 04/04/2026
 * @version 1.0.0
 */

public final class CsvExporter {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_DATE;

    private CsvExporter() {
    }

    public static byte[] exportClients(List<Client> clients) {
        StringBuilder builder = new StringBuilder();
        builder.append("sharedKey,businessId,email,phone,startDate,endDate,dateAdded")
                .append(System.lineSeparator());

        for (Client client : clients) {
            builder.append(escape(client.getSharedKey())).append(',')
                    .append(escape(client.getBusinessId())).append(',')
                    .append(escape(client.getEmail())).append(',')
                    .append(escape(client.getPhone())).append(',')
                    .append(formatDate(client.getStartDate())).append(',')
                    .append(formatDate(client.getEndDate())).append(',')
                    .append(formatDate(client.getDateAdded()))
                    .append(System.lineSeparator());
        }

        return builder.toString().getBytes(StandardCharsets.UTF_8);
    }

    private static String formatDate(java.time.LocalDate date) {
        return date == null ? "" : date.format(FORMATTER);
    }

    private static String escape(String value) {
        if (value == null) {
            return "";
        }
        String escaped = value.replace("\"", "\"\"");
        return '"' + escaped + '"';
    }
}
