const { ValidateSchemaModel, ValidateSchemaDefault, ValidateSchemaCustom, isModelFound } = require('../../utils/ValidateSchema');
const db = require('../../utils/database');
const generateValidationObject = require('../../utils/generateValidationObject');

const table = 'pelanggaran';

const ModelSchema = (options) => {
  const { checkIn, errorIf } = options;
  const configSchema = { checkIn, errorIf, dbModel: db[table] };

  const inputField = [
    'detailPelanggaran',
    'poinPelanggaran',
    'tanggal',
    'siswaId',
    'kelasId',
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
      index: 'detailPelanggaran',
    }),

    ...ValidateSchemaDefault({
      ...configSchema,
      index: 'poinPelanggaran',
    }),

    ...ValidateSchemaDefault({
      ...configSchema,
      index: 'tanggal',
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
      index: 'kelasId',
      custom: {
        options: async (value, { req }) => {
          await isModelFound(value, 'kelas')

          req.scarlet.body.kellasId = parseInt(value);
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
