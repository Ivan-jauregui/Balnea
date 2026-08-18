package com.balneamdp.service;

import com.balneamdp.exceptions.ressponse.PaymentProcessingException;
import com.mercadopago.client.preference.*;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.preference.Preference;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Service
public class PaymentService {

    @Value("${mercadopago.notification-url}")
    private String notificationUrl;

    public String createPreference(Long reservationId, String resortName, Integer numberBeachTent, BigDecimal price, String userEmail) {
        try {
            // 1. Detalle del ítem a cobrar
            PreferenceItemRequest item = PreferenceItemRequest.builder()
                    .id(reservationId.toString())
                    .title(String.format("Reserva Carpa #%d - %s", numberBeachTent, resortName))
                    .quantity(1)
                    .unitPrice(price)
                    .currencyId("ARS")
                    .build();

            // 2. URLs de retorno post-pago
            PreferenceBackUrlsRequest backUrls = PreferenceBackUrlsRequest.builder()
                    .success("https://balnea.com/reserva/exito")
                    .pending("https://balnea.com/reserva/pendiente")
                    .failure("https://balnea.com/reserva/fallo")
                    .build();

            // 3. Datos del pagador
            PreferencePayerRequest payer = PreferencePayerRequest.builder()
                    .email(userEmail)
                    .build();

            // 4. Armado de la solicitud de preferencia
            PreferenceRequest preferenceRequest = PreferenceRequest.builder()
                    .items(List.of(item))
                    .payer(payer)
                    .backUrls(backUrls)
                    .autoReturn("approved")
                    .externalReference(reservationId.toString())
                    .notificationUrl(notificationUrl)
                    .build();

            PreferenceClient client = new PreferenceClient();
            Preference preference = client.create(preferenceRequest);

            return preference.getInitPoint();

        } catch (MPApiException e) {
            log.error("Error devuelto por la API de Mercado Pago [HTTP {}]: {}",
                    e.getStatusCode(), e.getApiResponse().getContent(), e);
            throw new PaymentProcessingException("Error al comunicarse con la pasarela de pagos.", e);

        } catch (MPException e) {
            log.error("Error interno/conexión del SDK de Mercado Pago para la reserva #{}", reservationId, e);
            throw new PaymentProcessingException("No se pudo establecer conexión con el servicio de pagos.", e);

        } catch (Exception e) {
            log.error("Error inesperado al generar la preferencia de pago para la reserva #{}", reservationId, e);
            throw new PaymentProcessingException("Error inesperado durante la generación de la orden de pago.", e);
        }
    }
}