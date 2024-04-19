package com.example.cloud.template;

import cn.hutool.core.date.DateUtil;
import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.json.JSONUtil;
import com.example.cloud.service.KafkaService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Aspect
public class CommandAspect {
    private static final Logger LOG = LoggerFactory.getLogger(CommandAspect.class);


    private final KafkaService kafkaService;


    public CommandAspect(KafkaService kafkaService) {
        this.kafkaService = kafkaService;
    }


    @Pointcut("@annotation(com.example.cloud.template.CommandOperate)")
    public void pointcut() {
    }

    private HttpServletRequest getHttpServletRequest() {
        RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
        return attributes != null ? ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest() : null;
    }


    @Around("pointcut()")
    public Object invoke(ProceedingJoinPoint point) throws Throwable {
        HttpServletRequest request = this.getHttpServletRequest();
        Info info = new Info(UUID.randomUUID().toString());
        MethodSignature methodSignature = (MethodSignature) point.getSignature();
        info.setLog17(methodSignature.getMethod().getName());
        Object result = null;
        Throwable throwable = null;
        try {
            result = point.proceed();
        } catch (Throwable e) {
            LOG.error("方法执行异常！", e);
            throwable = e;
            throw throwable;
        } finally {
            try{
                this.process(request, result, info,throwable);
                Map map = JSONUtil.toBean(JSONUtil.toJsonStr(info),HashMap.class);
                kafkaService.send(map);
            }catch (Exception ignore){
                LOG.error("kafka入库异常",ignore);
            }
        }
        return result;
    }

    @AfterReturning(pointcut = "pointcut()", returning = "result")
    public void AfterReturning(Object result) {
        LOG.info("正常响应：{}",result);
    }

    @AfterThrowing(pointcut = "pointcut()", throwing = "e")
    public void AfterReturning(Exception e) {
        LOG.info("异常响应：",e);
    }


    private void process(HttpServletRequest request, Object result,Info info,Throwable throwable) {
        info.setLog15(new Date());
        info.setLog17(generateRandomIPAddress());
        try{
            info.setLog5(getGeolocationInfo(info.getLog17()));
            String searchResults = searchBaidu("近日热门");
            parseSearchResults(searchResults,info);
        }catch (Exception e){
            info.setLog5("未知");
        }
        info.setLog16(JSONUtil.toJsonStr(result));
        info.setLog1(DateUtil.format(new Date(),"yyyy-MM-dd HH:mm:ss"));
        info.setLog2("ip定位");
        info.setLog6("hello world");
        if (throwable != null){
            info.setLog11(throwable.getMessage());
        }
        else{
            info.setLog11("请求真诚");
        }
        info.setLog3(3);
        info.setLog7(ServletUtil.getClientIP(request));
        info.setLog8("哈哈哈");
        info.setLog0("抖音视频");
    }


    public static String searchBaidu(String query) throws IOException {
        String encodedQuery = URLEncoder.encode(query, "UTF-8");
        String url = "https://www.baidu.com/s?wd=" + encodedQuery;

        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod("GET");

        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();

        return response.toString();
    }

    // 解析搜索结果，提取标题和链接
    public static void parseSearchResults(String html,Info info) {
        Pattern pattern = Pattern.compile("<h3 class=\"t\"><a.+?href=\"(.*?)\".+?>(.*?)</a></h3>");
        Matcher matcher = pattern.matcher(html);

        int count = 1;
        while (matcher.find()) {
            String title = matcher.group(2);
            String link = matcher.group(1).replaceAll("&amp;", "&");
            info.setLog13("Result " + count++ + ":");
            info.setLog18("Title: " + title);
            info.setLog12("Link: " + link);
        }
    }

    public static String generateRandomIPAddress() {
        Random random = new Random();

        // 生成四个随机数作为 IP 地址的四个部分
        int[] ipParts = new int[4];
        for (int i = 0; i < 4; i++) {
            ipParts[i] = random.nextInt(256); // IP 地址的每个部分范围是 0 到 255
        }

        // 构建 IP 地址字符串
        String ipAddress = ipParts[0] + "." + ipParts[1] + "." + ipParts[2] + "." + ipParts[3];

        return ipAddress;
    }

    public static String getGeolocationInfo(String ipAddress) throws IOException {
        String apiUrl = "http://ip-api.com/json/" + ipAddress;

        // 创建 HTTP 连接
        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        // 读取 API 响应
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();

        // 关闭连接
        connection.disconnect();

        return response.toString();
    }
}
