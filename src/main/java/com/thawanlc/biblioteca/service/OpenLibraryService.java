package com.thawanlc.biblioteca.service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;



@Service
public class OpenLibraryService {
    
    private final RestTemplate restTemplate;

    public OpenLibraryService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public boolean isbnExiste(String isbn) {
        String url = "https://openlibrary.org/isbn/" + isbn + ".json";
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            return response.getStatusCode().is2xxSuccessful();
        } catch (Exception e) {
            return false;
        }
    }

    public static class OpenLibraryResponse {
        private String title;
        private List<AuthorRef> authors;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public List<AuthorRef> getAuthors() {
            return authors;
        }

        public void setAuthors(List<AuthorRef> authors) {
            this.authors = authors;
        }
    }

    public OpenLibraryResponse getBookDetails(String isbn) {
    String url = "https://openlibrary.org/isbn/" + isbn + ".json";

    try {
        ResponseEntity<OpenLibraryResponse> response = restTemplate.getForEntity(url, OpenLibraryResponse.class);

        return response.getStatusCode().is2xxSuccessful() ? response.getBody() : null;

    } catch (Exception e) {
        throw new RuntimeException("Erro ao buscar dados da Open Library", e);
        }
    }   

    public static class AuthorResponse {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

    public static class AuthorRef {
        private String key;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }
    }

    public String getAuthorName(OpenLibraryResponse book) {

    if (book == null ||
        book.getAuthors() == null ||
        book.getAuthors().isEmpty()) {

        return "Autor desconhecido";
    }

    String authorKey = book.getAuthors().get(0).getKey();
    String url = "https://openlibrary.org" + authorKey + ".json";

    try {
        ResponseEntity<AuthorResponse> response = restTemplate.getForEntity(url, AuthorResponse.class);

        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            return response.getBody().getName();
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return "Autor desconhecido";
}

}
