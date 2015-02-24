package main.java.webapp;

import java.util.HashSet;
import java.util.List;

import com.github.jreddit.entity.Submission;
import com.github.jreddit.retrieval.Submissions;
import com.github.jreddit.retrieval.params.QuerySyntax;
import com.github.jreddit.retrieval.params.SearchSort;
import com.github.jreddit.retrieval.params.TimeSpan;
import com.github.jreddit.utils.restclient.HttpRestClient;
import com.github.jreddit.utils.restclient.RestClient;

public class Fetcher {
	
	/**
	 * Checks if a given string has an image extension.
	 * @param imageString the String to be checked
	 * @return true if it has an image extension
	 */
	private static boolean hasImageExtension(String imageString){
		return imageString.endsWith(".jpg") || imageString.endsWith(".gif") || imageString.endsWith(".png");
	}
	
	/**
	 * Removes the submissions without thumbnails.
	 * @param submissions the fetched submissions.
	 */
	public static List<Submission> filterSubmissions(List<Submission> submissionsSubreddit){

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
	public static List<Submission> doSearch(String topic,boolean nsfw,TimeSpan time,int numberOfResults,Submission after){
		/* Searching through Reddit using JReddit */
		RestClient restClient = new HttpRestClient();
		Submissions subms = new Submissions(restClient);
		
		if(nsfw)
			topic += " nsfw:yes";
		else
			topic += " nsfw:no";
		
		//TODO: Surround with try catch and handle failed search because of over-loaded reddit's server.
		List<Submission> submissionsSubreddit = subms.search(topic, QuerySyntax.LUCENE, SearchSort.TOP, time, -1, numberOfResults, after, null, true); 
		return submissionsSubreddit;
	}

	public static Submission getLastSub(List<Submission> submissions) {
		return submissions.get(submissions.size() - 1);
	}
}
