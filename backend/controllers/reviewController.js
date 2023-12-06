const Review = require("../models/Review");
const Product = require("../models/Product");
const Pet = require("../models/Pet");

// exports.getAll = async (req, res) => {
//   try {
//     const categories = await Category.find();

//     res.json(categories);
//   } catch (error) {
//     console.log(error);
//     res.status(400).json(error);
//   }
// };
exports.getDetailReview = async (req, res) => {
  try {
    const id = req.params.id;
    const review = await Review.findById(id)

    res.json(review);
  } catch (error) {
    console.log(error);
    res.status(400).json(error);
  }
};

exports.create = async (req, res) => {
  try {
    const {userId, username, email, reviewText, rating, product, pet, image } =
      req.body;
    const created = await Review.create({
      userId,
      username,
      email,
      reviewText,
      rating,
      product,
      pet,
      image,
    });
    created.save((err, result) => {
      if(err) {
        console.log(err);
      } else {
        if(product) {
          Product.findById(product, (err, pro) => {
            if(err) {
              console.log(err);
            } else {
              pro.reviews.push(result)
              pro.save()
            }
          })
        } else if(pet) {
          Pet.findById(pet, (err, pro) => {
            if(err) {
              console.log(err);
            } else {
              pro.reviews.push(result)
              pro.save()
            }
          })
        }
      }
    })
    // const productRelated = await Product.findById(product);
    // productRelated.reviews.push(created?._id)
    // console.log("productRelated",productRelated);
    // await postRelated.save();

    res.json({ message: "Category successfuly created.", created });
  } catch (error) {
    res.status(400).json(error);
  }
};

exports.update = async (req, res) => {
  try {
    const { id } = req.params;
    const {userId, username, email, reviewText, rating, product, pet, image } = req.body;
    const created = await Category.findByIdAndUpdate(id, {
      userId, username, email, reviewText, rating, product, pet, image
    });

    res.json({ message: "Category successfuly updated.", created });
  } catch (error) {
    console.log(error);
    res.status(400).json(error);
  }
};

exports.delete = async (req, res) => {
  try {
    const { id } = req.params;

    const deleted = await Review.findByIdAndRemove(id);

    res.json({ message: "Category successfuly deleted.", deleted });
  } catch (error) {
    console.log(error);
    res.status(400).json(error);
  }
};
