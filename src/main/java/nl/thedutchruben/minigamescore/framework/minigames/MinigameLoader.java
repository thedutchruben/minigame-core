package nl.thedutchruben.minigamescore.framework.minigames;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Map;

public class MinigameLoader  extends URLClassLoader {
    private final Map<String, Class<?>> classes = new HashMap<>();
    private Minigame minigame;
    public MinigameLoader(YamlConfiguration data, File path, ClassLoader parent) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, MalformedURLException {
        super(new URL[]{path.toURI().toURL()}, parent);
        Class<?> javaClass = null;
        try {
            String mainClass = data.getString("main");
            if (mainClass == null) {

            }
            javaClass = Class.forName(mainClass, true, this);

        } catch (Exception e) {

        }

        Class<? extends Minigame> addonClass = null;
        try {
            addonClass = javaClass.asSubclass(Minigame.class);
        } catch (ClassCastException e) {

        }

        minigame = addonClass.getDeclaredConstructor().newInstance();


    }

    public Minigame getMinigame() {
        return minigame;
    }
}
