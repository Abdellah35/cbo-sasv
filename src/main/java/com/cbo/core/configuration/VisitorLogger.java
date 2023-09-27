package com.cbo.core.configuration;

import com.cbo.core.persistence.model.Visitor;
import com.cbo.core.service.VisitorService;
import com.cbo.core.utility.HttpRequestResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class VisitorLogger implements HandlerInterceptor {


    private final VisitorService visitorService;

    @Value("${spring.application.name}")
    private String appName;

    @Autowired
    public VisitorLogger(VisitorService visitorService) {
        this.visitorService = visitorService;

    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        final String ip = HttpRequestResponseUtils.getClientIpAddress();
        final String url = HttpRequestResponseUtils.getRequestUrl();
        final String page = HttpRequestResponseUtils.getRequestUri();
        final String refererPage = HttpRequestResponseUtils.getRefererPage();
        final String queryString = HttpRequestResponseUtils.getPageQueryString();
        final String userAgent = HttpRequestResponseUtils.getUserAgent();
        final String requestMethod = HttpRequestResponseUtils.getRequestMethod();
        final LocalDateTime timestamp = LocalDateTime.now();

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        DateTimeFormatter timef = DateTimeFormatter.ofPattern("HH:mm:ss");

        Visitor visitor = new Visitor();
        visitor.setAppUser(HttpRequestResponseUtils.getLoggedInUser());
        visitor.setIp(ip);
        visitor.setMethod(requestMethod);
        visitor.setUrl(url);
        visitor.setPage(page);
        visitor.setQueryString(queryString);
        visitor.setRefererPage(refererPage);
        visitor.setUserAgent(userAgent);
        visitor.setLoggedTimetime(timef.format(timestamp));
        visitor.setUniqueVisit(true);
        visitor.setApplication(appName);
        visitor.setLoggedTime(dtf.format(timestamp));
        System.out.println(timef.format(timestamp));
        visitorService.saveVisitorInfo(visitor);

        return true;
    }

}
