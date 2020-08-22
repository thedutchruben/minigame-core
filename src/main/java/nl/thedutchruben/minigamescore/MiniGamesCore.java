package nl.thedutchruben.minigamescore;

import nl.thedutchruben.minigamescore.modules.minigames.MinigameModule;
import org.bukkit.plugin.java.JavaPlugin;

public class MiniGamesCore extends JavaPlugin {
    private static MiniGamesCore instance;
    private MinigameModule minigameModule;
    @Override
    public void onEnable() {
        instance = this;
        minigameModule = new MinigameModule();
        minigameModule.loadMiniGames();
    }

    public static MiniGamesCore getInstance() {
        return instance;
    }
}
