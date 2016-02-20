/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.objetopruebavictormanuel.PasswordHash;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import services.ControllerServiceLogin;
import static constants.Constantes.*;
import static constantes.Constantes.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.codec.binary.Base64;

/**
 *
 * @author Victor
 */
@WebServlet(name = "ControllerConfirmRegister", urlPatterns = {"/ControllerConfirmRegister"})
public class ControllerConfirmRegister extends HttpServlet {

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
        try {
            String user=request.getParameter("user");
            System.out.println("ME PIDE "+user+" QUE LE DE DE ALTA... QUE ILUSO...");
            ControllerServiceLogin cs=new ControllerServiceLogin();
            if(!cs.activaUser(PasswordHash.descifra(Base64.decodeBase64(user.getBytes("UTF-8"))))){
                response.getWriter().print(MENSAJE_WRONG);
                cs.darBaja(user);
            }else{
                response.getWriter().print(MENSAJE_OK);
            }
        } catch (Exception ex) {
            //Logger.getLogger(ControllerConfirmRegister.class.getName()).log(Level.SEVERE, null, ex);
            response.getWriter().print(MENSAJE_WRONG + " Link");
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
