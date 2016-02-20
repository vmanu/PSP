/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.objetopruebavictormanuel.*;
import static constantes.Constantes.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.codec.binary.Base64;
import services.ControllerServiceLogin;

/**
 *
 * @author dam2
 */
@WebServlet(name = "ControllerLogin", urlPatterns = {"/ControllerLogin"})
public class ControllerLogin extends HttpServlet {

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
            ControllerServiceLogin cs=new ControllerServiceLogin();
            String userCifrado=request.getParameter("user");
            //OBJECTMAPPER... etc
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            Usuario usuario = mapper.readValue(PasswordHash.descifra(Base64.decodeBase64(userCifrado.getBytes("UTF-8"))), new TypeReference<Usuario>() {});
            if(request.getParameter("operacion").equals(OPERACION_REGISTRO)){
                String definitivaPass=PasswordHash.createHash(usuario.getPass());
                if(cs.compruebaLoginUnico(usuario.getLogin())){
                    //REGISTRA (INSERT)
                    //String mail=request.getParameter("mail"); NO DEBERIA ESTAR AQUI, SINO EN CLIENTE, NO?
                    if(cs.registra(usuario.getLogin(),definitivaPass)){
                        //DEVUELVE TRUE
                        System.out.println("RESULT REGISTRO: DEVUELVE TRUE");
                        response.getWriter().print(MENSAJE_TRUE);
                    }else{
                        //DEVUELVE FALSE REGISTRO
                        System.out.println("RESULT REGISTRO: DEVUELVE FALSE REGISTRO");
                        response.getWriter().print(MENSAJE_FALSE_REGISTRO);
                    }
                }else{
                    //DEVUELVE FALSE REPETICION
                    System.out.println("RESULT REGISTRO: DEVUELVE FALSE REPETICION");
                    response.getWriter().print(MENSAJE_FALSE_REPETICION);
                }
            }else{
                if(cs.login(usuario.getLogin(),usuario.getPass())){
                    request.getSession().setAttribute("Login", MENSAJE_OK);
                    System.out.println("RESULT LOGIN: DEVUELVE OK");
                    response.getWriter().print(MENSAJE_OK);
                }else{
                    request.getSession().setAttribute("Login", MENSAJE_WRONG);
                    System.out.println("RESULT LOGIN: DEVUELVE WRONG");
                    response.getWriter().print(MENSAJE_WRONG);
                }
            }
        } catch (NoSuchAlgorithmException ex) {
            System.err.println("Error en la generacion de hash");
            Logger.getLogger(ControllerLogin.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeySpecException ex) {
            System.err.println("Error en la generacion de hash");
            Logger.getLogger(ControllerLogin.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ControllerLogin.class.getName()).log(Level.SEVERE, null, ex);
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
