const ProductService = require("../services/ProductService");
const Product = require("../models/Product");
exports.createProduct = async (req, res) => {
  try {
    const {
      name,
      description,
      imageLabel,
      category,
      image,
      price,
      rating,
      discount,
      countInStock,
      type,
      reviews,
      expenses
    } = req.body;
    console.log("req.body", req.body);
    if (
      !name ||
      !image ||
      !type ||
      !price ||
      !countInStock ||
      !rating ||
      !discount ||
      !category ||
      !description
    ) {
      return res.status(200).json({
        status: "ERR",
        message: "The input is required",
      });
    }

    const response = await ProductService.createProduct(req.body);
    return res.status(200).json(response);
  } catch (error) {
    res.status(404).json(error);
  }
};

exports.updateProduct = async (req, res) => {
  try {
    const productId = req.params.id;
    const data = req.body;
    if (!productId) {
      return res.status(200).json({
        status: "ERR",
        message: "The productId is required",
      });
    }

    const response = await ProductService.updateProduct(productId, data);
    return res.status(200).json(response);
  } catch (e) {
    return res.status(404).json({
      message: e,
    });
  }
};

exports.getDetailProduct = async (req, res) => {
  try {
    const product = req.params.id;
    console.log("product", product);
    if (!product) {
      return res.status(200).json({
        status: "ERR",
        message: "The userId is required",
      });
    }

    const response = await ProductService.getDetailProduct(product);
    return res.status(200).json(response);
  } catch (e) {
    return res.status(404).json({
      message: e,
    });
  }
};

exports.getAllProductById = async (req, res) => {
  try {
    const data = req.body;
    if (!data) {
      return res.status(200).json({
        status: "ERR",
        message: "The userId is required",
      });
    }

    const response = await ProductService.getAllProductById(data);
    return res.status(200).json(response);
  } catch (e) {
    return res.status(404).json({
      message: e,
    });
  }
};

exports.deleteProduct = async (req, res) => {
  try {
    const productId = req.params.id;
    if (!productId) {
      return res.status(200).json({
        status: "ERR",
        message: "The productId is required",
      });
    }

    const response = await ProductService.deleteProduct(productId);
    return res.status(200).json(response);
  } catch (e) {
    return res.status(404).json({
      message: e,
    });
  }
};

exports.deleteManyProduct = async (req, res) => {
  try {
    const ids = req.body.ids;
    console.log("ids", ids);
    if (!ids) {
      return res.status(200).json({
        status: "ERR",
        message: "The ids is required",
      });
    }

    const response = await ProductService.deleteManyProduct(ids);
    return res.status(200).json(response);
  } catch (e) {
    return res.status(404).json({
      message: e,
    });
  }
};

exports.getAllType = async (req, res) => {
  try {
    const response = await ProductService.getAllType();
    return res.status(200).json(response);
  } catch (e) {
    return res.status(404).json({
      message: e,
    });
  }
};

exports.getAllProduct = async (req, res) => {
  try {
    const { limit, page, sort, filter } = req.query;
    const response = await ProductService.getAllProduct(
      Number(limit) || null,
      Number(page) || 0,
      sort,
      filter
    );
    return res.status(200).json(response);
  } catch (e) {
    return res.status(404).json({
      message: e,
    });
  }
};

exports.getProductByCategory = async (req, res) => {
  // try {
  //   const categories = await Product.find({ category: categoryId }).populate(
  //     "category"
  //   );

  //   res.json(products);
  // } catch (error) {
  //   console.log(error);
  //   res.status(400).json(error);
  // }

  try {
    const { categoryId } = req.params;
    const { limit, page, sort, filter } = req.query;
    const response = await ProductService.getProductByCategory(
      categoryId,
      Number(limit) || null,
      Number(page) || 0,
      sort,
      filter
    );
    return res.status(200).json(response);
  } catch (e) {
    return res.status(404).json({
      message: e,
    });
  }
};

exports.getAllType = async (req, res) => {
  try {
    const response = await ProductService.getAllType();
    return res.status(200).json(response);
  } catch (e) {
    return res.status(404).json({
      message: e,
    });
  }
};
