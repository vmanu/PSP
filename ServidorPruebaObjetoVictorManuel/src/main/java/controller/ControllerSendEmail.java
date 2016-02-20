/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.objetopruebavictormanuel.PasswordHash;
import static constantes.Constantes.MENSAJE_OK;
import static constantes.Constantes.MENSAJE_WRONG;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import services.ControllerServiceLogin;
import services.ControllerServiceSendEmail;
import static constants.Constantes.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.codec.binary.Base64;
import static utilidades.GeneraMensajeEmail.*;

/**
 *
 * @author Victor
 */
@WebServlet(name = "ControllerSendEmail", urlPatterns = {"/ControllerSendEmail"})
public class ControllerSendEmail extends HttpServlet {

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
            String email=request.getParameter("email");
            System.out.println("USER EN CONTROLLER ES: "+user);
            ControllerServiceSendEmail cs=new ControllerServiceSendEmail();
            if(cs.sendEmail(email, EMAIL_FROM, EMAIL_LOCALHOST, EMAIL_PORT, EMAIL_SUBJECT, generaMensaje(new String[]{EMAIL_MSG,PARTE_ESTANDAR_ENLACE,user}))){
                response.getWriter().print(MENSAJE_OK);
                System.out.println("ENTRAMOS EN QUE HA ENVIADO MAIL EN CONTROLLERSENDEMAIL");
            }else{
                cs.darBaja(user);
                System.out.println("ENTRAMOS EN QUE NO HA ENVIADO MAIL EN CONTROLLERSENDEMAIL, SE SUPONE DADO DE BAJA");
                response.getWriter().print(MENSAJE_WRONG);
            }
        } catch (Exception ex) {
            Logger.getLogger(ControllerSendEmail.class.getName()).log(Level.SEVERE, null, ex);
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
