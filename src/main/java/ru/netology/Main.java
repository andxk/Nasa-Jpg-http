package ru.netology;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import org.apache.http.HttpHeaders;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class Main {
    public static final String API_KEY = "DPRYGMbecCTF125ONHmdotrnpMqbikDKAaPUv7Dk";
    public static final String URL = "https://api.nasa.gov/planetary/apod?api_key="+API_KEY;

    public static String getJsonStringFromUrl(String url) {
        CloseableHttpClient httpClient = HttpClientBuilder.create()
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setConnectTimeout(5000)
                        .setSocketTimeout(30000)
                        .setRedirectsEnabled(false)
                        .build())
                .build();

        HttpGet request = new HttpGet(url);
        request.setHeader(HttpHeaders.ACCEPT, ContentType.APPLICATION_JSON.getMimeType());

        try {
            CloseableHttpResponse response = httpClient.execute(request);
            int code = response.getStatusLine().getStatusCode();
            if (code == 200) {
                return new String(response.getEntity().getContent().readAllBytes());
            }
            else {
                System.out.println(response.getStatusLine().toString());
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return "";
    }


    public static NasaAnswer jsonToNasa(String json) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        try {
            NasaAnswer answer = gson.fromJson(json, NasaAnswer.class);
            return answer;
        } catch (JsonSyntaxException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }


    public static void saveJpegUrlToFile(String url) {
        String fileName = url.substring(url.lastIndexOf("/")+1);

        CloseableHttpClient httpClient = HttpClientBuilder.create()
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setConnectTimeout(5000)
                        .setSocketTimeout(30000)
                        .setRedirectsEnabled(false)
                        .build())
                .build();

        HttpGet request = new HttpGet(url);
        request.setHeader(HttpHeaders.ACCEPT, ContentType.IMAGE_JPEG.getMimeType());

        try {
            CloseableHttpResponse response = httpClient.execute(request);
            int code = response.getStatusLine().getStatusCode();
            if (code == 200) {
                File dir = new File("Nasa");
                dir.mkdir();
                FileOutputStream fos = new FileOutputStream("Nasa/" + fileName);
                response.getEntity().writeTo(fos);
                System.out.println("Файл '" + fileName + "' сохранен.");
            }
            else {
                System.out.println(response.getStatusLine().toString());
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }


    public static void main(String[] args) {

        String json = getJsonStringFromUrl(URL);
        if (json.isEmpty()) return;
//        System.out.println(json);

        NasaAnswer a = jsonToNasa(json);
        if (a == null) return;
//        System.out.println(a.toString());
        System.out.println(a.getUrl());
        saveJpegUrlToFile(a.getUrl());
    }

}
