package de.geekinbusiness.openroutegateway.service;

import de.geekinbusiness.openroutegateway.schemas.response.geocoding.GeoCodingResponse;
import de.geekinbusiness.openroutegateway.schemas.request.in.StructuredQuery;
import de.geekinbusiness.openroutegateway.service.global.AbstractWebService;
import java.util.Optional;
import javax.ejb.Stateless;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Stateless
public class GeocodingService extends AbstractWebService<Object> {

    public GeocodingService() {
        super("geocoding");
    }

    public Optional<GeoCodingResponse> findByString(String simplequery) {
        WebTarget employeeWebTarget = baseTarget.queryParam("query", simplequery);
        Invocation.Builder invocationBuilder = employeeWebTarget.request(MediaType.APPLICATION_JSON);
        Response response = invocationBuilder.get();
        if (response.getStatus() == 200) {
            GeoCodingResponse state = response.readEntity(GeoCodingResponse.class);
            return Optional.of(state);
        }
        return Optional.empty();
    }

    public Optional<GeoCodingResponse> reverseGeocoding(String longitude, String latitude) {
        WebTarget employeeWebTarget = baseTarget.queryParam("location", longitude + "," + latitude);
        Invocation.Builder invocationBuilder = employeeWebTarget.request(MediaType.APPLICATION_JSON);
        Response response = invocationBuilder.get();
        if (response.getStatus() == 200) {
            GeoCodingResponse state = response.readEntity(GeoCodingResponse.class);
            return Optional.of(state);
        } else {
            System.out.println(response);
        }
        return Optional.empty();
    }

    public Optional<GeoCodingResponse> withStructuredQuery(StructuredQuery query) {
        String queryJson = super.queryToJson(query);
        queryJson = super.replaceSymbols(queryJson);
        WebTarget employeeWebTarget = baseTarget.queryParam("query", queryJson);
        Invocation.Builder invocationBuilder = employeeWebTarget.request(MediaType.APPLICATION_JSON);
        Response response = invocationBuilder.get();
        if (response.getStatus() == 200) {
            GeoCodingResponse state = response.readEntity(GeoCodingResponse.class);
            return Optional.of(state);
        } else {
            System.out.println(response);
        }
        return Optional.empty();
    }

}
