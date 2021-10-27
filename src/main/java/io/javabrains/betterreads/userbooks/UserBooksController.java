package io.javabrains.betterreads.userbooks;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.ModelAndView;

import io.javabrains.betterreads.book.Book;
import io.javabrains.betterreads.book.BookRepository;
import io.javabrains.betterreads.user.BooksByUser;
import io.javabrains.betterreads.user.BooksByUserRepository;
import io.javabrains.betterreads.validation.DateValidator;

@Controller
public class UserBooksController {

    @Autowired 
    UserBooksRepository userBooksRepository;

    @Autowired 
    BooksByUserRepository booksByUserRepository;

    @Autowired 
    BookRepository bookRepository;
    
    @PostMapping("/addUserBook")
    public ModelAndView addBookForUser(
        @RequestBody MultiValueMap<String, String> formData, 
        @AuthenticationPrincipal OAuth2User principal
    ) {
        if (principal == null || principal.getAttribute("login") == null) {
            return null;
        }

        String bookId = formData.getFirst("bookId");
        Optional<Book> optionalBook = bookRepository.findById(bookId);
        if (!optionalBook.isPresent()) {
            return new ModelAndView("redirect:/");
        }
        Book book = optionalBook.get();

        UserBooks userBooks  = new UserBooks();
        UserBooksPrimaryKey key = new UserBooksPrimaryKey();
        String userId = principal.getAttribute("login");
        key.setUserId(userId);
        key.setBookId(bookId);

        userBooks.setKey(key);

        int rating = Integer.parseInt(formData.getFirst("rating"));

        DateTimeFormatter dateFormatter = DateTimeFormatter.ISO_DATE;
        DateValidator validator = new DateValidator(dateFormatter);
        String startDate = formData.getFirst("startDate");
        String completedDate = formData.getFirst("completedDate");

        if (validator.completedDateIsGreaterThanStartDate(startDate, completedDate)) {
            if (validator.isValid(startDate)) {
                userBooks.setStartedDate(LocalDate.parse(startDate));
            }
            if (validator.isValid(completedDate)) {
                userBooks.setCompletedDate(LocalDate.parse(completedDate));
            }
        }
        userBooks.setRating(rating);
        userBooks.setReadingStatus(formData.getFirst("readingStatus"));

        userBooksRepository.save(userBooks);

        BooksByUser booksByUser = new BooksByUser();
        booksByUser.setId(userId);
        booksByUser.setBookId(bookId);
        booksByUser.setBookName(book.getName());
        booksByUser.setCoverIds(book.getCoverIds());
        booksByUser.setAuthorNames(book.getAuthorNames());
        booksByUser.setReadingStatus(formData.getFirst("readingStatus"));
        booksByUser.setRating(rating);
        booksByUserRepository.save(booksByUser);


        return new ModelAndView("redirect:/books/" + bookId);
        
    }
}
