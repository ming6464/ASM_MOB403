const User = require("../models/User.model");

//popular
const GetAll = async (req, res, next) => {
  try {
    let list = await User.find();
    res.json({ list });
  } catch (err) {
    res.json({ mes: "false" });
  }
};
const GetElement = async (req, res, next) => {
  try {
    const _id = req.params.id;
    const user = await User.findOne({ _id: _id });
    res.json({ user });
  } catch (error) {
    res.json({ mes: "false" });
  }
};
const CreateElement = async (req, res, next) => {
  try {
    let user = new User(req.body);
    await user.save();
    res.json({ user });
  } catch (error) {
    res.json({ mes: "false" });
  }
};
const UpdateElement = async (req, res, next) => {
  try {
    const user = await User.findById(req.params.id);

    await user.save({ $set: req.body });
    res.json({ user });
  } catch (error) {
    res.json({ mes: "false" });
  }
};
const DeleteElement = async (req, res, next) => {
  try {
    await User.deleteOne({ _id: req.params.id });
    res.json({ mes: "true" });
  } catch (error) {
    res.json({ mes: "false" });
  }
};
const DeleteAll = async (req, res, next) => {
  try {
    await User.deleteMany();
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
