package com.sector.webhook.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.Map;

@Configuration
public class EndpointLogger implements CommandLineRunner {

    @Autowired
    @Qualifier("requestMappingHandlerMapping")
    private RequestMappingHandlerMapping handlerMapping;


    @Override
    public void run(String... args) {
        System.out.println("\n=== 📡 Registered Endpoints ===");

        for (Map.Entry<RequestMappingInfo, HandlerMethod> entry : handlerMapping.getHandlerMethods().entrySet()) {
            var info = entry.getKey();
            var methods = info.getMethodsCondition().getMethods();
            var patterns = info.getPatternValues();

            for (var method : methods) {
                for (var path : patterns) {
                    AntPathMatcher matcher = new AntPathMatcher();
                    System.out.printf("%-6s %-40s %s%n",
                            method,
                            path,"🟢 PUBLIC");
                }
            }
        }
        System.out.println("===============================\n");
    }
}
