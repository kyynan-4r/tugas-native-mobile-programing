const express = require('express');
const NotFound = require('./api/utils/NotFound');
const Error = require('./api/utils/Error');

const app = express();

// Default Configuration
app.set('json spaces', 2);
app.use(express.json({ limit: '50mb' }));
app.use(express.urlencoded({ extended: true, limit: '50mb' }));

app.get('/', (req, res) => {
  res.json({
    message: 'Welcome to the Portfolio API'
  });
});

app.use((req, res, next) => {
  req.scarlet = {
    params: {},
    query: {},
    body: {},
  };
  next();
});

app.use('/siswa', require('./api/rest/siswa/Routes'));
app.use('/mapel', require('./api/rest/mapel/Routes'));
app.use('/guru', require('./api/rest/guru/Routes'));
app.use('/kelas', require('./api/rest/kelas/Routes'));
app.use('/nilai', require('./api/rest/mapel/Routes'));
app.use('/pelanggaran', require('./api/rest/pelanggaran/Routes'));

app.use(NotFound);
app.use(Error);

module.exports = app;
