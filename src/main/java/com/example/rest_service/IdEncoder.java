package com.example.rest_service;

import com.example.rest_service.Exceptions.InvalidIdException;
import org.springframework.stereotype.Component;

import java.util.Base64;

@Component
public class IdEncoder {
    public static String encode(int interId){
        String raw = "TRS_" + interId;
        return Base64.getEncoder().encodeToString(raw.getBytes());
    }

    public static int decode(String publicId) {
        try {
            String decoded = new String(Base64.getDecoder().decode(publicId));
            if (!decoded.startsWith("TRS_")) {
                throw new InvalidIdException("Invalid ID format");
            }
            return Integer.parseInt(decoded.substring(4));
        } catch (Exception e) {
            throw new InvalidIdException("Invalid transaction ID: " + publicId);
        }
    }
}
