const Comment = require("../models/Comment.model");
const User = require("../models/User.model");
const Story = require("../models/Story.model");

const Login = async (req, res) => {
  try {
    const { username, password } = req.body;

    console.log(username, password);

    const user = await User.findOne({ username, password });

    if (!user) throw Error;

    res.json({ result: true, mes: "Đăng nhập thành công !", user: user });
  } catch (err) {
    res.json({ result: false, mes: "Thông tin không chính xác !" });
  }
};

const Register = async (req, res) => {
  try {
    let { username, password, email, fullName, avatar } = req.body;

    console.log(username, password, email, fullName, avatar);

    try {
      let user1 = await User.findOne({ username: username });
      if (user1) {
        res.json({ result: false, mes: "username đã tồn tại !" });
      }
    } catch (error) {}

    try {
      let user1 = await User.findOne({ email: email });
      if (user1) {
        res.json({ result: false, mes: "username đã tồn tại !" });
      }
    } catch (error) {}

    let userz = new User(req.body);
    console.log(userz, true);
    await userz.save();
    res.json({ result: true, mes: "Đăng ký thành công !" });
  } catch (err) {
    console.log(err);
    res.json({ result: false, mes: "Đăng ký thất bại !" });
  }
};

const GetCommentWithStoryID = async (req, res) => {
  try {
    const story = await Story.findById(req.params.id);

    const list = [];

    for (let i = 0; i < story.Comments.length; i++) {
      try {
        const comment = await Comment.findById(story.Comments[i]);
        list.push(comment.content);
      } catch (err) {}
    }

    res.json({ result: true, list });
  } catch (err) {
    res.json({ result: false });
  }
};

module.exports = {
  Login,
  Register,
  GetCommentWithStoryID,
};
