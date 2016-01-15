/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.clienteweb;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 *
 * @author oscar
 */
public class JsoupClient {

    public static void main(String[] args) {
        Response response = null;
        Map<String, String> cookies = null;
        try {
            response = Jsoup.connect("http://localhost:8080/ContrasenaVictorManuelOviedo/cajaFuerte?num=1")
                    .execute();
            System.out.println(response.parse().body().text());
            cookies = response.cookies();
            response = Jsoup.connect("http://localhost:8080/ContrasenaVictorManuelOviedo/cajaFuerte?num=2")
                    .cookies(cookies)
                    .execute();
            System.out.println(response.parse().body().text());
            response = Jsoup.connect("http://localhost:8080/ContrasenaVictorManuelOviedo/cajaFuerte?num=3")
                    .cookies(cookies)
                    .execute();
            System.out.println(response.parse().body().text());
            response = Jsoup.connect("http://localhost:8080/ContrasenaVictorManuelOviedo/password?pass=paquete")
                    .cookies(cookies)
                    .execute();
            System.out.println(response.parse().body().text());
        } catch (IOException ex) {
            Logger.getLogger(JsoupClient.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
