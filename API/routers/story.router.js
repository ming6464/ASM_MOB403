const router = require("express").Router();
const ctrl = require("../controllers/story.controller");

router.get("/", ctrl.GetAll);
router.get("/getbyname/:ten", ctrl.GetElementByName);
router.get("/:id", ctrl.GetElement);
router.post("/create", ctrl.CreateElement);
router.put("/update/:id", ctrl.UpdateElement);
router.put("/delete/:id", ctrl.DeleteElement);
router.put("/delete", ctrl.DeleteAll);

module.exports = router;
