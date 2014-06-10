package zimek.zimek.sparkrest;

import spark.ResponseTransformer;

import com.google.gson.Gson;

public class JsonUtil {
	
	public static String toJson(Object o) {
		return new Gson().toJson(o);
	}
	
	public static ResponseTransformer json() {
		return JsonUtil::toJson;
	}
	
}
