import mongoose, { Schema } from 'mongoose';
const mongoose = require("mongoose");
const Schema = mongoose.Schema;

export const UserSchema = mongoose.model(
  'userlist',
  new mongoose.Schema({
    firstname: String,
    lastname: String,
    username: String,
    password: String,
    signedin: Boolean,
    contributing: Boolean,
    profilepic: String,
    Friends: [String]
   })
);
