var arrprot$=Array.prototype;
arrprot$.ser$$=function(d){
  var targ=this._elemTarg();
  d=d(AppliedClass$jsint($_Array,{Type$AppliedClass:{t:$_Array,a:{Element$Array:targ}},
    Arguments$AppliedClass:{t:'T',l:[{t:Iterable,a:{Element$Iterable:targ}}]}}));
  d.putTypeArgument(OpenTypeParam$jsint($_Array,'Element$Array'),typeLiteral$meta({Type$typeLiteral:targ}));
  d.putValue(OpenValue$jsint(lmp$(ex$,'$'),this.$prop$getSize),this.length,{Instance$putValue:{t:Integer}});
  for (var i=0; i < this.length; i++) {
    d.putElement(i,this[i],{Instance$putElement:targ});
  }
}
var origArrToString = arrprot$.toString;
arrprot$.toString = origArrToString;
arrprot$.rt$=function(t,ne) {
    if (t===null || t===undefined)t={t:Anything};
    if (this.$$targs$$===undefined)this.$$targs$$=={};
    add_type_arg(this,'Element$Iterable',t);
    add_type_arg(this,'Element$Array',t);
    add_type_arg(this,'Element$List',t);
    add_type_arg(this,'Element$Sequential',t);
    add_type_arg(this,'Absent$Iterable',ne?{t:Nothing}:{t:Null});
    return this;
}
arrprot$._elemTarg=function(){
  var t = this.$$targs$$ && this.$$targs$$.Element$Array;
  if (t===undefined)t = this.$$targs$$ && this.$$targs$$.Element$Iterable;
  if (t===undefined)t = this.$$targs$$ && this.$$targs$$.Element$List;
  if (t===undefined)t = {t:Anything};
  if (this.$$targs$$===undefined)this.$$targs$$={
    Element$Array:t, Element$List:t, Element$Iterable:t,
    Element$Collection:t, Item$Correspondence:t,Element$Ranged:t,
    Absent$Iterable:Null, Key$Correspondence:{t:Integer},
    Index$Ranged:{t:Integer}, Subrange$Ranged:{t:$_Array,a:{Element$Array:'Element$Array'}}
  };
  return t;
}
arrprot$.getT$name = function() {
    return $_Array.$$.T$name;
}
arrprot$.getT$all = function() {
    return $_Array.$$.T$all;
}

atr$(arrprot$,'reversed', function() {
  this._elemTarg();
  return this.Reversed$List();
},undefined,List.$$.prototype.$prop$getReversed.$crtmm$);
atr$(arrprot$,'rest', function() {
  this._elemTarg();
  return this.Rest$List(1);
},undefined,List.$$.prototype.$prop$getRest.$crtmm$);

//for sequenced enumerations
function sarg$(elems,spread,$$targs$$){
  $init$sarg();
  that=new sarg$.$$;
  Iterable($$targs$$,that);
  that.e=elems;
  that.s=spread;
  return that;
}
sarg$.$crtmm$=function(){return{mod:$CCMM$,'super':{t:Basic},ps:[],tp:{T$LazyIterable:{dv:'out'}},sts:[{t:Iterable,a:{Element$Iterable:'T$LazyIterable',Absent$Iterable:{t:Null}}}],pa:1,d:['$','LazyIterable']};};
ex$.sarg$=sarg$;
function $init$sarg(){if(sarg$.$$===undefined){
  initTypeProto(sarg$,'ceylon.language::LazyIterable',Basic,Iterable);
  (function(that){
    that.iterator=function (){
      var sarg=this;
      //ObjectDef it at caca.ceylon (13:4-15:4)
      function iter($$targs$$){
        var $$4=new iter.$$;
        $$4.outer$=sarg;
        $$4.$$targs$$=$$targs$$;
        Iterator({Element$Iterator:sarg.$$targs$$.Element$Iterable},$$4);
        $$4.i=0;
        return $$4;
      };iter.$crtmm$=function(){return{mod:$CCMM$,'super':{t:Basic},$cont:iterator,sts:[{t:Iterator,a:{Element$Iterator:'T$LazyIterable'}}],d:['$','LazyIterable','$m','iterator','$o','it']};};
      if(iter.$$===undefined){
        initTypeProto(iter,'LazyIterable.it',Basic,Iterator);
        iter.$$.prototype.next=function(){
          if (this.sp)return this.sp.next();
          var e=sarg.e(this.i);
          if (e===finished() && sarg.s) {
            this.sp=sarg.s().iterator();
            e=this.sp.next();
          } else {
            this.i++;
          }
          return e;
        };
        iter.$$.prototype.next.$crtmm$=function(){return{mod:$CCMM$,$t:{t:'u',l:['T$LazyIterable',{t:Finished}]},ps:[],$cont:iter,an:function(){return[shared(),actual()];},d:['$','LazyIterable','$m','iterator','$o','it','$m','next']};};
      }
      return iter({Element$Iterator:sarg.$$targs$$.T$LazyIterable});
    };
    that.iterator.$crtmm$=function(){return{mod:$CCMM$,$t:{t:Iterator,a:{Element$Iterator:'T$LazyIterable'}},ps:[],$cont:$sarg,an:function(){return[shared(),actual()];},d:['$','LazyIterable','$m','iterator']};};
  })(sarg$.$$.prototype);
}
return sarg$;
}
$init$sarg();
ex$.$init$sarg=$init$sarg;
$init$sarg();
//Turn a comprehension into a native array
function nfor$(c) {
  var a = [];
  var item;
  for (var iter = c.iterator();(item=iter.next())!==finished();){
    a.push(item);
  }
  return a;
}
ex$.nfor$=nfor$;
