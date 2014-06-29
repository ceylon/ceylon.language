//Addendum to ClassOrInterface
atr$(ClassOrInterface$meta$model.$$.prototype,'satisfiedTypes',function(){
  var ints = this.tipo.$crtmm$['satisfies'];
  if (ints && ints.length) {
    var rv = [];
    function resolveTypeArguments(root,type) {
      if (type.a) {
        var t2 = {t:type.t, a:{}};
        for (var targ in type.a) {
          t2.a[targ]=typeof(type.a[targ])==='string' ?
            t2.a[targ]=root.$$targs$$.Type$ClassOrInterface.a[type.a[targ]]
            : t2.a[targ]=type.a[targ];
          if (t2.a[targ] && t2.a[targ].a) {
            t2.a[targ]=resolveTypeArguments(root,t2.a[targ]);
          }
        }
        type=t2;
      }
      return type;
    }
    for (var i=0; i < ints.length; i++) {
      var ifc = resolveTypeArguments(this,ints[i]);
      var mm=getrtmm$$(ifc.t);
      if (mm.$cont) {
        rv.push(AppliedMemberInterface(ifc.t, {Type$MemberInterface:ifc}));
      } else {
        rv.push(AppliedInterface(ifc.t, {Type$Interface:ifc}));
      }
    }
    return rv.reifyCeylonType({t:InterfaceModel$meta$model,a:{Type$InterfaceModel:{t:Anything}}});
  }
  return getEmpty();
},undefined,function(){
  return{mod:$CCMM$,$t:{t:Sequential,a:{Element$Iterable:{t:InterfaceModel$meta$model,a:{Type:{t:Anything}}}}},$cont:ClassOrInterface$meta$model,
  an:function(){return[shared(),formal()];},d:['ceylon.language.meta.model','ClassOrInterface','$at','satisfiedTypes']};
});
ClassOrInterface$meta$model.$$.prototype.getMethod=function(name,types,$$$mptypes) {
  if (!extendsType($$$mptypes.Container$getMethod,{t:this.tipo}))throw IncompatibleTypeException$meta$model("Incompatible Container type argument");
  var fun = this.tipo[name];
  if (!fun) fun = this.tipo.$$.prototype[name];
  if (!fun) return null;
  var mm=getrtmm$$(fun);
  var _t=$$$mptypes.Type$getMethod;
  var _a=$$$mptypes.Arguments$getMethod;
  if (mm) {
    if (mm.$t){
      _t=mm.$t;
      if (typeof(_t)==='string') {
        var _ta;
        if (this.$targs && this.$targs[_t])_t=this.$targs[_t];
        else if ($$$mptypes.Container$getMethod && $$$mptypes.Container$getMethod.a && $$$mptypes.Container$getMethod.a[_t])_t=$$$mptypes.Container$getMethod.a[_t];
        else if (mm.tp && mm.tp[_t])_ta=mm.tp[_t];
        else if (this.tipo.$crtmm$.tp && this.tipo.$crtmm$.tp[_t])_ta=this.tipo.$crtmm$.tp[_t];
        if (_ta && _ta.satisfies)_t=_ta.satisfies.length===1?_ta.satisfies[0]:{t:'i',l:_ta.satisfies};
        if (typeof(_t)==='string')_t={t:Anything};
      }
      if (!extendsType(_t,$$$mptypes.Type$getMethod))throw IncompatibleTypeException$meta$model("Incompatible Type argument");
    }
    validate$params(mm.ps,_a,"Wrong number of Arguments for getMethod");
    _a=tupleize$params(mm.ps);
  }
  return AppliedMethod(fun, types, {Container$Method:{t:this.tipo},Type$Method:_t,Arguments$Method:_a});
}
ClassOrInterface$meta$model.$$.prototype.getMethod.$crtmm$=function(){return{mod:$CCMM$,$t:{ t:'u', l:[{t:Null},{t:Method$meta$model,a:{Arguments:'Arguments',Type:'Type',Container:'Container'}}]},ps:[{nm:'name',mt:'prm',$t:{t:$_String},an:function(){return[];}},{nm:'types',mt:'prm',seq:1,$t:{t:Sequential,a:{Element$Iterable:{t:Type$meta$model,a:{Type:{t:Anything}}}}},an:function(){return[];}}],$cont:ClassOrInterface$meta$model,tp:{Container:{},Type:{},Arguments:{'satisfies':[{t:Sequential,a:{Element$Iterable:{t:Anything}}}]}},an:function(){return[shared(),actual()];},d:['ceylon.language.meta.model','ClassOrInterface','$m','getMethod']};};
ClassOrInterface$meta$model.$$.prototype.getDeclaredMethod=function(name,types,$$$mptypes) {
  var rv=this.getMethod(name,types,{Type$getMethod:$$$mptypes.Type$getDeclaredMethod,Arguments$getMethod:$$$mptypes.Arguments$getDeclaredMethod,
    Container$getMethod:$$$mptypes.Container$getDeclaredMethod});
  if (rv && rv.tipo) {
    var mm=getrtmm$$(rv.tipo);
    if (mm && mm.$cont!==this.tipo)return null;
  }
  return rv;
}
ClassOrInterface$meta$model.$$.prototype.getDeclaredMethod.$crtmm$=function(){return{mod:$CCMM$,$t:{ t:'u', l:[{t:Null},{t:Method$meta$model,a:{Arguments:'Arguments',Type:'Type',Container:'Container'}}]},ps:[{nm:'name',mt:'prm',$t:{t:$_String},an:function(){return[];}},{nm:'types',mt:'prm',seq:1,$t:{t:Sequential,a:{Element$Iterable:{t:Type$meta$model,a:{Type:{t:Anything}}}}},an:function(){return[];}}],$cont:ClassOrInterface$meta$model,tp:{Container:{},Type:{},Arguments:{'satisfies':[{t:Sequential,a:{Element$Iterable:{t:Anything}}}]}},an:function(){return[shared(),actual()];},d:['ceylon.language.meta.model','ClassOrInterface','$m','getDeclaredMethod']};};
ClassOrInterface$meta$model.$$.prototype.getAttribute=function getAttribute(name$15,$$$mptypes){
  if (!extendsType($$$mptypes.Container$getAttribute,{t:this.tipo}))throw IncompatibleTypeException$meta$model("Incompatible Container type argument");
  var nom = '$prop$get' + name$15[0].toUpperCase() + name$15.substring(1);
  var at = this.tipo.$$.prototype[nom];
  if (!at) {
    nom = '$prop$get$_' + name$15;
    at = this.tipo.$$.prototype[nom];
    if (!at)return null;
  }
  var mm=getrtmm$$(at);
  var _t=$$$mptypes.Get$getAttribute;
  if (mm && mm.$t) {
    if (!extendsType(mm.$t,_t))throw IncompatibleTypeException$meta$model("Incompatible Get type argument");
    if (!extendsType($$$mptypes.Set$getAttribute,at.set?mm.$t:{t:Nothing}))throw IncompatibleTypeException$meta$model("Incompatible Set type argument");
    _t=mm.$t;
  }
  var rv=AppliedAttribute(name$15, at, {Get$Attribute:_t,Set$Attribute:at.set?_t:{t:Nothing}, Container$Attribute:{t:this.tipo}});
  if (this.$targs)rv.$$targs$$.Container$Attribute.a=this.$targs;
  rv.$parent=this;
  return rv;
};
ClassOrInterface$meta$model.$$.prototype.getAttribute.$crtmm$=function(){return{mod:$CCMM$,$t:{ t:'u', l:[{t:Null},{t:Attribute$meta$model,a:{Get:'Get',Set:'Set',Container:'Container'}}]},ps:[{nm:'name',mt:'prm',$t:{t:$_String},an:function(){return[];}}],$cont:ClassOrInterface$meta$model,tp:{Container:{},Type:{}},an:function(){return[shared(),actual()];},d:['ceylon.language.meta.model','ClassOrInterface','$m','getAttribute']};};
ClassOrInterface$meta$model.$$.prototype.getDeclaredAttribute=function getDeclaredAttribute(name$15,$$$mptypes){
  var rv=this.getAttribute(name$15,{Get$getAttribute:$$$mptypes.Get$getDeclaredAttribute,Set$getAttribute:$$$mptypes.Set$getDeclaredAttribute,
    Container$getAttribute:$$$mptypes.Container$getDeclaredAttribute});
  if (rv && rv.tipo) {
    var mm=getrtmm$$(rv.tipo);
    if (mm&&mm.$cont!==this.tipo)return null;
  }
  return rv;
};ClassOrInterface$meta$model.$$.prototype.getDeclaredAttribute.$crtmm$=function(){return{mod:$CCMM$,$t:{ t:'u', l:[{t:Null},{t:Attribute$meta$model,a:{Get:'Get',Set:'Set',Container:'Container'}}]},ps:[{nm:'name',mt:'prm',$t:{t:$_String},an:function(){return[];}}],$cont:ClassOrInterface$meta$model,tp:{Container:{},Type:{}},an:function(){return[shared(),actual()];},d:['ceylon.language.meta.model','ClassOrInterface','$m','getDeclaredAttribute']};};
atr$(ClassOrInterface$meta$model.$$.prototype,'container',function(){
  if (this.$parent)return this.$parent;
  var cont = this.tipo.$crtmm$.$cont;
  if (cont === undefined)return null;
  if (get_model(cont.$crtmm$).mt === 'i')
    return AppliedInterface(cont,{Type$Interface:{t:cont}});
  return AppliedClass(cont,{Type$Class:{t:cont},Arguments$Class:{t:Sequential,a:{Element$Iterable:{t:Anything}}}});
},undefined,function(){return{mod:$CCMM$,$t:{ t:'u', l:[{t:Null},{t:Type$meta$model,a:{Type:{t:Anything}}}]},$cont:ClassOrInterface$meta$model,an:function(){return[shared(),actual()];},d:['ceylon.language.meta.model','Member','$at','container']};});
ClassOrInterface$meta$model.$$.prototype.getClassOrInterface=function getClassOrInterface(name$2,types$3,$$$mptypes,noInherit){
  if (!extendsType($$$mptypes.Kind$getClassOrInterface, {t:ClassOrInterface$meta$model}))throw IncompatibleTypeException$meta$model("Kind must be ClassOrInterface");
  if (!extendsType($$$mptypes.Container$getClassOrInterface,{t:this.tipo}))throw IncompatibleTypeException$meta$model("Incompatible type specified in Container");
  var $$clase=this;
  if(types$3===undefined){types$3=getEmpty();}
  var mm = getrtmm$$($$clase.tipo);
  var nom = name$2 + '$' + mm.d[mm.d.length-1];
  var ic = $$clase.tipo.$$.prototype[nom];
  if (!ic) {
    if (noInherit)return null;
    var pere=mm['super'];
    while (!ic && pere) {
      mm=getrtmm$$(pere.t);
      nom=mm&&mm.d?name$2+'$'+mm.d[mm.d.length-1]:undefined;
      if (nom)ic=$$clase.tipo.$$.prototype[nom];
      if (!ic)pere=mm['super'];
    }
  }
  if (ic) {
    mm = getrtmm$$(ic);
    var md = get_model(mm);
    var rv;
    var ict={t:ic};
    var _cont={t:this.tipo};
    if (this.$targs)_cont.a=this.$targs;
    if (md.mt==='i') {
      if (!extendsType({t:Interface$meta$model},{t:$$$mptypes.Kind$getClassOrInterface.t}))throw IncompatibleTypeException$meta$model("Member " + name$2 + " is an interface");
      validate$typeparams(ict,ic.$crtmm$.tp,types$3);
      rv=AppliedMemberInterface(ic, {Container$MemberInterface:_cont,Type$MemberInterface:ict});
    } else if (md.mt==='c'){
      if (!extendsType({t:Class$meta$model},{t:$$$mptypes.Kind$getClassOrInterface.t}))throw IncompatibleTypeException$meta$model("Member " + name$2 + " is a class");
      validate$typeparams(ict,ic.$crtmm$.tp,types$3);
      rv=AppliedMemberClass(ic, {Container$MemberClass:_cont,Type$MemberClass:ict, Arguments$MemberClass:$$$mptypes.Arguments$getClassOrInterface});
    } else {
      throw IncompatibleTypeException$meta$model("Member " + name$2 + " is not a class or interface");
    }
    if (ict.a)rv.$targs=ict.a;
    rv.$parent=this;
    return rv;
  }
  return null;
};ClassOrInterface$meta$model.$$.prototype.getClassOrInterface.$crtmm$=function(){return{mod:$CCMM$,$t:{ t:'u', l:[{t:Null},{t:Member$meta$model,a:{Type$Member:'Container$ClassOrInterface',Kind$Member:'Kind$ClassOrInterface'}}]},ps:[{nm:'name',mt:'prm',$t:{t:$_String},an:function(){return[];}},{nm:'types',mt:'prm',seq:1,$t:{t:Sequential,a:{Element$Iterable:{t:Type$meta$model,a:{Type:{t:Anything}}}}},an:function(){return[];}}],$cont:ClassOrInterface$meta$model,tp:{Container$ClassOrInterface:{},Kind$ClassOrInterface:{'satisfies':[{t:ClassOrInterface$meta$model,a:{Type$ClassOrInterface:{t:Anything}}}]}},an:function(){return[shared(),actual()];},d:['ceylon.language.meta.model','ClassOrInterface','$m','getClassOrInterface']};};
ClassOrInterface$meta$model.$$.prototype.getDeclaredClassOrInterface=function getDeclaredClassOrInterface(name$2,types$3,$$$mptypes){
  return this.getClassOrInterface(name$2,types$3,{Kind$getClassOrInterface:$$$mptypes.Kind$getDeclaredClassOrInterface,
    Arguments$getClassOrInterface:$$$mptypes.Arguments$getDeclaredClassOrInterface,
    Container$getClassOrInterface:$$$mptypes.Container$getDeclaredClassOrInterface},1);
};ClassOrInterface$meta$model.$$.prototype.getDeclaredClassOrInterface.$crtmm$=function(){return{mod:$CCMM$,$t:{ t:'u', l:[{t:Null},{t:Member$meta$model,a:{Type$Member:'Container',Kind$Member:'Kind'}}]},ps:[{nm:'name',mt:'prm',$t:{t:$_String},an:function(){return[];}},{nm:'types',mt:'prm',seq:1,$t:{t:Sequential,a:{Element$Iterable:{t:Type$meta$model,a:{Type:{t:Anything}}}}},an:function(){return[];}}],$cont:ClassOrInterface$meta$model,tp:{Container:{},Kind:{'satisfies':[{t:ClassOrInterface$meta$model,a:{Type$ClassOrInterface:{t:Anything}}}]}},an:function(){return[shared(),actual()];},d:['ceylon.language.meta.model','ClassOrInterface','$m','getDeclaredClassOrInterface']};};

