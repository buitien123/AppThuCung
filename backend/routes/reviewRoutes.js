const express = require("express");
const reviewController = require("../controllers/reviewController");

const router = express.Router();

//get all media
// router.get("/all", reviewController.getAll);

router.get("/detail/:id", reviewController.getDetailReview)
//post create new media
router.post("/create", reviewController.create);


//post create new media
router.put("/update/:id", reviewController.update);

router.delete("/delete/:id", reviewController.delete);

module.exports = router;
