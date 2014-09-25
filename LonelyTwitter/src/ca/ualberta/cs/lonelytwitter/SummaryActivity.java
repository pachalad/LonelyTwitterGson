package ca.ualberta.cs.lonelytwitter;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

public class SummaryActivity extends Activity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		String num;
		String avg;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_summary);
		Intent intent=getIntent();
		num=intent.getStringExtra("num");
		avg = intent.getStringExtra("avg");
		
		TextView textView = (TextView) findViewById(R.id.num);
		textView.setTextSize(35);
		textView.setText(num);
		
		textView = (TextView) findViewById(R.id.avglength);
		textView.setTextSize(35);
		textView.setText(avg);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.summary, menu);
		return true;
	}

}
