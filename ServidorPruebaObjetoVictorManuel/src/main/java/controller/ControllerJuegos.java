/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.objetopruebavictormanuel.Juego;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
//        Juego j = new Juego();
        ControllerServiceJuegos sj=new ControllerServiceJuegos();
        String nombre=null;
       
        int creador,tipo,ventas;
        Date fecha;
        Juego j;
        String op = request.getParameter("opcion");
        switch(op){
            case "insert":
                nombre= request.getParameter("name");
                tipo=Integer.parseInt(request.getParameter("type"));
                fecha=new Date(request.getParameter("date"));
                creador=Integer.parseInt((request.getParameter("creator")));
                ventas=Integer.parseInt((request.getParameter("sales")));
                j=new Juego(nombre, fecha, ventas, tipo, creador);
                sj.insertJuego(j);
                break;
            case "remove":
                //id=Integer.parseInt(request.getParameter("id"));
                //sp.updateJuego(sp.getPeliPorId(id));
                break;
            case "update":
                
                break;
            case "get":
                request.setAttribute("juegos", sj.getAllJuegos());
                break;
        }
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
