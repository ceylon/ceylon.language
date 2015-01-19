//internal
function tpl$(elems,types,spread){
  if (spread!==undefined) {
    if (is$(spread,{t:Sequence})) {
    } else {
      var e;
      for (var iter=spread.iterator();(e=iter.next())!==finished();) {
        elems.push(e);
      }
      types=undefined;
      spread=undefined;
    }
  }
  if (elems.size===0&&(spread===undefined||spread.size===0))return getEmpty();
  if (types===undefined || types.t!=='T') {
    types=[];
    for (var i=0; i < elems.size; i++){
      if (elems[i]===null) {
        types.push({t:Null});
      } else if (elems[i].getT$all && elems[i].getT$name) {
        var _et={t:elems[i].getT$all()[elems[i].getT$name()]};
        if (elems[i].$$targs$$)_et.a=elems[i].$$targs$$;
        types.push(_et);
      } else {
        console.log("Tuple: WTF do I use for the type of " + elems[i]);
        types.push({t:Anything});
      }
    }
    //Check if I need to do this
    //if (spread && spread.$$targs$$) {
    //  types.push(spread.$$targs$$.Element$Sequence);
    //}
    types={t:'T',l:types};
  }
  $init$Tuple();
  var that=new Tuple.$$;
  that.$$targs$$=types;
  $_Object(that);
  var _t=types.l.length===1?types.l[0]:{t:'u',l:types.l};
  Sequence({Element$Sequence:_t},that);
  elems.$$targs$$={Element$Tuple:_t,First$Tuple:types.l[0],Element$Iterable:_t,
    Element$Sequential:_t,Element$List:_t,Element$Array:_t,Element$Sequence:_t,Absent$Iterable:{t:Nothing},
    Element$Collection:_t,Key$Correspondence:{t:Integer},Item$Correspondence:_t};
  set_type_args(that,elems.$$targs$$,Tuple);
  that.$$targs$$.Element$Tuple=_t;
  that.$$targs$$.First$Tuple=types.l[0];
  that.$$targs$$.Rest$Tuple=types.l.length===1?{t:Empty}:{t:Sequence,a:_t};
  that.first_=elems[0];
  that.getFromFirst=function(i){
    if (spread && i>=elems.length) {
      return spread.getFromFirst(i-elems.length);
    }
    var e=elems[i];
    return e===undefined?null:e;
  };
  that.getFromFirst.$crtmm$=Tuple.$$.prototype.getFromFirst.$crtmm$;
  that.iterator=function(){
    if (spread) {
      return ChainedIterator(elems,spread,{Element$ChainedIterator:types,Other$ChainedIterator:spread.$$targs$$.Element$Sequence});
    }
    return elems.iterator();
  }
  that.iterator.$crtmm$=Tuple.$$.prototype.iterator.$crtmm$;
  that.contains=function(i) { return elems.contains(i) || (spread&&spread.contains(i)); }
  that.contains.$crtmm$=Tuple.$$.prototype.contains.$crtmm$;
  that.withLeading=function(a,b){
    var e2 = elems.slice(0); e2.unshift(a);
    var t2 = types.l.slice(0); t2.unshift(b.Other$withLeading);
    return tpl$(e2,{t:'T',l:t2},spread);
  }
  that.withLeading.$crtmm$=Tuple.$$.prototype.withLeading.$crtmm$;
  that.span=function(a,b){//from,to
    if (spread) {
      if (a>=elems.length&&b>=elems.length){
        return spread.span(a-elems.length,b-elems.length);
      }
      if (b>=elems.length) {
        var s1=elems.spanFrom(a);
        var s2=spread.spanTo(b-elems.length);
        return s1.chain(s2,{Other$chain:spread.$$targs$$.Element$Sequence,OtherAbsent$chain:{t:Nothing}}).sequence();
      }
      if (a>=elems.length) {
        var s1=spread.span(a-elems.length,0);
        var s2=elems.span(elems.length-1,b)
        return s1.chain(s2,{Other$chain:types,OtherAbsent$chain:{t:Nothing}}).sequence();
      }
    }
    var r=elems.span(a,b);
    return r.size===0?getEmpty():ArraySequence(r,{Element$ArraySequence:_t});
  }
  that.span.$crtmm$=Tuple.$$.prototype.span.$crtmm$;
  that.spanTo=function(x){
    if (spread) {
      if (x<0)return getEmpty();
      return this.span(0,x);
    }
    var r=elems.spanTo(x);
    return r.size===0?getEmpty():ArraySequence(r,{Element$ArraySequence:_t});
  }
  that.spanTo.$crtmm$=Tuple.$$.prototype.spanTo.$crtmm$;
  that.spanFrom=function(x){
    if (spread) {
      if (x>=elems.length) {
        return spread.spanFrom(x-elems.length);
      } else if (x<0) {
        return this;
      }
      return elems.spanFrom(x).chain(spread,{Other$chain:spread.$$targs$$.Element$Sequence}).sequence();
    }
    var r=elems.spanFrom(x);
    return r.size===0?getEmpty():ArraySequence(r,{Element$ArraySequence:_t});
  }
  that.spanFrom.$crtmm$=Tuple.$$.prototype.spanFrom.$crtmm$;
  that.measure=function(a,b){//from,length
    if(b===0)return getEmpty();
    if (spread) {
      if (a>=elems.length) {
          var m1=spread.measure(a-elems.length,b);
        if (b>0) {
          return m1;
        } else {
          console.log("missing tpl.measure with negative length");
        }
      }
      if (b>0 && a+b-1>=elems.length) {
        var m1=elems.measure(a,elems.length-a);
        var m2=spread.measure(0,b-m1.size);
        return m1.chain(m2,{Other$chain:spread.$$targs$$.Element$Sequence}).sequence();
      }
    }
    var r=elems.measure(a,b);
    return r.size===0?getEmpty():ArraySequence(r,{Element$ArraySequence:_t});
  }
  that.measure.$crtmm$=Tuple.$$.prototype.measure.$crtmm$;
  that.equals=function(o){
    if (spread) {
      if (!is$(o,{t:Sequential}))return false;
      if (!o.size===this.size)return false;
      var oi=o.iterator();
      var ot=this.iterator();
      var ni=oi.next();
      var nt=ot.next();
      while (ni!==finished()) {
        if (!ni.equals(nt))return false;
        ni=oi.next();
        nt=ot.next();
      }
      return true;
    }
    return elems.equals(o);
  }
  that.equals.$crtmm$=List.$$.prototype.equals.$crtmm$;
  that.withTrailing=function(a,b){
    if (spread) {
      return tpl$(elems,types,spread.withTrailing(a,b));
    }
    var e2=elems.slice(0);e2.push(a);
    var t2=types.l.slice(0);t2.push(b.Other$withTrailing);
    return tpl$(e2,{t:'T',l:t2});
  }
  that.withTrailing.$crtmm$=Sequential.$$.prototype.withTrailing.$crtmm$;
  that.chain=function(a,b){return elems.chain(a,b);}
  that.chain.$crtmm$=Iterable.$$.prototype.chain.$crtmm$;
  that.longerThan=function(i){return elems.longerThan(i);}
  that.longerThan.$crtmm$=Iterable.$$.prototype.longerThan.$crtmm$;
  that.shorterThan=function(i){return elems.shorterThan(i);}
  that.shorterThan.$crtmm$=Iterable.$$.prototype.shorterThan.$crtmm$;
  atr$(that,'hash',function(){
    return elems.hash+(spread?spread.hash:0);
  },undefined,List.$$.prototype.$prop$getHash.$crtmm$);
  atr$(that,'rest',function(){
    return elems.size===1?spread||getEmpty():tpl$(elems.slice(1),{t:'T',l:types.l.slice(1)},spread);
  },undefined,Tuple.$$.prototype.$prop$getRest.$crtmm$);
  atr$(that,'size',function(){
    return elems.size+(spread?spread.size:0);
  },undefined,Tuple.$$.prototype.$prop$getSize.$crtmm$);
  atr$(that,'lastIndex',function(){
    return elems.size-1+(spread?spread.size:0);
  },undefined,Tuple.$$.prototype.$prop$getLastIndex.$crtmm$);
  atr$(that,'last',function(){
    return spread?spread.last:elems[elems.size-1];
  },undefined,Tuple.$$.prototype.$prop$getLast.$crtmm$);
  atr$(that,'string',function(){
    return '['+commaList(elems)+(spread?', '+commaList(spread):'')+']';
  },undefined,Tuple.$$.prototype.$prop$getString.$crtmm$);
  that.nativeArray=function(){
    if (spread) {
      var e=new Array(elems.length+spread.size);
      for (var i=0;i<elems.length;i++) {
        e[i]=elems[i];
      }
      var elem;for(var iter=spread.iterator();(elem=iter.next())!==finished();) {
        e[i++]=elem;
      }
      return e;
    }
    return elems;
  }
  return that;
}
ex$.tpl$=tpl$;
