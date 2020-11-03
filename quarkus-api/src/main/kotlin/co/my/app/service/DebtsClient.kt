package co.my.app.service

import co.my.app.model.Debt
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces

@Path("/druska/trueaccord-mock-payments-api")
@RegisterRestClient(baseUri = "https://my-json-server.typicode.com")
interface DebtsClient {
    @GET
    @Path("/debts")
    @Produces("application/json")
    fun get(): List<Debt>
}