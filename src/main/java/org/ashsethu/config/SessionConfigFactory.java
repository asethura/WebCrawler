package org.ashsethu.config;

public class SessionConfigFactory {
    //Make it a singleton
    static final SessionConfig sessionConfig = new SessionConfig();

    public static SessionConfig getSessionConfig(){
        return sessionConfig;
    }

}
