package TrainSim;

import java.util.Random;

public class Route {

	Random r = new Random();
	
	private Track[] myTracks = new Track[30]; //30 as placeholder max size as no system exceeds this  //should have different name?
    private Station[] stations = new Station[30];
    private boolean isLoop = false;
	
    //////
    public Route(Track a) {
    	
    	for(int z = 0; z < myTracks.length;z++)
    	{
    		if (myTracks[z] != null)
    		{
    			//do nothing if it's not null
    		}
    		else //otherwise add it to array and break
    		{
    			myTracks[z] = a;
    			break;
    		}	
    	}
        
    	//first two stations are added from first track end points and stations recognize which Routes are available    
        stations[0] = a.getStation1();
        stations[1] = a.getStation2();
        
        a.getStation1().addRoute(this);
        a.getStation2().addRoute(this);
    }

//////////////
	public void addTrack(Track a)
	{
		if (isLoop)
		{
			System.out.println("Invalid: Can't add path to loop");
			return;
		}
		
		//write case checking track is connected
		if (a.getStation1() != myTracks[myTracks.length-1].getStation2() && !isLoop) 
		{
			System.out.println("Invalid: Track is not connected.");
			return;
		}
		else
		{
			//adds track to list along with station and route to added station if needed
			for(int z = 0; z < myTracks.length;z++)
	    	{
	    		if (myTracks[z] != null)
	    		{
	    			//do nothing if it's not null
	    		}
	    		else //otherwise add it to array and break
	    		{
	    			myTracks[z] = a;
	    			break;
	    		}	
	    	}
			
			for(int z = 0; z < stations.length;z++)
	    	{
	    		if (stations[z] != null)
	    		{
	    			//do nothing if it's not null/empty
	    		}
	    		else //otherwise add it to array and break
	    		{
	    			stations[z] = a.getStation2();
	    			break;
	    		}	
	    	}

			a.getStation2().addRoute(this);
		}

	}
	
	
	public void addLoop(Track a)
	{
		//checks to see if starting track is connected to old last track

	if (a.getStation1() != myTracks[myTracks.length-1].getStation2() && a.getStation2() != myTracks[0].getStation1() && !isLoop)
		{
			System.out.println("Invalid: Track is not conected.");
			return;
		}
		else
		{
			isLoop = true;
			
			for(int z = 0; z < myTracks.length;z++)
	    	{
	    		if (myTracks[z] != null)
	    		{
	    			//do nothing if it's not null
	    		}
	    		else //otherwise add it to array and break
	    		{
	    			myTracks[z] = a;
	    			break;
	    		}	
	    	}
			
		}
	
	}

	public boolean isLoop()
	{
		return isLoop;
	}

	////////////// work past here needed to atleast check
    public Track getTrack(int i) {
        if (i < 0 || i >= myTracks.length) {
            return null;
        }
        return myTracks[i];
    }

    public boolean contains(Station aStation) {
        boolean ans = false;
        for (int i = 0; i < stations.length; i++) {
            if (stations[i].equals(aStation)) {
                ans = true;
            }
        }
        return ans;
    }
