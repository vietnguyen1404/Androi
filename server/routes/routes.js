var appRouter = function (app) {

  app.get("/", function (req, res) {
    res.status(200).send("Welcome to RESTFUL API - NODEJS - TINK43");
  });

  app.post("/userinfo", function (req, res) {
    res.status(200).send("USERINFO API");
  });

  app.post("/login", function (req, res) {
    var user = req.body.username;
    var pass = req.body.password;
    if (user == "vvdung" && pass == "123456")
      res.status(200).send("Dang nhap thanh cong [" + user + "/" + pass + "]");
    else
      res.status(503).send("Loi Dang nhap [" + user + "/" + pass + "]");
  });

  app.post("/register", function (req, res) {
    res.status(200).send("REGISTER API");
  });

}

module.exports = appRouter;