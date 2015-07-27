function initialize(context,$mpt){
  var me=partialImpl$;

  function getReferredInstance2(ctxt, refid) {
    var referred = ctxt.leakInstance(refid);
    if (is$(referred,{t:Partial$serialization})) {
      referred = referred.instance_;
    }
    return referred;
  }
  function getReferredInstance(ctxt, st, rc) {
    var x=st.$_get(rc);
    return getReferredInstance2(ctxt, x);
  }

  function initializeObject(inst) {
    var reachables = inst.getT$all()[inst.getT$name()].ser$refs$(inst);
    var numLate = 0;
    for (var i=0;i<reachables.length;i++) {
      if (is$(r,{t:Member$serialization}) && r.attribute.late) {
        numLate++;
      } else if (is$(r,{t:Outer$serialization})) {
        numLate++;
      }
    }
    if (me.state.size < reachables.length-numLate) {
      var missingNames=[];
      for (var i=0;i<reachables.length;i++) {
        if (!missingNames.contains(reachables[i])) {
          missingNames.push(reachables[i]);
        }
      }
      var next;for(var iter=me.state.keys.iterator;(next=iter.next())!==finished();) {
        for (var i=0;i<missingNames.length;i++) {
          if (missingNames[i].equals(next)) {
            missingNames.splice(i,1);
            break;
          }
        }
      }
      throw DeserializationException("lacking sufficient state for instance with id " + me.id + ": " + missingNames.string);
    }
    for (var i=0;i<reachables.length;i++) {
      var ref=reachables[i];
      if (is$(ref,{t:Member$serialization})) {
        if (ref.attribute.late && !me.state.contains(member)
            || state.$_get(member) === uninitializedLateValue$serialization()) {
          continue;
        }
        console.log("TODO get the type of the member " + ref.string);
        var referredInstance = getReferredInstance(context, me.state, ref);
        console.log("TODO get type of instance " + referredInstance.string);
        inst.ser$set$(ref, referredInstance);
      } else if (is$(ref,{t:Outer$serialization})) {
        continue;
      } else {
        throw AssertionException("unexpected ReachableReference " + ref.string);
      }
    }
  }
  var inst=me.instance_;
  if (is$(inst,{t:$_Array})) {
    console.log("TODO! initialize array");
  } else if (is$(inst,{t:Tuple})) {
    console.log("TODO! initialize tuple");
  } else {
    initializeObject(inst);
  }
  me.state=null;
}
