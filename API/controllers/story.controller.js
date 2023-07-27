const Story = require("../models/Story.model");
const Comment = require("../models/Comment.model");

const GetElementByName = async (req, res, next) => {
  try {
    const ten = req.body.ten.toLowerCase();
    let list = await Story.find();
    let list1 = [];
    for (let i = 0; i < list.length; i++) {
      let namei = list[i].name.toLowerCase();
      if (namei.includes(ten)) {
        list1.push(list[i]);
      }
    }
    res.json({ list1 });
  } catch (err) {
    res.json({ mes: "false" });
  }
};

const GetAll = async (req, res, next) => {
  try {
    let list = await Story.find();
    res.json({ list });
  } catch (err) {
    res.json({ mes: "false" });
  }
};
const GetElement = async (req, res, next) => {
  try {
    const story = await Story.findOne({ _id: req.params.id });
    res.json({ story });
  } catch (error) {
    res.json({ mes: "false" });
  }
};
const CreateElement = async (req, res, next) => {
  try {
    let story = new Story(req.body);

    await story.save();
    res.json({ story });
  } catch (error) {
    res.json({ Mes: "false" });
  }
};
const UpdateElement = async (req, res, next) => {
  try {
    const story = await Story.findById(req.params.id);

    await story.save({ $set: req.body });
    res.json({ story });
  } catch (error) {
    res.json({ Mes: "false" });
  }
};
const DeleteElement = async (req, res, next) => {
  try {
    const story = await Story.findById(req.params.id);

    for (let i = 0; i < story.Comments.length; i++) {
      try {
        await Comment.deleteOne({ _id: story.Comments[i] });
      } catch (err) {}
    }
    await Story.findByIdAndDelete(story.id);
    res.json({ mes: "true" });
  } catch (error) {
    res.json({ mes: "false" });
  }
};
const DeleteAll = async (req, res, next) => {
  console.log("delete all");
  try {
    const list = await Story.find();
    for (let i = 0; i < list.length; i++) {
      const story = await Story.findById(list[i].id);

      for (let i = 0; i < story.Comments.length; i++) {
        try {
          await Comment.deleteOne({ _id: story.Comments[i] });
        } catch (err) {}
      }
    }

    await Story.deleteMany();
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
  GetElementByName,
};
