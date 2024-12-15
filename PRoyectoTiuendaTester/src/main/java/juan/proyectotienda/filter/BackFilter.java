package juan.proyectotienda.filter;

import juan.proyectotienda.model.Usuario;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.annotation.WebInitParam;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebFilter(
        urlPatterns = {"/back/*"},
        initParams = {
                @WebInitParam(name = "acceso-concedido-a-rol", value = "admin")
        }
)
public class BackFilter extends HttpFilter implements Filter {

    private String rolAcceso;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

        this.rolAcceso = filterConfig.getInitParameter("acceso-concedido-a-rol");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpReq = (HttpServletRequest) request;
        HttpServletResponse httpResp = (HttpServletResponse) response;

        HttpSession session = httpReq.getSession();
        Usuario userlog = null;

        if (session != null) {
            userlog = (Usuario)session.getAttribute("usuario");
        }
        if (userlog == null || !rolAcceso.equals(userlog.getRol())) {
            httpResp.sendRedirect(httpReq.getContextPath() + "/");
            return;
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}
