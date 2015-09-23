import java.lang {
    StrBuilder=StringBuilder,
    Char=Character,
    Str=String
}
import java.util {
    Locale
}
"""A string of characters. Each character in the string is a 
   [[32-bit Unicode character|Character]]. The internal 
   UTF-16 encoding is hidden from clients.
   
   Literal strings may be written between double quotes:
   
       "hello world"
       "\r\n"
       "\{#03C0} \{#2248} 3.14159"
       "\{GREEK SMALL LETTER PI} \{ALMOST EQUAL TO} 3.14159"
   
   Alternatively, a _verbatim string_ may be written between
   tripled double quotes.
   
   The _empty string_, `""`, is a string with no characters.
   
   A string is a [[Category]] of its [[characters]], and of 
   its substrings:
   
       'w' in greeting 
       "hello" in greeting
   
   Strings are [[summable|Summable]]:
   
       String greeting = "hello" + " " + "world";
   
   They are efficiently [[iterable|Iterable]]:
   
       for (char in "hello world") { ... }
   
   They are [[lists|List]] of [[characters|Character]]:
   
       value char = "hello world"[5];
   
   They are [[ranged|Ranged]]:
   
       String who = "hello world"[6...];
   
   Note that since `string[index]` evaluates to the optional 
   type `Character?`, it is often more convenient to write 
   `string[index..index]`, which evaluates to a `String` 
   containing a single character, or to the empty string 
   `""` if `index` refers to a position outside the string.
   
   It is easy to use comprehensions to transform strings:
   
       String { for (s in "hello world") if (s.letter) s.uppercased }
   
   Since a `String` has an underlying UTF-16 encoding, 
   certain operations are expensive, requiring iteration of 
   the characters of the string. In particular, [[size]]
   requires iteration of the whole string, and `get()`,
   `span()`, and `measure()` require iteration from the 
   beginning of the string to the given index."""
