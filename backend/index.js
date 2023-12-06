const express = require("express");
const mongoose = require("mongoose");
const dotenv = require("dotenv");
const cors = require("cors");
const morgan = require("morgan");
const path = require("path");
const routes = require("./routes");
const bodyParser = require("body-parser"); // nhận req.body từ client gửi lên
const cookieParser = require("cookie-parser"); // nhận req.body từ client gửi lên
const app = express();
dotenv.config();
app.use(cors());

app.use(express.json({limit: '50mb'}));
app.use(express.urlencoded({limit: '50mb'}));
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true }))
app.use(morgan("tiny")); // ghi log
app.use(cookieParser())

app.use("/public", express.static(path.join(__dirname, "public")));// kết nối đến file tĩnh là public
routes(app)

const mongodbUri = "mongodb+srv://qlthucung1:2Kwhm2YTFYnPwkz7@cluster0.d0zaumm.mongodb.net/?retryWrites=true&w=majority";

mongoose.connect(mongodbUri, {
  useNewUrlParser: true,
});

mongoose.connection.on("connected", () => {
  console.log("Connected to mongodb...");
});

mongoose.connection.on("error", (err) => {
  console.log("Error connecting to mongo", err);
});

app.listen(4001, () => {
  console.log("App is running on PORT 4001");
});
