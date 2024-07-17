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
    // Pembelianid
    ...ValidateSchemaCustom({
      ...configSchema,
      index: 'pembelianid',
      custom: {
        options: async (value, { req }) => {
          const user = await db.pembelian.findUnique({ where: { id: value } });
          if (!user) throw new Error('validations.model.data-not-found');

          req.scarlet.body.pembelianid = value;
        },
      },
    }),
    // Produkid
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
    // kuririd
    ...ValidateSchemaCustom({
      ...configSchema,
      index: 'kuririd',
      custom: {
        options: async (value, { req }) => {
          const user = await db.kurir.findUnique({ where: { id: value } });
          if (!user) throw new Error('validations.model.data-not-found');

          req.scarlet.body.kuririd = value;
        },
      },
    }),
    // HargaSatuan
    ...ValidateSchemaDefault({
      ...configSchema,
      changeValue: (x) => parseInt(x, 10),
      index: 'hargasatuan',
    }),
    // Jumlah
    ...ValidateSchemaDefault({
      ...configSchema,
      changeValue: (x) => parseInt(x, 10),
      index: 'jumlah',
    }),
    // SubTotal
    ...ValidateSchemaDefault({
      ...configSchema,
      changeValue: (x) => parseInt(x, 10),
      index: 'subtotal',
    }),
  };
};

function CreateValidator() {
  const { pembelianid, produkid, kuririd, hargasatuan, jumlah, subtotal } = ModelSchema({
    checkIn: ['body'],
    errorIf: 'exist'
  });

  const input = {
    hargasatuan: { ...hargasatuan, optional: true },
    jumlah: { ...jumlah, optional: true },
    subtotal: { ...subtotal, optional: true },
    pembelianid: { ...pembelianid, notEmpty: { errorMessage: 'validations.required' } },
    produkid: { ...produkid, notEmpty: { errorMessage: 'validations.required' } },
    kuririd: { ...kuririd, notEmpty: { errorMessage: 'validations.required' } },
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
  const { pembelianid, produkid, kuririd, hargasatuan, jumlah, subtotal } = ModelSchema({
    checkIn: ['body'],
    errorIf: 'exist'
  });

  const input = {
    hargasatuan: { ...hargasatuan, optional: true },
    jumlah: { ...jumlah, optional: true },
    subtotal: { ...subtotal, optional: true },
    pembelianid: { ...pembelianid, optional: true },
    produkid: { ...produkid, optional: true },
    kuririd: { ...kuririd, optional: true },
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
