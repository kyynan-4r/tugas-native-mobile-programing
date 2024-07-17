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
    // Pemasok id
    ...ValidateSchemaCustom({
      ...configSchema,
      index: 'pemasokid',
      custom: {
        options: async (value, { req }) => {
          const user = await db.pemasok.findUnique({ where: { id: value } });
          if (!user) throw new Error('validations.model.data-not-found');

          req.scarlet.body.pemasokid = value;
        },
      },
    }),
    // Tanggal
    ...ValidateSchemaDefault({
      ...configSchema,
      index: 'tanggal',
    }),
   // Jumlah
   ...ValidateSchemaDefault({
    ...configSchema,
    changeValue: (x) => parseInt(x, 10),
    index: 'total',
  }),
  };
};

function CreateValidator() {
  const { pemasokid, tanggal, total } = ModelSchema({
    checkIn: ['body'],
    errorIf: 'exist'
  });

  const input = {
    tanggal: { ...tanggal, optional: true },
    total: { ...total, optional: true },
    pemasokid: { ...pemasokid, notEmpty: { errorMessage: 'validations.required' } },

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
  const { tanggal, total, pemasokid } = ModelSchema({
    checkIn: ['body'],
    errorIf: 'exist'
  });

  const input = {
    tanggal: { ...tanggal, optional: true },
    total: { ...total, optional: true },
    pemasokid: { ...pemasokid, optional: true },

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
