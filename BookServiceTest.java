import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllBooks() {
        // Arrange
        Book book1 = new Book(1L, "Title1", "Author1");
        Book book2 = new Book(2L, "Title2", "Author2");
        List<Book> books = Arrays.asList(book1, book2);

        when(bookRepository.findAll()).thenReturn(books);

        // Act
        List<Book> result = bookService.getAllBooks();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Title1", result.get(0).getTitle());
    }

    @Test
    public void testGetBookById() {
        // Arrange
        Book book = new Book(1L, "Title1", "Author1");

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        // Act
        Book result = bookService.getBookById(1L);

        // Assert
        assertNotNull(result);
        assertEquals("Title1", result.getTitle());
    }

    @Test
    public void testGetBookById_NotFound() {
        // Arrange
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        // Act
        Book result = bookService.getBookById(1L);

        // Assert
        assertNull(result);
    }

    @Test
    public void testSaveBook() {
        // Arrange
        Book book = new Book(1L, "Title1", "Author1");

        when(bookRepository.save(book)).thenReturn(book);

        // Act
        Book result = bookService.saveBook(book);

        // Assert
        assertNotNull(result);
        assertEquals("Title1", result.getTitle());
    }

    @Test
    public void testDeleteBookById() {
        // Act
        bookService.deleteBookById(1L);

        // Assert
        verify(bookRepository, times(1)).deleteById(1L);
    }
}
