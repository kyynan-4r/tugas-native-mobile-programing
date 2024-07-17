/* eslint-disable no-unused-vars */
const express = require('express');

const router = express.Router();

// Initial Custom Request :<
router.use((req, res, next) => {
  req.scarlet = {
    params: {},
    query: {},
    body: {},
    pagination: {
      skip: 0,
      take: 10
    }
  };
  next();
});

router.use('/rest', [
  //
], require('./rest/Routes'));

module.exports = router;
