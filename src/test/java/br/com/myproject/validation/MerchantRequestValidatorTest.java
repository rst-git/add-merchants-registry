package br.com.myproject.validation;

import br.com.myproject.exception.InvalidCnpjException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MerchantRequestValidatorTest {

    private final MerchantRequestValidator validator = new MerchantRequestValidator();

    @Test
    void shouldAcceptValidFormattedNumericCnpj() {
        assertThatCode(() -> validator.validateCnpj("11.222.333/0001-81"))
                .doesNotThrowAnyException();
    }

    @Test
    void shouldAcceptValidAlphanumericCnpj() {
        assertThatCode(() -> validator.validateCnpj("12.ABC.345/01DE-35"))
                .doesNotThrowAnyException();
    }

    @Test
    void shouldRejectInvalidCheckDigits() {
        assertThatThrownBy(() -> validator.validateCnpj("11.222.333/0001-80"))
                .isInstanceOf(InvalidCnpjException.class)
                .hasMessage("CNPJ inválido.");
    }

    @Test
    void shouldRejectRepeatedDigits() {
        assertThatThrownBy(() -> validator.validateCnpj("00.000.000/0000-00"))
                .isInstanceOf(InvalidCnpjException.class);
    }

    @Test
    void shouldRejectNullCnpj() {
        assertThatThrownBy(() -> validator.validateCnpj(null))
                .isInstanceOf(InvalidCnpjException.class);
    }
}