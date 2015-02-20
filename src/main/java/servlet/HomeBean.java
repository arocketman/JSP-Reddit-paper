package main.java.webapp;

import java.util.List;

import com.github.jreddit.entity.Submission;
import com.github.jreddit.retrieval.Submissions;
import com.github.jreddit.retrieval.params.SubmissionSort;
import com.github.jreddit.utils.restclient.HttpRestClient;
import com.github.jreddit.utils.restclient.RestClient;

public class HomeBean {
	List<Submission> submissions; 
	
	public HomeBean(){
		// Handle to Submissions, which offers the basic API submission functionality
		RestClient restClient = new HttpRestClient();
		Submissions subms = new Submissions(restClient);
		submissions = subms.ofSubreddit("all", SubmissionSort.TOP, -1, 100, null, null, true);
		Fetcher.filterSubmissions(submissions);
	}

	public List<Submission> getSubmissions() {
		return submissions;
	}

	public void setSubmissions(List<Submission> submissions) {
		this.submissions = submissions;
	}
	
	
}
