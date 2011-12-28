package ch.fixme.cowsay;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import android.util.Log;
import android.content.Context;
import android.content.res.AssetManager;

public class Cow
{
    public String style = "default";
    private String eyes = "oo";
    private String tongue = "  ";
    public String thoughts = "";
    public String message;
   
    private int think = 0;
    public int face;
    
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

    public Cow(Context myContext) {
        context = myContext;
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
        int maxlen = (message.length() > WRAPLEN) ? WRAPLEN : message.length();
        // Balloon borders
        // up-left, up-right, down-left, down-right, left, right
        final char[] border;
        if(think==1) {
            thoughts = "o";
            border = new char[] { '(',')','(',')','(',')' };
        } else if(message.length() < WRAPLEN) {
            thoughts = "\\";
            border = new char[] { '<','>' };
        } else {
            thoughts = "\\";
            border = new char[] { '/', '\\', '\\', '/', '|', '|' };
        }
        // Draw balloon content
        balloon += " " + new String(new char[maxlen]).replace("\0", "_") + " \n";
        if (message.length() > WRAPLEN) {
            for (int i = 0; i < message.length(); i += WRAPLEN){
                if(i < WRAPLEN){ // First line
                    balloon += border[0] + message.substring(0, WRAPLEN) + border[1] + " \n";
                } else {
                    int sublen = message.substring(i, message.length()-1).length();
                    if(sublen < WRAPLEN) { // Last line
                        int padlen = WRAPLEN - sublen;
                        String padding = new String(new char[padlen]).replace("\0", " ");
                        balloon += border[2] + message.substring(i, message.length()-1) + padding + border[3] + " \n";
                    } else { // Middle line
                        balloon += border[4] + message.substring(i, i+WRAPLEN) + border[5] + " \n";
                    }
                }
            }
        } else {
            balloon += border[0] + message + border[1] + " \n";
        }
        
        balloon += " " + new String(new char[maxlen]).replace("\0", "-") + " \n";
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
}
