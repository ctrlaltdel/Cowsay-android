package ch.fixme.cowsay;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import android.util.Log;
import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

public class Cow
{
    private String style = "default";
    private String eyes = "oo";
    private String tongue = "  ";
    private String thoughts = "";
    private String balloon = "";
    public String[] message;
   
    private int maxlen; 
    private int think = 0;
    private int face;
    
    public static final int FACE_BORG = 1;
    public static final int FACE_DEAD = 2;
    public static final int FACE_GREEDY = 3;
    public static final int FACE_PARANOID = 4;
    public static final int FACE_STONED = 5;
    public static final int FACE_TIRED = 6;
    public static final int FACE_WIRED = 7;
    public static final int FACE_YOUNG = 8;

    private final int WRAPLEN = 40;
    
    final Context context;

    public Cow(Context myContext, String message, String cow, int face) {
        Log.e("TEST", "message="+message);
        context = myContext;
        this.maxlen = (message.length() < WRAPLEN) ? message.length() : WRAPLEN;
        this.message = message.split("\n");
        this.face = face;
        construct_face();
    }
    
    public String asString() {
    	try {
			AssetManager mngr = context.getAssets();
			InputStream is = mngr.open("cows/" + style + ".cow");
			return getBalloon() + parse_cowfile(is);
    	} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "No cow today";
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
			
			Log.d("Cow", "Returns: '" + text + "'");
			
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

    private String getBalloon() {
    	String balloon = "";
        int max2 = maxlen + 2;
        // up-left, up-right, down-left, down-right, left, right
        final char[] border;
        if(think==1) {
            thoughts = "o";
            border = new char[] { '(',')','(',')','(',')' };
        } else if(message.length < 2) {
            thoughts = "\\";
            border = new char[] { '<','>' };
        } else {
            thoughts = "\\";
            border = new char[] { '/', '\\', '\\', '/', '|', '|' };
        }
        // Draw balloon content
        if(message.length > 1){
            balloon += " " + new String(new char[max2]).replace("\0", "_") + " \n";
            balloon += border[0] + message[0] + border[1];
            for (int i = 1; i < message.length - 1; i++) {
                balloon += border[4] + message[i] + border[5];
            }
            balloon += border[2] + message[message.length-1] + border[3];
            balloon += " " + new String(new char[max2]).replace("\0", "-") + " \n";
        } else {
            balloon += border[0] + message[0] + border[1];
        }
        
        return balloon;
    }

    private void construct_face() {
        switch(face){
            case FACE_BORG:
                eyes = "==";
                break;
            case FACE_DEAD:
                eyes = "xx";
                tongue = "U ";
                break;
            case FACE_GREEDY:
                eyes = "$$";
                break;
            case FACE_PARANOID:
                eyes = "@@";
                break;
            case FACE_STONED:
                eyes = "**";
                tongue = "U ";
                break;
            case FACE_WIRED:
                eyes = "00";
                break;
            case FACE_YOUNG:
            case FACE_TIRED:
                eyes = "..";
                break;
            default:
                break;
        }
    }

    private void display_usage() {
    }
}
