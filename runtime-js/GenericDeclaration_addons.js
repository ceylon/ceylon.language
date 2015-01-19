//Addendum to GenericDeclaration
atr$(GenericDeclaration$meta$declaration.$$.prototype,'typeParameterDeclarations',function(){
  if (this.tipo) {
    var mm=getrtmm$$(this.tipo);
    var tps=mm.tp;
    if (tps) {
      var rv=[];
      for (var tp in tps) {
        rv.push(OpenTypeParam$jsint(this.tipo,tp));
      }
      return rv.length===0?empty():ArraySequence(rv,{Element$ArraySequence:{t:TypeParameter$meta$declaration}});
    }
    return empty();
  }
  console.log("TODO GenericDeclaration.typeParameterDeclarations without a type");
  return empty();
},undefined,function(){return{mod:$CCMM$,$t:{t:Sequential,a:{Element$Sequential:{t:TypeParameter$meta$declaration}}},$cont:GenericDeclaration,an:function(){return[shared(),actual()];},d:['ceylon.language.meta.declaration','GenericDeclaration','$at','typeParameterDeclarations']};});
GenericDeclaration$meta$declaration.$$.prototype.getTypeParameterDeclaration=function (name$14){
  if (this.tipo) {
    var mm=getrtmm$$(this.tipo);
    var tps=mm.tp;
    if (tps) {
      for (var tp in tps) {
        var tpnm = tp;
        if (tp.indexOf('$')>0)tpnm=tp.substring(0,tp.indexOf('$'));
        if (name$14==tpnm) {
          return OpenTypeParam$jsint(this.tipo,tp);
        }
      }
    }
    return null;
  }
  console.log("TODO GenericDeclaration.getTypeParameterDeclaration(" + name$14 + ") without a type");
  return null;
};
GenericDeclaration$meta$declaration.$$.prototype.getTypeParameterDeclaration.$crtmm$=function(){return{mod:$CCMM$,$t:{ t:'u', l:[{t:Null},{t:TypeParameter$meta$declaration}]},ps:[{nm:'name',mt:'prm',$t:{t:$_String}}],$cont:GenericDeclaration,an:function(){return[shared(),actual()];},d:['ceylon.language.meta.declaration','GenericDeclaration','$m','getTypeParameterDeclaration']};};
 
