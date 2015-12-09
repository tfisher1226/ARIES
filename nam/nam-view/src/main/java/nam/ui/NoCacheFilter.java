package nam.ui;

import java.io.IOException;

import javax.faces.application.ResourceHandler;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebFilter(urlPatterns = {"/*"})
public class NoCacheFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        String requestURI = httpServletRequest.getRequestURI();

        // Skip JSF resources (CSS/JS/Images/etc)
        boolean skip0 = requestURI.endsWith("RES_NOT_FOUND");
        boolean skip1 = requestURI.startsWith(httpServletRequest.getContextPath() + "/org.richfaces.resources");
        boolean include0 = requestURI.endsWith("Page.seam");
        boolean include1 = requestURI.endsWith("css.seam");
        boolean include2 = requestURI.endsWith("js.seam");
        boolean include3 = requestURI.endsWith("home.seam");
        if (!skip0 && !skip1 && (include0 || include1 || include2 || include3)) {
            //System.out.println("No cache: "+requestURI);
        	httpServletResponse.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
            httpServletResponse.setHeader("Pragma", "no-cache"); // HTTP 1.0.
            httpServletResponse.setDateHeader("Expires", 0); // Proxies.
        } else {
            //System.out.println("NO FILTER>>> "+requestURI);
        }

        if (include3)
        	System.out.println();
        chain.doFilter(request, response);
    }

	@Override
	public void destroy() {
	}

}