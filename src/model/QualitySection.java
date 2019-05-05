package model;

public class QualitySection extends AbstractSection {
    private String body;

    public QualitySection(String body) {
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
        if (!(o instanceof QualitySection)) return false;

        QualitySection that = (QualitySection) o;

        return body.equals(that.body);

    }

    @Override
    public int hashCode() {
        return body.hashCode();
    }
}
