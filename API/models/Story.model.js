const db = require("./db.model");

const storyShema = new db.mongoose.Schema({
  name: {
    type: String,
    require: true,
    Unique: true,
  },
  describe: {
    type: String,
    require: true,
    Unique: true,
  },
  author: {
    type: String,
    require: true,
    Unique: true,
  },
  timeRelease: {
    type: Number,
    require: true,
    Unique: true,
  },
  Background: {
    type: String,
    require: true,
    Unique: true,
  },
  Images: {
    type: [String],
  },
  Comments: [
    {
      type: db.mongoose.Schema.ObjectId,
      ref: "Comment",
    },
  ],
});
module.exports = db.mongoose.model("Story", storyShema);
