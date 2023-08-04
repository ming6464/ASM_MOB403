const router = require("express").Router();
const ctrl = require("../controllers/comment.controller");

router.get("/", ctrl.GetAll);
router.get("/:id", ctrl.GetElement);
router.get("/getByStoryID/:id", ctrl.GetByStoryID);
router.post("/create", ctrl.CreateElement);
router.put("/update/:id", ctrl.UpdateElement);
router.put("/delete/:id", ctrl.DeleteElement);
router.put("/delete", ctrl.DeleteAll);

module.exports = router;
