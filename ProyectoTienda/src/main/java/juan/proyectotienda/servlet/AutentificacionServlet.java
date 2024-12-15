package juan.proyectotienda.servlet;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;
import jakarta.servlet.http.HttpSession;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import juan.proyectotienda.dao.UsuarioDAO;
import juan.proyectotienda.dao.UsuarioDAOImpl;
import juan.proyectotienda.model.Usuario;
import juan.proyectotienda.utilities.Util;



@WebServlet(name = "loginServlet", value = {"/login", "/registro"})
public class AutentificacionServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private UsuarioDAO usuarioDAO = new UsuarioDAOImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher;
        String pathInfo = request.getServletPath(); //CON PATHINFO DA NULL!!!
        System.out.println(pathInfo);
        if ("/login".equals(pathInfo)) {
        dispatcher= request.getRequestDispatcher("/WEB-INF/jsp/front/login.jsp");
        } else {
            dispatcher= request.getRequestDispatcher("/WEB-INF/jsp/front/registro.jsp");
        }
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String m = req.getParameter("__method__");

        if ("logout".equalsIgnoreCase(m)) {
            HttpSession session = req.getSession(false);
            if (session != null) {
                session.removeAttribute("usuario");
                //SI MODIFICO LO DE CARRITO TENDRIA QUE QUITARLO
                session.invalidate();
            }
            resp.sendRedirect(req.getContextPath() + "/ventas");

        } else if ("login".equalsIgnoreCase(m)) {
            String nombre = req.getParameter("nombre");
            String password = req.getParameter("password");

            if (password == null || password.isEmpty()) {
                resp.sendRedirect(req.getContextPath() + "/login");
                return;
            }

            try {
                String passHasheada = Util.hashPassword(password);
                Optional<Usuario> user = usuarioDAO.getByPassword(passHasheada, nombre);

                if (user.isPresent()) {
                    HttpSession session = req.getSession(true);
                    session.setAttribute("usuario", user.get());
                    resp.sendRedirect(req.getContextPath() + "/ventas");
                } else {
                    resp.sendRedirect(req.getContextPath() + "/login");
                }

            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
                resp.sendRedirect(req.getContextPath() + "/login");
            }

        } else {
            String nombre = req.getParameter("nombre");
            String password = req.getParameter("password");
            Optional<Usuario> existe = usuarioDAO.getByNombre(nombre);

            if (password == null || password.isEmpty() || nombre == null || nombre.isEmpty()) {
                resp.sendRedirect(req.getContextPath() + "/registro");
                return;
            }
    if (existe.isPresent()) {

        req.setAttribute("existe", true);
        //si uso el resp.sendRedirect RECARGA la pagina, y no vale para nada, hay que pasar a un Dispatcher
        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/jsp/front/registro.jsp");
        dispatcher.forward(req, resp);
        return;
    }
            try {
                Usuario u = new Usuario();
                u.setNombre(nombre);
                u.setPassword(Util.hashPassword(password));
                u.setRol("cliente");
                usuarioDAO.create(u);

                resp.sendRedirect(req.getContextPath() + "/login");
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
                resp.sendRedirect(req.getContextPath() + "/registro");
            }
        }
    }

}
