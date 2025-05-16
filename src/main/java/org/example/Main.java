package org.example;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        Scanner scanner = new Scanner(System.in);
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();

        System.out.println("Digite o CEP que deseja consultar:");
        String busca = scanner.nextLine();
        String endereco = "http://www.viacep.com.br/ws/" + busca + "/json/";
        System.out.println(endereco);

        HttpClient client = HttpClient.newBuilder()
                .followRedirects(HttpClient.Redirect.ALWAYS)
                .build();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(endereco))
                .build();
        HttpResponse<String> response = client
                .send(request, HttpResponse.BodyHandlers.ofString());

        String json = response.body();
        System.out.println(json);

        var convertedJson = gson.fromJson(json, Cep.class);
        System.out.println(convertedJson);

        FileWriter writer = new FileWriter("cep.json");
        writer.write(gson.toJson(convertedJson));
        writer.close();
    }
}