public class Configuration{
}

private class LogConfiguration {
    private LogConfigurationLevelType level;
    private LogConfigurationFormatType format;
    private 
    private boolean logAppend;
}

enum LogConfigurationFormatType{
    JSON,
    TEXT
}

enum LogConfigurationLevelType {
    DEBUG,
    INFO,
    WARNING,
    ERROR
}