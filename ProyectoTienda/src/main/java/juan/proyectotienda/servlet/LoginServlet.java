package juan.proyectotienda.servlet;

import juan.proyectotienda.dao.UsuarioDAO;
import juan.proyectotienda.dao.UsuarioDAOImpl;
import juan.proyectotienda.model.Usuario;
import juan.proyectotienda.utilities.Util;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

@WebServlet(name = "loginServlet", value = "/login2")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UsuarioDAO usuarioDAO = new UsuarioDAOImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/front/login.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String accion = req.getParameter("accion");

        if ("logout".equalsIgnoreCase(accion)) {
            HttpSession session = req.getSession(false);
            if (session != null) {
                session.removeAttribute("usuario");
                session.invalidate();
            }
            resp.sendRedirect(req.getContextPath() + "/");
            return;
        }

        // Accion de login normal
        String nombre = req.getParameter("nombre");
        String password = req.getParameter("password");

        if (password == null || password.isEmpty()) {
            // Contraseña vacía, no podemos loguear
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        try {
            String hashed = Util.hashPassword(password);
            Optional<Usuario> user = usuarioDAO.getByPassword(hashed, nombre);
            if (user.isPresent()) {
                HttpSession session = req.getSession(true);
                session.setAttribute("usuario", user.get());
                resp.sendRedirect(req.getContextPath() + "/");
            } else {
                resp.sendRedirect(req.getContextPath() + "/login");
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            resp.sendRedirect(req.getContextPath() + "/login");
        }
    }
}
