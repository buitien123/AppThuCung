const Product = require("../models/Product");
const Review = require("../models/Review");

const createProduct = (newProduct) => {
  return new Promise(async (resolve, reject) => {
    const {
      name,
      image,
      type,
      price,
      countInStock,
      rating,
      description,
      discount,
      selled,
      category,
      reviews,
      expenses
    } = newProduct;
    try {
      const checkProduct = await Product.findOne({
        name: name,
      });
      if (checkProduct !== null) {
        resolve({
          status: "OK",
          message: "the name of product is already",
        });
      }

      const createProduct = await Product.create({
        name,
        image,
        type,
        price,
        countInStock,
        rating,
        description,
        discount,
        selled,
        category,
        expenses
      });
      if (createProduct) {
        resolve({
          status: "OK",
          message: "Success",
          data: createProduct,
        });
      }
    } catch (e) {
      reject(e);
    }
  });
};

const updateProduct = (id, data) => {
  return new Promise(async (resolve, reject) => {
    try {
      const checkProdcut = await Product.findById(id);
      if (checkProdcut === null) {
        resolve({
          status: "OK",
          message: "the product is not defined",
        });
      }

      const updateProduct = await Product.findByIdAndUpdate(id, data, {
        new: true,
      }); //new: true : lay moi nhat

      resolve({
        status: "OK",
        message: "Success",
        data: updateProduct,
      });
    } catch (e) {
      reject(e.message);
    }
  });
};

const getDetailProduct = (id) => {
  return new Promise(async (resolve, reject) => {
    try {
      const product = await Product.findById(id).populate('reviews')
      if (product === null) {
        resolve({
          status: "OK",
          message: "the user is not defined",
        });
      }

      resolve({
        status: "OK",
        message: "Success",
        data: product,
        reviews:product.reviews
      });
    } catch (e) {
      reject(e);
    }
  });
};

const getAllProductById = (data) => {
  return new Promise(async (resolve, reject) => {
    try {
      const heartData = [];
      const Data = data?.map(async (id) => {
        const product = await Product.findById(id);
        if (product) {
          heartData.push(product);
        }
      });

      const results = await Promise.all(Data);
      const newData = results && results[0] && results[0].id;

      if (heartData === null) {
        resolve({
          status: "OK",
          message: "the data is not defined",
        });
      }

      resolve({
        status: "OK",
        message: "Success",
        data: heartData,
      });
    } catch (e) {
      reject(e);
    }
  });
};

const deleteProduct = (id) => {
  return new Promise(async (resolve, reject) => {
    try {
      const checkProduct = await Product.findById(id);
      if (checkProduct === null) {
        resolve({
          status: "OK",
          message: "the product is not defined",
        });
      }

      await Product.findByIdAndDelete(id);

      resolve({
        status: "OK",
        message: "Delete product success",
      });
    } catch (e) {
      reject(e.message);
    }
  });
};

const deleteManyProduct = (ids) => {
  return new Promise(async (resolve, reject) => {
    try {
      await Product.deleteMany({ _id: ids });

      resolve({
        status: "OK",
        message: "Delete product success",
      });
    } catch (e) {
      reject(e.message);
    }
  });
};