by ("Gavin")
tagged("Basic types", "Strings")
shared native final class String
        extends Object
        satisfies List<Character> &
                  Comparable<String> &
                  Summable<String> & 
                  Ranged<Integer,Character,String> {
    
    shared native new (
        "The characters that form this string."
        {Character*} characters) 
            extends Object() {}
    
    shared native new instance(String string) 
            extends Object() {}
    
    "This string, with all characters in lowercase.
     
     Conversion of uppercase characters to lowercase is
     performed according to a locale-independent mapping
     that produces incorrect results in certain locales
     (e.g. `tr-TR`).
     
     The resulting string may not have the same number of
     characters as this string, since the uppercase 
     representation of certain characters comprises multiple
     characters, for example the lowercase representation of 
     \{LATIN CAPITAL LETTER I WITH DOT ABOVE} is two 
     characters wide."
    shared native String lowercased;
    
    "This string, with all characters in uppercase.
     
     Conversion of lowercase characters to uppercase is
     performed according to a locale-independent mapping
     that produces incorrect results in certain locales
     (e.g. `tr-TR`).
     
     The resulting string may not have the same number of
     characters as this string, since the uppercase 
     representation of certain characters comprises multiple
     characters, for example the uppercase representation of 
     \{LATIN SMALL LETTER SHARP S} is SS."
    shared native String uppercased;
    
    "Split the string into tokens, using the given 
     [[predicate function|splitting]] to determine which 
     characters are separator characters.
     
         value pathElements = path.split('/'.equals);
     
     The flags [[discardSeparators]] and [[groupSeparators]]
     determine how separator characters should occur in the
     resulting stream.
     
     Note that for the case of the empty string, `split()` 
     always produces a stream containing a single empty 
     token. For example:
     
         \"\".split('/'.equals)
     
     evaluates to the nonempty stream `{ \"\" }`."
    shared native {String+} split(
            "A predicate that determines if a character is a
             separator characters at which to split. Default 
             to split at any 
             [[whitespace|Character.whitespace]] character."
            Boolean splitting(Character ch) => ch.whitespace,
            "Specifies that the separator characters
             occurring in the string should be discarded. If 
             `false`, they will be included in the resulting 
             iterator."
            Boolean discardSeparators=true,
            "Specifies that the separator tokens should be 
             grouped eagerly and not be treated as 
             single-character tokens. If `false` each 
             separator token will be of size `1`."
            Boolean groupSeparators=true);
    
    "The first character in the string."
    shared actual native Character? first;
    
    "The last character in the string."
    shared actual native Character? last;
    
    "The rest of the string, without its first character."
    shared actual native String rest;
    
    //"Get the character at the specified index, or `null` if
    // the index falls outside the bounds of this string."
    //shared actual native Character? get(Integer index);
    
    "Get the character at the specified index, where the 
     string is indexed from the _end_ of the string, or 
     `null` if the index falls outside the bounds of this 
     string."
    shared actual native Character? getFromLast(Integer index);
    
    "A sequence containing all indexes of this string."
    shared actual native Integer[] keys => 0:size;
    
    "Join the [[string representations|Object.string]] of 
     the given [[objects]], using this string as a separator."
    shared native String join({Object*} objects);
    
    "Split the string into lines of text, discarding line
     breaks. Recognized line break sequences are `\\n` and 
     `\\r\\n`."
    see (`value linesWithBreaks`)
    shared native {String*} lines 
            => split('\n'.equals, true, false)
               .spread(String.trimTrailing)('\r'.equals);
    
    "Split the string into lines of text with line breaks.
     Each line will be terminated by a line break sequence,
     `\\n` or `\\r\\n`."
    see (`value lines`)
    shared native {String*} linesWithBreaks
            => split('\n'.equals, false, false)
            .partition(2)
            .map((lineWithBreak)
                    => let (line = lineWithBreak[0], 
                            br = lineWithBreak[1])
                    if (exists br) then line+br else line);
    
    "A string containing the characters of this string, 
     after discarding [[whitespace|Character.whitespace]] 
     from the beginning and end of the string."
    shared native String trimmed => trim(Character.whitespace);
    
    "A string containing the characters of this string, 
     after discarding the characters matching the given 
     [[predicate function|trimming]] from the beginning and 
     end of the string.
     
         value trimmed = name.trim('_'.equals);
     
     A character is removed from the string if it matches
     the given predicate and if either:
     
     - every character occurring earlier in the string also 
       matches the predicate, or
     - every character occurring later in the string also
       matches the predicate."
    shared actual native String trim(
            "The predicate function that determines whether
             a character should be trimmed"
            Boolean trimming(Character element));
    
    "A string containing the characters of this string, 
     after discarding the characters matching the given 
     [[predicate function|trimming]] from the 
     beginning of the string.
     
     A character is removed from the string if it matches
     the given predicate and every character occurring 
     earlier in the string also matches the predicate."
    shared actual native String trimLeading(
            "The predicate function that determines whether
             a character should be trimmed"
            Boolean trimming(Character element));
    
    "A string containing the characters of this string, 
     after discarding the characters matching the given 
     [[predicate function|trimming]] from the end of the 
     string.
     
     A character is removed from the string if it matches
     the given predicate and every character occurring 
     later in the string also matches the predicate."
    shared actual native String trimTrailing(
            "The predicate function that determines whether
             a character should be trimmed"
            Boolean trimming(Character element));

    "A string containing the characters of this string after 
     collapsing strings of [[whitespace|Character.whitespace]] 
     into single space characters and discarding whitespace 
     from the beginning and end of the string."
    shared native String normalized;
    
    "A string containing the characters of this string, with
     the characters in reverse order."
    shared native actual String reversed;
    
    "Determines if this string contains a character at the
     given [[index]], that is, if `0<=index<size`."
    shared native actual Boolean defines(Integer index);
    
    "A string containing the characters of this string 
     between the given indexes. If the [[start index|from]] 
     is the same as the [[end index|to]], return a string 
     with a single character. If the start index is larger 
     than the end index, return the characters in the 
     reverse order from the order in which they appear in 
     this string. If both the start index and the end index 
     are larger than the last index in the string, or if 
     both the start index and the end index are smaller than
     the first index in the string, return the empty string. 
     Otherwise, if the last index is larger than the last 
     index in the string, return all characters from the 
     start index to last character of the string."
    shared actual native String span(Integer from, Integer to);
    
    "A string containing the characters of this string from 
     the given [[start index|from]] inclusive to the end of 
     the string. If the start index is larger than the last 
     index of the string, return the empty string. If the
     start index is negative, return this string."
    shared actual native String spanFrom(Integer from)
            => from<size then span(from, size) else "";
    
    "A string containing the characters of this string from 
     the start of the string up to and including the given 
     [[end index|to]]. If the end index is negative, return 
     the empty string. If the end index is larger than the
     last index in this string, return this string."
    shared actual native String spanTo(Integer to)
            => to>=0 then span(0, to) else "";
    
    "A string containing the characters of this string 
     beginning at the given [[start index|from]], returning 
     a string no longer than the given [[length]]. If the 
     portion of this string starting at the given index is 
     shorter than the given length, return the portion of 
     this string from the given index until the end of this 
     string. Otherwise, return a string of the given length. 
     If the start index is larger than the last index of the 
     string, return the empty string."
    shared native actual String measure(Integer from, 
                                        Integer length);
    
    "Select the first characters of this string, returning a 
     string no longer than the given [[length]]. If this 
     string is shorter than the given length, return this 
     string. Otherwise, return a string of the given length."
    shared native actual String initial(Integer length);
    
    "Select the last characters of the string, returning a 
     string no longer than the given [[length]]. If this 
     string is shorter than the given length, return this 
     string. Otherwise, return a string of the given length."
    shared native actual String terminal(Integer length);
    
    "Return two strings, the first containing the characters
     that occur before the given [[index]], the second with
     the characters that occur after the given `index`. If 
     the given `index` is outside the range of indices of 
     this string, one of the returned strings will be empty."
    shared native actual [String,String] slice(Integer index);
    
    "The length of the string (the number of characters it 
     contains). In the case of the empty string, the string 
     has length zero. Note that this operation is 
     potentially costly for long strings, since the
     underlying representation of the characters uses a
     UTF-16 encoding. Use of [[longerThan]] or 
     [[shorterThan]] is highly recommended."
    see (`function longerThan`, `function shorterThan`)
    shared actual native Integer size;
    
    "The index of the last character in the string, or 
     `null` if the string has no characters. Note that this 
     operation is potentially costly for long strings, since 
     the underlying representation of the characters uses a 
     UTF-16 encoding. For any nonempty string:
     
         string.lastIndex == string.size-1"
    shared actual native Integer? lastIndex {
        if (size==0) {
            return null;
        }
        else {
            return size-1;
        }
    }
    
    "An iterator for the characters of the string."
    shared actual native Iterator<Character> iterator();
    
    "Returns the character at the given [[index]] in the 
     string, or `null` if the index is before the start of 
     the string or past the end of string. The first 
     character in the string occurs at index zero. The last 
     character in the string occurs at index 
     `string.size-1`."
    shared actual native Character? getFromFirst(Integer index);
    
    //shared actual native Boolean->Character? lookup(Integer index);
    
    "Determines if the given object is a `String` and, if 
     so, if it occurs as a substring of this string, or if 
     the object is a `Character` that occurs in this string. 
     That is to say, a string is considered a [[Category]] 
     of its substrings and of its characters."
    shared actual native Boolean contains(Object element);
    
    shared actual native Boolean startsWith(List<Anything> substring);
    
    shared actual native Boolean endsWith(List<Anything> substring);
    
    "Returns the concatenation of this string with the
     given string."
    shared actual native String plus(String other);
    
    "Returns a string formed by repeating this string the 
     given number of [[times]], or the empty string if
     `times<=0`."
    shared actual native String repeat(Integer times);
    
    "Returns a string formed by replacing every occurrence 
     in this string of the given [[substring]] with the 
     given [[replacement]] string, working from the start of
     this string to the end."
    shared native String replace(String substring, 
                                 String replacement);
    
    "Returns a string formed by replacing the first 
     occurrence in this string of the given [[substring]], 
     if any, with the given [[replacement]] string."
    shared native String replaceFirst(String substring, 
                                      String replacement);
    
    "Returns a string formed by replacing the last 
     occurrence in this string of the given [[substring]], 
     if any, with the given [[replacement]] string."
    shared native String replaceLast(String substring, 
                                     String replacement);
    
    "Compare this string with the given string 
     lexicographically, according to the Unicode code points
     of the characters.
     
     This defines a locale-independent collation that is
     incorrect in some locales."
    shared actual native Comparison compare(String other) {
        value min = smallest(size, other.size);
        for (i in 0:min) {
            assert (exists thisChar = this.getFromFirst(i),
                    exists thatChar = other.getFromFirst(i));
            if (thisChar!=thatChar) {
                return thisChar <=> thatChar;
            }
        }
        return size <=> other.size;
    }
    
    "Compare this string with the given string 
     lexicographically, ignoring the case of the characters.
     That is, by considering two characters `x` and `y` as
     equal if:
     
     - `x == y`,
     - `x.uppercased == y.uppercased`, or
     - `x.lowercased == y.lowercased`.
     
     This defines a locale-independent collation that is
     incorrect in some locales."
    see (`value Character.lowercased`, 
         `value Character.uppercased`)
    shared native Comparison compareIgnoringCase(String other) {
        value min = smallest(size, other.size);
        for (i in 0:min) {
            assert (exists thisChar = this.getFromFirst(i),
                    exists thatChar = other.getFromFirst(i));
            if (thisChar!=thatChar && 
                thisChar.uppercased!=thatChar.uppercased &&
                thisChar.lowercased!=thatChar.lowercased) {
                return thisChar.lowercased <=> thatChar.lowercased;
            }
        }
        return size <=> other.size;
    }
    
    "Determines if this string is longer than the given
     [[length]]. This is a more efficient operation than
     `string.size>length`."
    see (`value size`)
    shared actual native Boolean longerThan(Integer length);
    
    "Determines if this string is shorter than the given
     [[length]]. This is a more efficient operation than
     `string.size>length`."
    see (`value size`)
    shared actual native Boolean shorterThan(Integer length);
    
    "Determines if the given object is a `String`, and if 
     so, if this string has the same [[length|size]], and 
     the same [[characters]], in the same order, as the 
     given [[string|that]]."
    shared actual native Boolean equals(Object that) {
        if (is String that) {
            if (size!=that.size) {
                return false;
            }
            value min = smallest(size, that.size);
            for (i in 0:min) {
                assert (exists thisChar = this.getFromFirst(i),
                        exists thatChar = that.getFromFirst(i));
                if (thisChar!=thatChar) {
                    return false;
                }
            }
            return true;
        }
        else {
            return false;
        }
    }
    
    "Compare this string with the given string, ignoring the 
     case of the characters. That is, by considering two 
     characters `x` and `y` as equal if:
     
     - `x == y`,
     - `x.uppercased == y.uppercased`, or
     - `x.lowercased == y.lowercased`."
    see (`value Character.lowercased`, 
         `value Character.uppercased`)
    shared native Boolean equalsIgnoringCase(String that) {
        if (size!=that.size) {
            return false;
        }
        value min = smallest(size, that.size);
        for (i in 0:min) {
            assert (exists thisChar = this.getFromFirst(i),
                    exists thatChar = that.getFromFirst(i));
            if (thisChar!=thatChar && 
                thisChar.uppercased!=thatChar.uppercased &&
                thisChar.lowercased!=thatChar.lowercased) {
                return false;
            }
        }
        return true;
    }
    
    shared actual native Integer hash;
    
    "This string."
    shared actual native String string => this;
    
    "Determines if this string has no characters, that is, 
     if it has zero [[size]]. This is a _much_ more 
     efficient operation than `string.size==0`."
    see (`value size`)
    shared actual native Boolean empty;
    
    "This string."
    shared actual native String coalesced => this;
    
    "This string."
    shared actual native String clone() => this;
    
    "Pad this string with the given [[character]], producing 
     a string of the given minimum [[size]], centering the
     string."
    shared native String pad(Integer size, 
        "The padding character"
        Character character=' ');
    
    "Left pad this string with the given [[character]], 
     producing a string of the given minimum [[size]]."
    shared native String padLeading(Integer size, 
        "The padding character"
        Character character=' ');
    
    "Right pad this string with the given [[character]], 
     producing a string of the given minimum [[size]]."
    shared native String padTrailing(Integer size, 
        "The padding character"
        Character character=' ');
    
    "Efficiently copy the characters in the segment
     `sourcePosition:length` of this string to the segment 
     `destinationPosition:length` of the given 
     [[character array|destination]]."
    shared native 
    void copyTo(
        "The array into which to copy the elements."
        Array<Character> destination,
        "The index of the first element in this array to 
         copy."
        Integer sourcePosition = 0,
        "The index in the given array into which to copy the 
         first element."
        Integer destinationPosition = 0,
        "The number of elements to copy."
        Integer length 
                = smallest(size - sourcePosition,
                    destination.size - destinationPosition));
    
    shared native Integer? firstOccurrence(Character element, 
        Integer from=0, Integer length=size-from);
    shared native Integer? lastOccurrence(Character element, 
        Integer from=0, Integer length=size-from);
    
    /*shared actual native Boolean occurs(Character element, Integer from, Integer length);
    shared actual native Boolean occursAt(Integer index, Character element);
    shared actual native Boolean includes(List<Character> sublist, Integer from);
    shared actual native Boolean includesAt(Integer index, List<Character> sublist);
        
    shared actual native Integer? firstOccurrence(Character element, Integer from, Integer length);
    shared actual native Integer? lastOccurrence(Character element, Integer from, Integer length);
    shared actual native Integer? firstInclusion(List<Character> sublist, Integer from);
    shared actual native Integer? lastInclusion(List<Character> sublist, Integer from);
    
    shared actual native Integer countOccurrences(Character sublist, Integer from, Integer length);
    shared actual native Integer countInclusions(List<Character> sublist, Integer from);*/
        
    shared actual native Boolean largerThan(String other); 
    shared actual native Boolean smallerThan(String other); 
    shared actual native Boolean notSmallerThan(String other); 
    shared actual native Boolean notLargerThan(String other);
    
    /*shared actual native void each(void step(Character element));
    shared actual native Integer count(Boolean selecting(Character element));
    shared actual native Boolean every(Boolean selecting(Character element));
    shared actual native Boolean any(Boolean selecting(Character element));
    shared actual native Result|Character|Null reduce<Result>
            (Result accumulating(Result|Character partial, Character element));
    shared actual native Character? find(Boolean selecting(Character element));
    shared actual native Character? findLast(Boolean selecting(Character element));
    shared actual native <Integer->Character>? locate(Boolean selecting(Character element));
    shared actual native <Integer->Character>? locateLast(Boolean selecting(Character element));*/
}

