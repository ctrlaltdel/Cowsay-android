package ch.fixme.cowsay;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

public class Cow
{
    public int face;
    public String style;
    public String thoughts;
    public String message = "Moo";
    
    private int think = 0;
    private String rawCow = "";
    private String eyes;
    private String tongue;
    
    public static final int FACE_DEFAULT = 0;
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
    final AssetManager mngr;

    public Cow(Context context) {
        this.context = context;
        this.mngr = context.getAssets();
        getCowFile();
    }
    
    public String getFinalCow() {
        construct_face();
        String newCow = new String(rawCow)
            .replace("$eyes", eyes)
            .replace("${eyes}", eyes)
            .replace("$tongue", tongue)
            .replace("${tongue}", tongue)
            .replace("$thoughts", thoughts)
            .replace("${thoughts}", thoughts)
            .replace("\\@", "@")
            .replace("\\\\", "\\");
		return getBalloon() + newCow;
    }

    public String[] getCowTypes() { 
    	ArrayList<String> res = new ArrayList<String>();
    	try {
			String[] cows = context.getAssets().list("cows");
			for (int i = 0; i < cows.length; i++) {
				String string = cows[i];
				res.add(string.substring(0, string.length() - 4));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
    	return (String[]) res.toArray(new String[res.size()]);
    }

    private String getBalloon() {
        message = message.replace("\n", ""); //TODO: handle multiline input
        int msglen = message.length();
        int maxlen = (msglen > WRAPLEN) ? WRAPLEN : msglen;
        // Balloon borders
        // up-left, up-right, down-left, down-right, left, right
        final char[] border;
        if(think==1) {
            border = new char[] { '(',')','(',')','(',')' };
        } else if(msglen > WRAPLEN) {
            border = new char[] { '/', '\\', '\\', '/', '|', '|' };
        } else {
            border = new char[] { '<','>' };
        }
        // Draw balloon content
    	StringBuffer balloon = new StringBuffer();
        balloon.append(" ").append(new String(new char[maxlen+2]).replace("\0", "_")).append(" \n"); // append(char[] chars, int start, int length)
        if (msglen > WRAPLEN) {
            for (int i = 0; i < msglen; i += WRAPLEN){
                // First line
                if(i < WRAPLEN){
                    balloon.append(border[0]).append(" ").append(message.substring(0, WRAPLEN)).append(" ").append(border[1]).append(" \n");
                } else {
                // Last line
                    int sublen = message.substring(i, msglen-1).length();
                    if(sublen < WRAPLEN) {
                        int padlen = WRAPLEN - sublen;
                        String padding = new String(new char[padlen]).replace("\0", " ");
                        balloon.append(border[2]).append(" ").append(message.substring(i, msglen)).append(padding).append(border[3]).append(" \n");
                // Middle line
                    } else {
                        balloon.append(border[4]).append(" ").append(message.substring(i, i+WRAPLEN)).append(" ").append(border[5]).append(" \n");
                    }
                }
            }
        } else {
            balloon.append(border[0]).append(" ").append(message).append(" ").append(border[1]).append(" \n");
        }
        
        balloon.append(" ").append(new String(new char[maxlen+2]).replace("\0", "-")).append(" \n");
        return balloon.toString();
    }

    public void getCowFile(){
        if(style==null){
            Log.e("getCowFile()", "FIXME: cow style is null");
            return;
        }
        try {
            InputStream is = mngr.open("cows/" + style + ".cow");
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();
        	String line;
    		// Jump to cow start
    		while (true) {
    			line = br.readLine();
    			Log.d("Cow", "Line: " + line);
    			if (line == null) {
    				rawCow = "Cow parsing failure";
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
            rawCow = sb.toString();
		} catch (IOException e) {
			e.printStackTrace();
			rawCow = "No cow available due to some parser crash, too bad!";
		} 
    }

    private void construct_face() {
        if(think==1) {
            thoughts = "o";
        } else {
            thoughts = "\\";
        }
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
