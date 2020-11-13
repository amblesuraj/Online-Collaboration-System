package com.ngu.Exception;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@RestController
//public class GlobleException implements ErrorController {
//
//    private static final String PATH = "/error";
//
//    @RequestMapping(value = PATH)
//    public void error(HttpServletResponse response) throws IOException {
//        int status = response.getStatus();
//        switch (status) {
//            case 404:
//                response.sendRedirect("/error/404");
//                break;
//            case 500:
//                response.sendRedirect("/error/500");
//                break;
//            case 403:
//                response.sendRedirect("/error/401");
//                break;
//            default:
//                response.sendRedirect("/error/404");
//        }
//        
//    }
//
//	@Override
//	public String getErrorPath() {
//		// TODO Auto-generated method stub
//		return PATH;
//	}
//
//	
//}
