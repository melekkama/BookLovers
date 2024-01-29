import BookViewDto from "../dtos/BookView.dto.js";
import bookRepository from "../repositories/book.repository.js";
import { PaginationModel } from "../../../common/pagination.js";

class BookService {
  async getAll() {
    const books = await bookRepository.getAll();
    const models = books.map((book) => new BookViewDto(book));
    return models;
  }

  async getAllPagination(query) {
    const books = await this.getAll();
    const data = PaginationModel.create(
      books,
      query,
      (item, search) =>
        item.name.toLowerCase().includes(search) ||
        item.isbn.includes(search) ||
        item.author.name.toLowerCase().includes(search)
    );
    return data;
  }

  async getById(id) {
    const book = await bookRepository.getById(id);
    return new BookViewDto(book);
  }

  async create(book) {
    const created = await bookRepository.create(book);
    return new BookViewDto(created);
  }
}

const instance = new BookService();

export default instance;

export { BookService };
