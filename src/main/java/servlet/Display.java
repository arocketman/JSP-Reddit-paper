package main.java.webapp;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.github.jreddit.entity.Submission;
import com.github.jreddit.retrieval.params.TimeSpan;

/**
 * Servlet implementation class Display
 */
@WebServlet("/Display")
public class Display extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final int RESULTS_NUMBER = 100;
	private Submission lastSub;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Display() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/* Getting the searched parameter */ 
		String topic = request.getParameter("topic");
		if(topic == null) {
			request.getRequestDispatcher("index.jsp").forward(request, response);
			return;
		}
		
		String etaParam = request.getParameter("eta");
		String nsfwParam = request.getParameter("nsfw");
		String afterParam = request.getParameter("after");
		boolean nsfwAllowed = processNswfParam(nsfwParam);
		TimeSpan eta = processEtaParam(etaParam);
		Submission after = processAfterParam(afterParam);
		
		List<Submission> submissions = Fetcher.doSearch(topic,nsfwAllowed,eta,RESULTS_NUMBER,after);
		
		/* Checking if the given query returned empty */
		if(submissions.isEmpty()){
			request.setAttribute("errMessage", "No results were returned from your search");
			request.getRequestDispatcher("index.jsp").forward(request, response);
			return;
		}
		
		Fetcher.filterSubmissions(submissions);
		lastSub = Fetcher.getLastSub(submissions);
		
		request.setAttribute("submissions", submissions);
		request.setAttribute("etaparam", etaParam);
		request.setAttribute("topic", topic);
		request.setAttribute("nsfwparam", nsfwParam);
		
		String isAjaxRequest = request.getParameter("ajax");
		if(isAjaxRequest != null)
			request.getRequestDispatcher("results.jsp").forward(request, response);
		else
			request.getRequestDispatcher("display.jsp").forward(request, response);
	}
	
	private Submission processAfterParam(String afterParam) {
		if(afterParam == null)
			return null;
		else{
			return lastSub;
		}
	}

	/**
	 * Processes the eta param
	 * @param etaParam
	 * @return the time span aka when to search.
	 */
	private TimeSpan processEtaParam(String etaParam) {
		if(etaParam != null){
			if(etaParam.equalsIgnoreCase("hour")) return TimeSpan.HOUR;
			if(etaParam.equalsIgnoreCase("day")) return TimeSpan.DAY;
			if(etaParam.equalsIgnoreCase("month")) return TimeSpan.MONTH;
			if(etaParam.equalsIgnoreCase("week")) return TimeSpan.WEEK;
			if(etaParam.equalsIgnoreCase("year")) return TimeSpan.YEAR;
			if(etaParam.equalsIgnoreCase("all")) return TimeSpan.ALL;
		}
		return TimeSpan.ALL;
	}

	/**
	 * Processes the nsfw param
	 * @param nsfwParam
	 * @return true if nsfw is allowed
	 */
	private boolean processNswfParam(String nsfwParam) {
		if(nsfwParam != null){
			if(nsfwParam.equalsIgnoreCase("yes")) return true;
		}
		return false;
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}
	


}
