package pg.proj.pg.author;

public record AuthorInfo(String name) {
    @Override
    public boolean equals(Object obj) {
        if(obj == this) {
            return true;
        }

        if(!(obj instanceof AuthorInfo(String name1))) {
            return false;
        }

        return name1.equals(name);
    }
}
