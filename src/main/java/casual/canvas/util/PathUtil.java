package casual.canvas.util;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * @author miaomuzhi
 * @since 2018/9/15
 */
public class PathUtil {

    private static String filePath;

    static {
        Properties pathProperty = new Properties();
        try {
            pathProperty.load(new InputStreamReader(PathUtil.class.getResourceAsStream("/path.properties"),"UTF-8"));
            filePath = pathProperty.getProperty("file-path");
        } catch (IOException e) {
            LoggerUtil.getLogger().warning(e);
        }
    }

    private PathUtil(){}

    public static String getFilePath() {
        return filePath;
    }
}
