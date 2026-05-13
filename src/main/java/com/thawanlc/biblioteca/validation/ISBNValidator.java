package com.thawanlc.biblioteca.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ISBNValidator implements ConstraintValidator<ISBN, String> {
    
    @Override
    public boolean isValid(String isbn, ConstraintValidatorContext context) {

        if (isbn == null) {
            return false;
        }

        isbn = isbn.replace("-", "").replace(" ", "");

        if (isbn.length() == 10) {
            return validarISBN10(isbn);
        }

        if (isbn.length() == 13) {
            return validarISBN13(isbn);
        }

        return false;
    }

    private boolean validarISBN10(String isbn) {

        int soma = 0;

        for (int i = 0; i < 9; i++) {

            if (!Character.isDigit(isbn.charAt(i))) {
                return false;
            }

            soma += (isbn.charAt(i) - '0') * (10 - i);
        }

        char ultimo = isbn.charAt(9);

        if (ultimo == 'X') {
            soma += 10;
        } else if (Character.isDigit(ultimo)) {
            soma += ultimo - '0';
        } else {
            return false;
        }

        return soma % 11 == 0;
    }

    private boolean validarISBN13(String isbn) {

        if (!isbn.matches("\\d{13}")) {
            return false;
        }

        int soma = 0;

        for (int i = 0; i < 12; i++) {

            int numero = isbn.charAt(i) - '0';

            soma += (i % 2 == 0) ? numero : numero * 3;
        }

        int digito = (10 - (soma % 10)) % 10;

        return digito == (isbn.charAt(12) - '0');
    }
}
