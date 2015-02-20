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
		//Get the parameter we need. 
		String topic = request.getParameter("topic");
		if(topic == null) {
			request.getRequestDispatcher("index.jsp").forward(request, response);
			return;
		}
		
		//Search
		RestClient restClient = new HttpRestClient();
		Submissions subms = new Submissions(restClient);
		
		HashSet<Submission> toRemove = new HashSet<Submission>();
		List<Submission> submissionsSubreddit = subms.search(topic + " self:no nsfw:no", QuerySyntax.LUCENE, SearchSort.HOT, TimeSpan.MONTH, -1, RESULTS_NUMBER, null, null, true); 
		
		if(submissionsSubreddit.isEmpty()){
			//TODO: Make this better
			request.getRequestDispatcher("index.jsp").forward(request, response);
			return;
		}
		//Filtering non-thumbnail results
		for(int i = 0; i < submissionsSubreddit.size(); i++){
			String thumb = submissionsSubreddit.get(i).getThumbnail();
			if(thumb.isEmpty() || !hasImageExtension(thumb))
				toRemove.add(submissionsSubreddit.get(i));
		}
		for(Submission s : toRemove){
			submissionsSubreddit.remove(s);
		}
		
		
		request.setAttribute("submissions", submissionsSubreddit);
		request.getRequestDispatcher("display.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}
	
	public boolean hasImageExtension(String imageString)
	{
		return imageString.endsWith(".jpg") || imageString.endsWith(".gif") || imageString.endsWith(".png");
	}

}
