function references(o){
  var index=-1;
  var refs = o.getT$all()[o.getT$name()].ser$refs$(o);
  return for$iter(function(){
    index++;
    if (refs.length>index) {
      return refs[index];
    } else if (is$(o,{t:$_Array}) && index<o.size) {
      return ElementImpl$impl(index);
    }
    return finished();
  },{Element$Iterator:{t:ReachableReference$serialization}});
}
