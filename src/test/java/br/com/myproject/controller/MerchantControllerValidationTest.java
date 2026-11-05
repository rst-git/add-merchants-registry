package br.com.myproject.controller;

import br.com.myproject.exception.GlobalExceptionHandler;
import br.com.myproject.service.MerchantServiceImpl;
import br.com.myproject.validation.MerchantRequestValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

class MerchantControllerValidationTest {

    private MerchantServiceImpl merchantService;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        merchantService = mock(MerchantServiceImpl.class);
        MerchantController controller = new MerchantController(
                merchantService,
                new MerchantRequestValidator());
        mockMvc = standaloneSetup(controller)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void shouldReturnBadRequestForInvalidCnpjOnSave() throws Exception {
        mockMvc.perform(post("/merchants")
                        .contentType(APPLICATION_JSON)
                        .content("{\"cnpj\":\"11.222.333/0001-80\",\"razaoSocial\":\"Padaria Central Ltda\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.detail").value("CNPJ inválido."));

        verifyNoInteractions(merchantService);
    }

    @Test
    void shouldReturnBadRequestForInvalidCnpjOnLookup() throws Exception {
        mockMvc.perform(get("/merchants/00000000000000"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.detail").value("CNPJ inválido."));

        verifyNoInteractions(merchantService);
    }
}