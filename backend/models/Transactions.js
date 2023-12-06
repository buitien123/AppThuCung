const mongoose = require("mongoose");

const transactionSchema = new mongoose.Schema(
  {
    name: {
      type: String,
      required: true,
    },
    type: {
        type: String,
        required: true,
    },
    totalPrice: {
        type: Number,
        required: true,
    },
    Expenses: {
        type: Number,
        required: true,
    },
    createdAt: {
      type: Date,
      default: Date.now,
    },
  },
  { timestamps: true }
);

const Transaction = mongoose.model('Transaction', transactionSchema);
module.exports = Transaction