ClassOrInterface$meta$model.$$.prototype.getClass=function getClass(name,types,$mptypes) {
  var rv=this.getClassOrInterface(name,types,{Container$getClassOrInterface:$mptypes.Container$getClass,
    Kind$getClassOrInterface:Class$meta$model});
  if (rv && !is$(rv, {t:AppliedMemberClass})) {
    throw IncompatibleTypeException$meta$model("Member " + name + " is not a class");
  }
  return rv;
};ClassOrInterface$meta$model.$$.prototype.getClass.$crtmm$=function(){return{mod:$CCMM$,$t:{t:MemberClass$meta$model},ps:[],$cont:ClassOrInterface$meta$model,tp:{Container:{},Type:{},Arguments:{satisfies:[{t:Sequential,a:{Element$Iterable:{t:Anything}}}]}},d:['ceylon.language.meta.model','ClassOrInterface','$m','getClass']};};
ClassOrInterface$meta$model.$$.prototype.getDeclaredClass=function getClass(name,types,$mptypes) {
  var rv=this.getDeclaredClassOrInterface(name,types,{Container$getDeclaredClassOrInterface:$mptypes.Container$getClass,
    Kind$getDeclaredClassOrInterface:Class$meta$model});
  if (rv && !is$(rv, {t:AppliedMemberClass})) {
    throw IncompatibleTypeException$meta$model("Member " + name + " is not a class");
  }
  return rv;
};ClassOrInterface$meta$model.$$.prototype.getDeclaredClass.$crtmm$=function(){return{mod:$CCMM$,$t:{t:MemberClass$meta$model},ps:[],$cont:ClassOrInterface$meta$model,tp:{Container:{},Type:{},Arguments:{satisfies:[{t:Sequential,a:{Element$Iterable:{t:Anything}}}]}},d:['ceylon.language.meta.model','ClassOrInterface','$m','getDeclaredClass']};};
ClassOrInterface$meta$model.$$.prototype.getInterface=function(name,types,$mptypes) {
  var rv=this.getClassOrInterface(name,types,{Container$getClassOrInterface:$mptypes.Container$getInterface,
    Kind$getClassOrInterface:Interface$meta$model});
  if (rv && !is$(rv, {t:AppliedMemberInterface})) {
    throw IncompatibleTypeException$meta$model("Member " + name + " is not an interface");
  }
  return rv;
};ClassOrInterface$meta$model.$$.prototype.getInterface.$crtmm$=function(){return{mod:$CCMM$,$t:{t:MemberInterface$meta$model},ps:[],$cont:ClassOrInterface$meta$model,tp:{Container:{},Type:{},Arguments:{satisfies:[{t:Sequential,a:{Element$Iterable:{t:Anything}}}]}},d:['ceylon.language.meta.model','ClassOrInterface','$m','getInterface']};};
ClassOrInterface$meta$model.$$.prototype.getDeclaredInterface=function(name,types,$mptypes) {
  var rv=this.getDeclaredClassOrInterface(name,types,{Container$getDeclaredClassOrInterface:$mptypes.Container$getDeclaredInterface,
    Kind$getDeclaredClassOrInterface:Interface$meta$model});
  if (rv && !is$(rv, {t:AppliedMemberInterface})) {
    throw IncompatibleTypeException$meta$model("Member " + name + " is not an interface");
  }
  return rv;
};ClassOrInterface$meta$model.$$.prototype.getDeclaredInterface.$crtmm$=function(){return{mod:$CCMM$,$t:{t:MemberInterface$meta$model},ps:[],$cont:ClassOrInterface$meta$model,tp:{Container:{},Type:{},Arguments:{satisfies:[{t:Sequential,a:{Element$Iterable:{t:Anything}}}]}},d:['ceylon.language.meta.model','ClassOrInterface','$m','getDeclaredInterface']};};


