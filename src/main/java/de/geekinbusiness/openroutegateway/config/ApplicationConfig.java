package de.geekinbusiness.openroutegateway.config;

import java.util.Optional;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ApplicationConfig {

    private final static String OPENURL = "OPENROUTE_URL";

    private final static String APIKEYENV = "OPENROUTE_APIKEY";

    private String openRouteUrl;

    private String apikey;

    @PostConstruct
    public void init() {
        this.openRouteUrl = Optional.
                ofNullable(System.getenv(OPENURL))
                .orElse(Optional.ofNullable(System.getProperty(OPENURL))
                        .orElse(buildDefault()));
        this.apikey = Optional.
                ofNullable(System.getenv(APIKEYENV))
                .orElse(Optional.ofNullable(System.getProperty(APIKEYENV)).orElse("fucking void"));
    }

    private String buildDefault() {
        String defaultpath = "/";
        String defaultProtocoll = "https://";
        String defaultNameAndPort = "api.openrouteservice.org";
        String defaultdd;
//        try {
//            HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
////            String scheme = req.getServerName();
////            String ctxPaht = req.getContextPath();
//            String serverName = req.getServerName();
//            int serverPort = req.getServerPort();
//            defaultdd = defaultProtocoll + serverName + ":" + serverPort + defaultpath;
//        } catch (Exception ex) {
//            defaultdd = defaultProtocoll + defaultNameAndPort + defaultpath;
//        }
        defaultdd = defaultProtocoll + defaultNameAndPort + defaultpath;
        return defaultdd;
    }

    public String getOpenRouteUrl() {
        return openRouteUrl;
    }

    public String getApikey() {
        return apikey;
    }

    public void setApikey(String apikey) {
        this.apikey = apikey;
    }

}
