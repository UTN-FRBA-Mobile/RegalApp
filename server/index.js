const express = require('express')
const path = require('path')
const PORT = process.env.PORT || 3000;
var indexRouter = require('./routes/index');
const admin = require('firebase-admin')
var serviceAccount = require("./google-services.json");

admin.initializeApp({
  credential: admin.credential.cert(serviceAccount)
});

express()
  // .use(express.static(path.join(__dirname, 'public')))
  .use(express.json())
  .use(express.urlencoded({ extended: true }))
  .use("/regalapp", indexRouter)
  .listen(PORT, () => console.log(`Listening on ${ PORT }`))
