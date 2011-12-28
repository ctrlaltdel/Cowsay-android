package ch.fixme.cowsay;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import android.view.WindowManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.content.Context;
import android.content.res.AssetManager;

public class Cow
{
    public String style = "default";
    private String eyes;
    private String tongue;
    public String thoughts = "";
    public String message;
   
    private int think = 0;
    public int face = -1;
    
    public static final int FACE_BORG = 1;
    public static final int FACE_DEAD = 2;
    public static final int FACE_GREEDY = 3;
    public static final int FACE_PARANOID = 4;
    public static final int FACE_STONED = 5;
    public static final int FACE_TIRED = 6;
    public static final int FACE_WIRED = 7;
    public static final int FACE_YOUNG = 8;

    private final int WRAPLEN = 30;
    
    final Context context;

    public Cow(Context context) {
        this.context = context;
        construct_face();
        DisplayMetrics dm = new DisplayMetrics();
        ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(dm);
        Log.e("Cow", "x="+dm.widthPixels);

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
    		while (true) {
    			line = br.readLine();
    			Log.d("Cow", "Line: " + line);
    		  
    			if (line == null) {
    				return "Cow parsing failure";
    			};
    			
    			if (line.contains("$the_cow =")) { 
    				break;
    			};
    		}
    		
    		Log.d("Cow", "Got the cow!");
    		
			while ((line = br.readLine()) != null) {
				Log.d("Cow", "Line: " + line);

				if ((line.contains("EOC") || line.contains("EOF"))) {
					Log.d("Cow", "End of cow found");
					break;
				}
								
				sb.append(line + "\n");
			}
			
			String text = sb.toString();
			
			Log.d("Cow", "Before replacment: '" + text + "'");
			
			text = text.replace("$eyes", eyes);
			text = text.replace("${eyes}", eyes);
			text = text.replace("$tongue", tongue);
			text = text.replace("${tongue}", tongue);
			text = text.replace("$thoughts", thoughts);
			text = text.replace("${thoughts}", thoughts);
			
			text = text.replace("\\@", "@");
			text = text.replace("\\\\", "\\");
			
			Log.d("Cow", "Returns:\n'" + text + "'");
			
			return text;
		} catch (IOException e) {
			e.printStackTrace();
			return "No cow available due to some parser crash, too bad!";
		} 
    }

    public String[] getCowTypes() { 
    	ArrayList res = new ArrayList();
    	try {
			String[] cows = context.getAssets().list("cows");
			for (int i = 0; i < cows.length; i++) {
				String string = cows[i];
				
				res.add(string.substring(0, string.length() - 4));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    	String[] array = new String[res.size()];
    	return (String[]) res.toArray(array);
    }

    private String getBalloon() {
    	String balloon = "";
        int msglen = message.length();
        int maxlen = (msglen > WRAPLEN) ? WRAPLEN : msglen;
        int max2 = maxlen + 2;
        // Balloon borders
        // up-left, up-right, down-left, down-right, left, right
        final char[] border;
        if(think==1) {
            thoughts = "o";
            border = new char[] { '(',')','(',')','(',')' };
        } else if(msglen > WRAPLEN) {
            thoughts = "\\";
            border = new char[] { '/', '\\', '\\', '/', '|', '|' };
        } else {
            thoughts = "\\";
            border = new char[] { '<','>' };
        }
        // Draw balloon content
        balloon += " " + new String(new char[max2]).replace("\0", "_") + " \n";
        if (msglen > WRAPLEN) {
            for (int i = 0; i < msglen; i += WRAPLEN){
                // First line
                if(i < WRAPLEN){
                    balloon += border[0] +  " " + message.substring(0, WRAPLEN) + " " + border[1] + " \n";
                } else {
                // Last line
                    int sublen = message.substring(i, msglen-1).length();
                    if(sublen < WRAPLEN) {
                        int padlen = WRAPLEN - sublen;
                        String padding = new String(new char[padlen]).replace("\0", " ");
                        balloon += border[2] + " " + message.substring(i, msglen) + padding + border[3] + " \n";
                // Middle line
                    } else {
                        balloon += border[4] + " " + message.substring(i, i+WRAPLEN) + " " + border[5] + " \n";
                    }
                }
            }
        } else {
            balloon += border[0] + " " + message + " " + border[1] + " \n";
        }
        
        balloon += " " + new String(new char[max2]).replace("\0", "-") + " \n";
        return balloon;
    }

    private void construct_face() {
        eyes = "oo";
        tongue = "  ";
        switch(face){
            case FACE_BORG:
                eyes = "==";
                tongue = "  ";
                break;
            case FACE_DEAD:
                eyes = "xx";
                tongue = "U ";
                break;
            case FACE_GREEDY:
                eyes = "$$";
                tongue = "  ";
                break;
            case FACE_PARANOID:
                eyes = "@@";
                tongue = "  ";
                break;
            case FACE_STONED:
                eyes = "**";
                tongue = "U ";
                break;
            case FACE_WIRED:
                eyes = "00";
                tongue = "  ";
                break;
            case FACE_YOUNG:
            case FACE_TIRED:
                eyes = "..";
                tongue = "  ";
                break;
        }
    }
}
