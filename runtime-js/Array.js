function $_Array(elems,$$targs$$) {
    $init$$_Array();
    var e=[];
    if (!(elems === null || elems === undefined)) {
        var item;for(var iter=elems.iterator();(item=iter.next())!==finished();) {
            e.push(item);
        }
    }
    var t=$$targs$$.Element$Array;
    List({Element$List:t}, e);
    return e.rt$(t);
}
$_Array.deser$$=function(a,cm){
  var targ=cm.$$targs$$.Type$Class.a.Element$Array;
  var tam=a.getValue(OpenValue$jsint(lmp$(ex$,'$'),$_Array.$$.prototype.$prop$getSize),{Instance$getValue:{t:Integer}});
  var b = new Array(tam);
  for (var i=0; i < tam; i++) {
    b[i]=a.getElement(i,{Instance$getElement:targ});
  }
  return b.rt$(targ);
}
