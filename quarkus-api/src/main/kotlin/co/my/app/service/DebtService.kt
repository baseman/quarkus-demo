package co.my.app.service

import co.my.app.model.Debt
import co.my.app.model.PaymentInfo
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class DebtService {
    fun applyPaymentInfo(debtItems: Array<Debt>, paymentInfoItems: Array<PaymentInfo>) {
        debtItems.forEach { debtItem ->

            paymentInfoItems.forEach { paymentInfoItem ->
                if (paymentInfoItem.debtId == debtItem.id) {
                    debtItem.is_in_payment_plan = true
                    debtItem.remaining_amount = paymentInfoItem.remaining_amount
                }

                if (paymentInfoItem.debtId == debtItem.id && debtItem.remaining_amount != 0.0) {
                    debtItem.next_payment_due_date = paymentInfoItem.next_payment_due_date
                }
            }


            val debtIdVals = paymentInfoItems.map { it.debtId }
            if (debtIdVals.contains(debtItem.id)) {
                debtItem.is_in_payment_plan = true
            }
        }
    }

}
