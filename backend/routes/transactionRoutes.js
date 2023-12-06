const express = require("express");
const TransactionController = require("../controllers/transactionController");

const router = express.Router();

//get all media
// router.get("/all", reviewController.getAll);

router.get("/detail/:id", TransactionController.getDetailReview)
//post create new media
router.post("/create", TransactionController.create);


//post create new media
router.put("/update/:id", TransactionController.update);

router.delete("/delete/:id", TransactionController.delete);

module.exports = router;
