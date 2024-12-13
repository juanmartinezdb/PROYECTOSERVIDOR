package juan.proyectotienda.filter;

import juan.proyectotienda.model.Usuario;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebFilter(
        urlPatterns = {"/back/*"}
)
public class BackFilter extends HttpFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // inicialización si fuera necesaria
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpReq = (HttpServletRequest) request;
        HttpServletResponse httpResp = (HttpServletResponse) response;

        HttpSession session = httpReq.getSession(false);
        Usuario userlog = null;
        if (session != null) {
            userlog = (Usuario) session.getAttribute("usuario");
        }

        if (userlog == null || !"admin".equals(userlog.getRol())) {
            // Usuario no logeado o no admin, redirigir al index
            httpResp.sendRedirect(httpReq.getContextPath() + "/");
            return;
        }

        // Usuario admin, continuar
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        // limpieza si fuera necesaria
    }
}
