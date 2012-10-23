package com.zsoft.SignalADemo;

import com.androidquery.AQuery;
import com.zsoft.SignalA.Transport.StateBase;
import com.zsoft.SignalA.transport.longpolling.LongPollingTransport;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

public class DemoActivity extends Activity {

	private AQuery aq;
	private com.zsoft.SignalA.Connection con = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.main);
    
		aq = new AQuery(this);
	    String url = "http://10.0.2.2:8081/echo/";

	    aq.id(R.id.btnConnect).clicked(this, "startSignalA");
	    aq.id(R.id.btnDisconnect).clicked(this, "stopSignalA");
	    aq.id(R.id.btnSend).clicked(this, "sendMessage");
	    aq.id(R.id.etText).text("zsoft");
		aq.id(R.id.tvReceivedMessage).text("No message yet");
	    
	    con = new com.zsoft.SignalA.Connection(url, this, new LongPollingTransport()) {

			@Override
			public void OnError(Exception exception) {
	            Toast.makeText(DemoActivity.this, "On error: " + exception.getMessage(), Toast.LENGTH_LONG).show();
			}

			@Override
			public void OnMessage(String message) {
				aq.id(R.id.tvReceivedMessage).text(message);
	            Toast.makeText(DemoActivity.this, "Message: " + message, Toast.LENGTH_LONG).show();
			}

			@Override
			public void OnStateChanged(StateBase oldState, StateBase newState) {
				aq.id(R.id.tvStatus).text(oldState.getState() + " -> " + newState.getState());
			}
		};
	}
	
	public void startSignalA()
	{
		if(con!=null)
			con.Start();
	}
	
	public void stopSignalA()
	{
		if(con!=null)
			con.Stop();
	}
	
	public void sendMessage()
	{
		if(con!=null &&			
			aq.id(R.id.etText).getText().length()>0)
		{
			con.Send(aq.id(R.id.etText).getText());
		}
	}
	
	
	
}