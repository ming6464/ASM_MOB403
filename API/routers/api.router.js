const router = require("express").Router();
const ctrl = require("../controllers/api.controller");

router.post("/login", ctrl.Login);
router.post("/register", ctrl.Register);
router.get("/comment/:id", ctrl.GetCommentWithStoryID);

module.exports = router;
