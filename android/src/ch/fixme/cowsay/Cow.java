package ch.fixme.cowsay;

import android.content.Context;

public class Cow
{
    String cow = "default"; //.cow
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
        return " _____________________\n< Java sucks big time >\n ---------------------\n        \\   ^__^\n         \\  (oo)\\_______\n            (__)\\       )\\/\\\n                ||----w |\n                ||     ||\n";
    }


    private void list_cowfiles() {
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
