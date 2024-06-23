public class Logger {
    private static Logger instance;
    private LogConfiguration logConfiguration;

    private Logger() {
        this.logConfiguration = new LogConfiguration();
    }

    public static Logger getInstance() {
        if (instance == null) {
            instance = new Logger();
        }
        return instance;
    }

    public void Info(String message) {
        log(message, LogConfigurationLevelType.INFO);
    }

    public void Debug(String message) {
        log(message, LogConfigurationLevelType.DEBUG);
    }

    public void Warning(String message) {
        log(message, LogConfigurationLevelType.WARNING);
    }

    public void Error(String message) {
        log(message, LogConfigurationLevelType.ERROR);
    }

    public void log(String message, LogConfigurationLevelType level) {
        if (level.ordinal() >= logConfiguration.getLevel().ordinal()) {
            switch (logConfiguration.getFormat()) {
                case JSON:
                    logJSON(message, level);
                    break;
                case TEXT:
                    logText(message, level);
                    break;
            }
        }
    }

    private void logJSON(String message, LogConfigurationLevelType level) {
        // Log in JSON format
    }

    private void logText(String message, LogConfigurationLevelType level) {
        // Log in text format
    }

    public void setLogConfiguration(LogConfiguration logConfiguration) {
        this.logConfiguration = logConfiguration;
    }

    public LogConfiguration getLogConfiguration() {
        return logConfiguration;
    }
}