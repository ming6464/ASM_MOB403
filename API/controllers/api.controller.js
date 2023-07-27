const Comment = require("../models/Comment.model");
const User = require("../models/User.model");
const Story = require("../models/Story.model");

const Login = async (req, res) => {
  try {
    const { email, password } = req.body;

    console.log(email, password);

    const user = await User.findOne({ email, password });

    if (!user) throw Error;

    res.json({ result: "true", mes: "Đăng nhập thành công !", user });
  } catch (err) {
    res.json({ result: "false", mes: "Thông tin không chính xác !" });
  }
};

const Register = async (req, res) => {
  try {
    const user = new User(req.body);
    try {
      await User.findOne({ username: user.username });
      res.json({ result: "false", mes: "username đã tồn tại !" });
    } catch (err) {}
    try {
      await User.findOne({ email: user.email });
      res.json({ result: "false", mes: "User đã tồn tại !" });
    } catch (err) {}

    await user.save();
    res.json({ result: "true", mes: "Đăng ký thành công !" });
  } catch (err) {
    res.json({ result: "false", mes: "Đăng ký thất bại !" });
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

    res.json({ result: "true", list });
  } catch (err) {
    res.json({ result: "false" });
  }
};

module.exports = {
  Login,
  Register,
  GetCommentWithStoryID,
};
