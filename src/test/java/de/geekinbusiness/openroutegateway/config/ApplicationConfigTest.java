package de.geekinbusiness.openroutegateway.config;

import org.assertj.core.api.Assertions;
import org.junit.Test;

/**
 *
 * @author manuel müller <manuel.mueller@geekinbusiness.de>
 */
public class ApplicationConfigTest {

    public ApplicationConfigTest() {
    }

    /**
     * Test of init method, of class ApplicationConfig.
     */
    @Test
    public void testInit() {
        ApplicationConfig cfg = new ApplicationConfig();
        cfg.init();
        Assertions.assertThat(cfg.getOpenRouteUrl())
                .isEqualToIgnoringCase("https://api.openrouteservice.org/");
        Assertions.assertThat(cfg.getApikey()).describedAs("Set OPENROUTE_APIKEY as Enviroment Variable")
                .isNotEqualToIgnoringCase("fucking");
    }

    /**
     * Test of getOpenRouteUrl method, of class ApplicationConfig.
     */
    @Test
    public void testGetOpenRouteUrl() {
        ApplicationConfig cfg = new ApplicationConfig();
        cfg.init();
        Assertions.assertThat(cfg.getOpenRouteUrl())
                .isNotNull();
    }

    /**
     * Test of getApikey method, of class ApplicationConfig.
     */
    @Test
    public void testGetApikey() {
        ApplicationConfig cfg = new ApplicationConfig();
        cfg.init();
        Assertions.assertThat(cfg.getApikey())
                .isNotNull();
    }

    /**
     * Test of setApikey method, of class ApplicationConfig.
     */
    @Test
    public void testSetApikey() {
        ApplicationConfig cfg = new ApplicationConfig();
        cfg.init();
        cfg.setApikey("my_very_own_key");
        Assertions.assertThat(cfg.getApikey())
                .isEqualTo("my_very_own_key");
    }

}
