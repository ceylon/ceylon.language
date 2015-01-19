if (this.argv!==undefined)return this.argv;
this.argv = empty();
this.namedArgs = {};
if ((typeof process !== "undefined") && (process.argv !== undefined)) {
    // parse command line arguments
    if (process.argv.length > 2) {
        // Ignore the first two arguments (see https://github.com/ceylon/ceylon.language/issues/503)
        var args = process.argv.slice(2);
        for (var i=0; i<args.length; ++i) {
            var arg = args[i];
            if (arg.charAt(0) == '-') {
                var pos = 1;
                if (arg.charAt(1) == '-') { pos = 2; }
                arg = arg.substr(pos);
                pos = arg.indexOf('=');
                if (pos >= 0) {
                    this.namedArgs[arg.substr(0, pos)] = arg.substr(pos+1);
                } else {
                    var value = args[i+1];
                    if ((value !== undefined) && (value.charAt(0) != '-')) {
                        this.namedArgs[arg] = value;
                        ++i;
                    } else {
                        this.namedArgs[arg] = null;
                    }
                }
            }
            args[i] = $_String(args[i]);
        }
        this.argv = args.length===0?empty():ArraySequence(args, {Element$ArraySequence:{t:$_String}});
    }
} else if (typeof window !== "undefined") {
    // parse URL parameters
    var parts = window.location.search.substr(1).replace('+', ' ').split('&');
    if ((parts.length > 1) || ((parts.length > 0) && (parts[0].length > 0))) {
        var argStrings = new Array(parts.length);
        //can't do "for (i in parts)" anymore because of the added stuff to arrays
        var i;
        for (i=0; i<parts.length; i++) { argStrings[i] = parts[i]; }
        this.argv = parts.length===0?empty():ArraySequence(argStrings, {Element$ArraySequence:{t:$_String}});
        
        for (i=0; i < parts.length; i++) {
            var part = parts[i];
            var pos = part.indexOf('=');
            if (pos >= 0) {
                var value = decodeURIComponent(part.substr(pos+1));
                this.namedArgs[part.substr(0, pos)] = value;
            } else {
                this.namedArgs[part] = null;
            }
        }
    }
}
return this.argv;
