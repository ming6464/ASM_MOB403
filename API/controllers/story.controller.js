const Story = require("../models/Story.model");
const Comment = require("../models/Comment.model");

const GetElementByName = async (req, res, next) => {
  try {
    //const ten = req.body.ten.toLowerCase();
    const ten = req.params.ten.toLowerCase();
    console.log(ten);
    let list = await Story.find();
    let list1 = [];
    for (let i = 0; i < list.length; i++) {
      let namei = list[i].name.toLowerCase();
      if (namei.includes(ten)) {
        list1.push(list[i]);
      }
    }
    res.json(list1);
  } catch (err) {
    res.json({ mes: "false" });
  }
};

const GetAll = async (req, res, next) => {
  try {
    let list = await Story.find().populate("Comments");
    res.json(list);
  } catch (err) {
    res.json(false);
  }
};
const GetElement = async (req, res, next) => {
  try {
    const story = await Story.findOne({ _id: req.params.id }).populate(
      "Comments"
    );
    res.json(story);
  } catch (error) {
    res.json(false);
  }
};
const CreateElement = async (req, res, next) => {
  try {
    let story = new Story(req.body);

    await story.save();
    res.json(story);
  } catch (error) {
    res.json(false);
  }
};
const UpdateElement = async (req, res, next) => {
  try {
    const story = await Story.findById(req.params.id);
    await story.save({ $set: req.body });
    res.json(story);
  } catch (error) {
    res.json(false);
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
    res.json(true);
  } catch (error) {
    res.json(false);
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
  GetElementByName,
};
