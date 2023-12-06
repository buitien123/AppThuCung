const petRoutes = require("../routes/petRotues");
const categoryRoutes = require("../routes/categoryRoutes");
const reviewRoutes = require("../routes/reviewRoutes");
const adoptionRoutes = require("../routes/adoptionRoutes");
const productRoutes = require("../routes/productRoutes");
const userRoutes = require("../routes/userRoutes");
const OrderRoutes = require("../routes/OrderRoutes");
const PaymentRoutes = require("../routes/PaymentRoutes");
const TransactionRoutes = require("../routes/transactionRoutes");

const routes = (app) => {
    app.use("/api/pets", petRoutes);
    app.use("/api/category", categoryRoutes);
    app.use("/api/review", reviewRoutes);
    app.use("/api/adoption", adoptionRoutes);
    app.use("/api/product", productRoutes);
    app.use("/api/user", userRoutes);
    app.use("/api/order", OrderRoutes);
    app.use('/api/payment', PaymentRoutes)
    app.use('/api/transactions', TransactionRoutes)
}

module.exports = routes

