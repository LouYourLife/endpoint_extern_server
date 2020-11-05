package rest;

import com.google.gson.Gson;
import dtos.ChuckDTO;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;
import jokefetcher.JokeFetcher;
import utils.HttpUtils;

/**
 * REST Web Service
 *
 * @author lam
 */
@Path("jokes")
public class JokeResource {

    @Context
    private UriInfo context;

   
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getJokes() throws IOException, InterruptedException, ExecutionException {
        //return HttpUtils.jokeFetch();
        return HttpUtils.fetchDataParallel();
    }

   
}