const getAllProduct = (limit, page, sort, filter) => {
  return new Promise(async (resolve, reject) => {
    try {
      const totalProduct = await Product.count();
      console.log("limit, page, sort, filter", limit, page, sort, filter);
      let allProduct = [];
      if (filter) {
        const label = filter[0];
        const allObjectFilter = await Product.find({
          [label]: { $regex: filter[1] },
        })
          .limit(limit)
          .skip(page * limit)
          .sort({ createdAt: -1, updatedAt: -1 });
        resolve({
          status: "OK",
          message: "Success",
          data: allObjectFilter,
          total: totalProduct,
          pageCurrent: Number(page + 1),
          totalPage: Math.ceil(totalProduct / limit),
        });
      }
      if (sort) {
        const objectSort = {};
        objectSort[sort[1]] = sort[0];
        const allProductSort = await Product.find()
          .limit(limit)
          .skip(page * limit)
          .sort(objectSort)
          .sort({ createdAt: -1, updatedAt: -1 });
        resolve({
          status: "OK",
          message: "Success",
          data: allProductSort,
          total: totalProduct,
          pageCurrent: Number(page + 1),
          totalPage: Math.ceil(totalProduct / limit),
        });
      }
      if (!limit) {
        allProduct = await Product.find().sort({
          createdAt: -1,
          updatedAt: -1,
        });
      } else {
        allProduct = await Product.find()
          .limit(limit)
          .skip(page * limit)
          .sort({ createdAt: -1, updatedAt: -1 });
      }
      resolve({
        status: "OK",
        message: "Success",
        data: allProduct,
        total: totalProduct,
        pageCurrent: Number(page + 1),
        totalPage: Math.ceil(totalProduct / limit),
      });
    } catch (e) {
      reject(e);
    }
  });
};

const getProductByCategory = (categoryId, limit, page, sort, filter) => {
  return new Promise(async (resolve, reject) => {
    try {
      const totalProduct = await Product.count();
      // const categories = await Product.find({ category: categoryId }).populate(
      //   "category"
      // );
      console.log("limit, page, sort, filter", limit, page, sort, filter);
      let allCategory = [];
      if (filter) {
        const label = filter[0];
        const objectSort = {}
        objectSort[filter[0]] = filter[2]
        const object = objectSort.price ? objectSort : {}
        console.log('objectSort',objectSort);
        const allObjectFilter = await Product.find(
            {
              category: categoryId,
              [label]: { $gt: Number(filter[1]) }
            }
        )
          .populate("category")
          .limit(limit)
          .skip(page * limit)
          .sort(object)
          .sort({ createdAt: -1, updatedAt: -1 });
        resolve({
          status: "OK",
          message: "Success",
          data: allObjectFilter,
          total: totalProduct,
          pageCurrent: Number(page + 1),
          totalPage: Math.ceil(totalProduct / limit),
        });
      }
      if (sort) {
        const objectSort = {};
        objectSort[sort[0]] = sort[1];
        
        console.log('objectSort',objectSort);
        const allCategorySort = await Product.find({ category: categoryId })
          .populate("category")
          .limit(limit)
          .skip(page * limit)
          .sort(objectSort)
          .sort({ createdAt: -1, updatedAt: -1 });
        resolve({
          status: "OK",
          message: "Success",
          data: allCategorySort,
          total: totalProduct,
          pageCurrent: Number(page + 1),
          totalPage: Math.ceil(totalProduct / limit),
        });
      }
      if (!limit) {
        allCategory = await Product.find({ category: categoryId })
          .populate("category")
          .sort({
            createdAt: -1,
            updatedAt: -1,
          });
      } else {
        allCategory = await Product.find({ category: categoryId })
          .populate("category")
          .limit(limit)
          .skip(page * limit)
          .sort({ createdAt: -1, updatedAt: -1 });
      }
      resolve({
        status: "OK",
        message: "Success",
        data: allCategory,
        total: totalProduct,
        pageCurrent: Number(page + 1),
        totalPage: Math.ceil(totalProduct / limit),
      });
    } catch (e) {
      reject(e);
    }
  });
};

const getAllType = () => {
  return new Promise(async (resolve, reject) => {
    try {
      const allProduct = await Product.distinct("type");
      resolve({
        status: "OK",
        message: "Success",
        data: allProduct,
      });
    } catch (e) {
      reject(e);
    }
  });
};

module.exports = {
  createProduct,
  updateProduct,
  getDetailProduct,
  deleteProduct,
  getAllProduct,
  deleteManyProduct,
  getAllType,
  getAllProductById,
  getProductByCategory,
};
