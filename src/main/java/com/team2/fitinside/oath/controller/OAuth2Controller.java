package com.team2.fitinside.oath.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class OAuth2Controller {

    @GetMapping("/login/oauth2/code/google")
    public String googleLoginCallback(RedirectAttributes redirectAttributes) {
        // 인증 후 처리 로직

        // API Gateway URL로 리디렉션
        String apiGatewayUrl = "https://obpedvusnf.execute-api.ap-northeast-2.amazonaws.com/login/oauth2/code/google";
        System.out.println("apiGateWay = " + apiGatewayUrl);
        return "redirect:" + apiGatewayUrl;
    }
}
