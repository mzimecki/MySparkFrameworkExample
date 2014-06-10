package zimek.zimek.sparkrest;

import static spark.Spark.after;
import static spark.Spark.exception;
import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.put;
import static zimek.zimek.sparkrest.JsonUtil.json;
import static zimek.zimek.sparkrest.JsonUtil.toJson;

public class RestUserController {
	
	public RestUserController(final UserService us) {
		
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
		
		put("/users/:id", (req, res) -> us.updateUser(
				req.params(":id"),
				req.queryParams("name"),
				req.queryParams("email")
				), json());

		after((req, res) -> res.type("application/json"));
		
		exception(IllegalArgumentException.class, (e, req, res) -> {
			res.status(400);
			res.body(toJson(new ResponseError(e)));
		});
	}
}
