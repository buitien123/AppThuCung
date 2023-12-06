const Order = require("../models/Order");
const Product = require("../models/Product");
const Pet = require("../models/Pet");
const User = require("../models/User");
const EmailService = require("../services/EmailService");

const createOrder = (newOrder) => {
  return new Promise(async (resolve, reject) => {
    const {
      orderItems,
      orderPetItems,
      paymentMethod,
      itemsPrice,
      shippingPrice,
      totalPrice,
      fullName,
      address,
      city,
      phone,
      user,
      isPaid,
      paidAt,
      email,
    } = newOrder;
    try {
      const promiseProduct = orderItems.map(async (order) => {
        
        const productData = await Product.findOneAndUpdate(
          {
            // filter
            _id: order.product, // tìm phần tử
            countInStock: { $gte: order.amount }, //Tìm phần tử đó kiểm tra amount đủ để giảm số lượng
          },
          {
            $inc: {
              // update
              countInStock: -order.amount, // giảm số lượng hàng
              selled: +order.amount, // tăng số lượng đã bán
            },
          },
          { new: true } // trả về th mới nhất
        );
        if (productData) {
          return {
            status: "OK",
            message: "SUCCESS",
          };
        } else {
          return {
            status: "OK",
            message: "ERR",
            id: order.product,
          };
        }
      });

      const promisePet = orderPetItems.map(async (order) => {
        
        const petData = await Pet.findOneAndUpdate(
          {
            // filter
            _id: order.pet, // tìm phần tử
            countInStock: { $gte: order.amount }, //Tìm phần tử đó kiểm tra amount đủ để giảm số lượng
          },
          {
            $inc: {
              // update
              countInStock: -order.amount, // giảm số lượng hàng
              selled: +order.amount, // tăng số lượng đã bán
            },
          },
          { new: true } // trả về th mới nhất
        );
        if (petData) {
          return {
            status: "OK",
            message: "SUCCESS",
          };
        } else {
          return {
            status: "OK",
            message: "ERR",
            id: order.pet,
          };
        }
      });
      const results = await Promise.all(promiseProduct,promisePet);
      const newData = results && results.filter((item) => item.id);

      if (newData.length) {
        const arrId = [];
        newData.forEach((item) => {
          arrId.push(item.id);
        });
        resolve({
          status: "ERR",
          message: `San pham voi id: ${arrId.join(",")} khong du hang`,
        });
      } else {
        const createdOrder = await Order.create({
          orderItems,
          orderPetItems,
          shippingAddress: {
            fullName,
            address,
            city,
            phone,
          },
          paymentMethod,
          itemsPrice,
          shippingPrice,
          totalPrice,
          user: user,
          isPaid,
          paidAt,
        });
        if (createdOrder) {
          
          await EmailService.sendEmailCreateOrder(email, orderItems);
          resolve({
            status: "OK",
            message: "success",
          });
        }
        // if(createdOrder) {
        //   resolve({
        //         status: "OK",
        //         message: "success",
        //   });
        // }
      }
    } catch (e) {
      //   console.log('e', e)
      reject(e);
    }
  });
};

// const deleteManyProduct = (ids) => {
//     return new Promise(async (resolve, reject) => {
//         try {
//             await Product.deleteMany({ _id: ids })
//             resolve({
//                 status: 'OK',
//                 message: 'Delete product success',
//             })
//         } catch (e) {
//             reject(e)
//         }
//     })
// }