shared native("jvm") final class String
        extends Object
        satisfies List<Character> &
        Comparable<String> &
        Summable<String> & 
        Ranged<Integer,Character,String> {
    
    Str str;
    
    shared native("jvm") new ({Character*} characters) 
            extends Object() {
        
        if (is String characters) {
            str = characters.str;
        }
        else {
            StrBuilder sb = StrBuilder();
            characters.each((char) {
                sb.appendCodePoint(char.integer);
            });
            str = sb.string.str;
        }
    }
    
    shared native("jvm") new instance(String string) 
            extends Object() {
        str = string.str;
    }
    
    shared native("jvm") String lowercased 
            => str.toLowerCase(Locale.\iROOT);
    
    shared native("jvm") String uppercased
            => str.toUpperCase(Locale.\iROOT);
    
    shared native("jvm") {String+} split(
        Boolean splitting(Character ch) => ch.whitespace,
        Boolean discardSeparators=true,
        //TODO!
        Boolean groupSeparators=true) 
            => object satisfies {String+} {
        iterator() 
                => object satisfies Iterator<String> {
            variable value index = 0;
            shared actual String|Finished next() {
                value len = str.length();
                if (index>=len) {
                    return finished;
                }
                value builder = StrBuilder();
                while (index<len) {
                    value cp = str.codePointAt(index);
                    if (splitting(cp.character)) {
                        if (discardSeparators) {
                            index += Char.charCount(cp);
                            break;
                        }
                        else {
                            if (builder.length()==0) {
                                index += Char.charCount(cp);
                                return cp.character.string;
                            }
                            else {
                                break;
                            }
                        }
                    }
                    else {
                        index += Char.charCount(cp);
                        builder.appendCodePoint(cp);
                    }
                }
                return builder.string;
            }
        };
    };
    
    shared actual native("jvm") Character? first
            => str.length()>0 then str.charAt(0);
    
    shared actual native("jvm") Character? last
            => str.length()>0 then str.charAt(str.length()-1);
    
    shared actual native("jvm") String rest
            => str.length()>0 then str.substring(1) else "";
    
    shared native("jvm") String join({Object*} objects) {
        StrBuilder sb = StrBuilder();
        for (element in objects) {
            if (sb.length()>0) {
                sb.append(str);
            }
            sb.append(element);
        }
        return sb.string;
    }
    
    shared actual native("jvm") String trim(
        Boolean trimming(Character element))
            //TODO:
            => trimLeading(trimming).trimTrailing(trimming);
    
    shared actual native("jvm") String trimLeading(
        Boolean trimming(Character element)) {
        variable value from = 0;
        while (from < str.length()) {
            value cp = Char.codePointAt(str, from);
            if (!trimming(cp.character)) {
                break;
            }
            from += Char.charCount(cp);
        }
        return str.substring(from);
    }
    shared actual native("jvm") String trimTrailing(
        Boolean trimming(Character element)) {
        variable value to = str.length();
        while (to > 0) {
            value cp = Char.codePointBefore(str, to);
            if (!trimming(cp.character)) {
                break;
            }
            to -= Char.charCount(cp);
        }
        return str.substring(0, to);
    }
    
    shared native("jvm") String normalized {
        value result = StrBuilder(str.length());
        variable value previousWasWhitespace=false;
        variable value i=0;
        while (i<str.length()) {
            value c = Char.codePointAt(str, i);
            value isWhitespace = Char.isWhitespace(c);
            if (!isWhitespace) {
                result.appendCodePoint(c);
            }
            else if (!previousWasWhitespace) {
                result.append(" ");
            }
            previousWasWhitespace = isWhitespace;
            i+=Char.charCount(c);
        }
        // TODO Should be able to figure out the indices to 
        //      substring on while iterating
        return result.string.trimmed;
    }
    
    shared native("jvm") actual String reversed {
        value len = size;
        if (len < 2) {
            return this;
        }
        // FIXME: this would be better to directly build the Sequence<Character>
        value builder = StrBuilder(str.length());
        variable value offset = str.length();
        while (offset > 0) {
            value cp = str.codePointBefore(offset);
            builder.appendCodePoint(cp);
            offset -= Char.charCount(cp);
        }
        return builder.string;
    }
    
    shared native("jvm") actual Boolean defines(Integer index)
            => 0<=index<size;
    
    shared actual native("jvm") String span(
        variable Integer from, variable Integer to) {
        value len = size;
        if (len == 0) {
            return "";
        }
        value reverse = to < from;
        if (reverse) {
            value _tmp = to;
            to = from;
            from = _tmp;
        }
        if (to < 0 || from >= len) {
            return "";
        }
        if (to >= len) {
            to = len - 1;
        }
        if (from < 0) {
            from = 0;
        }
        value start = str.offsetByCodePoints(0, from);
        value end = str.offsetByCodePoints(start, to - from + 1);
        value result = str.substring(start, end);
        return if (reverse) then result.reversed else result;
    }
    
    shared actual native("jvm") String spanFrom(Integer from)
            => from<size then span(from, size) else "";
    
    shared actual native("jvm") String spanTo(Integer to)
            => to>=0 then span(0, to) else "";
    
    shared native("jvm") actual String measure(
        Integer from, Integer length) {
        value fromIndex = from;
        value len = size;
        if (fromIndex >= len || length <= 0) {
            return "";
        }
        value resultLength = 
                if (fromIndex + length > len)
        then len - fromIndex else length;
        value start = str.offsetByCodePoints(0, fromIndex);
        value end = str.offsetByCodePoints(start, resultLength);
        return str.substring(start, end);
    }
    
    shared native("jvm") actual String initial(Integer length) {
        if (length <= 0) {
            return "";
        }
        else if (length >= size) {
            return this;
        }
        else {
            value offset = str.offsetByCodePoints(0, length);
            return str.substring(0, offset);
        }
    }
    
    shared native("jvm") actual String terminal(Integer length) {
        if (length <= 0) {
            return "";
        }
        else if (length >= size) {
            return this;
        }
        else {
            value offset = 
                    str.offsetByCodePoints(0, str.length()-length);
            return str.substring(offset, str.length());
        }
    }
    
    shared native("jvm") actual [String,String] slice(Integer index) {
        String first;
        String second;
        if (index<=0) {
            first = "";
            second = this;
        }
        else if (index>=str.length()) {
            first = this;
            second = "";
        }
        else {
            value i = str.offsetByCodePoints(0, index);
            first = str.substring(0, i);
            second = str.substring(i);
        }
        return [first,second];
    }
    
    shared actual native("jvm") Integer size
            => str.codePointCount(0, str.length());
    
    //TODO: why did I have to implement this?
    shared actual native("jvm") Integer? lastIndex 
            => let (len=size) (len>0 then len-1);
    
    shared actual native("jvm") Iterator<Character> iterator()
            => object satisfies Iterator<Character> {
        variable value offset = 0;
        shared actual Character|Finished next() {
            if (offset < str.length()) {
                value codePoint = str.codePointAt(offset);
                offset += Char.charCount(codePoint);
                return codePoint.character;
            }
            else {
                return finished;
            }
        }
    };
    
    shared actual native("jvm") Character? getFromFirst(Integer index) {
        try {
            return str.codePointAt(
                str.offsetByCodePoints(0, index))
                    .character;
        }
        catch (iobe) {
            return null;
        }
    }
    
    shared actual native("jvm") Character? getFromLast(Integer index) {
        try {
            return str.codePointAt(
                str.offsetByCodePoints(str.length(), -index-1))
                    .character;
        }
        catch (iobe) {
            return null;
        }
    }
    
    shared actual native("jvm") Boolean contains(Object element) {
        if (is String element) {
            return str.indexOf(element) >= 0;
        }
        else if (is Character element) {
            return str.indexOf(element.integer) >= 0;
        }
        else {
            return false;
        }
    }
    
    shared actual native("jvm") Boolean startsWith(List<Anything> substring) 
            => if (is String substring) 
    then str.startsWith(substring) 
    else super.startsWith(substring);
    
    shared actual native("jvm") Boolean endsWith(List<Anything> substring) 
            => if (is String substring) 
    then str.endsWith(substring) 
    else super.startsWith(substring);
    
    shared actual native("jvm") String plus(String other)
            => StrBuilder().append(this).append(other).string;
    
    shared actual native("jvm") String repeat(Integer times) {
        value len = str.length();
        if (times<=0 || len==0) {
            return "";
        }
        if (times==1) {
            return this;
        }
        value builder = StrBuilder(len*times);
        for (i in 0:times) {
            builder.append(this);
        }
        return builder.string;
        
    }
    
    shared native("jvm") String replace(String substring, 
        String replacement) {
        variable Integer index = str.indexOf(substring);
        if (index<0) {
            return this;
        }
        value builder = StrBuilder(str);
        while (index>=0) {
            builder.replace(index, 
                index+substring.str.length(), 
                replacement);
            index = 
                    str.indexOf(substring, 
                index+replacement.str.length());
        }
        return builder.string;
    }
    
    shared native("jvm") String replaceFirst(String substring, 
        String replacement) {
        Integer index = str.indexOf(substring);
        if (index<0) {
            return this;
        }
        value builder = StrBuilder(str);
        builder.replace(index, 
            index+substring.str.length(), 
            replacement);
        return builder.string;
    }
    
    shared native("jvm") String replaceLast(String substring, 
        String replacement) {
        Integer index = str.lastIndexOf(substring);
        if (index<0) {
            return this;
        }
        value builder = StrBuilder(str);
        builder.replace(index, 
            index+substring.str.length(), 
            replacement);
        return builder.string;
    }
    
    shared actual native("jvm") Comparison compare(String other) 
            => str.compareTo(other) <=> 0;
    
    shared native("jvm") Comparison compareIgnoringCase(String other)
            => str.compareToIgnoreCase(other) <=> 0;
    
    shared actual native("jvm") Boolean longerThan(Integer length) {
        try {
            str.offsetByCodePoints(0, length+1);
            return true;
        }
        catch (iobe) {
            return false;
        }
    }
    
    shared actual native("jvm") Boolean shorterThan(Integer length) {
        try {
            str.offsetByCodePoints(0, length);
            return false;
        }
        catch (iobe) {
            return true;
        }
    }
    
    shared actual native("jvm") Boolean equals(Object that) 
            => if (is String that) then str.equals(that) else false;
    
    shared native("jvm") Boolean equalsIgnoringCase(String that)
            => str.equalsIgnoreCase(that);
    
    shared actual native("jvm") Integer hash => str.hash;
    
    shared actual native("jvm") Boolean empty => str.empty;
    
    shared native("jvm") String pad(Integer size, 
        Character character=' ') {
        value length = str.length();
        if (size<=length) {
            return this;
        }
        value leftPad = (size-length)/2;
        value rightPad = leftPad + (size-length)%2;
        value builder = StrBuilder();
        for (i in 0:leftPad) {
            builder.appendCodePoint(character.integer);
        }
        builder.append(str);
        for (i in 0:rightPad) {
            builder.appendCodePoint(character.integer);
        }
        return builder.string;
    }
    
    shared native("jvm") String padLeading(Integer size, 
        Character character=' ') {
        value length = str.length();
        if (size<=length) {
            return this;
        }
        value leftPad = size-length;
        value builder = StrBuilder();
        for (i in 0:leftPad) {
            builder.appendCodePoint(character.integer);
        }
        builder.append(str);
        return builder.string;
    }
    
    shared native("jvm") String padTrailing(Integer size, 
        Character character=' ') {
        value length = str.length();
        if (size<=length) {
            return this;
        }
        value rightPad = size-length;
        value builder = StrBuilder(str);
        for (i in 0:rightPad) {
            builder.appendCodePoint(character.integer);
        }
        return builder.string;
    }
    
    shared native("jvm") 
    void copyTo(
        Array<Character> destination,
        Integer sourcePosition = 0,
        Integer destinationPosition = 0,
        Integer length 
                = smallest(size - sourcePosition,
            destination.size - destinationPosition)) {
        variable Integer count = 0;
        variable value index = 
                str.offsetByCodePoints(0,sourcePosition);
        while (count<length) {
            value codePoint = str.codePointAt(index);
            //TODO!!!!
            //((int[])destination.toArray())[count+dest] = codePoint;
            destination.set(
                count+destinationPosition, 
                codePoint.character);
            index += Char.charCount(codePoint);
            count++;
        }
    }
    
    shared native("jvm") Integer? firstOccurrence(Character element, 
            variable Integer from, variable Integer length) {
        if (from>=str.length() || length<=0) {
            return null;
        }
        if (from<0) {
            length+=from;
            from = 0;
        }
        Integer start;
        try {
            start = str.offsetByCodePoints(0, from);
        }
        catch (e) {
            return null;
        }
        value index = str.indexOf(element.integer, start);
        if (index >= 0) {
            value result = str.codePointCount(0, index);
            return result<from+length then result;
        }
        else {
            return null;
        }
    }
    
    shared native("jvm") Integer? lastOccurrence(Character element, 
            variable Integer from, variable Integer length) {
        if (from>=str.length() || length<=0) {
            return null;
        }
        if (from<0) {
            length+=from;
            from = 0;
        }
        Integer start;
        try {
            start = str.offsetByCodePoints(str.length(), -from - 1);
        }
        catch (e) {
            return null;
        }
        value index = str.lastIndexOf(element.integer, start);
        if (index >= 0) {
            value dist = str.codePointCount(index, str.length());
            return dist<from+length then str.codePointCount(0, index);
        }
        else {
            return null;
        }
    }
    
    /*shared actual native("jvm") Boolean occurs(Character element, Integer from, Integer length);
     shared actual native("jvm") Boolean occursAt(Integer index, Character element);
     shared actual native("jvm") Boolean includes(List<Character> sublist, Integer from);
     shared actual native("jvm") Boolean includesAt(Integer index, List<Character> sublist);
     
     shared actual native("jvm") Integer? firstOccurrence(Character element, Integer from, Integer length);
     shared actual native("jvm") Integer? lastOccurrence(Character element, Integer from, Integer length);
     shared actual native("jvm") Integer? firstInclusion(List<Character> sublist, Integer from);
     shared actual native("jvm") Integer? lastInclusion(List<Character> sublist, Integer from);
     
     shared actual native("jvm") Integer countOccurrences(Character sublist, Integer from, Integer length);
     shared actual native("jvm") Integer countInclusions(List<Character> sublist, Integer from);*/
     
     shared actual native("jvm") Boolean largerThan(String other) 
            => str.compareTo(other)>0; 
     shared actual native("jvm") Boolean smallerThan(String other) 
             => str.compareTo(other)<0; 
     shared actual native("jvm") Boolean notSmallerThan(String other) 
             => str.compareTo(other)>=0; 
     shared actual native("jvm") Boolean notLargerThan(String other) 
             => str.compareTo(other)<=0;
    
    /*shared actual native("jvm") void each(void step(Character element));
     shared actual native("jvm") Integer count(Boolean selecting(Character element));
     shared actual native("jvm") Boolean every(Boolean selecting(Character element));
     shared actual native("jvm") Boolean any(Boolean selecting(Character element));
     shared actual native("jvm") Result|Character|Null reduce<Result>
            (Result accumulating(Result|Character partial, Character element));
     shared actual native("jvm") Character? find(Boolean selecting(Character element));
     shared actual native("jvm") Character? findLast(Boolean selecting(Character element));
     shared actual native("jvm") <Integer->Character>? locate(Boolean selecting(Character element));
     shared actual native("jvm") <Integer->Character>? locateLast(Boolean selecting(Character element));*/

    shared actual native("jvm") String coalesced => this;
    
    shared actual native("jvm") String clone() => this;
    
    shared actual native("jvm") Integer[] keys => 0:size;
    
    shared actual native("jvm") String string => this;

    
}
