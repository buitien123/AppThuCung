const express = require("express");
const productController = require("../controllers/productController");
const { authMiddleWare } = require("../middleware/authMiddleware");

const router = express.Router();

//get all media
// router.get("/all", accessoryController.getAll);

//post create new media
router.post("/create", productController.createProduct);
router.get('/get-all', productController.getAllProduct);
router.post('/get-heart', productController.getAllProductById);
router.get("/category/:categoryId", productController.getProductByCategory);
router.post('/delete-many',authMiddleWare, productController.deleteManyProduct)
router.get('/get-all-type', productController.getAllType)
router.put('/update/:id', authMiddleWare, productController.updateProduct)
router.get('/get-details/:id', productController.getDetailProduct)
router.delete('/delete/:id',authMiddleWare, productController.deleteProduct)
// //post create new media
// router.put("/update/:id", categoryController.create);

// router.delete("/delete/:id", categoryController.create);

module.exports = router;
