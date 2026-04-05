package com.afidu.clientadmin.util;

import java.text.Normalizer;
import java.util.Locale;

/**
 * En la clase SharedKeyGenerator se genera la shared key asociada a cada cliente según la lógica definida para el demo.
 *
 * @author hsgdevelop
 * @fechaCreacion 04/04/2026
 * @version 1.0.0
 */

public final class SharedKeyGenerator {

    private SharedKeyGenerator() {
    }

    public static String generateBaseSharedKey(String name) {
        if (name == null || name.isBlank()) {
            return "client";
        }

        String normalized = Normalizer.normalize(name, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "")
                .toLowerCase(Locale.ROOT)
                .replaceAll("[^a-z0-9 ]", " ")
                .trim()
                .replaceAll("\\s+", " ");

        String[] parts = normalized.split(" ");
        if (parts.length == 1) {
            return truncate(parts[0], 20);
        }

        String first = parts[0];
        String last = parts[parts.length - 1];
        return truncate(first.substring(0, 1) + last, 20);
    }

    public static String appendSequence(String base, int sequence) {
        if (sequence <= 1) {
            return base;
        }
        String suffix = String.valueOf(sequence);
        int maxBaseLength = Math.max(1, 20 - suffix.length());
        return truncate(base, maxBaseLength) + suffix;
    }

    private static String truncate(String value, int max) {
        return value.length() <= max ? value : value.substring(0, max);
    }
}
