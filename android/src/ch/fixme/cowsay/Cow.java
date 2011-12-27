package ch.fixme.cowsay;

import android.util.Log;

public class Cow
{
    private String cow;
    private String eyes = "oo";
    private String tongue = "  ";
    private String thoughts = "";
    private String balloon = "";
    private String[] message;
    private String thecow = "          ^__^\n         \\  (oo)\\_______\n            (__)\\       )\\/\\\n                ||----w |\n                ||     ||\n"; 
   
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

    public Cow(String message, String cow, int face) {
        this.maxlen = (message.length() < WRAPLEN) ? message.length() : WRAPLEN;
        this.message = message.split("\n");
        Log.e("TEST", "message="+message);
        this.cow = cow;
        this.face = face;
        construct_balloon();
        construct_face();
    }
    
    public String get_cow() {
        return balloon + thecow;
    }


    private void list_cowfiles() {
    }

    private void construct_balloon() {
        int max2 = maxlen + 2;
        // up-left, up-right, down-left, down-right, left, right
        final char[] border;
        if(think==1) {
            thoughts = "o";
            border = new char[] { '(',')','(',')','(',')' };
        } else if(this.message.length < 2) {
            thoughts = "\\";
            border = new char[] { '<','>' };
        } else {
            thoughts = "\\";
            border = new char[] { '/', '\\', '\\', '/', '|', '|' };
        }
        // Draw balloon
        if(this.message.length > 1){
            balloon += " " + new String(new char[max2]).replace("\0", "_") + " \n";
            balloon += border[0] + message[0] + border[1];
            for (int i = 1; i < message.length - 1; i++) {
                balloon += border[4] + message[i] + border[5];
            }
            balloon += border[2] + message[message.length-1] + border[3];
        } else {
            balloon += border[0] + message[0] + border[1];
        }
        Log.e("TEST", balloon);
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
