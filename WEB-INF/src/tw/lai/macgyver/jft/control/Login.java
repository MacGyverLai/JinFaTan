package tw.lai.macgyver.jft.control;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import tw.lai.macgyver.jft.form.LoginForm;
import tw.lai.macgyver.jft.module.IJinFaTanCenter;
import tw.lai.macgyver.jft.util.MacLogger;

@Controller
public class Login {

	private IJinFaTanCenter jftCenter = null;

	@RequestMapping("/login.do")
	public ModelAndView handleRequest(@ModelAttribute("loginForm") LoginForm loginForm, 
			HttpServletRequest req, HttpServletResponse res) throws Exception {
		ModelAndView result = null;
		MacLogger macLog = new MacLogger();
		macLog.initCollectDebug("login.do", req.getRemoteAddr(), req.getRemoteUser(), "None");
		
		System.out.println("U / P = " + loginForm.getUserName() + " / " 
				+ loginForm.getPassword());
		
		int authorise = this.jftCenter.checkUserAuthority(loginForm.getUserName(), 
				loginForm.getPassword());
		
		String targetPage = "main";
		switch (authorise) {
		case IJinFaTanCenter.AUTHORITY_ADMIN:
			macLog.log("Authorise is ADMIN");
			req.getSession().setAttribute("Authorise", authorise);
			break;
		case IJinFaTanCenter.AUTHORITY_MANAGER:
			macLog.log("Authorise is MANAGER");
			req.getSession().setAttribute("Authorise", authorise);
			break;
		case IJinFaTanCenter.AUTHORITY_NORMAL:
			macLog.log("Authorise is NORMAL");
			req.getSession().setAttribute("Authorise", authorise);
			targetPage = "main";
			break;
		default:
			targetPage = "404";
		}
		macLog.log("target page = " + targetPage);
		result = new ModelAndView(targetPage);
		
		macLog.flushCollectDebug();
		return result;
	}

	public IJinFaTanCenter getJftCenter() {
		return jftCenter;
	}

	@Resource(name = "jftCenter")
	public void setJftCenter(IJinFaTanCenter jftCenter) {
		this.jftCenter = jftCenter;
	}
}
