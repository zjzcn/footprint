package com.footprint.client.log;

import java.io.StringReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.xml.sax.InputSource;

import com.footprint.client.ClientConfig;
import com.footprint.client.ucm.DataListener;
import com.footprint.client.ucm.UcmClient;
import com.footprint.common.utils.StringUtils;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;

public class LogConfigure {

    private static final Logger logger = LoggerFactory.getLogger(LogConfigure.class);

    private static final String LOG_CONFIG_NODE= "logback.xml";

    static {
        //SLF4JBridgeHandler.install();
    }

    public static void init() {
    	logger.info("[log]start log configure init.");
    	boolean isRemote = ClientConfig.getLogIsRemote();
    	if(!isRemote) {
    		return;
    	}
    	
        String nodePath = ClientConfig.getLogNodePath();
        nodePath = StringUtils.removeStart(nodePath, "/");
        nodePath = StringUtils.removeEnd(nodePath, "/");
        nodePath = nodePath + "/" + LOG_CONFIG_NODE;
        UcmClient client = UcmClient.getInstance();
        String data = client.watchConfig(nodePath,  new DataListener() {
        	@Override
        	public void dataChanged(String data) {
        		doLogConfig(data);
        	}
        });
        doLogConfig(data);
    }

    private synchronized static void doLogConfig(String config) {
        logger.info("[log]start log configure. config content:\n{}", config);
        if (StringUtils.isNotBlank(config)) {
            StringReader reader = new StringReader(config);
            InputSource is = new InputSource(reader);
            LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
            loggerContext.reset();
            JoranConfigurator joranConfigurator = new JoranConfigurator();
            joranConfigurator.setContext(loggerContext);
            try {
                joranConfigurator.doConfigure(is);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
 
}
