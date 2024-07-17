const { checkSchema } = require('express-validator');

const { dbModel } = require('./Services');
const SchemaValidatorHandler = require('../../../utils/services/SchemaValidatorHandler');
const { ValidateSchemaModel, ValidateSchemaDefault, ValidateSchemaCustom } = require('../../../utils/services/ValidateSchema');
const { db } = require('../../../utils/database');

const config = {
  //
};

const FilterSchema = () => ({
  //
});

const ModelSchema = (options) => {
  const { customModel, checkIn, errorIf } = options;
  const configSchema = {
    checkIn,
    errorIf,
    dbModel: customModel || dbModel
  };

  return {
    // Id
    ...ValidateSchemaModel({
      ...configSchema,
      index: 'id',
    }),
    // transaksiid
    ...ValidateSchemaCustom({
      ...configSchema,
      index: 'transaksiid',
      custom: {
        options: async (value, { req }) => {
          const user = await db.transaksi.findUnique({ where: { id: value } });
          if (!user) throw new Error('validations.model.data-not-found');

          req.scarlet.body.transaksiid = value;
        },
      },
    }),
    // produkid
    ...ValidateSchemaCustom({
      ...configSchema,
      index: 'produkid',
      custom: {
        options: async (value, { req }) => {
          const user = await db.produk.findUnique({ where: { id: value } });
          if (!user) throw new Error('validations.model.data-not-found');

          req.scarlet.body.produkid = value;
        },
      },
    }),
    // Jumlah
    ...ValidateSchemaDefault({
      ...configSchema,
      changeValue: (x) => parseInt(x, 10),
      index: 'jumlah',
    }),
    // HargaSatuan
    ...ValidateSchemaDefault({
      ...configSchema,
      changeValue: (x) => parseInt(x, 10),
      index: 'hargasatuan',
    }),
    // SubTotal
    ...ValidateSchemaDefault({
      ...configSchema,
      changeValue: (x) => parseInt(x, 10),
      index: 'subTotal',
    }),
  };
};

function CreateValidator() {
  const { transaksiid, produkid, jumlah, hargasatuan, subTotal} = ModelSchema({
    checkIn: ['body'],
    errorIf: 'exist'
  });

  const input = {
    jumlah: { ...jumlah, optional: true },
    hargasatuan: { ...hargasatuan, optional: true },
    subTotal: { ...subTotal, optional: true },
    transaksiid: { ...transaksiid, notEmpty: { errorMessage: 'validations.required' } },
    produkid: { ...produkid, notEmpty: { errorMessage: 'validations.required' } },
  };

  return SchemaValidatorHandler([checkSchema(input)]);
}

function ReadValidator() {
  const { id } = ModelSchema({
    checkIn: ['params'],
    errorIf: 'notExist'
  });

  const input = {
    id: { ...id, exists: { errorMessage: 'validations.required', } }
  };

  return SchemaValidatorHandler([checkSchema(input)]);
}

function UpdateValidator() {
  const { transaksiid, produkid, jumlah, hargasatuan, subTotal } = ModelSchema({
    checkIn: ['body'],
    errorIf: 'exist'
  });

  const input = {
    jumlah: { ...jumlah, optional: true },
    hargasatuan: { ...hargasatuan, optional: true },
    subTotal: { ...subTotal, optional: true },
    transaksiid: { ...transaksiid, optional: true },
    produkid: { ...produkid, optional: true },
  };

  return SchemaValidatorHandler([checkSchema(input)]);
}

function DeleteValidator() {
  return SchemaValidatorHandler([]);
}

function WheresValidator() {
  const input = FilterSchema();

  return SchemaValidatorHandler([checkSchema(input)]);
}

module.exports = {
  // Config
  config,
  ModelSchema,
  FilterSchema,

  // CRUD
  CreateValidator,
  ReadValidator,
  UpdateValidator,
  DeleteValidator,

  // OTHER
  WheresValidator
};
