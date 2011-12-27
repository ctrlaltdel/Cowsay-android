package ch.fixme.cowsay;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

public class Cow
{
    String style = "default"; //.cow
    String eyes = "oo";
    String tongue = "  ";
    String thoughts = "";
    String[] message;
    
    int think = 0;
    int face;
    final int FACE_BORG = 1;
    final int FACE_DEAD = 2;
    final int FACE_GREEDY = 3;
    final int FACE_PARANOID = 4;
    final int FACE_STONED = 5;
    final int FACE_TIRED = 6;
    final int FACE_WIRED = 7;
    final int FACE_YOUNG = 8;

    final String format = "%s %s-%s %s\n";
    
    final Context context;

    public Cow(Context myContext, String message) {
        this.message = message.split("\n"); // Always split (cowsay has a -n option not to split)
        context = myContext;
        // Construct balloon
        // Construct face
    }

    //public Cow(String message, String cow, String eyes, String tongue) {
    //    this.cow = cow;
    //    this.eyes = eyes;
    //    this.tongue = tongue;
    //    //super(message);
    //}
    
    public String get_cow() { // The method used in Main.java to get the constructed cow
    	try {
			AssetManager mngr = context.getAssets();
			InputStream is = mngr.open("cows/" + style + ".cow");
			return parse_cowfile(is);
    	} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
    }

    private String parse_cowfile(InputStream is) {
    	BufferedReader br = new BufferedReader(new InputStreamReader(is));
		
		StringBuilder sb = new StringBuilder();
    	try {
        	String line;
    		// Jump to cow start
    		do {
    			line = br.readLine(); 
    			Log.d("Cow", "Line: '" + line + "'");
    		} while (line != null && !line.equals("$the_cow = <<\"EOC\";"));
    		
    		Log.d("Cow", "Got the cow!");
    		
			while ((line = br.readLine()) != null) {
				Log.d("Cow", "Line: " + line);

				if ((line.equals("EOC"))) {
					break;
				}
								
				sb.append(line + "\n");
			}
			
			String text = sb.toString();
			
			text = text.replace("$eyes", eyes);
			text = text.replace("$tongue", tongue);
			text = text.replace("$thoughts", thoughts);
			
			return text;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "No cow available due to some parser crash, too bad!";
		} 
    }

    private String[] list_cowfiles() {    	
    	try {
			String[] cows = context.getAssets().list("cows/");
			return cows;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
    }

    private void slurp_input() {
    }

    private int maxlen() {
        return 10;
    }

    private void construct_balloon() {
        final char[] border;
        //int max = maxlen(this.message);
        int max = 10;
        int max2 = max + 2;
        if(think==1) {
            thoughts = "o";
        } else if(this.message.length < 2) {
            thoughts = "\\";
            border = new char[] { ' ' };
        } else {
            thoughts = "\\";
            // up-left, up-right, down-left, down-right, left, right
            border = new char[] { '/', '\\', '\\', '/', '|', '|' };
        }
    }

    private void construct_face() {
        switch(face){
            case FACE_BORG:
                eyes = "==";
            case FACE_DEAD:
                eyes = "xx";
                tongue = "U ";
            case FACE_GREEDY:
                eyes = "$$";
            case FACE_PARANOID:
                eyes = "@@";
            case FACE_STONED:
                eyes = "**";
                tongue = "U ";
            case FACE_TIRED:
                eyes = "..";
            case FACE_WIRED:
                eyes = "00";
            case FACE_YOUNG:
                eyes = "..";
        }
    }

    private void display_usage() {
    }
}
