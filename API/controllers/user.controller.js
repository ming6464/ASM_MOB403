const User = require("../models/User.model");
const Comment = require("../models/Comment.model");

//popular
const GetAll = async (req, res, next) => {
  try {
    let list = await User.find();
    res.json(list);
  } catch (err) {
    res.json(false);
  }
};
const GetElement = async (req, res, next) => {
  try {
    const _id = req.params.id;
    const user = await User.findOne({ _id: _id });
    res.json(user);
  } catch (error) {
    res.json(false);
  }
};
const CreateElement = async (req, res, next) => {
  try {
    console.log(req.body);
    let user = new User(req.body);
    await user.save();
    res.json(user);
  } catch (error) {
    res.json(false);
  }
};
const UpdateElement = async (req, res, next) => {
  try {
    const user = await User.findById(req.params.id);

    //await user.save({ $set: req.body });

    user.set(req.body);
    await user.save();

    console.log(req.body);

    res.json(user);
  } catch (error) {
    res.json(false);
  }
};
const DeleteElement = async (req, res, next) => {
  try {
    let list = await Comment.find();

    for (let i = 0; i < list.length; i++) {
      if (list[i]._id == req.params.id) {
        try {
          const comment = await Comment.findById(list[i]._id);

          if (comment.storyID) {
            try {
              const story = await Story.findById(comment.storyID);
              await story.Comments.pull(comment.id);
              await story.save();
            } catch (err) {}
          }
          await Comment.deleteOne({ _id: comment.id });
        } catch (error) {}
      }
    }

    await User.deleteOne({ _id: req.params.id });
    res.json(true);
  } catch (error) {
    res.json(false);
  }
};
const DeleteAll = async (req, res, next) => {
  try {
    let listU = await User.find();
    for (let j = 0; j < listU.length; j++) {
      let list = await Comment.find();

      for (let i = 0; i < list.length; i++) {
        if (list[i]._id == listU[j]._id) {
          try {
            const comment = await Comment.findById(list[i]._id);

            if (comment.storyID) {
              try {
                const story = await Story.findById(comment.storyID);
                await story.Comments.pull(comment.id);
                await story.save();
              } catch (err) {}
            }
            await Comment.deleteOne({ _id: comment.id });
          } catch (error) {}
        }
      }
    }

    await User.deleteMany();
    res.json(true);
  } catch (error) {
    res.json(false);
  }
};

module.exports = {
  GetAll,
  GetElement,
  CreateElement,
  UpdateElement,
  DeleteAll,
  DeleteElement,
};
