package com.bank.client.models;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;

public class ResponseApi {
    private StringBuilder sb;
    private String ResponseAddress;

    private int code;

    public StringBuilder getSb() {
        return sb;
    }

    public void setSb(StringBuilder sb) {
        this.sb = sb;
    }

    public String getResponseAddress() {
        return ResponseAddress;
    }

    public void setResponseAddress(String responseAddress) {
        ResponseAddress = responseAddress;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public ResponseApi sendRequest(Method method, String body, String address) {
        HttpURLConnection con = null;
        StringBuilder sb = new StringBuilder();

        try {
            con = (HttpURLConnection) new URL(address).openConnection();
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestMethod(String.valueOf(method));

            if (Objects.equals(method.toString(), "POST")) {
                con.setDoOutput(true);
                try(OutputStream os = con.getOutputStream()) {
                    byte[] input = body.getBytes("utf-8");
                    os.write(input, 0, input.length);
                }
            }

            con.connect();

            if (HttpURLConnection.HTTP_OK == con.getResponseCode()) {
                BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));

                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                    sb.append("\n");
                }

                System.out.println(con.getResponseCode()+"\n"+sb);
            } else {
                System.out.println("failed: " + con.getResponseCode() + " error " + con.getResponseMessage());
            }

        } catch (IOException e) {
            System.out.println(e);
        } finally {
            if (con != null) {
                con.disconnect();
            }
        }
        ResponseApi responseApi = new ResponseApi();
        responseApi.setSb(sb);
        responseApi.setResponseAddress(con.getURL().toString());
        try {
            responseApi.setCode(con.getResponseCode());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return responseApi;
    }
}