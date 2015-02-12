function className(obj) {
    if (obj===null) return 'ceylon.language::Null';
    if (obj===undefined) return "JavaScript UNDEFINED";
    if (obj===true) return 'ceylon.language::true';
    if (obj===false) return 'ceylon.language::false';
    function _typename(t) {
        if (t.t==='i' || t.t==='u') {
            var _sep = t.t==='i'?'&':'|';
            var ct = '';
            for (var i=0; i < t.l.length; i++) {
                if (i>0) { ct+=_sep; }
                ct += _typename(t.l[i]);
            }
            return ct;
        } else {
            var tn = t.t.$$.T$name;
            if (t.a) {
                tn += '<';
                for (var i = 0; i < t.a.length; i++) {
                    if (i>0) { tn += ','; }
                    tn += _typename(t.a[i]);
                }
                tn += '>';
            }
            return tn;
        }
    }
    var tn = obj.getT$name && obj.getT$name();
    if (!tn) {
        if (typeof obj === 'function') {
            tn = 'ceylon.language::Callable';
        } else {
            tn = 'UNKNOWN';
        }
    }
    /*else if (obj.$$targs$$) {
        tn += '<';
        for (var i=0; i < obj.$$targs$$.length; i++) {
            if (i>0) { tn += ','; }
            tn += _typename(obj.$$targs$$[i]);
        }
        tn += '>';
    }*/
    return tn;
}
