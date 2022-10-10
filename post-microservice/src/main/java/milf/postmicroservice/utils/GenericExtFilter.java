package milf.postmicroservice.utils;

public class GenericExtFilter {
    private final String[] exts;

    public GenericExtFilter(String... exts) {
        this.exts = exts;
    }


    public boolean accept(String name) {
        for (String ext : exts) {
            if (name.endsWith(ext)) {
                return true;
            }
        }

        return false;
    }
}
