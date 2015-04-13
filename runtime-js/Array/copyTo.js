function(other,srcpos,dstpos,length){
    if(srcpos===undefined)srcpos=0;
    if(dstpos===undefined)dstpos=0;
    if (length===0)return;
    if(length===undefined)length=Math.min(this.size-srcpos,other.size-dstpos);
    var endpos=srcpos+length-1;
    if (srcpos<0||srcpos>=this.size||length<1||dstpos+length>other.size||endpos>=this.size)throw Exception("Array index out of bounds");
    if (other===this && dstpos>srcpos) {
      dstpos+=length-1;
      for (var i=endpos; i>=srcpos; i--) {
        other[dstpos--]=this[i];
      }
    } else {
      for (var i=srcpos;i<=endpos;i++) {
        other[dstpos++]=this[i];
      }
    }
}
