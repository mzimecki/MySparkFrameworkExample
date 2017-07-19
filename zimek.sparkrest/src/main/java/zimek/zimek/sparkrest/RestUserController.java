package zimek.zimek.sparkrest;

import static spark.Spark.after;
import static spark.Spark.exception;
import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.put;
import static spark.Spark.path;
import static zimek.zimek.sparkrest.JsonUtil.json;
import static zimek.zimek.sparkrest.JsonUtil.toJson;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import freemarker.template.Configuration;
import spark.ModelAndView;
import spark.template.freemarker.FreeMarkerEngine;

public class RestUserController {

	public RestUserController(final UserService us) {

		get("/", (req, res) -> {
			Map<String, Object> attr = new HashMap<>();
			attr.put("blogTitle", "My blog!");
			attr.put("myList", us.getAllUsers());
			return getFreeMarkerEngine().render(new ModelAndView(attr, "layout.ftl"));
		});

		after("/", (req, res) -> res.type("text/html"));

		path("/api", () -> {
			get("/users", (req, res) -> us.getAllUsers(), json());

			get("/users/:id", (req, res) -> {
				String id = req.params(":id");
				User user = us.getUser(id);
				if (user != null) {
					return user;
				}
				res.status(400);
				return new ResponseError("No user with %s found", id);
			}, json());

			post("/users", (req, res) -> us.createUser(req.queryParams("name"), req.queryParams("email")), json());

			put("/users/:id",
					(req, res) -> us.updateUser(req.params(":id"), req.queryParams("name"), req.queryParams("email")),
					json());

			after("/users/*", (req, res) -> res.type("application/json"));
		});

		exception(IllegalArgumentException.class, (e, req, res) -> {
			res.status(400);
			res.body(toJson(new ResponseError(e)));
		});
	}

	private FreeMarkerEngine getFreeMarkerEngine() {
		final Configuration cfg = new Configuration(Configuration.VERSION_2_3_23);
		try {
			cfg.setDirectoryForTemplateLoading(new File("./src/test/resources/spark/template/freemarker"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new FreeMarkerEngine(cfg);
	}
}
