package Archery_trainer.server;


import java.io.Serializable;

public class Archer implements Serializable {

    private String name;
    private boolean rightHanded;

    public Archer(String _name, boolean _rightHanded) {
        name = _name;
        rightHanded = _rightHanded;
    }

    public Archer(String _name) {
        name = _name;
        rightHanded = true;
    }

    public Archer() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String toString() {
        String res = "Archer: " + name + ", handedness: ";
        res += (rightHanded) ? "right" : "left";
        return res;
    }

}
