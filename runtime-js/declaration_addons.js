//Addendum to model.declaration.ClassOrInterfaceDeclaration
ClassOrInterfaceDeclaration$meta$declaration.$$.prototype.getMemberDeclaration=function(name$20,$$$mptypes,noInherit){
  var _m=undefined;
  var raiz;
  if (this.tipo.$$ && this.tipo.$$.prototype) {
    raiz=this.tipo.$$.prototype;
  } else {
    raiz = this.tipo();
  }
  if (extendsType($$$mptypes.Kind$getMemberDeclaration, {t:ValueDeclaration$meta$declaration})) {
    var propname='$prop$get'+name$20[0].toUpperCase()+name$20.substring(1);
    var _d = raiz[propname];
    if (_d){
      if (noInherit) {
        var mm=getrtmm$$(_d);
        //If we found the attribute but it's inherited, forget about it
        if (mm.$cont!==this.tipo)_d=undefined;
      }
    }
    if (_d===undefined) {
      //Browse all non-shared attributes looking for original name
      for (nsats in raiz) if (nsats.substring(0,10)==='$prop$get$') {
        var atmm=getrtmm$$(raiz[nsats]);
        //Review the name in the model in case it's not shared
        if (atmm.d && atmm.d[atmm.d.length-1].substring(0,name$20.length+1)===name$20+'$') {
          _d=raiz[nsats];
          break;
        }
      }
    }
    if(_d)_m=OpenValue$jsint(this.containingPackage, _d);
  }
  if (!_m && extendsType($$$mptypes.Kind$getMemberDeclaration, {t:FunctionDeclaration$meta$declaration})) {
    var nom=name$20;
    if (['hasOwnProperty', 'isPrototypeOf', 'propertyIsEnumerable', 'call', 'arguments', 'caller', 'apply', 'bind', 'toFixed', 'valueOf', 'toPrecision', 'toExponential', "charAt", "strike", "fixed", "sub", "charCodeAt", "trimLeft", "toLocaleUpperCase", "toUpperCase", "fontsize", "search", "toLocaleLowerCase", "small", "big", "fontcolor", "blink", "trim", "bold", "match", "substr", "trimRight", "replace", "split", "sup", "link", "localeCompare", "valueOf", "substring", "toLowerCase", "italics", "anchor", "toLocaleString", "splice", "map", "forEach", "reverse", "join", "push", "shift", "pop", "sort", "unshift", "reduceRight", "reduce", "every", "filter", "length", "toString", "constructor", "prototype", "concat", "indexOf", "lastIndexOf", "slice", "get"].indexOf(nom)>=0)nom='$_'+nom;
    var _d = raiz[nom];
    if (_d===undefined) {
      //Let's just look for this thing everywhere
      for (var $k in raiz) {
        var propname='$prop$get'+$k[0].toUpperCase()+$k.substring(1);
        if (!$k.startsWith("$prop$get") && raiz[propname]===undefined && typeof(raiz[$k])==='function') {
          var lafun=raiz[$k];
          var mm=getrtmm$$(lafun);
          var mod=mm&&get_model(mm);
          if (mod && mod.nm===nom) {
            _d=lafun; break;
          }
        }
        var m$ = raiz[propname] ? undefined: raiz[$k];
        _d = typeof(m$)==='function' && m$.$$===undefined ? getrtmm$$(m$) : undefined;
        if (_d && _d.d && _d.d[_d.d.length-1]===nom){
          _d = raiz[$k];
          break;
        }else _d=undefined;
      }
    }
    if(_d){
      if (noInherit) {
        var mm=getrtmm$$(_d);
        if (mm.$cont!==this.tipo)return null;
      }
      _m=OpenFunction(this.containingPackage, _d);
    }
  }
  if (!_m && extendsType($$$mptypes.Kind$getMemberDeclaration, {t:ClassOrInterfaceDeclaration$meta$declaration})) {
    var nom=name$20+'$'+this.name;
    var _d = raiz[nom];
    if (_d===undefined) {
      //Let's just look for this thing everywhere
      for (var $k in raiz) {
        var propname='$prop$get'+$k[0].toUpperCase()+$k.substring(1);
        var m$ = raiz[propname] ? undefined: raiz[$k];
        _d = typeof(m$)==='function' && m$.$$ ? getrtmm$$(m$) : undefined;
        if (_d && _d.d && _d.d[_d.d.length-1]===nom){
          _d = raiz[$k];
          break;
        }else _d=undefined;
      }
    }
    if (!_d) {
      if (noInherit)return null;
      var pere=this.tipo.$crtmm$['super'];
      while (!_d && pere) {
        var mm=getrtmm$$(pere.t);
        nom=mm&&mm.d?name$20+'$'+mm.d[mm.d.length-1]:undefined;
        if(nom)_d=raiz[nom];
        if (!_d)pere=mm['super'];
      }
    }
    if(_d){
      var wantsClass=extendsType($$$mptypes.Kind$getMemberDeclaration,{t:ClassDeclaration$meta$declaration});
      var wantsIface=extendsType($$$mptypes.Kind$getMemberDeclaration,{t:InterfaceDeclaration$meta$declaration});
      var _$m = getrtmm$$(_d);
      var _mdl=get_model(_$m);
      if ((wantsClass && _mdl.mt!=='c') || (wantsIface && _mdl.mt!=='i'))return null;
      _m=(_mdl.mt==='c'?OpenClass$jsint:OpenInterface$jsint)(this.containingPackage, _d);
    }
  }
  if (_m) {
    _m.$parent=this;
    return _m;
  }
  return null;
};
ClassOrInterfaceDeclaration$meta$declaration.$$.prototype.getMemberDeclaration.$crtmm$=function(){return{mod:$CCMM$,$t:{ t:'u', l:[{t:Null},'Kind']},ps:[{nm:'name',mt:'prm',$t:{t:$_String}}],$cont:ClassOrInterfaceDeclaration,tp:{Kind:{sts:[{t:NestableDeclaration$meta$declaration}]}},an:function(){return[shared(),actual()];},d:['ceylon.language.meta.declaration','ClassOrInterfaceDeclaration','$m','getMemberDeclaration']};};
ClassOrInterfaceDeclaration$meta$declaration.$$.prototype.getDeclaredMemberDeclaration=function(nm,$mptypes){
  return this.getMemberDeclaration(nm,{Kind$getMemberDeclaration:$mptypes.Kind$getDeclaredMemberDeclaration},1);
}
ClassOrInterfaceDeclaration$meta$declaration.$$.prototype.getDeclaredMemberDeclaration.$crtmm$=function(){return{mod:$CCMM$,$t:{ t:'u', l:[{t:Null},'Kind']},ps:[{nm:'name',mt:'prm',$t:{t:$_String}}],$cont:ClassOrInterfaceDeclaration,tp:{Kind:{sts:[{t:NestableDeclaration$meta$declaration}]}},an:function(){return[shared(),actual()];},d:['ceylon.language.meta.declaration','ClassOrInterfaceDeclaration','$m','getDeclaredMemberDeclaration']};};
atr$(FunctionalDeclaration$meta$declaration.$$.prototype,'parameterDeclarations',function(){
  var that=this;
  var parms = that.tipo.$crtmm$.ps;
  if (!parms || parms.length === 0)return empty();
  var rv = [];
  for (var i=0; i<parms.length;i++) {
    var p = parms[i];
    if (p.$pt === 'f') {
      rv.push(FunParamDecl(this,p));
    } else {
      rv.push(ValParamDecl(this,p));
    }
  }
  return rv.length===0?empty():ArraySequence(rv,{Element$ArraySequence:{t:FunctionOrValueDeclaration$meta$declaration},Absent$Iterable:{t:Null}});
},undefined,function(){return{mod:$CCMM$,$t:{t:Sequential,a:{Element$Sequential:{t:FunctionOrValueDeclaration$meta$declaration}}},$cont:FunctionalDeclaration$meta$declaration,an:function(){return[shared(),formal()];},d:['ceylon.language.meta.declaration','FunctionalDeclaration','$at','parameterDeclarations']};});
ClassOrInterfaceDeclaration$meta$declaration.$$.prototype.memberDeclarations=function memberDeclarations($$$mptypes,inherited){
  var defs=[];
  var _prot=this.tipo && this.tipo.$$ && this.tipo.$$.prototype;
  if (_prot) {
    //Try iterating over the prototype's members first
    var props = Object.getOwnPropertyNames(_prot);
    function isProp(mem) {
      return _prot['$prop$get' + mem[0].toUpperCase() + mem.substring(1)]!==undefined;
    }
    for (var mem in _prot) {
      if (isProp(mem))continue;
      var _d = undefined;
      var mm=getrtmm$$(_prot[mem]);
      if (mm && inherited && mm.$cont!==this.tipo)continue;
      if (mem.substring(0,9)==='$prop$get' && extendsType($$$mptypes.Kind$memberDeclarations,{t:ValueDeclaration$meta$declaration})) {
        var _nom=mm.d[mm.d.length-1];
        var _idx=_nom.indexOf('$');
        if (_idx>0)_nom=_nom.substring(0,_idx);
        _d=this.getMemberDeclaration(_nom,{Kind$getMemberDeclaration:{t:ValueDeclaration$meta$declaration}});
      } else if (_prot[mem].$$ && extendsType($$$mptypes.Kind$memberDeclarations,{t:ClassOrInterfaceDeclaration$meta$declaration})) {
        var mt=mm.d[mm.d.length-2];
        if ((mt==='$c' && !extendsType({t:ClassDeclaration$meta$declaration},$$$mptypes.Kind$memberDeclarations))
            ||(mt==='$i' && !extendsType({t:InterfaceDeclaration$meta$declaration},$$$mptypes.Kind$memberDeclarations)))continue;
        _d=this.getMemberDeclaration(mm.d[mm.d.length-1],
          {Kind$getMemberDeclaration:{t:ClassOrInterfaceDeclaration$meta$declaration}});
      } else if(mm && mm.d) {
        var mt=mm.d[mm.d.length-2];
        if (mt === '$m' && extendsType({t:FunctionDeclaration$meta$declaration},$$$mptypes.Kind$memberDeclarations)) {
          _d=this.getMemberDeclaration(mm.d[mm.d.length-1],{Kind$getMemberDeclaration:{t:FunctionDeclaration$meta$declaration}});
        }
      }
      if (_d){_d.parent$=this;defs.push(_d);};
    }
    return defs.length?ArraySequence(defs,{Element$ArraySequence:$$$mptypes.Kind$memberDeclarations}):empty();
  }
  //Fallback to the model declarations
  if (extendsType({t:FunctionDeclaration$meta$declaration},$$$mptypes.Kind$memberDeclarations)) {
    for (var df in this.meta['$m']) {
      defs.push(this.meta['$m'][df]);
    }
  }
  if (extendsType({t:ValueDeclaration$meta$declaration},$$$mptypes.Kind$memberDeclarations)) {
    for (var df in this.meta['$at']) {
      defs.push(this.meta['$at'][df]);
    }
  }
  if (extendsType({t:InterfaceDeclaration$meta$declaration},$$$mptypes.Kind$memberDeclarations)) {
    for (var df in this.meta['$i']) {
      defs.push(this.meta['$i'][df]);
    }
  }
  if (extendsType({t:ClassDeclaration$meta$declaration},$$$mptypes.Kind$memberDeclarations)) {
    for (var df in this.meta['$c']) {
      defs.push(this.meta['$c'][df]);
    }
  }
  var pkg = this.containingPackage;
  var r=[];
  for (var i=0; i<defs.length; i++) {
    var m=defs[i];
    var mt = m['mt'];
    var _d;
    if (mt === 'm') {
      _d=this.getMemberDeclaration(m.nm, {Kind$getMemberDeclaration:{t:FunctionDeclaration$meta$declaration}});
    } else if (mt==='c') {
      _d=this.getMemberDeclaration(m.nm, {Kind$getMemberDeclaration:{t:ClassDeclaration$meta$declaration}});
    } else if (mt==='i') {
      _d=this.getMemberDeclaration(m.nm, {Kind$getMemberDeclaration:{t:InterfaceDeclaration$meta$declaration}});
    } else if (mt==='a'||mt==='g'||mt==='o'||mt==='s') {
      _d=this.getMemberDeclaration(m.nm, {Kind$getMemberDeclaration:{t:ValueDeclaration$meta$declaration}});
    }
    if (_d) {
      _d.$parent=this;
      r.push(_d);
    }
  }
  return r.length===0?empty():ArraySequence(r,{Element$ArraySequence:$$$mptypes.Kind$memberDeclarations});
};
ClassOrInterfaceDeclaration$meta$declaration.$$.prototype.memberDeclarations.$crtmm$=function(){return{mod:$CCMM$,$t:{t:Sequential,a:{Element$Sequential:'Kind'}},ps:[],$cont:ClassOrInterfaceDeclaration,tp:{Kind:{sts:[{t:NestableDeclaration$meta$declaration}]}},an:function(){return[shared(),actual()];},d:['ceylon.language.meta.declaration','ClassOrInterfaceDeclaration','$m','memberDeclarations']};};
ClassOrInterfaceDeclaration$meta$declaration.$$.prototype.declaredMemberDeclarations=function(m) {
  return this.memberDeclarations({Kind$memberDeclarations:m.Kind$declaredMemberDeclarations},1);
}
ClassOrInterfaceDeclaration$meta$declaration.$$.prototype.declaredMemberDeclarations.$crtmm$=function(){return{mod:$CCMM$,$t:{t:Sequential,a:{Element$Sequential:'Kind'}},ps:[],$cont:ClassOrInterfaceDeclaration,tp:{Kind:{sts:[{t:NestableDeclaration$meta$declaration}]}},an:function(){return[shared(),actual()];},d:['ceylon.language.meta.declaration','ClassOrInterfaceDeclaration','$m','declaredMemberDeclarations']};};
atr$(ClassOrInterfaceDeclaration$meta$declaration.$$.prototype,'caseTypes',function(){
  var casos = this.tipo.$crtmm$.of;
  if (casos && casos.length > 0) {
    var ct=[]; var ta=[];
    for (var i=0; i < casos.length; i++) {
      if (typeof(casos[i])==='string') {
        ct.push(OpenTvar$jsint(OpenTypeParam$jsint(this, casos[i])));
        if (!ta.contains(OpenTypeVariable$meta$declaration))ta.push(OpenTypeVariable$meta$declaration);
      } else {
        var _t=casos[i];
        if (typeof(_t)==='function')_t=getrtmm$$(_t).$t;
        var ot=_openTypeFromTarg(_t);
        ct.push(ot);
        if (is$(ot,{t:OpenClassType$meta$declaration}) && !ta.contains(OpenClassType$meta$declaration))ta.push(OpenClassType$meta$declaration);
        else if (is$(ot,{t:OpenInterfaceType$meta$declaration})&&!ta.contains(OpenInterfaceType$meta$declaration))ta.push(OpenInterfaceType$meta$declaration);
      }
    }
    if (ta.length===0)ta={t:OpenType$meta$declaration};
    else if (ta.length===1)ta={t:ta[0]};
    else {
      for (var i=0;i<ta.length;i++)ta[i]={t:ta[i]};
      ta={t:'u',l:ta};
    }
    return ct.length===0?empty():ArraySequence(ct,{Element$ArraySequence:ta});
  }
  return empty();
},undefined,function(){return{mod:$CCMM$,$t:{t:Sequential,a:{Element$Sequential:{t:OpenType$meta$declaration}}},$cont:ClassOrInterfaceDeclaration$meta$declaration,an:function(){return[shared(),formal()];},d:['ceylon.language.meta.declaration','ClassOrInterfaceDeclaration','$at','caseTypes']};});
ClassOrInterfaceDeclaration$meta$declaration.$$.prototype.$_apply=function(types,$mptypes){
  var _t = {t:this.tipo};
  var _m = getrtmm$$(this.tipo);
  validate$typeparams(_t,_m.tp,types);
  if (!extendsType(_t, $mptypes.Type$apply))
    throw IncompatibleTypeException$meta$model("ClassOrInterface<"+this.name+"> is not compatible with expected type: ClassOrInterface<" + qname$($mptypes.Type$apply)+">. Try passing the type argument explicitly with: apply<" + this.name+">");
  if ($mptypes.Type$apply.a)_t.a=$mptypes.Type$apply.a;
  var rv=this.meta.mt==='i'?AppliedInterface$jsint(_t.t, {Type$AppliedInterface:_t}):
    AppliedClass$jsint(_t.t, {Type$AppliedClass:_t,Arguments$AppliedClass:$mptypes.Arguments$apply});
  if (_t.a)rv.$targs=_t.a;
  return rv;
}
ClassOrInterfaceDeclaration$meta$declaration.$$.prototype.$_apply.$crtmm$=function(){return{mod:$CCMM$,d:['ceylon.language.meta.declaration','ClassOrInterfaceDeclaration','$m','apply']};};
ClassOrInterfaceDeclaration$meta$declaration.$$.prototype.memberApply=function(cont, types,$mptypes) {
  var mm=getrtmm$$(this.tipo);
  if (!extendsType({t:cont.tipo},{t:mm.$cont}))throw IncompatibleTypeException$meta$model("Incompatible Container type");
  var _t = {t:this.tipo};
  if ($mptypes.Type$memberApply.a)_t.a=$mptypes.Type$memberApply.a;
  validate$typeparams(_t,mm.tp,types);
  if (!extendsType(_t, $mptypes.Type$memberApply))
    throw IncompatibleTypeException$meta$model("Type argument for 'Type' must be a supertype of " + this.string);
  var rv=this.meta.mt==='i'?AppliedMemberInterface$jsint(_t.t, {Container$AppliedMemberInterface:$mptypes.Container$memberApply, Type$AppliedMemberInterface:_t})
    :AppliedMemberClass$jsint(_t.t, {Container$AppliedMemberClass:$mptypes.Container$memberApply, Type$AppliedMemberClass:_t, Arguments$AppliedMemberClass:$mptypes.Arguments$memberApply});
  if (_t.a)rv.$targs=_t.a;
  return rv;
};ClassOrInterfaceDeclaration$meta$declaration.$$.prototype.memberApply.$crtmm$=function(){return{mod:$CCMM$,d:['ceylon.language.meta.declaration','ClassOrInterfaceDeclaration','$m','memberApply']};};
atr$(ClassOrInterfaceDeclaration$meta$declaration.$$.prototype,'container',function(){
  if (this.$parent)return this.$parent;
  var mm=getrtmm$$(this.tipo);
  var _c=mm.$cont;
  if (_c===0)return this.containingPackage;
  if (_c) {
    if (_c.t === undefined && _c.get && _c.$crtmm$) {
      return OpenValue$jsint(this.containingPackage, _c);
    }
    return typeLiteral$meta({Type$typeLiteral:_c});
  }
  return this.containingPackage;
},undefined,function(){return{mod:$CCMM$,$t:{t:'u',l:[{t:NestableDeclaration$meta$declaration},{t:Package$meta$declaration}]},d:['ceylon.language.meta.declaration.NestableDeclaration','$at','container']};});
ClassOrInterfaceDeclaration$meta$declaration.$$.prototype.annotatedMemberDeclarations=function($$$mptypes,inherited){
  var list=this.memberDeclarations({Kind$memberDeclarations:$$$mptypes.Kind$annotatedMemberDeclarations},inherited);
  if (list.size>0) {
    var rv=[];
    var bits = getAnnotationBitmask($$$mptypes.Annotation$annotatedMemberDeclarations);
    for (var i=0; i < list.size; i++) {
      var mm = getrtmm$$(list.$_get(i).tipo);
      if (mm.pa&bits) {
        rv.push(list.$_get(i));
        continue;
      }
      var anns = mm&&mm.an;
      if (typeof(anns)==='function'){anns=anns();mm.an=anns;}
      if (anns) for (var j=0; j<anns.length; j++) {
        if (is$(anns[j],$$$mptypes.Annotation$annotatedMemberDeclarations)) {
          rv.push(list.$_get(i));
          break;
        }
      }
    }
    if (rv.length)return ArraySequence(rv,{Element$ArraySequence:$$$mptypes.Kind$annotatedMemberDeclarations});
  }
  return empty();
};
ClassOrInterfaceDeclaration$meta$declaration.$$.prototype.annotatedMemberDeclarations.$crtmm$=function(){return{mod:$CCMM$,$t:{t:Sequential,a:{Element$Sequential:'Kind'}},ps:[],$cont:ClassOrInterfaceDeclaration$meta$declarationl,tp:{Kind:{sts:[{t:NestableDeclaration$meta$declaration}]},Annotation:{}},an:function(){return[shared(),actual()];},d:['ceylon.language.meta.declaration','ClassOrInterfaceDeclaration','$m','annotatedMemberDeclarations']};};
ClassOrInterfaceDeclaration$meta$declaration.$$.prototype.annotatedDeclaredMemberDeclarations=function ($$$mptypes){
  return this.annotatedMemberDeclarations({Kind$annotatedMemberDeclarations:$$$mptypes.Kind$annotatedDeclaredMemberDeclarations,
    Annotation$annotatedMemberDeclarations:$$$mptypes.Annotation$annotatedDeclaredMemberDeclarations},1);
};
ClassOrInterfaceDeclaration$meta$declaration.$$.prototype.annotatedDeclaredMemberDeclarations.$crtmm$=function(){return{mod:$CCMM$,$t:{t:Sequential,a:{Element$Sequential:'Kind'}},ps:[],$cont:ClassOrInterfaceDeclaration$meta$declarationl,tp:{Kind:{sts:[{t:NestableDeclaration$meta$declaration}]},Annotation:{}},an:function(){return[shared(),actual()];},d:['ceylon.language.meta.declaration','ClassOrInterfaceDeclaration','$m','annotatedDeclaredMemberDeclarations']};};

