package constants;


import utils.ReadConfigUtils;

public abstract class Data {

    public static final String LOCALDATA_PATH = ReadConfigUtils.getValue("localDataPath");
    public static final String GLOBALDATA_PATH = ReadConfigUtils.getValue("globalDataPath");
    public static final String SCHEDULE_PATH = ReadConfigUtils.getValue("schedulePath");
    public static final String URL = ReadConfigUtils.getValue("url");

    public static String BROWSER = ReadConfigUtils.getValue("browser");

    public static final String CONFIG_PATH = "src/main/resources/config.properties";

    public static void setBrowser(String browser) {
        BROWSER = browser;
    }
}
