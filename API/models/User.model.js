const db = require("./db.model");

const userShema = new db.mongoose.Schema({
  username: {
    type: String,
    require: true,
    Unique: true,
  },
  password: {
    type: String,
    require: true,
  },
  email: {
    type: String,
    require: true,
  },
  fullname: {
    type: String,
  },
});
module.exports = db.mongoose.model("User", userShema);
