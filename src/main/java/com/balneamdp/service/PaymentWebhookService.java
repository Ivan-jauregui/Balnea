package com.balneamdp.service;

import com.balneamdp.enums.PayState;
import com.balneamdp.exceptions.ResourseNotFoundException;
import com.balneamdp.models.Reservation;
import com.balneamdp.repository.ReservationRepository;
import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.payment.Payment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class PaymentWebhookService {
    private final ReservationRepository reservationRepository;

    public void processNotification(Long paymentId) throws MPException, MPApiException {
        PaymentClient client = new PaymentClient();
        Payment payment = client.get(paymentId);

        String extenalRef = payment.getExternalReference();
        Long reservationId = Long.parseLong(extenalRef);

        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(()-> new ResourseNotFoundException("Reserva no encontrada"));

        if("approved".equals(payment.getStatus())){

            BigDecimal amountPaid = payment.getTransactionAmount();

            if(amountPaid.compareTo(reservation.getTotal())>=0){
                reservation.setPayState(PayState.PAGADO);
            }else{
                reservation.setPayState(PayState.SEÑADO);
            }
        }
    }
}
