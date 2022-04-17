//include
var express = require('express');
var bodyParser = require('body-parser');

var routes = require("./routes/routes.js");
var app = express();

console.log('Welcome NODE!!!');

app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true }));

// app.get("/", function (res, req) {
//     req.status(200).send("WELCOME GET METHOD ROOT")
// })

// app.post("/login", function (res, req) {
//     req.status(200).send("WELCOME GET METHOD Login")
// })

routes(app);

var server = app.listen(3080, function () {
    console.log("app running on port.", server.address().port);
});