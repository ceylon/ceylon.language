package ceylon.language.descriptor;

import com.redhat.ceylon.compiler.metadata.java.Name;

public class Import {
    private ceylon.language.Quoted name;
    private ceylon.language.Quoted version;
    private boolean optional;
    private boolean export;
    private boolean onDemand;
    private PathFilter exports;
    private PathFilter imports;

    public Import(@Name("name") ceylon.language.Quoted name,
            @Name("version") ceylon.language.Quoted version, 
            @Name("optional") boolean optional,
            @Name("export") boolean export) {
        this(name, version, optional, export, false, PathFilters.rejectAll(), PathFilters.acceptAll());
    }

    public Import(@Name("name") ceylon.language.Quoted name,
            @Name("version") ceylon.language.Quoted version,
            @Name("optional") boolean optional,
            @Name("export") boolean export,
            @Name("onDemand") boolean onDemand,
            @Name("exports") PathFilter exports,
            @Name("imports") PathFilter imports) {
        this.name = name;
        this.version = version;
        this.optional = optional;
        this.export = export;
        this.onDemand = onDemand;
        this.exports = exports;
        this.imports = imports;
    }

    public ceylon.language.Quoted getName() {
        return name;
    }

    public ceylon.language.Quoted getVersion() {
        return version;
    }

    public boolean getOptional() {
        return optional;
    }

    public boolean getExport() {
        return export;
    }

    public boolean getOnDemand() {
        return onDemand;
    }

    public PathFilter getExports() {
        return exports;
    }

    public PathFilter getImports() {
        return imports;
    }
}
