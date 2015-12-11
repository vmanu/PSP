package com.miprimerappj2ee;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author dam2
 */
@WebServlet(urlPatterns = {"/prueba"})
public class ServletPrueba extends HttpServlet {

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
        // <editor-fold defaultstate="collapsed" desc="PARTE BORRADA">
        /*response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {*/
        //</editor-fold>
            /* TODO output your page here. You may use following sample code. */
        
        // <editor-fold defaultstate="collapsed" desc="PARTE BORRADA">
            /*out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ServletPrueba Victor</title>");            
            out.println("</head>");
            out.println("<body>");*/
            //</editor-fold>  
            //RECORRO PARAMETROS (NO SE SUELE HACER)
            Map <String, String[]> param=request.getParameterMap();
            for(Entry<String,String []> e:param.entrySet()){
                // <editor-fold defaultstate="collapsed" desc="PARTE BORRADA">
                /*out.println("<h1> nombre "+e.getKey()+" </h1>");
                out.println("<h1> valor "+e.getValue()[0]+" </h1>");*/
                //</editor-fold>
            }
            //saco valor 
            String code=request.getParameter("code");
            if(code==null){
                code="";
            }
            
            switch(code){
                case "UNO":
                    request.getRequestDispatcher("/uno.jsp").forward(request, response);
                    break;
                case "DOS":
                    request.getRequestDispatcher("/dos.jsp").forward(request, response);
                    break;
                default:
                    request.getRequestDispatcher("/error.jsp").forward(request, response);
            }
            // <editor-fold defaultstate="collapsed" desc="PARTE BORRADA">
        /*
            out.println("<h1> valor operacion"+request.getParameter("operacion")+" </h1>");
            out.println("<h1>Servlet ServletPrueba at </h1>");
            out.println("</body>");
            out.println("</html>");
        }*/
            //</editor-fold>
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
