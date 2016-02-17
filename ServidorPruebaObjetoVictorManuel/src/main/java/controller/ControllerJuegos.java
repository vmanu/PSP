/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.objetopruebavictormanuel.Juego;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.objetopruebavictormanuel.PasswordHash;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpEntity;
import services.ControllerServiceJuegos;

/**
 *
 * @author oscar
 */
@WebServlet(name = "ControllerJuegos", urlPatterns = {"/ControllerJuegos"})
public class ControllerJuegos extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ControllerServiceJuegos sj = new ControllerServiceJuegos();
        Juego j;
        String op = request.getParameter("opcion");
        ObjectMapper mapper;
        String json, encrip;
        switch (op) {
            case "insert":
                try {
                    System.out.println("ENTRA EN INSERT");
                    mapper = new ObjectMapper();
                    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                    encrip = request.getParameter("juego");
                    json = PasswordHash.descifra(Base64.decodeBase64(encrip.getBytes("UTF-8")));
                    j = mapper.readValue(json, new TypeReference<Juego>() {});
                    System.out.println("Juego: " + j);
                    sj.insertJuego(j);
                    request.setAttribute("juegos", j.getId());
                } catch (Exception ex) {
                    Logger.getLogger(ControllerJuegos.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case "remove":
                try {
                    System.out.println("ENTRA EN REMOVE");
                    mapper = new ObjectMapper();
                    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                    encrip = request.getParameter("juego");
                    json = PasswordHash.descifra(Base64.decodeBase64(encrip.getBytes("UTF-8")));
                    int id = mapper.readValue(json, new TypeReference<Integer>() {});
                    System.out.println("id a borrar: " + id);
                    boolean returnment = sj.removeJuego(id);
                    request.setAttribute("juegos", returnment);
                } catch (Exception ex) {
                    Logger.getLogger(ControllerJuegos.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case "update":
                try {
                    System.out.println("ENTRA EN UPDATE");
                    mapper = new ObjectMapper();
                    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                    encrip = request.getParameter("juego");
                    json = PasswordHash.descifra(Base64.decodeBase64(encrip.getBytes("UTF-8")));
                    j = mapper.readValue(json, new TypeReference<Juego>() {
                    });
                    System.out.println("Juego: " + j);
                    sj.updateJuego(j);
                } catch (Exception ex) {
                    Logger.getLogger(ControllerJuegos.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case "get":
                System.out.println("ENTRA EN GET");
                request.setAttribute("juegos", sj.getAllJuegos());
                break;
        }
        request.setAttribute("msg", "JUEGOS");

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
