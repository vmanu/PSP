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
        Document homePage = null;
        Response response = null;

        try {
            response = Jsoup.connect("http://localhost:8080/ServletSession")
                    .execute();
            response.body();
            
            
            homePage = Jsoup.connect("http://localhost:8080/ServletSession")
                    //.data("optionsProfesores", "null")
                    .cookies(response.cookies())
                    .post();
            
            response = Jsoup.connect("http://localhost:8080/ServletSession")
                    .cookies(response.cookies())
                    .execute();
            response.body();

        } catch (IOException ex) {
            Logger.getLogger(JsoupClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println(homePage.body().text());
        try {
            System.out.println(response.parse().body().text());
        } catch (IOException ex) {
            Logger.getLogger(JsoupClient.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
