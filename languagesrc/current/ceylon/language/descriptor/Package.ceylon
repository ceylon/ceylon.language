import ceylon.language { desc = doc, exported=shared }

desc "A package descriptor."
exported class Package(

        desc "The name of the package."
        Quoted name,

        desc "The visibility of the package."
        Boolean shared = false,

        desc "A description of the package."
        String doc="") {

    //TODO implement

}