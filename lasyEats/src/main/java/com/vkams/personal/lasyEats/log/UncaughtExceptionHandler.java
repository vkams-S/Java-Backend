package com.vkams.personal.lasyEats.log;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UncaughtExceptionHandler implements Thread.UncaughtExceptionHandler{
    private static final Logger log = LogManager.getLogger(UncaughtExceptionHandler.class);
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        ObjectNode logEventJsonObjNode = JsonNodeFactory.instance.objectNode();
        if(e.getStackTrace()!=null && e.getStackTrace().length>0)
        {
            ArrayNode logStackTraceJsonArrNode =JsonNodeFactory.instance.arrayNode();
            for(StackTraceElement stackTraceElement:e.getStackTrace())
            {
                logStackTraceJsonArrNode.add(stackTraceElement.toString());
            }
            logEventJsonObjNode.set("stacktrace",logStackTraceJsonArrNode);
        }
//if spotbugs are enabled, the build fails for the unsused variable below.
         int variableForTestingSpotBugs=0;
         logEventJsonObjNode.put("cause",e.toString());
         log.error(logEventJsonObjNode.toString(),e);
    }
}
