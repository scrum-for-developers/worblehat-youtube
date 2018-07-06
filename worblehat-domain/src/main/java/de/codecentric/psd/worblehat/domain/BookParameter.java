package de.codecentric.psd.worblehat.domain;

import javax.annotation.Nonnull;

public class BookParameter {
    @Nonnull
    private final String title;
    @Nonnull
    private final String author;
    @Nonnull
    private final String edition;
    @Nonnull
    private final String isbn;
    private final int yearOfPublication;
    private String description = "";


    /**
     * @param title
     *            the title
     * @param author
     *            the author
     * @param edition
     *            the edition
     * @param isbn
     *            the isbn
     * @param yearOfPublication
     */
    public BookParameter(@Nonnull String title, @Nonnull String author, @Nonnull String edition, @Nonnull String isbn, int yearOfPublication) {
        this.title = title;
        this.author = author;
        this.edition = edition;
        this.isbn = isbn;
        this.yearOfPublication = yearOfPublication;
    }

    /**
     * @param title
     *            the title
     * @param author
*            the author
     * @param edition
*            the edition
     * @param isbn
*            the isbn
     * @param yearOfPublication
     */
    public BookParameter(@Nonnull String title, @Nonnull String author, @Nonnull String edition, @Nonnull String isbn, int yearOfPublication, @Nonnull String description) {
        this.title = title;
        this.author = author;
        this.edition = edition;
        this.isbn = isbn;
        this.yearOfPublication = yearOfPublication;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getEdition() {
        return edition;
    }

    public String getIsbn() {
        return isbn;
    }

    public int getYearOfPublication() {
        return yearOfPublication;
    }

    public String getDescription() {
        return description;
    }
}