ClassOrInterface$meta$model.$$.prototype.typeOf=function typeOf(instance$8){
  var _t={t:this.tipo};
  if (this.$targs)_t.a=this.$targs;
  return is$(instance$8,_t);
};
ClassOrInterface$meta$model.$$.prototype.typeOf.$crtmm$=function(){return{mod:$CCMM$,$t:{t:$_Boolean},ps:[{nm:'instance',mt:'prm',$t:{t:Anything},an:function(){return[];}}],$cont:ClassOrInterface$meta$model,an:function(){return[shared(),actual()];},d:['ceylon.language.meta.model','Type','$m','typeOf']};};
ClassOrInterface$meta$model.$$.prototype.supertypeOf=function supertypeOf(type$9){
  return extendsType({t:type$9.tipo}, {t:this.tipo});
};
ClassOrInterface$meta$model.$$.prototype.supertypeOf.$crtmm$=function(){return{mod:$CCMM$,$t:{t:$_Boolean},ps:[{nm:'type',mt:'prm',$t:{t:Type$meta$model,a:{Type:{t:Anything}}},an:function(){return[];}}],$cont:ClassOrInterface$meta$model,an:function(){return[shared(),actual()];},d:['ceylon.language.meta.model','Type','$m','supertypeOf']};};
ClassOrInterface$meta$model.$$.prototype.exactly=function exactly(type$10){
  return type$10.tipo && this.tipo === type$10.tipo;
};
ClassOrInterface$meta$model.$$.prototype.exactly.$crtmm$=function(){return{mod:$CCMM$,$t:{t:$_Boolean},ps:[{nm:'type',mt:'prm',$t:{t:Type$meta$model,a:{Type:{t:Anything}}},an:function(){return[];}}],$cont:ClassOrInterface$meta$model,an:function(){return[shared(),actual()];},d:['ceylon.language.meta.model','Type','$m','exactly']};};
atr$(ClassOrInterface$meta$model.$$.prototype,'typeArguments',function(){
  var mm = getrtmm$$(this.tipo);
  if (mm) {
    if (mm.tp) {
      var targs={};
      for (var tp in mm.tp) {
        var param = OpenTypeParam$jsint(this.tipo,tp);
        var targ;
        if (this.$$targs$$ && this.$$targs$$.Type$ClassOrInterface && this.$$targs$$.Type$ClassOrInterface.a
            && this.$$targs$$.Type$ClassOrInterface.a[tp]) {
          var _targ=this.$$targs$$.Type$ClassOrInterface.a[tp];
          if (typeof(_targ)==='string') {
            console.log("TODO buscar " + tp + "->" + _targ + " para " + this.declaration.qualifiedName);
            _targ={t:Anything};
          }
          targ=typeLiteral$meta({Type$typeLiteral:_targ});
        } else {
          targ=typeLiteral$meta({Type$typeLiteral:{t:Anything}});
        }
        targs[param.qualifiedName]=[param,targ];
      }
      return Mapita(targs,{V$Mapita:{t:Type$meta$model,a:{Type$Type:{t:Anything}}}});
    }
    return getEmpty();
  }
  throw Exception("ClassOrInterface.typeArguments-we don't have a metamodel!");
},undefined,function(){return{mod:$CCMM$,$t:{t:Map,a:{Key:{t:TypeParameter$meta$declaration},Item:{t:Type$meta$model,a:{Type:{t:Anything}}}}},$cont:ClassOrInterface$meta$model,an:function(){return[shared(),actual()];},d:['ceylon.language.meta.model','Generic','$at','typeArguments']};});
atr$(ClassOrInterface$meta$model.$$.prototype,'string',function(){
    var mm = getrtmm$$(this.tipo);
    var qn=this.tipo.$$ && this.tipo.$$.prototype && this.tipo.$$.prototype.getT$name ? this.tipo.$$.prototype.getT$name() : qname$(mm);
    if (mm.tp) {
      qn+="<";
      var first=true;
      for (var tp in mm.tp) {
        var targ;
        if (this.$$targs$$ && this.$$targs$$.Type$ClassOrInterface && this.$$targs$$.Type$ClassOrInterface.a && this.$$targs$$.Type$ClassOrInterface.a[tp]) {
          var _targ=this.$$targs$$.Type$ClassOrInterface.a[tp];
          if (typeof(_targ)==='string') {
            console.log("TODO buscar " + tp + "->" + _targ + " para " + this.declaration.qualifiedName);
            _targ={t:Anything};
          }
          targ=typeLiteral$meta({Type$typeLiteral:_targ});
        } else {
          targ=typeLiteral$meta({Type$typeLiteral:{t:Anything}});
        }
        if (first)first=false; else qn+=",";
        if (targ.declaration) {
          qn+=targ.declaration.qualifiedName;
        } else {
          qn+=targ.string;
        }
      }
      qn+=">";
    }
    return qn;
},undefined,function(){return{mod:$CCMM$,$t:{t:$_String},d:['$','Object','$at','string']};});
atr$(ClassOrInterface$meta$model.$$.prototype,'hash',function(){
  var mm = getrtmm$$(this.tipo);
  var h=qname$(mm).hash;
  if (mm.tp) {
    for (var tp in mm.tp) {
      var targ;
      if (this.$$targs$$ && this.$$targs$$.Type$ClassOrInterface && this.$$targs$$.Type$ClassOrInterface.a && this.$$targs$$.Type$ClassOrInterface.a[tp]) {
        var _targ=this.$$targs$$.Type$ClassOrInterface.a[tp];
        if (typeof(_targ)==='string') {
          console.log("TODO buscar " + tp + "->" + _targ + " para " + this.declaration.qualifiedName);
          _targ={t:Anything};
        }
        targ=typeLiteral$meta({Type$typeLiteral:_targ});
      } else {
        targ=typeLiteral$meta({Type$typeLiteral:{t:Anything}});
      }
      h+=targ.hash;
    }
  }
  if (this.$bound)h+=this.$bound.hash;
  return h;
},undefined,function(){return{mod:$CCMM$,$t:{t:Integer},d:['$','Object','$at','hash']};});
atr$(ClassOrInterface$meta$model.$$.prototype,'extendedType',function(){
  var sc = this.tipo.$crtmm$['super'];
  if (sc === undefined)return null;
  var mm = getrtmm$$(sc.t);
  var ac;
  if (mm.$cont) {
    ac=AppliedMemberClass(sc.t, {Type$MemberClass:sc,Arguments$MemberClass:{t:Sequential,a:{Element$Iterable:{t:Anything}}},Container$MemberClass:mm.$cont});
  } else {
    ac=AppliedClass(sc.t, {Type$Class:sc,Arguments$Class:{t:Sequential,a:{Element$Iterable:{t:Anything}}}});
  }
  if (sc.a)ac.$targs=sc.a;
  return ac;
},undefined,function(){return{mod:$CCMM$,$t:{ t:'u', l:[{t:Null},{t:ClassModel$meta$model,a:{Arguments:{t:Nothing},Type:{t:Anything}}}]},$cont:AppliedClass,an:function(){return[shared(),actual()];},d:['ceylon.language.meta.model','Class','$at','extendedType']};});

