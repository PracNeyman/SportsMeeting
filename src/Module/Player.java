package Module;

public class Player {
    private char[] ID;
    private char[] name;
    private char sex;
    private int height;
    private int weigth;

    public char[] getID() {
        return ID;
    }

    public void setID(char[] ID) {
        this.ID = ID;
    }

    public int getWeigth() {
        return weigth;
    }

    public void setWeigth(int weigth) {
        this.weigth = weigth;
    }

    public char getSex() {
        return sex;
    }

    public void setSex(char sex) {
        this.sex = sex;
    }

    public char[] getName() {
        return name;
    }

    public void setName(char[] name) {
        this.name = name;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}

