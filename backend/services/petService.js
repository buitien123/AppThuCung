const Pet = require("../models/Pet");

exports.getAllType = () => {
  return new Promise(async (resolve, reject) => {
    try {
      const allPets = await Pet.distinct("type");
      resolve({
        status: "OK",
        message: "Success",
        data: allPets,
      });
    } catch (e) {
      reject(e);
    }
  });
};

exports.getAllBreed = (type) => {
  return new Promise(async (resolve, reject) => {
    try {
      const allPets = await Pet.find({ type: type}).distinct("breed");
      resolve({
        status: "OK",
        message: "Success",
        data: allPets,
      });
    } catch (e) {
      reject(e);
    }
  });
};

exports.getAllPetById = (data) => {
  return new Promise(async (resolve, reject) => {
    try {
      const heartData = [];
      const Data = data?.map(async (id) => {
        const pet = await Pet.findById(id);
        if (pet) {
          heartData.push(pet);
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

exports.getDetailPet = (id) => {
  return new Promise(async (resolve, reject) => {
    try {
      const pet = await Pet.findById(id).populate("reviews");
      if (pet === null) {
        resolve({
          status: "OK",
          message: "the user is not defined",
        });
      }

      resolve({
        status: "OK",
        message: "Success",
        data: pet,
        reviews: pet.reviews,
      });
    } catch (e) {
      reject(e);
    }
  });
};

exports.getAllPet = (limit, page, sort, filter) => {
  return new Promise(async (resolve, reject) => {
    try {
      const totalPet = await Pet.count();
      let allPet = [];
      if (filter) {
        const label = filter[0];
        const allObjectFilter = await Pet.find({
          [label]: { $regex: filter[1] },
        })
          .populate("category")
          .limit(limit)
          .skip(page * limit)
          .sort({ createdAt: -1, updatedAt: -1 });
        resolve({
          status: "OK",
          message: "Success",
          data: allObjectFilter,
          total: totalPet,
          pageCurrent: Number(page + 1),
          totalPage: Math.ceil(totalPet / limit),
        });
      }
      if (sort) {
        const objectSort = {};
        objectSort[sort[1]] = sort[0];
        const allPetSort = await Pet.find()
          .limit(limit)
          .skip(page * limit)
          .sort(objectSort)
          .sort({ createdAt: -1, updatedAt: -1 });
        resolve({
          status: "OK",
          message: "Success",
          data: allPetSort,
          total: totalPet,
          pageCurrent: Number(page + 1),
          totalPage: Math.ceil(totalPet / limit),
        });
      }
      if (!limit) {
        allPet = await Pet.find().sort({ createdAt: -1, updatedAt: -1 });
      } else {
        allPet = await Pet.find()
          .limit(limit)
          .skip(page * limit)
          .sort({ createdAt: -1, updatedAt: -1 });
      }
      resolve({
        status: "OK",
        message: "Success",
        data: allPet,
        total: totalPet,
        pageCurrent: Number(page + 1),
        totalPage: Math.ceil(totalPet / limit),
      });
    } catch (e) {
      reject(e);
    }
  });
};

exports.getPetByCategory = (categoryId, limit, page, sort, filter = []) => {
  return new Promise(async (resolve, reject) => {
    try {
      const totalPet = await Pet.count();
      // const categories = await Pet.find({ category: categoryId }).populate(
      //   "category"
      // );
      console.log("limit, page, sort, filter", limit, page, sort, filter);
      let allCategory = [];
      if (filter && filter[0] === "price") {
        const label = filter[0];
        const label1 = filter[2];
        const label2 = filter[4];
        const objectSort = {};
        const lastFilter = filter.includes("sort") ? filter.length - 1 : 100;
        console.log("filter",lastFilter);
        objectSort[filter[0]] = filter[lastFilter];
        const object = objectSort.price ? objectSort : {};
        const allObjectFilter = await Pet.find({
          category: categoryId,
          [label]: { $gt: Number(filter[1]) },
          [label1]: { $regex: filter[3] },
          // [label1]: { $regex: filter[4] },
          [label2]: { $regex: filter[5] },
        })
          .populate("category")
          .limit(limit)
          .skip(page * limit)
          .sort(object)
          .sort({ createdAt: -1, updatedAt: -1 });
        resolve({
          status: "OK",
          message: "Success",
          data: allObjectFilter,
          total: totalPet,
          pageCurrent: Number(page + 1),
          totalPage: Math.ceil(totalPet / limit),
        });
      } else if (filter && filter[0] === "type" && !sort) {
        console.log("type");
        const label = filter[0];
        const label1 = filter[2];
        const objectSort = {};
        objectSort[filter[0]] = filter[2];
        const object = objectSort.price ? objectSort : {};
        const allObjectFilter = await Pet.find({
          category: categoryId,
          // [label]: { $gt: Number(filter[1]) },
          [label]: { $regex: filter[1] },
          [label1]: { $regex: filter[3] },
        })
          .populate("category")
          .limit(limit)
          .skip(page * limit)
          .sort(object)
          .sort({ createdAt: -1, updatedAt: -1 });
        resolve({
          status: "OK",
          message: "Success",
          data: allObjectFilter,
          total: totalPet,
          pageCurrent: Number(page + 1),
          totalPage: Math.ceil(totalPet / limit),
        });
      }
      if (sort) {
        console.log("def2");
        const label = filter[0];
        const label1 = filter[2];
        const objectSort = {};
        objectSort[sort[0]] = sort[1];
        const allCategorySort = await Pet.find({
          category: categoryId,
          [label]: { $regex: filter[1] },
          [label1]: { $regex: filter[3] },
        })
          .populate("category")
          .limit(limit)
          .skip(page * limit)
          .sort(objectSort)
          .sort({ createdAt: -1, updatedAt: -1 });
        resolve({
          status: "OK",
          message: "Success",
          data: allCategorySort,
          total: totalPet,
          pageCurrent: Number(page + 1),
          totalPage: Math.ceil(totalPet / limit),
        });
      }
      if (!limit) {
        console.log("def1");
        allCategory = await Pet.find({ category: categoryId })
          .populate("category")
          .sort({
            createdAt: -1,
            updatedAt: -1,
          });
      } else {
        console.log("def");
        allCategory = await Pet.find({ category: categoryId })
          .populate("category")
          .limit(limit)
          .skip(page * limit)
          .sort({ createdAt: -1, updatedAt: -1 });
      }

      resolve({
        status: "OK",
        message: "Success",
        data: allCategory,
        total: totalPet,
        pageCurrent: Number(page + 1),
        totalPage: Math.ceil(totalPet / limit),
      });
    } catch (e) {
      reject(e);
    }
  });
};
