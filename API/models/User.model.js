const db = require("./db.model");

const userShema = new db.mongoose.Schema({
  username: {
    type: String,
    require: true,
  },
  password: {
    type: String,
    require: true,
  },
  email: {
    type: String,
    require: true,
  },
  fullName: {
    type: String,
    require: true,
  },
  avatar: {
    type: String,
    require: true,
  },
});
module.exports = db.mongoose.model("User", userShema);
