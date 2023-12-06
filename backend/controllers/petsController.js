const Pet = require("../models/Pet");
const fs = require("fs");
const path = require("path");
const PetService = require("../services/petService");

exports.getAll = async (req, res) => {
  try {
    const { limit, page, sort, filter } = req.query;
    const response = await PetService.getAllPet(
      Number(limit) || null,
      Number(page) || 0,
      sort,
      filter
    );
    return res.status(200).json(response);
  } catch (e) {
    return res.status(404).json({
      message: e,
    });
  }
};

exports.getAllPetById = async (req, res) => {
  try {
    const data = req.body;
    if (!data) {
      return res.status(200).json({
        status: "ERR",
        message: "The userId is required",
      });
    }

    const response = await PetService.getAllPetById(data);
    return res.status(200).json(response);
  } catch (e) {
    return res.status(404).json({
      message: e,
    });
  }
};

exports.getDetailPet = async (req, res) => {
  try {
    const pet = req.params.id;
    if (!pet) {
      return res.status(200).json({
        status: "ERR",
        message: "The userId is required",
      });
    }

    const response = await PetService.getDetailPet(pet);
    return res.status(200).json(response);
  } catch (e) {
    return res.status(404).json({
      message: e,
    });
  }
};

exports.getOne = async (req, res) => {
  const { id } = req.params;
  try {
    const pet = await Pet.findById(id).populate("category");

    res.json(pet);
  } catch (error) {
    console.log(error);
    res.status(400).json(error);
  }
};

exports.getByCategory = async (req, res) => {
  try {
    const { categoryId } = req.params;
    const { limit, page, sort, filter } = req.query;
    const response = await PetService.getPetByCategory(
      categoryId,
      Number(limit) || null,
      Number(page) || 0,
      sort,
      filter
    );
    return res.status(200).json(response);
  } catch (e) {
    return res.status(404).json({
      message: e,
    });
  }
};

exports.create = async (req, res) => {
  try {
    const {
      name,
      age,
      breed,
      color,
      description,
      rating,
      type,
      category,
      price,
      discount,
      countInStock,
      image,
      expenses
    } = req.body;
    // const { image, additionalImages } = req.files;

    // let imagePath = "";
    // let additionalImagesPaths = [];
    // if (req.files.image && req.files.image.length > 0) {
    //   imagePath = image[0].path;
    // }

    // if (req.files.additionalImages && req.files.additionalImages.length > 0) {
    //   additionalImagesPaths = additionalImages.map((file) => file.path);
    // }

    const createdPet = await Pet.create({
      name,
      age,
      breed,
      color,
      description,
      rating,
      category,
      price,
      type,
      discount,
      countInStock,
      image,
      expenses
      // additionalImages: additionalImagesPaths,
    });

    res.json({
      message: "Pet created successfully.",
      status: "OK",
      data: createdPet,
    });
  } catch (error) {
    console.log(error);
    res.status(404).json(error);
  }
};

exports.update = async (req, res) => {
  try {
    const { id } = req.params;
    const {
      name,
      age,
      breed,
      color,
      description,
      rating,
      category,
      price,
      type,
      discount,
      countInStock,
      image,
      expenses
    } = req.body;
    // const { image, additionalImages } = req.files;

    // let imagePath = "";
    // let additionalImagesPaths = [];
    // if (req.files.image && req.files.image.length > 0) {
    //   imagePath = image[0].path;
    // }

    // if (
    //   Array.isArray(req.files.additionalImages) &&
    //   req.files.additionalImages.length > 0
    // ) {
    //   additionalImagesPaths = additionalImages.map((file) => file.path);
    // }

    // const existing = await Pet.findById(id);
    // console.log(existing.additionalImages);

    // if (additionalImagesPaths.length === 0) {
    //   additionalImagesPaths = existing.additionalImages;
    // } else if (
    //   Array.isArray(existing.additionalImages) &&
    //   existing.additionalImages.length > 0
    // ) {
    //   console.log(existing.additionalImages);
    //   await Promise.all(
    //     existing.additionalImages.map(
    //       async (img) =>
    //         await fs.unlink(path.join(__dirname, "../", img), (err, res) => {
    //           if (err) {
    //             console.log(err);
    //           } else {
    //             console.log("Files deleted successfuly");
    //           }
    //         })
    //     )
    //   )
    //     .then(console.log)
    //     .catch(console.log);
    // }

    // if (imagePath.length === 0) {
    //   imagePath = existing.image;
    // } else {
    //   await fs.unlink(existing.image, (err, res) => {
    //     if (err) {
    //       console.log(err);
    //     } else {
    //       return;
    //     }
    //   });
    // }

    const createdPet = await Pet.findByIdAndUpdate(
      id,
      {
        name,
        age,
        breed,
        color,
        description,
        rating,
        category,
        price,
        type,
        discount,
        countInStock,
        image,
        expenses
        // additionalImages: additionalImagesPaths,
      },
      { new: true }
    );

    res.json({
      message: "Pet updated successfully.",
      status: "OK",
      data: createdPet,
    });
  } catch (error) {
    console.log(error);
    res.status(400).json(error);
  }
};

exports.delete = async (req, res) => {
  const { id } = req.params;
  try {
    const pet = await Pet.findByIdAndRemove(id);

    res.json({ message: "Pet successfuly deleted", status : "OK", data:pet });
  } catch (error) {
    console.log(error);
    res.status(400).json(error);
  }
};

exports.getAllType = async (req, res) => {
  try {
    const response = await PetService.getAllType();
    return res.status(200).json(response);
  } catch (e) {
    return res.status(404).json({
      message: e,
    });
  }
};

exports.getAllBreed = async (req, res) => {
  try {
    const data = req.body;
    const response = await PetService.getAllBreed(data.type);
    return res.status(200).json(response);
  } catch (e) {
    return res.status(404).json({
      message: e,
    });
  }
};