const getAllOrderDetails = (id) => {
  return new Promise(async (resolve, reject) => {
    try {
      const order = await Order.find({
        user: id,
      }).sort({ createdAt: -1, updatedAt: -1 });
      if (order === null) {
        resolve({
          status: "ERR",
          message: "The order is not defined",
        });
      }
      console.log('order',order);
      const idItem = []
      order?.map(async (or) => {
        if(or?.isDelivered === "Đơn Hàng Đã Hoàn Thành") {
          // const getUser = await User.findById({ _id: id})
          or?.orderItems?.map((product) => {
            return idItem.push(product.product)
          })
          or?.orderPetItems?.map((pet) => {
            return idItem.push(pet.pet)
          })
          await User.findByIdAndUpdate({_id: id}, {
            commentIdOrder: idItem
          }, { new: true })
        } else {
          await User.findByIdAndUpdate({_id: id}, {
            commentIdOrder: []
          }, { new: true })
        }
      })
      

      resolve({
        status: "OK",
        message: "SUCESSS",
        data: order,
      });
    } catch (e) {
      // console.log('e', e)
      reject(e);
    }
  });
};

const getOrderDetails = (id) => {
  return new Promise(async (resolve, reject) => {
    try {
      const order = await Order.findById({
        _id: id,
      });
      if (order === null) {
        resolve({
          status: "ERR",
          message: "The order is not defined",
        });
      }

      resolve({
        status: "OK",
        message: "SUCESSS orderDetails",
        data: order,
      });
    } catch (e) {
      // console.log('e', e)
      reject(e);
    }
  });
};

const cancelOrderDetails = (id, datapro, datapet) => {
  return new Promise(async (resolve, reject) => {
    try {
      let orders = [];
      const promiseProduct = datapro.map(async (order) => {
        const productData = await Product.findOneAndUpdate(
          {
            _id: order?.product,
            selled: { $gte: order.amount },
          },
          {
            $inc: {
              countInStock: +order.amount,
              selled: -order.amount,
            },
          },
          { new: true }
        );
        if (productData) {
          orders = await Order.findByIdAndDelete(id);
          if (orders === null) {
            resolve({
              status: "ERR",
              message: "The order is not defined",
            });
          }
        } else {
          return {
            status: "ERR",
            message: "ERR",
            id: order?.product,
          };
        }
        return {
          status: "OK",
          message: "Success",
        };
      });

      const promisePet = datapet.map(async (order) => {
        const petData = await Pet.findOneAndUpdate(
          {
            _id: order?.pet,
            selled: { $gte: order.amount },
          },
          {
            $inc: {
              countInStock: +order.amount,
              selled: -order.amount,
            },
          },
          { new: true }
        );
        if (petData) {
          orders = await Order.findByIdAndDelete(id);
          if (orders === null) {
            resolve({
              status: "ERR",
              message: "The order is not defined",
            });
          }
        } else {
          return {
            status: "ERR",
            message: "ERR",
            id: order?.product,
          };
        }
        return {
          status: "OK",
          message: "Success",
        };
      });
      const results = await Promise.all(promiseProduct,promisePet);
      const newData = results && results[0] && results[0].id;

      if (newData) {
        resolve({
          status: "ERR",
          message: `San pham voi id: ${newData} khong ton tai`,
        });
      }
      resolve({
        status: "OK",
        message: "success",
        data: orders,
      });
    } catch (e) {
      reject(e);
    }
  });
};

const getAllOrder = () => {
  return new Promise(async (resolve, reject) => {
    try {
      const allOrder = await Order.find().sort({
        createdAt: -1,
        updatedAt: -1,
      });
      resolve({
        status: "OK",
        message: "Success",
        data: allOrder,
      });
    } catch (e) {
      reject(e);
    }
  });
};

const updateProduct = (id, data) => {
  return new Promise(async (resolve, reject) => {
    try {
      const checkOrder = await Order.findById(id);
      if (checkOrder === null) {
        resolve({
          status: "OK",
          message: "the product is not defined",
        });
      }
      
      const updateOrder = await Order.findByIdAndUpdate(id, data, { new: true }) //new: true : lay moi nhat
      
      resolve({
        status: "OK",
        message: "Success update",
        data: updateOrder
      });
    } catch (e) {
      reject(e.message);
    }
  });
};

module.exports = {
  createOrder,
  getAllOrderDetails,
  getOrderDetails,
  cancelOrderDetails,
  getAllOrder,
  updateProduct
};
