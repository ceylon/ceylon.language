function(offset) {
  return offset==0 ? this : Byte(this.integer+offset);
}
