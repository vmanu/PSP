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
@WebServlet(name = "TestInitParan", urlPatterns = {"/TestInitParan"},initParams = {
        @WebInitParam(name="secretValue1",value="test"),@WebInitParam(name="secretValue2",value="testa"),@WebInitParam(name="secretValue3",value="testar")})
public class TestInitParan extends HttpServlet {

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
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet TestInitParan</title>");            
            out.println("</head>");
            out.println("<body>");
            boolean adivinatodo=true;
            if(request.getParameter("v1").equals(getServletConfig().getInitParameter("secretValue1"))){
                out.println("<h1>Primer secretValue esta adivinado: \""+getServletConfig().getInitParameter("secretValue1")+"\"</h1>");
            }else{
                out.println("<h1>Primer secretValue no esta adivinado, \""+request.getParameter("v1")+"\" es incorrecto</h1>");
                adivinatodo=false;
            }
            if(request.getParameter("v2").equals(getServletConfig().getInitParameter("secretValue2"))){
                out.println("<h1>Segundo secretValue esta adivinado: \""+getServletConfig().getInitParameter("secretValue2")+"\"</h1>");
            }else{
                out.println("<h1>Segundo secretValue no esta adivinado, \""+request.getParameter("v2")+"\" es incorrecto</h1>");
                adivinatodo=false;
            }
            if(request.getParameter("v3").equals(getServletConfig().getInitParameter("secretValue3"))){
                out.println("<h1>Tercer secretValue esta adivinado: \""+getServletConfig().getInitParameter("secretValue3")+"\"</h1>");
            }else{
                out.println("<h1>Tercer secretValue no esta adivinado, \""+request.getParameter("v3")+"\" es incorrecto</h1>");
                adivinatodo=false;
            }
            if(adivinatodo){
                out.println("<h1>TODO ADIVINADO!!!</h1>");
            }
            out.println("</body>");
            out.println("</html>");
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