atr$(ClassOrInterface$meta$model.$$.prototype,'caseValues',function(){
  var cts = this.tipo.$crtmm$.of;
  if (cts && cts.length > 0) {
    var rv=[];
    for (var i=0; i < cts.length; i++) {
      if (typeof(cts[i])==='function') {
        rv.push(cts[i]());
      }
    }
    return rv.length===0?getEmpty():ArraySequence(rv,{Element$ArraySequence:{t:this.tipo}});
  }
  return getEmpty();
},undefined,function(){return{mod:$CCMM$,$t:{t:Sequential,a:{Element$Sequential:'Type$ClassOrInterface'}},$cont:ClassOrInterface$meta$model,an:function(){return[doc($CCMM$['ceylon.language.meta.model'].ClassOrInterface.$at.caseValues.an.doc[0]),shared(),formal()];},d:['ceylon.language.meta.model','ClassOrInterface','$at','caseValues']};});

//This is the new shit May 2014
ClassOrInterface$meta$model.$$.prototype.getClasses=function getClasses(anntypes,$$$mptypes,noInherit){
  var mems=[];
  var ats=coi$get$anns(anntypes);
  for (m in this.tipo.$$.prototype) {
    var mem=this.tipo.$$.prototype[m];
    if (mem && mem.$$) {
      var mm=getrtmm$$(mem);
      if (mm && mm.d && mm.d[mm.d.length-2]==='$c') {
        if (noInherit && mm.$cont!==$$$mptypes.Container$getClasses.t)continue;
        if (!extendsType({t:this.tipo},$$$mptypes.Container$getClasses))continue;
        if (!extendsType({t:mem},$$$mptypes.Type$getClasses))continue;
        if (typeof(mm.an)==='function')mm.an=mm.an();
        var a2=getAnnotationsForBitmask(mm.pa);
        if (a2) {
          mm.pa=0;
          if (mm.an) {
            for (var i=0;i<a2.length;i++)mm.an.push(a2[i]);
          } else {
            mm.an=a2;
          }
        }
        var anns=mm.an;
        if (anns && coi$is$anns(anns,ats) && validate$params(mm.ps,$$$mptypes.Arguments$getClasses,'',1)) {
          mems.push(AppliedMemberClass(mem, {Container$MemberClass:this.tipo,Type$MemberClass:{t:mem}, Arguments$MemberClass:$$$mptypes.Arguments$getClasses}));
        }
      }
    }
  }
  return mems.length===0?getEmpty():ArraySequence(mems,{Element$ArraySequence:{t:MemberClass$meta$model,a:{Arguments$MemberClass:$$$mptypes.Arguments$getClasses,Container$MemberClass:$$$mptypes.Container$getClasses,Type$MemberClass:$$$mptypes.Type$getClasses}}});
}
ClassOrInterface$meta$model.$$.prototype.getClasses.$crtmm$=function(){return{mod:$CCMM$,$t:{t:Sequential,a:{Element$Sequential:{t:MemberClass$meta$model,a:{Arguments$MemberClass:'Arguments$getClasses',Container$MemberClass:'Container$getClasses',Type$MemberClass:'Type$getClasses'}}}},ps:[{nm:'annotationTypes',mt:'prm',seq:1,$t:{t:Sequential,a:{Element$Sequential:{t:Type$meta$model,a:{Type$Type:{t:Annotation}}}}},an:function(){return[];}}],$cont:ClassOrInterface$meta$model,tp:{Container$getClasses:{'def':{t:Nothing}},Type$getClasses:{'def':{t:Anything}},Arguments$getClasses:{'satisfies':[{t:Sequential,a:{Element$Sequential:{t:Anything}}}],'def':{t:Nothing}}},an:function(){return[shared(),actual()];},d:['ceylon.language.meta.model','ClassOrInterface','$m','getClasses']};};
ClassOrInterface$meta$model.$$.prototype.getDeclaredClasses=function getDeclaredClasses(anntypes,$$$mptypes) {
  return this.getClasses(anntypes,{Container$getClasses:$$$mptypes.Container$getDeclaredClasses,Type$getClasses:$$$mptypes.Type$getDeclaredClasses,Arguments$getClasses:$$$mptypes.Arguments$getDeclaredClasses},1);
}
ClassOrInterface$meta$model.$$.prototype.getDeclaredClasses.$crtmm$=function(){return{mod:$CCMM$,$t:{t:Sequential,a:{Element$Sequential:{t:MemberClass$meta$model,a:{Arguments$MemberClass:'Arguments$getDeclaredClasses',Container$MemberClass:'Container$getDeclaredClasses',Type$MemberClass:'Type$getDeclaredClasses'}}}},ps:[{nm:'annotationTypes',mt:'prm',seq:1,$t:{t:Sequential,a:{Element$Sequential:{t:Type$meta$model,a:{Type$Type:{t:Annotation}}}}},an:function(){return[];}}],$cont:ClassOrInterface$meta$model,tp:{Container$getDeclaredClasses:{'def':{t:Nothing}},Type$getDeclaredClasses:{'def':{t:Anything}},Arguments$getDeclaredClasses:{'satisfies':[{t:Sequential,a:{Element$Sequential:{t:Anything}}}],'def':{t:Nothing}}},an:function(){return[shared(),actual()];},d:['ceylon.language.meta.model','ClassOrInterface','$m','getDeclaredClasses']};};
ClassOrInterface$meta$model.$$.prototype.getInterfaces=function getInterfaces(anntypes,$$$mptypes,noInherit){
  var mems=[];
  var ats=coi$get$anns(anntypes);
  for (m in this.tipo.$$.prototype) {
    var mem=this.tipo.$$.prototype[m];
    if (mem && mem.$$) {
      var mm=getrtmm$$(mem);
      if (mm && mm.d && mm.d[mm.d.length-2]==='$i') {
        if (noInherit && mm.$cont!==$$$mptypes.Container$getInterfaces.t)continue;
        if (!extendsType({t:this.tipo},$$$mptypes.Container$getInterfaces))continue;
        if (!extendsType({t:mem},$$$mptypes.Type$getInterfaces))continue;
        if (typeof(mm.an)==='function')mm.an=mm.an();
        var a2=getAnnotationsForBitmask(mm.pa);
        if (a2) {
          mm.pa=0;
          if (mm.an) {
            for (var i=0;i<a2.length;i++)mm.an.push(a2[i]);
          } else {
            mm.an=a2;
          }
        }
        var anns=mm.an;
        if (anns && coi$is$anns(anns,ats)) {
          mems.push(AppliedMemberInterface(mem, {Container$MemberInterface:this.tipo,Type$MemberInterface:{t:mem}}));
        }
      }
    }
  }
  return mems.length===0?getEmpty():ArraySequence(mems,{Element$ArraySequence:{t:MemberInterface$meta$model,a:{Container$MemberInterface:$$$mptypes.Container$getInterfaces,Type$MemberInterface:$$$mptypes.Type$getInterfaces}}});
}
ClassOrInterface$meta$model.$$.prototype.getInterfaces.$crtmm$=function(){return{mod:$CCMM$,$t:{t:Sequential,a:{Element$Sequential:{t:MemberInterface$meta$model,a:{Container$MemberInterface:'Container$getInterfaces',Type$MemberInterface:'Type$getInterfaces'}}}},ps:[{nm:'annotationTypes',mt:'prm',seq:1,$t:{t:Sequential,a:{Element$Sequential:{t:Type$meta$model,a:{Type$Type:{t:Annotation}}}}},an:function(){return[];}}],$cont:ClassOrInterface$meta$model,tp:{Container$getInterfaces:{'def':{t:Nothing}},Type$getInterfaces:{'def':{t:Anything}}},an:function(){return[shared(),actual()];},d:['ceylon.language.meta.model','ClassOrInterface','$m','getInterfaces']};};
ClassOrInterface$meta$model.$$.prototype.getDeclaredInterfaces=function getDeclaredInterfaces(anntypes,$$$mptypes){
  return this.getInterfaces(anntypes,{Container$getInterfaces:$$$mptypes.Container$getDeclaredInterfaces,Type$getInterfaces:$$$mptypes.Type$getDeclaredInterfaces},1);
}
ClassOrInterface$meta$model.$$.prototype.getDeclaredInterfaces.$crtmm$=function(){return{mod:$CCMM$,$t:{t:Sequential,a:{Element$Sequential:{t:MemberInterface$meta$model,a:{Container$MemberInterface:'Container$getDeclaredInterfaces',Type$MemberInterface:'Type$getDeclaredInterfaces'}}}},ps:[{nm:'annotationTypes',mt:'prm',seq:1,$t:{t:Sequential,a:{Element$Sequential:{t:Type$meta$model,a:{Type$Type:{t:Annotation}}}}},an:function(){return[];}}],$cont:ClassOrInterface$meta$model,tp:{Container$getDeclaredInterfaces:{'def':{t:Nothing}},Type$getDeclaredInterfaces:{'def':{t:Anything}}},an:function(){return[shared(),actual()];},d:['ceylon.language.meta.model','ClassOrInterface','$m','getDeclaredInterfaces']};};
ClassOrInterface$meta$model.$$.prototype.getAttributes=function getAttributes(anntypes,$$$mptypes,noInherit){
  var mems=[];
  var ats=coi$get$anns(anntypes);
  for (m in this.tipo.$$.prototype) {
    if (m.substring(0,6)==='$prop$') {
      var mm=getrtmm$$(this.tipo.$$.prototype[m]);
      if (mm) {
        if (noInherit && mm.$cont!==this.tipo)continue;
        if (!extendsType(mm.$t,$$$mptypes.Get$getAttributes))continue;
        var setter=this.tipo.$$.prototype[m].set && extendsType($$$mptypes.Set$getAttributes,mm.$t);
        if ($$$mptypes.Set$getAttributes.t!==Nothing && !setter)continue;
        if (!extendsType({t:this.tipo},$$$mptypes.Container$getAttributes))continue;
        if (typeof(mm.an)==='function')mm.an=mm.an();
        var a2=getAnnotationsForBitmask(mm.pa);
        if (a2) {
          mm.pa=0;
          if (mm.an) {
            for (var i=0;i<a2.length;i++)mm.an.push(a2[i]);
          } else {
            mm.an=a2;
          }
        }
        var anns=mm.an;
        if (anns && coi$is$anns(anns,ats)) {
          var atname=mm.d[mm.d.length-1];
          if (atname.indexOf('$')>0)atname=atname.substring(0,atname.indexOf('$'));
          mems.push(AppliedAttribute(atname,this.tipo.$$.prototype[m],{Container$Attribute:{t:mm.$cont},Get$Attribute:mm.$t,Set$Attribute:setter?mm.$t:{t:Nothing}}));
        }
      }
    }
  }
  return mems.length==0?getEmpty():ArraySequence(mems,{Element$ArraySequence:{t:Attribute$meta$model,a:{Set$Attribute:$$$mptypes.Set$getAttributes,Container$Attribute:$$$mptypes.Container$getAttributes,Get$Attribute:$$$mptypes.Get$getAttributes}}});
}
ClassOrInterface$meta$model.$$.prototype.getAttributes.$crtmm$=function(){return{mod:$CCMM$,$t:{t:Sequential,a:{Element$Sequential:{t:Attribute$meta$model,a:{Set$Attribute:'Set$getAttributes',Container$Attribute:'Container$getAttributes',Get$Attribute:'Get$getAttributes'}}}},ps:[{nm:'annotationTypes',mt:'prm',seq:1,$t:{t:Sequential,a:{Element$Sequential:{t:Type$meta$model,a:{Type$Type:{t:Annotation}}}}},an:function(){return[];}}],$cont:ClassOrInterface$meta$model,tp:{Container$getAttributes:{'def':{t:Nothing}},Get$getAttributes:{'def':{t:Anything}},Set$getAttributes:{'def':{t:Nothing}}},an:function(){return[shared(),actual()];},d:['ceylon.language.meta.model','ClassOrInterface','$m','getAttributes']};};
ClassOrInterface$meta$model.$$.prototype.getDeclaredAttributes=function getDeclaredAttributes(anntypes,$$$mptypes) {
  return this.getAttributes(anntypes,{Container$getAttributes:$$$mptypes.Container$getDeclaredAttributes,Get$getAttributes:$$$mptypes.Get$getDeclaredAttributes,Set$getAttributes:$$$mptypes.Set$getDeclaredAttributes},1);
}
ClassOrInterface$meta$model.$$.prototype.getDeclaredAttributes.$crtmm$=function(){return{mod:$CCMM$,$t:{t:Sequential,a:{Element$Sequential:{t:Attribute$meta$model,a:{Set$Attribute:'Set$getDeclaredAttributes',Container$Attribute:'Container$getDeclaredAttributes',Get$Attribute:'Get$getDeclaredAttributes'}}}},ps:[{nm:'annotationTypes',mt:'prm',seq:1,$t:{t:Sequential,a:{Element$Sequential:{t:Type$meta$model,a:{Type$Type:{t:Annotation}}}}},an:function(){return[];}}],$cont:ClassOrInterface$meta$model,tp:{Container$getDeclaredAttributes:{'def':{t:Nothing}},Get$getDeclaredAttributes:{'def':{t:Anything}},Set$getDeclaredAttributes:{'def':{t:Nothing}}},an:function(){return[shared(),actual()];},d:['ceylon.language.meta.model','ClassOrInterface','$m','getDeclaredAttributes']};};
ClassOrInterface$meta$model.$$.prototype.getMethods=function getMethods(anntypes,$$$mptypes,noInherit) {
  var mems=[];
  var ats=coi$get$anns(anntypes);
  for (m in this.tipo.$$.prototype) {
    var mem=this.tipo.$$.prototype[m];
    if (mem && m.substring(0,6)!='$prop$' && mem.$$===undefined) {
      var mm=getrtmm$$(mem);
      if (mm && mm.d && mm.d[mm.d.length-2]=='$m') {
        if (noInherit && mm.$cont!==$$$mptypes.Container$getMethods.t)continue;
        if (!extendsType(mm.$t,$$$mptypes.Type$getMethods))continue;
        if (typeof(mm.an)==='function')mm.an=mm.an();
        var a2=getAnnotationsForBitmask(mm.pa);
        if (a2) {
          mm.pa=0;
          if (mm.an) {
            for (var i=0;i<a2.length;i++)mm.an.push(a2[i]);
          } else {
            mm.an=a2;
          }
        }
        var anns=mm.an;
        if (anns && coi$is$anns(anns,ats) && validate$params(mm.ps,$$$mptypes.Arguments$getMethods,'',1)) {
          var types=[].reifyCeylonType({t:Type$meta$model,a:{Type:{t:Anything}}});
          if (mm.ps) for (var i=0; i<mm.ps.length;i++) {
            types.push(typeLiteral$meta({Type$typeLiteral:mm.ps[i].$t}));
          }
          mems.push(AppliedMethod(mem, types, {Container$Method:{t:this.tipo},Type$Method:mm.$t,Arguments$Method:$$$mptypes.Arguments$getMethods}));
        }
      }
    }
  }
  return mems.length===0?getEmpty():ArraySequence(mems,{Element$ArraySequence:{t:Method$meta$model,a:{Container$Method:$$$mptypes.Container$getMethods,Arguments$Method:$$$mptypes.Arguments$getMethods,Type$Method:$$$mptypes.Type$getMethods}}});
}
ClassOrInterface$meta$model.$$.prototype.getMethods.$crtmm$=function(){return{mod:$CCMM$,$t:{t:Sequential,a:{Element$Sequential:{t:Method$meta$model,a:{Container$Method:'Container$getMethods',Arguments$Method:'Arguments$getMethods',Type$Method:'Type$getMethods'}}}},ps:[{nm:'annotationTypes',mt:'prm',seq:1,$t:{t:Sequential,a:{Element$Sequential:{t:Type$meta$model,a:{Type$Type:{t:Annotation}}}}},an:function(){return[];}}],$cont:ClassOrInterface$meta$model,tp:{Container$getMethods:{'def':{t:Nothing}},Type$getMethods:{'def':{t:Anything}},Arguments$getMethods:{'satisfies':[{t:Sequential,a:{Element$Sequential:{t:Anything}}}],'def':{t:Nothing}}},an:function(){return[shared(),actual()];},d:['ceylon.language.meta.model','ClassOrInterface','$m','getMethods']};};
ClassOrInterface$meta$model.$$.prototype.getDeclaredMethods=function getDeclaredMethods(anntypes,$$$mptypes) {
  return this.getMethods(anntypes,{Container$getMethods:$$$mptypes.Container$getDeclaredMethods,Type$getMethods:$$$mptypes.Type$getDeclaredMethods,Arguments$getMethods:$$$mptypes.Arguments$getDeclaredMethods},1);
}
ClassOrInterface$meta$model.$$.prototype.getDeclaredMethods.$crtmm$=function(){return{mod:$CCMM$,$t:{t:Sequential,a:{Element$Sequential:{t:Method$meta$model,a:{Container$Method:'Container$getDeclaredMethods',Arguments$Method:'Arguments$getDeclaredMethods',Type$Method:'Type$getDeclaredMethods'}}}},ps:[{nm:'annotationTypes',mt:'prm',seq:1,$t:{t:Sequential,a:{Element$Sequential:{t:Type$meta$model,a:{Type$Type:{t:Annotation}}}}},an:function(){return[];}}],$cont:ClassOrInterface$meta$model,tp:{Container$getDeclaredMethods:{'def':{t:Nothing}},Type$getDeclaredMethods:{'def':{t:Anything}},Arguments$getDeclaredMethods:{'satisfies':[{t:Sequential,a:{Element$Sequential:{t:Anything}}}],'def':{t:Nothing}}},an:function(){return[shared(),actual()];},d:['ceylon.language.meta.model','ClassOrInterface','$m','getDeclaredMethods']};};

function coi$get$anns(anntypes) {
  var ats=[];
  var iter=anntypes.iterator();
  var a;while((a=iter.next())!==getFinished()){
    ats.push({t:a.tipo});
  }
  return ats;
}
function coi$is$anns(anns,ats) {
  for (var i=0;i<ats.length;i++) {
    var f=false;
    for (var j=0;j<anns.length;j++) {
      f|=(is$(anns[j],ats[i]));
    }
    if (!f)return false;
  }
  return true;
}
