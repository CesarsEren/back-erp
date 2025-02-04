package com.alo.digital.facturacion.service.impl;

import com.alo.digital.facturacion.entity.rest.GuiaRemisionTransportista;
import com.alo.digital.facturacion.entity.security.Login;
import com.alo.digital.facturacion.entity.rest.response.ResponseGeneraXML;
import com.alo.digital.facturacion.entity.security.LoginResponse;
import com.alo.digital.facturacion.service.IConsumeRestApiSunat;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

@Service
@Slf4j
public class ConsumeRestApiSunatImpl implements IConsumeRestApiSunat {

    @Value("${app.api.path.grt}")
    public String PATH;
    @Value("${app.api.user.grt}")
    public String usuario;
    @Value("${app.api.ruc.grt}")
    public String ruc;
    @Value("${app.api.pass.grt}")
    public String contrasenia;
    @Value("${app.api.token.grt}")
    public String bearerToken;

     @Override
    public ResponseGeneraXML generateXML(GuiaRemisionTransportista guia) throws IOException {
        URL url = new URL(PATH.concat("/v2/proceso/guia?usuario=" + ruc + "&contrasena=" + contrasenia));

        // Conexión HTTP
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        //connection.setRequestProperty("Authorization", "Bearer " + bearerToken);
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Accept", "application/json");
        connection.setDoOutput(true);

        // Serializar objeto de entrada a JSON con Gson
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String requestBody = gson.toJson(guia);

        log.info(requestBody);
        // Enviar datos
        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = requestBody.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }

        // Leer respuesta
        int statusCode = connection.getResponseCode();
        if (statusCode == HttpURLConnection.HTTP_OK) {
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
                StringBuilder response = new StringBuilder();
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }

                // Deserializar respuesta a ResponseGeneraXML con Gson
                ResponseGeneraXML responseObj = gson.fromJson(response.toString(), ResponseGeneraXML.class);
                System.out.println("Respuesta de la API: " + responseObj);
                return responseObj;
            }

        } else {
            System.err.println("Error en la llamada a la API: " + statusCode);
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(connection.getErrorStream(), StandardCharsets.UTF_8))) {
                StringBuilder errorResponse = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    errorResponse.append(line.trim());
                }
                System.err.println("Mensaje de error: " + errorResponse);
            }
            return null;
        }
    }

    @Autowired
    private RestTemplate restTemplate;

    private void generateToken() {
        String url = PATH.concat("/authenticate/user");
        HttpHeaders headers = new HttpHeaders();
        //headers.set("Authorization", "Bearer " + bearerToken);
        headers.set("Content-Type", "application/json");
        Login login = new Login(usuario, contrasenia);
        HttpEntity<Login> requestEntity = new HttpEntity<>(login, headers);
        log.info(url);
        // Send POST request
        ResponseEntity<LoginResponse> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                requestEntity,
                LoginResponse.class
        );

        LoginResponse loginResponse = response.getBody();
        assert loginResponse != null;
        log.info(loginResponse.toString());
        bearerToken = loginResponse.getToken();

    }

/*    @Override
    public ResponseGeneraXML generateXML(GuiaRemisionTransportista guiaRemisionTransportista) throws IOException {
        // generateToken();
        //URL url = new URL(PATH.concat("/proceso/guia?usuario=" + usuario + "&contrasena=" + contrasenia));
        String url = PATH.concat("/v2/proceso/guia?usuario=" + ruc + "&contrasena=" + contrasenia);
        // Set headers
        HttpHeaders headers = new HttpHeaders();
        //headers.set("Authorization", "Bearer " + bearerToken);
        headers.set("Content-Type", "application/json");
        log.info(url);
        // Wrap payload and headers into HttpEntity
        HttpEntity<GuiaRemisionTransportista> requestEntity = new HttpEntity<>(guiaRemisionTransportista, headers);

        // Send POST request
        ResponseEntity<ResponseGeneraXML> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                requestEntity,
                ResponseGeneraXML.class
        );

        // Return the response body
        return response.getBody();
    }*/

    @Override
    public ResponseGeneraXML envioSunat(byte[] documento) throws Exception {
        //generateToken();
        String url = PATH.concat("/v2/envio/guia?usuario=" + ruc + usuario + "&contrasena=" + contrasenia);

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM); // Tipo para byte[]

        try {
            byte[] guiaBytes = documento;

            HttpEntity<byte[]> requestEntity = new HttpEntity<>(guiaBytes, headers);

            ResponseEntity<ResponseGeneraXML> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, ResponseGeneraXML.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                System.out.println("Envío exitoso: " + response.getBody());
                return response.getBody();
            } else {
                System.err.println("Error en el envío: " + response.getStatusCode());
                return null;
            }
        } catch (Exception e) {
            System.err.println("Error en la solicitud: " + e.getMessage());
            throw e;
        }
    }

    @Override
    public ResponseGeneraXML consultarCdrByTicket(String json_trama, String nroTicket) throws Exception {
        String url = PATH.concat("/v2/consulta/estadocomprobante/guia?usuario=" + ruc + usuario + "&contrasena=" + contrasenia + "&ticket=" + nroTicket);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        log.info(url);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        GuiaRemisionTransportista guiaRemisionTransportista = gson.fromJson(json_trama, GuiaRemisionTransportista.class);
        HttpEntity<GuiaRemisionTransportista> requestEntity = new HttpEntity<>(guiaRemisionTransportista, headers);
        ResponseEntity<ResponseGeneraXML> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                requestEntity,
                ResponseGeneraXML.class
        );
        return response.getBody();
    }
}
