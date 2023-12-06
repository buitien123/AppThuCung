const mongoose = require("mongoose");

const ProductSchema = new mongoose.Schema(
  {
    userId: {
      type: mongoose.Schema.Types.ObjectId,
      ref: "User",
    },
    name: {
      type: String,
      required: true,
    },
    description: {
      type: String,
      required: true,
    },
    imageLabel: {
      type: String,
      // required: true,
    },
    category: {
      type: mongoose.Schema.Types.ObjectId,
      ref: "Category",
    },
    image: {
      type: String,
      required: true,
    },
    price: {
      type: Number,
      required: true,
    },
    rating: { type: Number, required: true },
    type: { type: String, required: true },
    discount: { type: Number },
    selled: { type: Number },
    countInStock: { type: Number, required: true },
    reviews: [
      {
        type: mongoose.Schema.Types.ObjectId,
        ref: "Review",
      },
    ],
    expenses: { type: Number,
      //  required: true
       },
  },
  {
    timestamps: true,
  }
);

module.exports = Product = mongoose.model("Product", ProductSchema);
