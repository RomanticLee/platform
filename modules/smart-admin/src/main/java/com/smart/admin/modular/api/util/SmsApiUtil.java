package com.smart.admin.modular.api.util;//package com.smart.admin.modular.api.util;
//
//import java.io.BufferedReader;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.io.OutputStream;
//import java.net.HttpURLConnection;
//import java.net.URL;
//
//import org.json.JSONObject;
//
//public class SmsApiUtil {
//
//	//短信验证码
//	public static SmsSendResult SmsCodeSend(String phone, String code) {
//		SmsSendResult SmsResult = new SmsSendResult();
//		SmsResult.setReceiveNum(phone);
//		try {
//			URL url = new URL("https://sms.yunpian.com/v2/sms/single_send.json");
//			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//			conn.setRequestMethod("POST");
//
//			String content = "您的验证码是" + code + "。如非本人操作，请忽略本短信";
//			SmsResult.setContent(content);
//			String data = "apikey=cfed428d40b32cac8378a59082874b90&mobile="
//					+ phone + "&text=" + content + "";
//
//			conn.setDoOutput(true);
//			OutputStream ops = conn.getOutputStream();
//			ops.write(data.getBytes("utf-8"));
//			ops.flush();
//			ops.close();
//
//			InputStream is = conn.getInputStream();
//			InputStreamReader isreader = new InputStreamReader(is, "utf-8");
//			BufferedReader breader = new BufferedReader(isreader);
//
//			String line = null;
//			StringBuffer sb = new StringBuffer();
//			while ((line = breader.readLine()) != null) {
//				sb.append(line);
//			}
//			String jsonResult = sb.toString();
//			System.out.println("返回的JSON数据为：" + jsonResult);
//
//			JSONObject jsonObject = new JSONObject(jsonResult);
//			String result = jsonObject.getString("msg");
//
//			if (!("发送成功".equals(result))) {
//				SmsResult.setResult(false);
//				SmsResult.setMessage(result);
//				return SmsResult;
//			} else {
//				SmsResult.setResult(true);
//			}
//
//			breader.close();
//			isreader.close();
//			is.close();
//
//		} catch (Exception e) {
//			e.printStackTrace();
//			SmsResult.setResult(false);
//			return SmsResult;
//		}
//		return SmsResult;
//	}
//}
