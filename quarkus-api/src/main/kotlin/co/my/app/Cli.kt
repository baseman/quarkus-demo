import co.my.app.model.Debt
import co.my.app.model.PaymentInfo
import co.my.app.service.DebtService
import co.my.app.service.DebtsClient
import com.fasterxml.jackson.databind.ObjectMapper
import io.quarkus.runtime.QuarkusApplication
import io.quarkus.runtime.annotations.QuarkusMain
import io.vertx.axle.pgclient.PgPool
import org.eclipse.microprofile.rest.client.inject.RestClient
import javax.inject.Inject

@QuarkusMain
class Cli: QuarkusApplication {

    @Inject
    @field: RestClient
    internal lateinit var debtsClient: DebtsClient

    @Inject
    internal lateinit var client: PgPool

    @Inject
    internal lateinit var debtService: DebtService

    private val mapper: ObjectMapper = ObjectMapper()

    @Throws(Exception::class)
    override fun run(vararg args: String?): Int {

        val debtItems = debtsClient.get().sortedBy { it.id }.toTypedArray()

        debtService.applyPaymentInfo(
                debtItems,
                PaymentInfo.findPaymentInfoIn(debtItems = debtItems, client)
        )

        debtItems.forEach {
            println(mapper.writeValueAsString(it))
        }
        return 0
    }
}