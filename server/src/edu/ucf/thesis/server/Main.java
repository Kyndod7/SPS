package edu.ucf.thesis.server;

import edu.ucf.thesis.server.experiment.GCMDelay;
import edu.ucf.thesis.server.experiment.GCMSessionTester;
import edu.ucf.thesis.server.experiment.GCMTrafficSimulator;
import edu.ucf.thesis.server.experiment.SPSDelay;
import edu.ucf.thesis.server.experiment.SPSSessionTester;
import edu.ucf.thesis.server.experiment.SPSTrafficSimulator;
import edu.ucf.thesis.server.experiment.TrafficSimulator.UserType;

public class Main {

	public static void main(String[] args) {
		if (args[0].toLowerCase().equals("traffic"))
		{
			if (args[1].toLowerCase().equals("sps")) 
			{
				if(args[2].toLowerCase().equals("social")) 
				{
					(new SPSTrafficSimulator(UserType.SOCIAL)).start();
				}
				else if (args[2].toLowerCase().equals("business")) 
				{
					(new SPSTrafficSimulator(UserType.BUSINESS)).start();
				} 
				else 
				{
					System.err.println("Invalid arguments");
					System.exit(1);
				}	
			} 
			else if(args[1].toLowerCase().equals("gcm")) 
			{
				if(args[2].toLowerCase().equals("social")) 
				{
					(new GCMTrafficSimulator(UserType.SOCIAL)).start();
				}
				else if (args[2].toLowerCase().equals("business")) 
				{
					(new GCMTrafficSimulator(UserType.BUSINESS)).start();
				} 
				else 
				{
					System.err.println("Invalid arguments");
					System.exit(1);
				}
			}
			else
			{
				System.err.println("Invalid arguments");
				System.exit(1);
			}
		} else if (args[0].toLowerCase().equals("delay"))
		{
			if (args[1].toLowerCase().equals("sps")) 
			{
				(new SPSDelay()).start();
			} 
			else if(args[1].toLowerCase().equals("gcm")) 
			{
				(new GCMDelay()).start();
			}
			else
			{
				System.err.println("Invalid arguments");
				System.exit(1);
			}
		} 
		else if (args[0].toLowerCase().equals("session"))
		{
			if (args[1].toLowerCase().equals("sps")) 
			{
				(new SPSSessionTester()).start();
			} 
			else if(args[1].toLowerCase().equals("gcm")) 
			{
				(new GCMSessionTester()).start();
			}
			else
			{
				System.err.println("Invalid arguments");
				System.exit(1);
			}
		}
	}

}
