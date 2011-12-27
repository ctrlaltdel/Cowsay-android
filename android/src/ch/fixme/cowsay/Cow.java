package ch.fixme.cowsay;

public class Cow
{

    String cow = "default"; //.cow
    String eyes = "oo";
    String tongue = "  ";
    String[] message;
    
    int face;
    final int FACE_BORG = 1;
    final int FACE_DEAD = 2;
    final int FACE_GREEDY = 3;
    final int FACE_PARANOID = 4;
    final int FACE_STONED = 5;
    final int FACE_TIRED = 6;
    final int FACE_WIRED = 7;
    final int FACE_YOUNG = 8;

    // up-left, up-right, down-left, down-right, left, right
    final char[] border = new char[] { '/', '\\', '\\', '/', '|', '|' };
    final String format = "%s %s-%s %s\n";

    public Cow(String message) {
        // Process message 
        this.message = new String[]{};
        // Construct balloon
        // Construct face
    }

    public Cow(String message, String cow, String eyes, String tongue) {
    	this.cow = cow;
        this.eyes = eyes;
        this.tongue = tongue;
        //super(message);
    }
    
    public String get_cow() { // The method used in Main.java to get the constructed cow
        return "";
    }


    private void list_cowfiles() {
    }

    private void slurp_input() {
    }

    private int maxlen() {
        return 10;
    }

    private void construct_balloon() {
        int maxlen = maxlen();
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
