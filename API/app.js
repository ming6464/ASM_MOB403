const express = require("express");
const app = express();
const apiUserRouter = require("./routers/user.router");

const apiCommentRouter = require("./routers/comment.router");

const apiStoryRouter = require("./routers/story.router");

const apiRouter = require("./routers/api.router");

const bodyParser = require("body-parser");
const port = 9000;

app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true }));

app.use("/api/user", apiUserRouter);

app.use("/api/comment", apiCommentRouter);

app.use("/api/story", apiStoryRouter);

app.use("/api", apiRouter);

app.listen(port, (err) => {
  if (err) throw err;

  console.log(" Run port " + port);
});
