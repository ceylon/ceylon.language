void testProcess() {
    // basically just test if everything can be called without error
    Anything args = process.arguments;
    if (is Anything[] args) {
        for (arg in args) {
            check(arg is String, "process.arguments");
        }
    } else {
        fail("process.arguments is no sequence");
    }
    Anything argPresent = process.namedArgumentPresent("");
    check(argPresent is Boolean, "process.namedArgumentPresent");
    check(!process.namedArgumentValue("") exists, "process.namedArgumentValue");
    String? filesep = operatingSystem.pathSeparator;
    if (exists filesep) {
        check((filesep=="/")||(filesep=="\\"), "operatingSystem.pathSeparator");
    } else {
        fail("process.propertyValue (null)");
    }
    check(operatingSystem.newline.contains('\n'), "operatingSystem.newline");
    process.write("write");
    process.writeLine(" and writeLine");
    process.flush();
    process.writeError("writeError");
    process.writeErrorLine(" and writeErrorLine");
    process.flushError();
    print("Process VM ``machine.name`` version ``machine.version`` on ``operatingSystem.name`` v``operatingSystem.version``");
    check(system.milliseconds > 0, "machine.milliseconds");
    check(system.nanoseconds > 0, "machine.milliseconds");

    //language object
    check(language.version=="0.6.1", "language.version");
    check(language.majorVersion==0, "language.majorVersion");
    check(language.minorVersion==6, "language.minorVersion");
    check(language.releaseVersion==1, "language.releaseVersion");
    check(!language.versionName.empty, "language.versionName");
    check(language.majorVersionBinary==5, "language.majorVersionBinary");
    check(language.minorVersionBinary==0, "language.minorVersionBinary");
    print("Ceylon language version ``language.version`` major ``language.majorVersion`` " +
        "minor ``language.minorVersion`` release ``language.releaseVersion`` " +
        "\'``language.versionName``\' major bin ``language.majorVersionBinary`` minor bin " +
        "``language.minorVersionBinary``");
    check(process.string == "process", "process.string");
    check(language.string == "language", "language.string");
}
