package co.my.app.model

import io.vertx.axle.pgclient.PgPool


class Debt(
        var id: Int = -1,
        var amount: Double = 0.0,
        var remaining_amount: Double = 0.0,
        var is_in_payment_plan: Boolean = false,
        var next_payment_due_date: String? = null
)