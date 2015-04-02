package tw.lai.macgyver.jft.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tw.lai.macgyver.jft.module.IJinFaTanCenter;

public class SecurityFilter implements Filter {

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		HttpServletRequest httpReq = (HttpServletRequest) req;
		
		HttpServletResponse httpRes = (HttpServletResponse) res;
		
		System.out.println("ServletPath is " + httpReq.getServletPath());
		
		if (!"/login.do".equals(httpReq.getServletPath())) {
			Integer authorise = (Integer) httpReq.getSession().getAttribute("Authorise");
			if (authorise == null || authorise.intValue() == IJinFaTanCenter.AUTHORITY_ERROR) {
				httpRes.setHeader("Refresh", "5; " + httpReq.getContextPath() + "/login.jsp");
				httpReq.getRequestDispatcher("/WEB-INF/pages/404.jsp").forward(req, res);
				return;
			}
		}
		
		filterChain.doFilter(req, res);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

}
