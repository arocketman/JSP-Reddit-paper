package main.java.webapp;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.github.jreddit.entity.Submission;
import com.github.jreddit.retrieval.Submissions;
import com.github.jreddit.retrieval.params.QuerySyntax;
import com.github.jreddit.retrieval.params.SearchSort;
import com.github.jreddit.retrieval.params.TimeSpan;
import com.github.jreddit.utils.restclient.HttpRestClient;
import com.github.jreddit.utils.restclient.RestClient;

/**
 * Servlet implementation class Display
 */
@WebServlet("/Display")
public class Display extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final int RESULTS_NUMBER = 50;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Display() {
        super();
        // TODO Auto-generated constructor stub
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
		boolean nsfwAllowed = processNswfParam(nsfwParam);
		TimeSpan eta = processEtaParam(etaParam);
		
		List<Submission> submissions = doSearch(topic,nsfwAllowed,eta,RESULTS_NUMBER);
		
		/* Checking if the given query returned empty */
		if(submissions.isEmpty()){
			request.setAttribute("errMessage", "No results were returned from your search");
			request.getRequestDispatcher("index.jsp").forward(request, response);
			return;
		}
		
		filterSubmissions(submissions);
		
		request.setAttribute("submissions", submissions);
		request.setAttribute("etaparam", etaParam);
		request.setAttribute("topic", topic);
		request.setAttribute("nsfwparam", nsfwParam);
		request.getRequestDispatcher("display.jsp").forward(request, response);
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
	
	/**
	 * Checks if a given string has an image extension.
	 * @param imageString the String to be checked
	 * @return true if it has an image extension
	 */
	private boolean hasImageExtension(String imageString){
		return imageString.endsWith(".jpg") || imageString.endsWith(".gif") || imageString.endsWith(".png");
	}
	
	/**
	 * Removes the submissions without thumbnails.
	 * @param submissions the fetched submissions.
	 */
	private List<Submission> filterSubmissions(List<Submission> submissionsSubreddit){

		HashSet<Submission> toRemove = new HashSet<Submission>();
		
		/* Adding submission without thumbnail to the toRemove set*/
		for(int i = 0; i < submissionsSubreddit.size(); i++){
			Submission submission = submissionsSubreddit.get(i);
			String thumb = submission.getThumbnail();
			if(thumb.isEmpty() || !hasImageExtension(thumb)){
				
				if(submission.isNSFW())
					submission.setThumbnail("http://i.imgur.com/UHzw6.png");
				else
					toRemove.add(submission);
				
			}
		}
		
		/* Removing flagged submissions */
		for(Submission s : toRemove){
			submissionsSubreddit.remove(s);
		}
		
		return submissionsSubreddit;
	}
	
	/**
	 * Performs the search on reddit.
	 * @param topic the searched term.
	 * @param nsfw not safe for work results allowed
	 * @param time the timespan of the submmissions
	 * @param numberOfResults the number of results
	 * @return the submissions
	 */
	private List<Submission> doSearch(String topic,boolean nsfw,TimeSpan time,int numberOfResults){
		/* Searching through Reddit using JReddit */
		RestClient restClient = new HttpRestClient();
		Submissions subms = new Submissions(restClient);
		
		if(nsfw)
			topic += " nsfw:yes";
		else
			topic += " nsfw:no";
		
		List<Submission> submissionsSubreddit = subms.search(topic, QuerySyntax.LUCENE, SearchSort.HOT, time, -1, numberOfResults, null, null, true); 
		return submissionsSubreddit;
	}

}
