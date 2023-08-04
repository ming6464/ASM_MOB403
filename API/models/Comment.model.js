const db = require("./db.model");

const commentShema = new db.mongoose.Schema({
  storyID: {
    type: db.mongoose.Schema.ObjectId,
    ref: "Story",
  },
  userID: {
    type: db.mongoose.Schema.ObjectId,
    ref: "User",
  },
  content: {
    type: String,
    require: true,
  },
  time: {
    type: Number,
  },
});
module.exports = db.mongoose.model("Comment", commentShema);
