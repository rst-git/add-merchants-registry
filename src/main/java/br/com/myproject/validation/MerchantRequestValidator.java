package br.com.myproject.validation;

import br.com.myproject.exception.InvalidCnpjException;
import java.util.Locale;
import org.springframework.stereotype.Component;

@Component
public class MerchantRequestValidator {

    private static final int[] FIRST_WEIGHTS = {5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
    private static final int[] SECOND_WEIGHTS = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
    private static final String INVALID_CNPJ_MESSAGE = "CNPJ inválido.";

    public void validateCnpj(String cnpj) {
        if (cnpj == null) {
            throw new InvalidCnpjException(INVALID_CNPJ_MESSAGE);
        }

        String normalizedCnpj = cnpj.trim()
                .replace(".", "")
                .replace("/", "")
                .replace("-", "")
                .toUpperCase(Locale.ROOT);

        if (!normalizedCnpj.matches("[A-Z0-9]{12}[0-9]{2}")
                || normalizedCnpj.substring(0, 12).chars().distinct().count() == 1) {
            throw new InvalidCnpjException(INVALID_CNPJ_MESSAGE);
        }

        int firstCheckDigit = calculateCheckDigit(normalizedCnpj, FIRST_WEIGHTS);
        int secondCheckDigit = calculateCheckDigit(normalizedCnpj, SECOND_WEIGHTS);

        if (normalizedCnpj.charAt(12) - '0' != firstCheckDigit
                || normalizedCnpj.charAt(13) - '0' != secondCheckDigit) {
            throw new InvalidCnpjException(INVALID_CNPJ_MESSAGE);
        }
    }

    private int calculateCheckDigit(String cnpj, int[] weights) {
        int sum = 0;
        for (int index = 0; index < weights.length; index++) {
            char character = cnpj.charAt(index);
            int value = Character.isDigit(character)
                    ? character - '0'
                    : character - 'A' + 17;
            sum += value * weights[index];
        }

        int remainder = sum % 11;
        return remainder < 2 ? 0 : 11 - remainder;
    }
}