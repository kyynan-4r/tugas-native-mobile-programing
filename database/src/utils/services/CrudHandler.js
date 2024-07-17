class CrudHandler {
  constructor(dbModel) {
    this.dbModel = dbModel;
  }

  async create(data) {
    const output = await this.dbModel.create({
      data,
    });

    return output;
  }

  async read(id) {
    const output = await this.dbModel.findUnique({
      where: { id },
    });

    return output;
  }

  async reads(where, skip, take) {
    const output = await this.dbModel.findMany({
      where,
      skip,
      take
    });

    return output;
  }

  async update(id, data) {
    const output = await this.dbModel.update({
      data,
      where: { id },
    });

    return output;
  }

  async delete(id) {
    const output = await this.dbModel.delete({
      where: {
        id,
      },
    });

    return output;
  }
}

module.exports = CrudHandler;
