package com.org.store.pets.services.common;

import com.org.store.pets.services.utils.PetStatus;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static com.org.store.pets.services.common.Constants.PETS_STATUS_URL;
import static com.org.store.pets.services.common.Constants.PETS_STORE_SERVICE_URL;

public class Client {

    public static HttpResponse<String> getPetsByStatus(PetStatus petStatus) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(PETS_STATUS_URL+petStatus))
                .build();
        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    public static int getPetStoreServiceStatus() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(PETS_STORE_SERVICE_URL))
                .build();
        HttpResponse<String> response=client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.statusCode();

    }

}
