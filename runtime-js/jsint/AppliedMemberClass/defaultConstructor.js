if (this.$defcons$===undefined) {
  var mm=getrtmm$$(this.tipo);
  //Member Type Name
  var mtn=mm.d[mm.d.length-1];
  if (mtn.indexOf('$')>0)mtn=mtn.substring(0,mtn.indexOf('$'));
  var startingType=this.container;
  while (is$(startingType,{t:Class$meta$model})) {
    var pn=startingType.declaration.name;
    mtn+='$'+startingType.declaration.name;
    startingType=startingType.container;
  }
  var fn=mtn+'_$c$';
  var cn=this.tipo[fn];
  if (cn) {
    mm=getrtmm$$(cn).ps;
    var args=tupleize$params(getrtmm$$(cn).ps,this.$$targs$$.Target$Type.a);
    var r=AppliedMemberClassCallableConstructor$jsint(cn,
          {Type$AppliedMemberClassCallableConstructor:this.$$targs$$.Type$AppliedClass,
           Container$AppliedMemberClassCallableConstructor:this.$$targs$$.Container$AppliedClass,
           Arguments$AppliedMemberClassCallableConstructor:args},undefined,this.$targs);
    r.cont$=this;
    this.$defcons$=r;
    return r;
  }
  this.$defcons$=null;
}
return this.$defcons$;
