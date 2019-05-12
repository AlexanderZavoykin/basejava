package model;

import java.util.Objects;

public class TextSection extends AbstractSection {
    private String body;

    public TextSection(String body) {
        Objects.requireNonNull(body, "Content must not be null");
        this.body = body;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TextSection)) return false;

        TextSection that = (TextSection) o;

        return body.equals(that.body);

    }

    @Override
    public int hashCode() {
        return body.hashCode();
    }

    @Override
    public String toString() {
        return body;
    }
}
