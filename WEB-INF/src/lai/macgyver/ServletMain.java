package lai.macgyver;

import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.http.*;

public class ServletMain extends HttpServlet {

	public void doGet(HttpServletRequest requset, HttpServletResponse response)
			throws ServletException, IOException {
		
		PrintWriter out =response.getWriter();
		out.println("Hello World from Eclipse!!");
	}
	

}
