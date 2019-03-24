package hello;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
public class FrontRedirectFilter implements Filter {
	private static Logger logger = LoggerFactory.getLogger(FrontRedirectFilter.class);

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
//		logger.info(((HttpServletRequest) request).getRequestURL().toString());
//		logger.info(request.getServerName());
		String hostname=request.getServerName().toLowerCase();
		if (hostname.contains("librarymath")||hostname.startsWith("www."))
			((HttpServletResponse) response).sendRedirect("https://librarymath.com/");
		else
			chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}
}