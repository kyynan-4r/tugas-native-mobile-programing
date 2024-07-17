const { ValidateSchemaModel, ValidateSchemaDefault, ValidateSchemaCustom, isModelFound } = require('../../utils/ValidateSchema');
const db = require('../../utils/database');
const generateValidationObject = require('../../utils/generateValidationObject');

const table = 'nilai';

const ModelSchema = (options) => {
  const { checkIn, errorIf } = options;
  const configSchema = { checkIn, errorIf, dbModel: db[table] };

  const inputField = [
    'nilaiSiswa',
    'tanggal',
    'mapelId',
    'siswaId',
    'guruId',
  ];

  // *** //
  const model = {
    ...ValidateSchemaModel({
      ...configSchema,
      changeValue: (x) => parseInt(x, 10),
      index: 'id',
    }),

    ...ValidateSchemaDefault({
      ...configSchema,
      index: 'nilaiSiswa',
    }),

    ...ValidateSchemaDefault({
      ...configSchema,
      index: 'tanggal',
    }),

    ...ValidateSchemaCustom({
      ...configSchema,
      index: 'mapelId',
      custom: {
        options: async (value, { req }) => {
          await isModelFound(value, 'mapel')

          req.scarlet.body.mapelId = parseInt(value);
        },
      },
    }),

    ...ValidateSchemaCustom({
      ...configSchema,
      index: 'siswaId',
      custom: {
        options: async (value, { req }) => {
          await isModelFound(value, 'siswa')

          req.scarlet.body.siswaId = parseInt(value);
        },
      },
    }),

    ...ValidateSchemaCustom({
      ...configSchema,
      index: 'guruId',
      custom: {
        options: async (value, { req }) => {
          await isModelFound(value, 'guru')

          req.scarlet.body.guruId = parseInt(value);
        },
      },
    }),
  };

  const input = (x) => generateValidationObject(x, inputField, model)

  return { input, model }
  // *** //
};

module.exports = {
  table,
  ModelSchema,
};
