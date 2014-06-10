package zimek.zimek.sparkrest;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.fail;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import spark.Spark;
import spark.utils.IOUtils;

import com.google.gson.Gson;

public class RestUserControllerTest {

	@BeforeClass
	public static void beforeClass() {
		RestServiceApp.main(null);
	}

	@AfterClass
	public static void afterClass() {
		Spark.stop();
	}

	
	@Test
	public void aNewUserShouldBeCreated() {
		TestResponse res = request("POST", "/users?name=john&email=john@foobar.com");
		Map<String, String> json = res.json();
		assertEquals(200, res.status);
		assertEquals("john", json.get("name"));
		assertEquals("john@foobar.com", json.get("email"));
		assertNotNull(json.get("id"));
	}
	
	
	@Test
	public void shouldReturnOneUser() {
		TestResponse res = request("GET", "/users");
		List<Map<String, String>> json = res.jsonList();
		assertEquals(200, res.status);
		assertEquals("john", json.get(0).get("name"));
		assertEquals("john@foobar.com", json.get(0).get("email"));
	}

	private TestResponse request(String method, String path) {
		try {
			URL url = new URL("http://localhost:4567" + path);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod(method);
			connection.setDoOutput(true);
			connection.connect();
			String body = IOUtils.toString(connection.getInputStream());
			return new TestResponse(connection.getResponseCode(), body);
		} catch (IOException e) {
			e.printStackTrace();
			fail("Sending request failed: " + e.getMessage());
			return null;
		}
	}

	private static class TestResponse {

		public final String body;
		public final int status;

		public TestResponse(int status, String body) {
			this.status = status;
			this.body = body;
		}

		public Map<String, String> json() {
			return new Gson().fromJson(body, HashMap.class);
		}
		
		public List<Map<String, String>> jsonList() {
			return new Gson().fromJson(body, ArrayList.class);
		}
	}
}
