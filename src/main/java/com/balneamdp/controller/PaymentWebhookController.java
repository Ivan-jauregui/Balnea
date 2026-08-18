package com.balneamdp.controller;

import com.balneamdp.service.PaymentWebhookService;
import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.payment.Payment;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
public class PaymentWebhookController {
    private final PaymentWebhookService paymentWebhookService;

    @PostMapping("/webhook")
    public ResponseEntity<Void> handleWebhook(
            @RequestParam(value = "data.id", required = false) Long dataId,
            @RequestParam(value = "type", required = false) String type) throws MPException, MPApiException {

        if ("payment".equals(type) && dataId != null) {
            // Le pasamos el ID del pago al servicio
            paymentWebhookService.processNotification(dataId);
        }

        // Siempre responder 200 OK a MP para confirmar recepción
        return ResponseEntity.ok().build();
    }
}

