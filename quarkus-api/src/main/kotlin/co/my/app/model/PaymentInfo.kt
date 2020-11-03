package co.my.app.model

import io.vertx.axle.pgclient.PgPool
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


class PaymentInfo(
        val debtId: Int,
        val remaining_amount: Double,
        val next_payment_due_date: String? = null
) {
    companion object {
        fun findPaymentInfoIn(debtItems: Array<Debt>, client: PgPool): Array<PaymentInfo> {

            val inStr = debtItems.joinToString { "${it.id}" }

            return client.query(
                    "select d.id, (pp.amount_to_pay - p.amount) as remaining, pp.start_date " +
                            "from debts d " +
                            "inner join payment_plans pp on pp.debt_id = d.id " +
                            "inner join payments p on p.payment_plan_id = pp.id " +
                            "where d.id IN(${inStr}) order by d.id"
            ).execute().thenApply { rowset ->
                rowset.map {

                    val tz = TimeZone.getTimeZone("UTC")
                    val df: DateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'") // Quoted "Z" to indicate UTC, no timezone offset
                    df.timeZone = tz
                    val epoch: Double? = it.getDouble("start_date")

                    PaymentInfo(
                            debtId = it.getInteger("id"),
                            remaining_amount = it.getDouble("remaining"),
                            next_payment_due_date = if(epoch != null) {
                                df.format(Date(epoch.toLong() * 1000))
                            }
                            else {
                                null
                            }
                    )
                }.toTypedArray()
            }.toCompletableFuture().get()
        }
    }
}
