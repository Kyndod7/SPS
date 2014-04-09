package edu.ucf.thesis.app.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import edu.ucf.thesis.app.R;

public class LogsFragment extends Fragment {
	
	private BroadcastReceiver mLogEventReceiver;
	private IntentFilter mLogEventFilter;
	private TextView mTextView;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		mLogEventReceiver = new BroadcastReceiver() {

			@Override
			public void onReceive(Context context, Intent intent) {
				String message = intent.getExtras().getString("event");
				if (message != null) {
					// display log event
					mTextView.append(message);
					Log.d("LogsFragment", message);
				}
			}
		};
		
		mLogEventFilter = new IntentFilter();
		mLogEventFilter.addAction("LOG_EVENT_ACTION");
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_logs, null);
		mTextView = (TextView) view.findViewById(R.id.textView);
		return view; 
	}
	
	// TODO: save logs when flipping rotation

	@Override
	public void onResume() {
	  super.onResume();
	  getActivity().getApplicationContext().registerReceiver(mLogEventReceiver, mLogEventFilter);
	}
	
	@Override
	public void onPause() {
	  super.onPause(); 
	  getActivity().getApplicationContext().unregisterReceiver(mLogEventReceiver);
	}
	
}
