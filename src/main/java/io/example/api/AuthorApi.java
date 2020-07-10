package io.example.api;

import io.example.domain.dto.AuthorView;
import io.example.domain.dto.EditAuthorRequest;
import io.example.domain.mapper.AuthorEditMapper;
import io.example.domain.mapper.AuthorViewMapper;
import io.example.domain.model.Author;
import io.example.service.AuthorService;
import io.swagger.annotations.Api;
import org.bson.types.ObjectId;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "Author")
@RestController @RequestMapping(path = "api/author")
public class AuthorApi {

    private final AuthorEditMapper authorEditMapper;
    private final AuthorViewMapper authorViewMapper;
    private final AuthorService authorService;

    public AuthorApi(AuthorEditMapper authorEditMapper,
                     AuthorViewMapper authorViewMapper,
                     AuthorService authorService) {
        this.authorEditMapper = authorEditMapper;
        this.authorViewMapper = authorViewMapper;
        this.authorService = authorService;
    }

    @PostMapping
    public AuthorView createAuthor(@RequestBody EditAuthorRequest request) {
        Author author = authorEditMapper.create(request);

        author = authorService.save(author);

        return authorViewMapper.toAuthorView(author);
    }

    @PutMapping("{id}")
    public AuthorView editAuthor(@PathVariable String id, @RequestBody EditAuthorRequest request) {
        Author author = authorService.getAuthor(new ObjectId(id));
        authorEditMapper.update(request, author);

        author = authorService.save(author);

        return authorViewMapper.toAuthorView(author);
    }

    @GetMapping("{id}")
    public AuthorView getAuthor(@PathVariable String id) {
        Author author = authorService.getAuthor(new ObjectId(id));
        return authorViewMapper.toAuthorView(author);
    }

}
