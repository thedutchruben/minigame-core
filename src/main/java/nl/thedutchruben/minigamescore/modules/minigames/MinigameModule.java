package nl.thedutchruben.minigamescore.modules.minigames;

import nl.thedutchruben.minigamescore.MiniGamesCore;
import nl.thedutchruben.minigamescore.framework.minigames.Minigame;
import nl.thedutchruben.minigamescore.framework.minigames.MinigameLoader;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class MinigameModule {

    private List<Minigame> minigames = new ArrayList<>();

    public void loadMiniGames() {
        File f = new File(MiniGamesCore.getInstance().getDataFolder(), "minigames");
        if (!f.exists() && !f.mkdirs()) {
            return;
        }
        Arrays.stream(Objects.requireNonNull(f.listFiles())).filter(x -> !x.isDirectory() && x.getName().endsWith(".jar")).forEach(this::loadMinigame);
        enableMinigames();
    }

    private void loadMinigame(File f) {
        Minigame minigame;
        MinigameLoader minigameClassLoader;
        try (JarFile jar = new JarFile(f)) {
            // try loading the addon
            // Get description in the addon.yml file
            YamlConfiguration data = addonDescription(jar);

            // Load the addon
            minigameClassLoader = new MinigameLoader(data, f, this.getClass().getClassLoader());

            // Get the addon itself
            minigame = minigameClassLoader.getMinigame();
        } catch (Exception e) {
            // We couldn't load the addon, aborting.
            e.printStackTrace();
            return;
        }



        // Add it to the list of addons
        minigames.remove(minigame);
        minigames.add(minigame);


    }

    public void enableMinigames() {
        minigames.forEach(this::enableMinigame);

    }

    public void disableMinigames() {
        minigames.forEach(this::disableMinigame);

    }


    private void enableMinigame(Minigame minigame) {
        minigame.enable();
        System.out.println("Enable minigame : " + minigame.getName());
    }

    private void disableMinigame(Minigame minigame) {
        minigame.disable();
        System.out.println("Disable addon : " + minigame.getName());
    }

    private YamlConfiguration addonDescription(JarFile jar) throws IOException, InvalidConfigurationException {
        // Obtain the addon.yml file
        JarEntry entry = jar.getJarEntry("plugin.yml");
        // Open a reader to the jar
        BufferedReader reader = new BufferedReader(new InputStreamReader(jar.getInputStream(entry)));
        // Grab the description in the addon.yml file
        YamlConfiguration data = new YamlConfiguration();
        data.load(reader);
        return data;
    }
}
