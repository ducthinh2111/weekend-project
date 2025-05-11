package com.starea.boundary

import com.starea.control.ExecutorService
import jakarta.inject.Inject
import jakarta.ws.rs.Consumes
import jakarta.ws.rs.POST
import jakarta.ws.rs.Path
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response

@Path("/execute")
class ExecutorResource {

    @Inject
    private lateinit var executorService: ExecutorService;

    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    fun execute(code: String): Response {
        val result = executorService.execute(code)
        return Response.ok(result).build()
    }
}