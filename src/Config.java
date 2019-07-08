import java.io.*;
import java.util.Properties;

public class Config {
    private static final Config INSTANCE = new Config();
    private static final File PROPERTIES = new File("./config/resumes.properties");
    private Properties props = new Properties();
    private File storageDir;
    private File dbUrl;
    private String dbUser;
    private String dbPassword;

    public static Config getInstance() {
        return INSTANCE;
    }

    private Config() {
        try (InputStream is = new FileInputStream(PROPERTIES)){
            props.load(is);
            storageDir = new File(props.getProperty("storage.dir"));
        } catch (IOException e) {
            throw new IllegalStateException("INVALID CONFIG FILE: " + PROPERTIES.getAbsolutePath());
        }
    }

    public File getStorageDir() {
        return storageDir;
    }
}
