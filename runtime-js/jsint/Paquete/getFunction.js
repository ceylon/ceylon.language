function getFunction(name$6){
  var f = this._pkg[name$6];
  if (f && f['mt']==='m') {
    return OpenFunction$jsint(this, f);
  }
  return null;
}
