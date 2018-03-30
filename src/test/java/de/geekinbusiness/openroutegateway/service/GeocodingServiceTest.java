package de.geekinbusiness.openroutegateway.service;

import de.geekinbusiness.openroutegateway.config.ApplicationConfig;
import de.geekinbusiness.openroutegateway.schemas.request.in.StructuredQuery;
import de.geekinbusiness.openroutegateway.schemas.response.geocoding.GeoCodingResponse;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author manuel müller <manuel.mueller@geekinbusiness.de>
 */
public class GeocodingServiceTest {

    static GeocodingService service = new GeocodingService();
    static ApplicationConfig cfg = new ApplicationConfig();

    public GeocodingServiceTest() {

    }

    @BeforeClass
    public static void init() {
        cfg.init();
        service.appConfig = cfg;
        service.init();
    }

    @AfterClass
    public static void destroy() {
        service.destroy();
    }

    /**
     * Test of init method, of class GeocodingService.
     */
    /**
     * Test of findByArtikelFirmaOrt method, of class GeocodingService.
     */
    @Test
    public void testFindByArtikelFirmaOrt() throws Exception {
        Optional<GeoCodingResponse> opt = service.findByString("Am Kainbach " + 4);
        GeoCodingResponse result = opt.get();
        Assertions.assertThat(result).isNotNull();
    }

    @Test
    public void testFindMoreSpecific() throws Exception {
        Optional<GeoCodingResponse> opt = service.findByString("Burgheimerstraße 19");
        GeoCodingResponse result = opt.get();
        Assertions.assertThat(result).isNotNull();
    }

    @Test
    public void testStructured() throws Exception {
        StructuredQuery query = new StructuredQuery()
                .withAddress("BurheimerStraße 19")
                .withPostalcode("86643");
        Optional<GeoCodingResponse> opt = service.withStructuredQuery(query);
        GeoCodingResponse result = opt.get();
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getFeatures().get(0).getAdditionalProperties().get("placeType")).isNotEqualTo("country");
    }

    @Test
    public void testJsonReplacement() throws Exception {
        String[] inputs = {"{", "|", "}", "\"", "[", "]"};
        String[] expected = {"%7B", "%7C", "%7D", "%22", "%5B", "%5D"};
        String[] results = new String[6];
        for (int i = 0; i < inputs.length; i++) {
            results[i] = service.replaceSymbols(inputs[i]);
        }
        for (int i = 0; i < inputs.length; i++) {
            Assertions.assertThat(results[i]).describedAs(inputs[i] + "should be " + expected[i])
                    .isEqualTo(expected[i]);
        }
    }

}
