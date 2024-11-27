package com.salvai.eldar.controllers.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.salvai.eldar.controllers.OperationController;
import com.salvai.eldar.models.api.OperationDto;
import com.salvai.eldar.models.api.OperationRequest;
import com.salvai.eldar.services.OperationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = OperationController.class)
@AutoConfigureMockMvc(addFilters = false)
class OperationControllerImplTest {

    @MockBean
    private OperationService operationService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void addOperation_whenOk_ThenReturnEmptyBodyAndLocationHeader() throws Exception {
        final var url = "/credit-cards/{creditCardId}/operations";
        final var creditCardId = 1;
        final var amount = 100d;
        final var detail = "details";
        final var cvv = "123";
        final var operationRequest = new OperationRequest(amount, detail, cvv);

        final var operationId = 1;
        final var interestRate = 5d;
        final var total = 105d;
        final var operationDto = new OperationDto(operationId, amount, interestRate, total, detail, creditCardId);

        when(operationService.addOperation(eq(creditCardId), eq(operationRequest))).thenReturn(operationDto);

        mockMvc.perform(post(url, creditCardId).contentType(APPLICATION_JSON).content(objectMapper.writeValueAsString(operationRequest)))
            .andExpect(status().isCreated())
            .andExpect(header().string("location", "http://localhost/credit-cards/1/operations/1"));

        verify(operationService).addOperation(eq(creditCardId), eq(operationRequest));
    }
}