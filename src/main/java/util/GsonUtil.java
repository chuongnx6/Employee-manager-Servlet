package util;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import payload.ResponseData;

public class GsonUtil {
	private static Gson gson = new Gson();
	
	public static void responseJson(HttpServletResponse resp, ResponseData responseData) throws IOException {
		String json = gson.toJson(responseData);
		
		resp.setContentType("application/json");
		resp.setCharacterEncoding(Constant.CHARSET_UTF8);
		
		try (PrintWriter printWriter = resp.getWriter()) {
			printWriter.print(json);
			printWriter.flush();
		}
	}
}
