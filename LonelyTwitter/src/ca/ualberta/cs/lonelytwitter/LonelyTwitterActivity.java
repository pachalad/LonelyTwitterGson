package ca.ualberta.cs.lonelytwitter;

import java.util.ArrayList;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import ca.ualberta.cs.lonelytwitter.data.GsonDataManager;
import ca.ualberta.cs.lonelytwitter.data.IDataManager;

public class LonelyTwitterActivity extends Activity {

	private IDataManager dataManager;

	private EditText bodyText;

	private ListView oldTweetsList;

	private ArrayList<Tweet> tweets;

	private ArrayAdapter<Tweet> tweetsViewAdapter;
	
	private Summary sum;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.main);
		sum= new Summary();
		dataManager = new GsonDataManager(this);

		bodyText = (EditText) findViewById(R.id.body);
		oldTweetsList = (ListView) findViewById(R.id.oldTweetsList);
	}

	@Override
	protected void onStart() {
		super.onStart();

		tweets = dataManager.loadTweets();
		sum= dataManager.loadStats();
		tweetsViewAdapter = new ArrayAdapter<Tweet>(this,
				R.layout.list_item, tweets);
		oldTweetsList.setAdapter(tweetsViewAdapter);
	}

	public void save(View v) {

		String text = bodyText.getText().toString();

		Tweet tweet = new Tweet(new Date(), text);
		tweets.add(tweet);

		tweetsViewAdapter.notifyDataSetChanged();

		bodyText.setText("");
		dataManager.saveTweets(tweets);
	}

	public void clear(View v) {

		tweets.clear();
		tweetsViewAdapter.notifyDataSetChanged();
		dataManager.saveTweets(tweets);
		dataManager.saveStats(sum);
	}
	public void summary(View view){
		createSummary();
	
	}
	public void createSummary(){
		sum.setNumOfTweets(countTweets());
		sum.setAvgLenOfTweets(averageTweets());
		Intent intent= new Intent(this,SummaryActivity.class);
		intent.putExtra("num",Integer.toString(sum.getNumOfTweets()));
		intent.putExtra("avg",Long.toString(sum.getAvgLenOfTweets()));
		startActivity(intent);
		
	}
	public int countTweets(){
		return tweets.size();
	}
	public long averageTweets(){
		int totallength=0;
		long avglength=0;
		for (int i=0;i<tweets.size();i++){
			totallength=totallength+tweets.get(i).getTweetBody().length();
		}
		avglength=totallength/tweets.size();
		return avglength;
	}

}