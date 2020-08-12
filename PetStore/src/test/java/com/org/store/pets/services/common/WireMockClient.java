package com.org.store.pets.services.common;

import com.github.tomakehurst.wiremock.WireMockServer;



import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

public class WireMockClient {

    public WireMockClient() {
        WireMockServer wireMockServer = new WireMockServer(wireMockConfig());
        wireMockServer.start();
    }

    public void petsStoreStubbing(){
        stubFor(get(urlEqualTo("/pet/findByStatus?status=available"))
                .willReturn(aResponse()
                        .withHeader("Content-Type","application/json")
                        .withStatus(200)
                        .withBodyFile("pets.json")));

    }

}
