package edu.ucf.thesis.app.ui;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ToggleButton;
import edu.ucf.thesis.app.R;
import edu.ucf.thesis.app.push.DataClient;
import edu.ucf.thesis.app.push.CellularDataSwitch;
import edu.ucf.thesis.app.push.WifiSwitch;
import edu.ucf.thesis.app.util.AppLogger;
import edu.ucf.thesis.app.util.TrafficGenerator;

public class ControlsFragment extends Fragment {
	
	private TrafficGenerator mTrafficGenerator;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_controls, null);

		ToggleButton toggleButton = (ToggleButton) view.findViewById(R.id.cellularDataTglBtn);
		toggleButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				CellularDataSwitch cellularDataSwitch = new CellularDataSwitch(getActivity());
				if(((ToggleButton) v).isChecked()) {
					cellularDataSwitch.on();
				} else {
					cellularDataSwitch.off();
				}
			}
	         
	     });

		ToggleButton wifiTglBtn = (ToggleButton) view.findViewById(R.id.wifiTglBtn);
		wifiTglBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				WifiSwitch wifiSwitch = new WifiSwitch(getActivity());
				if(((ToggleButton) v).isChecked()) {
					wifiSwitch.on();
				} else {
					wifiSwitch.off();
				}
			}
	         
	     });
		
		ToggleButton trafficTglBtn = (ToggleButton) view.findViewById(R.id.trafficTglBtn);
		trafficTglBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if(((ToggleButton) v).isChecked()) {
					mTrafficGenerator = new TrafficGenerator();
					mTrafficGenerator.start();
				} else {
					mTrafficGenerator.stop();
				}
			}
	         
	     });
		
		Button sendButton = (Button) view.findViewById(R.id.sendButton);
		sendButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(v.getContext());
				String serverIp = pref.getString("setting_server_ip", "");
				int serverPort = Integer.parseInt(pref.getString("setting_server_port", ""));
				DataClient dc = new DataClient(serverIp, serverPort);
				dc.retrieveData();
			}
	         
	     });
		
		Button clearButton = (Button) view.findViewById(R.id.clearButton);
		clearButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				AppLogger.getInstance().clear();
			}
	         
	     });
		
		return view;
	}

}
