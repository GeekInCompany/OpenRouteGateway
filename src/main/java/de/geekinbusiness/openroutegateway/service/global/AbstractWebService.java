package de.geekinbusiness.openroutegateway.service.global;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.geekinbusiness.openroutegateway.config.ApplicationConfig;
import de.geekinbusiness.openroutegateway.schemas.request.in.StructuredQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

@Stateless
public abstract class AbstractWebService<T> {

    private final static ObjectMapper MAPPER = new ObjectMapper();

    private String apikey;

    @Inject
    public ApplicationConfig appConfig;

    private Client client;

    protected String basePath;

    protected WebTarget baseTarget;

    public AbstractWebService(String basePath) {
        this.basePath = basePath;
    }

    //TODO replace with Provider
    @PostConstruct
    public void init() {
        client = ClientBuilder.newClient();
        apikey = appConfig.getApikey();
        baseTarget = client.target(appConfig.getOpenRouteUrl()).path(basePath).queryParam("api_key", apikey);
    }

    @PreDestroy
    public void destroy() {
        client.close();
    }

    protected List<T> genericlistFromResponse(Response response) {
        if (response.getStatus() == 200) {
            List<T> list;
            list = response.readEntity(new GenericType<List<T>>() {
            });
            return list;
        }
        return new ArrayList();
    }

    public String queryToJson(StructuredQuery query) {
        try {
            return MAPPER.writeValueAsString(query);
        } catch (JsonProcessingException ex) {
            Logger.getLogger(AbstractWebService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }

    public String replaceSymbols(String query) {
        query = query.replaceAll("\\{", "%7B");
        query = query.replaceAll("\\|", "%7C");
        query = query.replaceAll("\\}", "%7D");
        query = query.replaceAll("\"", "%22");
        query = query.replaceAll("\\[", "%5B");
        query = query.replaceAll("\\]", "%5D");
        return query;
    }
}
