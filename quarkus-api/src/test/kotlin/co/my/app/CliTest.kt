package co.my.app

import co.my.app.model.Debt
import co.my.app.model.PaymentInfo
import co.my.app.service.DebtService
import io.quarkus.test.junit.QuarkusTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import javax.inject.Inject

@QuarkusTest
class CliTest{

    @Test
    fun shouldApplyPaymentInfo() {
        val actual = arrayOf(
                Debt(1, 1.1),
                Debt(2, 2.2, 0.0,false),
                Debt(3, 3.3)
        )
        val expected = arrayOf(
                Debt(1, 1.1),
                Debt(2, 2.2, 1.0, true),
                Debt(3, 3.3)
        )

        DebtService().applyPaymentInfo(
                actual,
                arrayOf(
                        PaymentInfo(1, 1.1),
                        PaymentInfo(2, 1.2)
                )
        )

        assertDebt(expected[0], actual[0])
        assertDebt(expected[0], actual[0])
        assertDebt(expected[0], actual[0])
    }

    private fun assertDebt(expected: Debt, actual: Debt) {
        assertEquals(expected.id, actual.id)
        assertEquals(expected.amount, actual.amount)
        assertEquals(expected.is_in_payment_plan, actual.is_in_payment_plan)
    }
}