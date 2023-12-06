const mongoose = require("mongoose");

const PetSchema = new mongoose.Schema(
  {
    name: {
      type: String,
      required: true,
    },
    age: {
      type: String,
      required: true,
    },
    color: {
      type: String,
      required: true,
    },
    description: {
      type: String,
    },
    breed: {
      type: String,
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
    },
    additionalImages: [{ type: String }],
    adoptionStatus: {
      type: String,
      enum: ["open", "pending", "closed"],
      default: "open",
    },
    price: {
      type: Number,
      // required: true,
    },
    discount: { type: Number },
    selled: { type: Number },
    countInStock: { type: Number, required: true },
    rating: { type: Number, required: true },
    type: { type: String },
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

module.exports = Pet = mongoose.model("Pet", PetSchema);
