//package com.smart.admin.modular.api.controller;
//
//import java.io.File;
//
//import javax.validation.constraints.NotBlank;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.google.zxing.Result;
//
//import com.smart.admin.modular.api.util.JsonResult;
//import com.smart.admin.modular.api.util.QRCodeUtil;
//import com.smart.admin.modular.api.util.RedisUtil;
//
//@Validated
//@RestController
//@RequestMapping("app/test")
//public class TestApi {
//
//	@Autowired
//	private RedisUtil redis;
//
//	public static void main(String[] args) {
//		//System.out.println(System.getProperty("java.io.tmpdir"));
//		QRCodeUtil.zxingCodeCreate("http://www.baidu.com", "E:"+File.separator+"tmp"+File.separator+"扫一扫.jpg", 500, null);
//		Result result = QRCodeUtil.zxingCodeAnalyze("E:"+File.separator+"tmp"+File.separator+"扫一扫.jpg");
//        System.err.println("二维码解析内容："+result.toString());
//	}
//
//
//	@RequestMapping("qwe")
//	public JsonResult test(
//
//			//@Length(min = 2, max = 10, message = "name 长度必须在 {min} - {max} 之间")
//			@NotBlank(message="name不能为空")String name
//			){
//		return new JsonResult();
//	}
//
//
//}
