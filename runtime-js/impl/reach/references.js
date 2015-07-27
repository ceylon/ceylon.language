function references(instance){
  var index=-1;
  var refs = instance.ser$refs$();
  return for$iter(function(){
    index++;
    if (refs.length>index) {
      return refs[index];
    } else if (is$(instance,{t:$_Array}) && index<instance.size) {
      return ElementImpl$impl(index);
    }
    return finished();
  },{Element$Iterator:{t:ReachableReference$serialization}});
}
