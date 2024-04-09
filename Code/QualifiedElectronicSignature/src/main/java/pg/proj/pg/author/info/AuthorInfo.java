package pg.proj.pg.author.info;

public record AuthorInfo(String name) {
    @Override
    public boolean equals(Object obj) {
        if(obj == this) {
            return true;
        }

        if(!(obj instanceof AuthorInfo other)) {
            return false;
        }

        return other.name.equals(name);
    }
}
