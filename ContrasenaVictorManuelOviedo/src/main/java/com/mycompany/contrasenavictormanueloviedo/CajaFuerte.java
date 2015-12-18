/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.contrasenavictormanueloviedo;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author dam2
 */
@WebServlet(name = "CajaFuerte", urlPatterns = {"/cajaFuerte"})
public class CajaFuerte extends HttpServlet {

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
        response.setContentType("text/html;charset=UTF-8");
        boolean cajaAbierta = false;
        int valores[] = {1, 2, 3};
        Boolean cajaAbiertaAntes=(Boolean) request.getSession().getAttribute("passOk");
        if(cajaAbiertaAntes==null){
            cajaAbiertaAntes=false;
        }
        if (!cajaAbiertaAntes) {
            Integer posicion = (Integer) request.getSession().getAttribute("posicion");
            if (posicion == null) {
                posicion = 0;
            }

            /* TODO output your page here. You may use following sample code. */
            if (request.getParameter("num").equals(valores[posicion] + "")) {
                posicion++;
                if (posicion == 3) {
                    cajaAbierta = true;
                    request.getSession().setAttribute("cajaAbierta", cajaAbierta);
                    posicion = 0;
                }
            } else {
                posicion = 0;
            }
            request.getSession().setAttribute("posicion", posicion);
        }else{
            request.getRequestDispatcher("/CajaAbierta.jsp").forward(request, response);
        }
        if (!cajaAbierta) {
            request.getRequestDispatcher("/SigueBuscando.jsp").forward(request, response);
        } else {
            request.getRequestDispatcher("/Password.jsp").forward(request, response);
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
