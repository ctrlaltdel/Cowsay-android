package ch.fixme.cowsay;

public class Cow
{

    String cow = "default"; //.cow
    String eyes = "oo";
    String tongue = "  ";

    String message = "I Love Android";
    
    int face;
    private final int FACE_BORG = 1;
    private final int FACE_DEAD = 2;
    private final int FACE_GREEDY = 3;
    private final int FACE_PARANOID = 4;
    private final int FACE_STONED = 5;
    private final int FACE_TIRED = 6;
    private final int FACE_WIRED = 7;
    private final int FACE_YOUNG = 8;

    void list_cowfiles() {
    }

    void slurp_input() {
    }

    void maxlen() {
    }

    void construct_balloon() {
    }

    void construct_face() {
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

    String get_cow() {
        return "";
    }

    void display_usage() {
    }
}
