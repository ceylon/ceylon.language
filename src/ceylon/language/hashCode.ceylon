"""Computes the hash value for items in input.
   
   This function is useful to implement [[Object.hash]]
   for objects containing multiples fields.
   
   For example,
   ```ceylon
   class Person(String firstname, String lastname, Integer age) {
       shared actual Integer hash => hashCode(firstname, lastname, age);
   }
   ```
   __Warning__: For a single element `x`, `hashCode(x)` is not equal to `x.hash`"""
see(`value Object.hash`)
shared Integer hashCode(Anything+ items) {
    variable Integer hash = 1;
    for (item in items) {
        hash = 31 * hash + (item?.hash else 0);
    }
    return hash;
}
