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

@WebServlet(name = "registroServlet", value = "/registro")
public class RegistroServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UsuarioDAO usuarioDAO = new UsuarioDAOImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/front/registro.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String nombre = req.getParameter("nombre");
        String password = req.getParameter("password");

        if (password == null || password.isEmpty() || nombre == null || nombre.isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "/registro");
            return;
        }

        Usuario u = new Usuario();
        u.setNombre(nombre);
        try {
            u.setPassword(Util.hashPassword(password));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            resp.sendRedirect(req.getContextPath() + "/registro");
            return;
        }
        u.setRol("cliente");
        usuarioDAO.create(u);

        resp.sendRedirect(req.getContextPath() + "/login");
    }
}
