const express = require('express');

const router = express.Router();

// Users Model
router.use('/pengguna', require('./pengguna/Routes'));
router.use('/produk', require('./produk/Routes'));
router.use('/kurir', require('./kurir/Routes'));
router.use('/pemasok', require('./pemasok/Routes'));
router.use('/detailpembelian', require('./detailpembelian/Routes'));
router.use('/detailtransaksi', require('./detailtransaksi/Routes'));
router.use('/transaksi', require('./transaksi/Routes'));
router.use('/pembelian', require('./pembelian/Routes'));

module.exports = router;
