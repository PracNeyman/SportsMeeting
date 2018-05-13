package Module;

public class Team {
    private char[] ID;
    private char[] name;
    private char[] captionID;

    public char[] getCaptionID() {
        return captionID;
    }

    public void setCaptionID(char[] captionID) {
        this.captionID = captionID;
    }

    public char[] getName() {
        return name;
    }

    public void setName(char[] name) {
        this.name = name;
    }

    public char[] getID() {
        return ID;
    }

    public void setID(char[] ID) {
        this.ID = ID;
    }
}
