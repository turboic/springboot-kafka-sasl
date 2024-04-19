package com.example.cloud.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdcardUtil;
import cn.hutool.extra.servlet.ServletUtil;
import com.example.cloud.entity.Result;
import com.example.cloud.template.CommandOperate;
import com.example.cloud.utils.ImageUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author root
 */
@RestController
@RequestMapping("/v1")
public class V1Controller {

    @GetMapping(value = "get")
    @CommandOperate
    public Result get(HttpServletRequest request) {
        Map map = new LinkedHashMap<>();
        map.put("command", "command");
        map.put("time", DateUtil.format(LocalDateTime.now(),"yyyy-MM-dd HH:mm:ss"));
        map.put("ip", ServletUtil.getClientIP(request));
        map.put("body", ServletUtil.getBody(request));
        map.put("type", "command");
        map.put("isIE", ServletUtil.isIE(request));
        map.put("userAgent", request.getHeader("user-agent"));
        map.put("getProvinceByIdCard", IdcardUtil.getProvinceByIdCard("410182201201092901"));
        return Result.ok(map);
    }

    @GetMapping(value = "image2Base64")
    @CommandOperate
    public Result image2Base64(@RequestParam(value = "file") MultipartFile file,HttpServletRequest request) throws IOException {
        Map map = new LinkedHashMap<>();
        map.put("file", file.getOriginalFilename());
        map.put("image2Base64", ImageUtils.image2Base64(file.getInputStream()));
        map.put("time", DateUtil.format(LocalDateTime.now(),"yyyy-MM-dd HH:mm:ss"));
        map.put("ip", ServletUtil.getClientIP(request));
        map.put("size",file.getSize());
        map.put("type", file.getContentType());
        map.put("isIE", ServletUtil.isIE(request));
        map.put("userAgent", request.getHeader("user-agent"));
        map.put("getProvinceByIdCard", IdcardUtil.getProvinceByIdCard("410182201201092901"));
        return Result.ok(map);
    }
}
