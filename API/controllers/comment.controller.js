const Comment = require("../models/Comment.model");
const { findById } = require("../models/Story.model");
const Story = require("../models/Story.model");

//

const GetAll = async (req, res, next) => {
  try {
    let list = await Comment.find();
    res.json({ list });
  } catch (err) {
    res.json({ mes: "false" });
  }
};
const GetElement = async (req, res, next) => {
  try {
    const _id = req.params.id;
    const commet = await Comment.findOne({ _id: _id });
    res.json({ commet });
  } catch (error) {
    res.json({ mes: "false" });
  }
};
const CreateElement = async (req, res, next) => {
  try {
    const comment = new Comment(req.body);
    if (req.body.storyID) {
      const story = await Story.findById(req.body.storyID);
      await comment.save();
      story.Comments.push(comment._id);
      await story.save();
    } else {
      await comment.save();
    }
    res.json({ comment });
  } catch (error) {
    res.json({ mes: "false" });
  }
};
const UpdateElement = async (req, res, next) => {
  try {
    const comment = await Comment.findById(req.params.id);
    if (req.body.storyID) {
      if (comment.storyID && req.body.storyID != comment.storyID) {
        try {
          const story1 = await Story.findById(comment.storyID);
          await story1.Comments.pull(req.params.id);
          await story1.save();
        } catch (error) {}
      }
      const story = await Story.findById(req.body.storyID);
      story.Comments.push(comment._id);
      await story.save();
    }

    await comment.updateOne({ $set: req.body });

    // const _id = req.params.id;
    // let { storyID, userID, content, time } = req.body;
    // await Comment.updateOne({ _id: _id }, { storyID, userID, content, time });
    res.json({ comment });
  } catch (error) {
    res.json({ mes: "false" });
  }
};
const DeleteElement = async (req, res, next) => {
  try {
    const comment = await Comment.findById(req.params.id);

    if (comment.storyID) {
      try {
        const story = await Story.findById(comment.storyID);
        await story.Comments.pull(comment.id);
        await story.save();
      } catch (err) {}
    }

    await Comment.deleteOne({ _id: comment.id });
    res.json({ mes: "true" });
  } catch (error) {
    res.json({ mes: "false" });
  }
};
const DeleteAll = async (req, res, next) => {
  try {
    const list = await Comment.find();

    for (let i = 0; i < list.length; i++) {
      let comment = await Comment.findById(list[i].id);
      if (comment.storyID) {
        try {
          const story = await Story.findById(comment.storyID);
          await story.Comments.pull(comment.id);
          await story.save();
        } catch (err) {}
      }
    }

    await Comment.deleteMany();
    res.json({ mes: "true" });
  } catch (error) {
    res.json({ mes: "false" });
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
