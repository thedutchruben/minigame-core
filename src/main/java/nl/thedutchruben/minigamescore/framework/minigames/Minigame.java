package nl.thedutchruben.minigamescore.framework.minigames;

public abstract class Minigame {
    private String name = "";

    public Minigame(String name) {
        this.name = name;
    }

    public abstract void enable();

    public abstract void disable();

    public String getName() {
        return name;
    }
}
