function(types,$mptypes){
  var mm=this.tipo.$crtmm$;
  var ta={t:this.tipo};
  validate$typeparams(ta,mm.tp,types);
  validate$params(mm.ps,$mptypes.Arguments$apply,"Wrong number of arguments when applying function");
  return ta.a?AppliedFunction$jsint(this.tipo,{Type$AppliedFunction:mm.$t,Arguments$AppliedFunction:tupleize$params(mm.ps,ta.a)},undefined,ta.a):
    AppliedFunction$jsint(this.tipo,{Type$AppliedFunction:mm.$t,Arguments$AppliedFunction:tupleize$params(mm.ps)});
}
