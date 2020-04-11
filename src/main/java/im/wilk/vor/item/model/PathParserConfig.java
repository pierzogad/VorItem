package im.wilk.vor.item.model;

import lombok.Data;

@Data
public class PathParserConfig {

    public static final char NO_INDEX_CLOSE = (char) 0;
    private final char partSeparator;
    private final char indexOpen;
    private final char indexClose;
    private final char aliasSeparator;

    public PathParserConfig(char partSeparator, char aliasSeparator, char indexOpen, char indexClose) {

        if (partSeparator == indexOpen || partSeparator == indexClose
                || partSeparator == aliasSeparator || indexOpen == indexClose
                || indexOpen == aliasSeparator || indexClose == aliasSeparator) {
            throw new IllegalArgumentException("Each separator must be different");
        }

        this.partSeparator = partSeparator;
        this.indexOpen = indexOpen;
        this.indexClose = indexClose;
        this.aliasSeparator = aliasSeparator;
    }

    public PathParserConfig(char partSeparator, char aliasSeparator, char indexOpen) {
        this(partSeparator, aliasSeparator, indexOpen, NO_INDEX_CLOSE);
    }

    public static PathParserConfig standard() {
        return pathWithSlashAndColon();
    }

    public static PathParserConfig pathWithSlashAndColon() {
        return new PathParserConfig('/', '=', ':');
    }

    public static PathParserConfig pathWithDotAndBrackets() {
        return new PathParserConfig('.', '=', '[', ']');
    }
}
