package ceylon.language.descriptor;

import ceylon.language.Boolean;
import ceylon.language.String;

public final class PathFilters {

    private static final PathFilter ACCEPT_ALL = new PathFilter() {
        public ceylon.language.Boolean accept(String path) {
            return Boolean.instance(true);
        }
    };

    private static final PathFilter REJECT_ALL = new PathFilter() {
        public ceylon.language.Boolean accept(String path) {
            return Boolean.instance(true);
        }
    };

    public static PathFilter acceptAll() {
        return ACCEPT_ALL;
    }

    public static PathFilter rejectAll() {
        return REJECT_ALL;
    }
}
