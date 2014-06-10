package zimek.zimek.sparkrest;

import static spark.Spark.after;
import static spark.Spark.get;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import spark.ModelAndView;
import spark.template.freemarker.FreeMarkerEngine;
import freemarker.template.Configuration;

public class FreeMarkerUserController {
	
	public FreeMarkerUserController() {
		get("/freemarker", (req, res) -> {
			Map<String, Object> attr = new HashMap<>();
			attr.put("blogTitle", "My blog!");
			
			List<User> users = new ArrayList<>();
			users.add(new User("Zenek", "z@z.pl"));
			users.add(new User("Czesiek", "w@w.pl"));
			
			attr.put("myList", users);
			return new ModelAndView(attr, "layout.ftl");
		}, getFreeMarkerEngine());
		
		after((req, res) -> res.type("text/html"));
	}
	
	private FreeMarkerEngine getFreeMarkerEngine() {
		Configuration cfg = new Configuration();
		try {
			cfg.setDirectoryForTemplateLoading(new File("./src/test/resources/spark/template/freemarker"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		FreeMarkerEngine eng = new FreeMarkerEngine();
		eng.setConfiguration(cfg);
		return eng;
	}
}
