package casual.canvas.util;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * @author miaomuzhi
 * @since 2018/7/23
 */
public class LoggerUtil {
    private static LoggerUtil loggerUtil;
    private final Logger logger;

    private LoggerUtil(){
        this.logger = Logger.getAnonymousLogger();
        try{
            FileHandler handler = new FileHandler("%h/logs%u.log");
            handler.setLevel(Level.INFO);
            handler.setFormatter(new SimpleFormatter());
            logger.addHandler(handler);
        }catch (IOException e){
            logger.log(Level.SEVERE, "fail to initialize logger", e);
        }
    }

    public static LoggerUtil getLogger(){
        if (LoggerUtil.loggerUtil == null){
            LoggerUtil.loggerUtil = new LoggerUtil();
        }
        return LoggerUtil.loggerUtil;
    }

    public void info(Exception e){
        logger.log(Level.INFO, "Logged Exception", e);
    }

    public void warning(Exception e) {
        logger.log(Level.WARNING, "Warning", e);
    }
}
