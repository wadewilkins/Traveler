package com.wwilkins.traveler;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

import static java.lang.System.*;

@SpringBootTest
class TravelerApplicationTests {

    @Test
    void contextLoads() throws IOException {
        out.println("\n\n\n");


        // Test put (updater) one traveler
        try {
            out.println("\nUpdate One Traveler Output from Server .... \n");
            URL url = new URL("http://localhost:8080/api/v1/traveler/d00e762d-6daf-4cbd-a8b6-e4fd2e7188b8");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoOutput(true);
            conn.setRequestMethod("PUT");
            String jsonInputString = "{\"travelerId\": \"d00e762d-6daf-4cbd-a8b6-e4fd2e7188b8\", " +
                    "\"customerId\": \"0006ade2-31e2-4b38-a641-e26135664b21\", " +
                    "\"firstName\": \"Wade\", " +
                    "\"middleName\": \"Clayton4\"," +
                    "\"lastName\": \"Wilkins\"," +
                    "\"gender\": \"M\"," +
                    "\"dob\": \"1972-03-17\"," +
                    "\"phone1\": \"4252238062\"," +
                    "\"countryCode1\": \"1\"," +
                    "\"countryCode2\": \"1\"," +
                    "\"phone2\": \"4252238064\"," +
                    "\"emergencyFirstName\": \"Alan\"," +
                    "\"emergencyLastName\": \"Wilkins\"," +
                    "\"emergencyCountryCode\": \"1\"," +
                    "\"emergencyPhone\": \"4252238065\"," +
                    "\"flightPrefSeat\": \"Middle\"," +
                    "\"flightPrefSpecial\": \"none\"," +
                    "\"passportCountryCode\": \"1\"," +
                    "\"passportNumber\": \"2\"," +
                    "\"lastLogin\": \"null\" " +
                    "}";
            try(OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
                os.write(input,0, input.length);
            }
            catch (Exception e){
                e.printStackTrace();
            }
            try(BufferedReader br = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
                StringBuilder response = new StringBuilder();
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                System.out.println(response);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }




        // Test Get all Travelers
        try {
            out.println("\nGet all Travelers Output from Server .... \n");
            URL url = new URL("http://localhost:8080/api/v1/travelers/0006ade2-31e2-4b38-a641-e26135664b21");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));
            String output;
            while ((output = br.readLine()) != null) {
                out.println(output);
            }
            conn.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        // Test get one traveler
        try {
            out.println("\nGet One Traveler Output from Server .... \n");
            URL url = new URL("http://localhost:8080/api/v1/traveler/d00e762d-6daf-4cbd-a8b6-e4fd2e7188b8");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));
            String output;
            while ((output = br.readLine()) != null) {
                out.println(output);
            }
            conn.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }


        // Test DELETE one traveler
        try {
            out.println("\nDelete One Traveler Output from Server .... \n");
            URL url = new URL("http://localhost:8080/api/v1/traveler/12939357-7474-4555-b287-e631de7cb493");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("DELETE");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoOutput(true);
            String jsonInputString = "{\"name\": \"Wade\", \"job\": \"Programmer\"}";
            try(OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
                os.write(input,0, input.length);
            }
            catch (Exception e){
                e.printStackTrace();
            }
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));
            String output;
            while ((output = br.readLine()) != null) {
                out.println(output);
            }
            conn.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }



        // Test create one traveler
        try {
            out.println("\nCreate One Traveler Output from Server .... \n");
            URL url = new URL("http://localhost:8080/api/v1/traveler");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoOutput(true);
            //conn.setRequestMethod("POST");
            String jsonInputString = "{"+
                    "\"travelerid\": \"-1\", " +
                    "\"customerId\": \"20000000-2000-1000-1000-100000000000\", " +
                    "\"firstName\": \"Wade\", " +
                    "\"middleName\": \"Clayton\"," +
                    "\"lastName\": \"Wilkins\"," +
                    "\"userName\": \"wadewilkins\"," +
                    "\"mobile\": \"4252238062\"," +
                    "\"email\": \"wadewilkins@gmail.com\"," +
                    "\"dob\": \"1980-03-17\" " +
                    "}";
            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            } catch (Exception e) {
                System.out.println("Error testing");
                //e.printStackTrace();
            }
            int statusCode = conn.getResponseCode();

            if (statusCode >= 200 && statusCode < 400) {
                try (BufferedReader br = new BufferedReader(
                        new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
                    StringBuilder response = new StringBuilder();
                    String responseLine;
                    while ((responseLine = br.readLine()) != null) {
                        response.append(responseLine.trim());
                    }
                    System.out.println(response);
                } catch (IOException e) {
                    System.out.println("Error testing 4.1");
                }
            }
            else{
                try (BufferedReader br = new BufferedReader(
                        new InputStreamReader(conn.getErrorStream(), StandardCharsets.UTF_8))) {
                    StringBuilder response = new StringBuilder();
                    String responseLine;
                    while ((responseLine = br.readLine()) != null) {
                        response.append(responseLine.trim());
                    }
                    System.out.println(response);
                    //throw new ArithmeticException("/ by zero");
                    throw new HttpClientErrorException(HttpStatus.BAD_REQUEST);
                } catch (IOException e) {
                    System.out.println("Error testing 4.1");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        /*
        String cid = null;
        cid = new String();
        // Test create MANY travelers
        try {
            for (int i = 1; i < 2000; i++) {
                cid = UUID.randomUUID().toString();
                for (int x = 1; x <= 10; x++) {
                    out.println("\nCreate One Traveler Output from Server .... \n");
                    URL url = new URL("http://localhost:8080/api/v1/traveler");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestProperty("Content-Type", "application/json; utf-8");
                    conn.setRequestProperty("Accept", "application/json");
                    conn.setDoOutput(true);
                    //conn.setRequestMethod("POST");
                    String jsonInputString = "{" +
                            "\"customerId\": \"" + cid + "\", " +
                            "\"travelerid\": \"-1\", " +
                            "\"firstName\": \"Wade" + x + "\", " +
                            "\"middleName\": \"Clayton\"," +
                            "\"lastName\": \"Wilkins\"," +
                            "\"userName\": \"wadewilkins\"," +
                            "\"mobile\": \"4252238062\"," +
                            "\"email\": \"wadewilkins@gmail.com\"," +
                            "\"dob\": \"1980-03-17\" " +
                            "}";
                    try (OutputStream os = conn.getOutputStream()) {
                        byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
                        os.write(input, 0, input.length);
                    } catch (Exception e) {
                        System.out.println("Error testing");
                        //e.printStackTrace();
                    }
                    int statusCode = conn.getResponseCode();

                    if (statusCode >= 200 && statusCode < 400) {
                        try (BufferedReader br = new BufferedReader(
                                new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
                            StringBuilder response = new StringBuilder();
                            String responseLine;
                            while ((responseLine = br.readLine()) != null) {
                                response.append(responseLine.trim());
                            }
                            System.out.println(response);
                        } catch (IOException e) {
                            System.out.println("Error testing 4.1");
                        }
                    } else {
                        try (BufferedReader br = new BufferedReader(
                                new InputStreamReader(conn.getErrorStream(), StandardCharsets.UTF_8))) {
                            StringBuilder response = new StringBuilder();
                            String responseLine;
                            while ((responseLine = br.readLine()) != null) {
                                response.append(responseLine.trim());
                            }
                            System.out.println(response);
                            //throw new ArithmeticException("/ by zero");
                            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST);
                        } catch (IOException e) {
                            System.out.println("Error testing 4.1");
                        }
                    }
                }
            }
        }catch(IOException e){
                    e.printStackTrace();
        }
    */



    }  // Function end


}
