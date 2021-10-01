package io.javabrains.betterreads.book;

import java.util.Optional;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import io.javabrains.betterreads.userbooks.UserBooks;
import io.javabrains.betterreads.userbooks.UserBooksPrimaryKey;
import io.javabrains.betterreads.userbooks.UserBooksRepository;

@Controller
public class BookController {

    private static final String COVER_IMAGE_ROOT = "http://covers.openlibrary.org/b/id/";

    private final BookRepository bookRepository;
    private final UserBooksRepository userBooksRepository;

    public BookController(BookRepository bookRepository, UserBooksRepository userBooksRepository){
        this.bookRepository = bookRepository;
        this.userBooksRepository = userBooksRepository;
    }
    
    @GetMapping(value = "/books/{bookId}")
    public String getBook(@PathVariable String bookId, Model model, @AuthenticationPrincipal OAuth2User principal) {
        Optional<Book> optionalBook = bookRepository.findById(bookId);
        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();
            String coverImageUrl = getCoverImageUrl(book);
            model.addAttribute("coverImage", coverImageUrl);
            model.addAttribute("book", book);
            if (principal != null && principal.getAttribute("login") != null) {
                String userId = principal.getAttribute("login");
                model.addAttribute("loginId", userId);
                UserBooksPrimaryKey key = new UserBooksPrimaryKey();
                key.setBookId(bookId);
                key.setUserId(userId);
                UserBooks userBooks = userBooksRepository.findById(key).orElse(new UserBooks());
                model.addAttribute("userBooks", userBooks);
            }
            return "book";


        }
        return "book-not-found";
    }

    private String getCoverImageUrl(Book book){
        String coverImageUrl = "/images/no-image.png";
        if (book.getCoverIds() != null && book.getCoverIds().size() > 0) {
            coverImageUrl = COVER_IMAGE_ROOT + book.getCoverIds().get(0) + "-L.jpg";
        }
        return coverImageUrl;
    }
}
