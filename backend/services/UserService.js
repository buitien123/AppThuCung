const User = require("../models/User");
const bcrypt = require("bcrypt");
const { genneralAccessToken, genneralRefreshToken } = require("./JwtService");
const createUser = (newUser) => {
  return new Promise(async (resolve, reject) => {
    const { name, email, password, confirmPassword, phone } = newUser;
    try {
      const checkUser = await User.findOne({
        email: email,
      });
      if (checkUser !== null) {
        resolve({
          status: "ERR",
          message: "the email is already",
        });
      }
      const hash = bcrypt.hashSync(password, 10); // mã hóa password

      const createUser = await User.create({
        name,
        email,
        password: hash,
        phone,
      });
      if (createUser) {
        resolve({
          status: "OK",
          message: "Success",
          data: createUser,
        });
      }
    } catch (e) {
      reject(e);
    }
  });
};

const loginUser = (userLogin) => {
  return new Promise(async (resolve, reject) => {
    const {email, password } = userLogin;
    console.log('email, password',email, password);
    try {
      const checkUser = await User.findOne({
        email: email,
      });
      if (checkUser === null) {
        resolve({
          status: "ERR",
          message: "the user is not defined",
        });
      }
      const comparePassword = bcrypt.compareSync(password, checkUser.password);
      if (!comparePassword) {
        resolve({
          status: "ERR",
          message: "the password or user is incorrect",
        });
      }
      const access_Token = await genneralAccessToken({
        id: checkUser.id,
        isAdmin: checkUser.isAdmin
      })

      const refresh_token =await genneralRefreshToken({ 
        id: checkUser.id,
        isAdmin: checkUser.isAdmin
      })
      
      resolve({
        status: "OK",
        message: "Success",
        access_Token,
        refresh_token
      });
    } catch (e) {
      reject(e);
    }
  });
};

const updateUser = (id, data) => {
  return new Promise(async (resolve, reject) => {
    try {
      const checkUser = await User.findById(id);
      if (checkUser === null) {
        resolve({
          status: "OK",
          message: "the user is not defined",
        });
      }
      
      const updateUser = await User.findByIdAndUpdate(id, data, { new: true }) //new: true : lay moi nhat
      
      resolve({
        status: "OK",
        message: "Success",
        data: updateUser
      });
    } catch (e) {
      reject(e.message);
    }
  });
};

const deleteUser = (id) => {
  return new Promise(async (resolve, reject) => {
    try {
      const checkUser = await User.findById(id);
      if (checkUser === null) {
        resolve({
          status: "OK",
          message: "the user is not defined",
        });
      }
      
      await User.findByIdAndDelete(id)

      
      resolve({
        status: "OK",
        message: "Delete user success"
      });
    } catch (e) {
      reject(e.message);
    }
  });
};

const deleteManyUser = (ids) => {
  return new Promise(async (resolve, reject) => {
    try { 
      await User.findByIdAndDelete({_id: ids})

      
      resolve({
        status: "OK",
        message: "Delete users success"
      });
    } catch (e) {
      reject(e.message);
    }
  });
};

const getAllUser = () => {
  return new Promise(async (resolve, reject) => {
    try {
      const allUser = await User.find()
      resolve({
        status: "OK",
        message: "SUCCESS",
        data: allUser
      });
    } catch (e) {
      reject(e.message);
    }
  });
};

const getDetailsUser = (id) => {
  return new Promise(async (resolve, reject) => {
    try {
      const user = await User.findById(id);
      if (user === null) {
        resolve({
          status: "OK",
          message: "the user is not defined",
        });
      }

      
      resolve({
        status: "OK",
        message: "Success",
        data: user
      });
    } catch (e) {
      reject(e);
    }
  });
};


module.exports = {
  createUser,
  loginUser,
  updateUser,
  deleteUser,
  getAllUser,
  getDetailsUser,
  deleteManyUser
};
