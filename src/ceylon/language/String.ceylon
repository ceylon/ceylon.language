doc "A string of characters. Each character in the string is 
     a 32-bit Unicode character. The internal UTF-16 
     encoding is hidden from clients."
by "Gavin"
shared abstract class String()
        extends Object()
        satisfies List<Character> & Comparable<String> &
                  Ranged<Integer,String> & 
                  FixedSized<Character> &
                  Summable<String> & Castable<String> &
                  Cloneable<String> {
    
    doc "The characters in this string."
    shared formal Character[] characters;
    
    doc "This string, with all characters in lowercase."
    shared formal String lowercased;
    
    doc "This string, with all characters in uppercase."
    shared formal String uppercased;
    
    doc "Split the string into tokens, using the given
         separator characters. If no separator characters
         are given, split the string at any Unicode 
         whitespace character."
    shared formal Iterable<String> split(
            doc "The separator characters at which to split.
                 If `null`, split at any Unicode whitespace
                 character."
            Iterable<Character>? separators=null,
            doc "Specifies that the separator characters
                 occurring in the string should be discarded.
                 If `false`, they will be included in the
                 resulting iterator."
            Boolean discardSeparators=false);
    
    doc "Join the given strings, using this string as a 
         separator."
    shared formal String join(String... strings);
    
    doc "Split the string into lines of text."
    shared formal Iterable<String> lines;

    doc "This string, after discarding whitespace from the 
         beginning and end of the string."
    shared formal String trimmed;

    doc "This string, after collapsing strings of whitespace 
         into single space characters and discarding whitespace 
         from the beginning and end of the string."
    shared formal String normalized;
    
    doc "This string, with the characters in reverse order."
    shared formal String reversed;
    
    doc "Select the characters between the given indexes.
         If the start index is the same as the end index,
         return a string with a single character.
         If the start index is larger than the end index, 
         return the characters in the reverse order from
         the order in which they appear in this string.
         If both the start index and the end index are 
         larger than the last index in the string, return 
         the empty string. Otherwise, if the last index is 
         larger than the last index in the sequence, return
         all characters from the start index to last 
         character of the string."
    shared actual formal String span(Integer from, Integer? to);
    
    doc "Select the characters of this string beginning at 
         the given index, returning a string no longer than 
         the given length. If the portion of this string
         starting at the given index is shorter than 
         the given length, return the portion of this string
         from the given index until the end of this string. Otherwise 
         return a string of the given length. If the start
         index is larger than the last index of the string,
         return the empty string."
    shared formal actual String segment(Integer from, 
                                        Integer length);
    
    doc "Select the first characters of this string, 
         returning a string no longer than the given 
         length. If this string is shorter than the given
         length, return this string. Otherwise return a
         string of the given length."
    shared formal String initial(Integer length);
    
    doc "Select the last characters of the string, 
         returning a string no longer than the given 
         length. If this string is shorter than the given
         length, return this string. Otherwise return a
         string of the given length."
    shared formal String terminal(Integer length);
    
    doc "The length of the string (the number of characters
         it contains). In the case of the empty string, the
         string has length zero. Note that this operation is
         potentially costly for long strings, since the
         underlying representation of the characters uses a
         UTF-16 encoding."
    see (longerThan, shorterThan)
    shared actual formal Integer size;
    
    doc "The index of the last character in the string, or
         `null` if the string has no characters. Note that 
         this operation is potentially costly for long 
         strings, since the underlying representation of the 
         characters uses a UTF-16 encoding."
    shared actual Integer? lastIndex {
        if (size==0) {
            return null;
        }
        else {
            return size-1;
        }
    }
    
    doc "An iterator for the characters of the string."
    shared actual formal Iterator<Character> iterator;
    
    doc "Returns the character at the given index in the 
         string, or `null` if the index is past the end of
         string. The first character in the string occurs at
         index zero. The last character in the string occurs
         at index `string.size-1`."
    shared actual formal Character? item(Integer index);
    
    doc "The character indexes at which the given substring
         occurs within this string. Occurrences do not 
         overlap."
    shared formal Iterable<Integer> occurrences(String substring);
    
    doc "The first index at which the given substring occurs
         within this string, or `null` if the substring does
         not occur in this string."
    shared formal Integer? firstOccurrence(String substring);
    
    doc "The last index at which the given substring occurs
         within this string, or `null` if the substring does
         not occur in this string."
    shared formal Integer? lastOccurrence(String substring);
    
    doc "The first index at which the given character occurs
         within this string, or `null` if the character does
         not occur in this string."
    shared formal Integer? firstCharacterOccurrence(Character substring);
    
    doc "The last index at which the given character occurs
         within this string, or `null` if the character does
         not occur in this string."
    shared formal Integer? lastCharacterOccurrence(Character substring);
    
    doc "Determines if the given object is a `String` and, 
         if so, if it occurs as a substring of this string,
         or if the object is a `Character` that occurs in
         this string. That is to say, a string is considered 
         a `Category` of its substrings and of its 
         characters."
    shared actual formal Boolean contains(Object element);
    
    doc "Determines if this string starts with the given 
         substring."
    shared formal Boolean startsWith(String substring);
    
    doc "Determines if this string ends with the given 
         substring."
    shared formal Boolean endsWith(String substring);
        
    doc "Returns the concatenation of this string with the
         given string."
    shared actual formal String plus(String other);
    
    doc "Returns a string formed by repeating this string
         the given number of times."
    shared formal String repeat(Integer times);
    
    doc "Returns a string formed by replacing every 
         occurrence in this string of the given substring
         with the given replacement string, working from 
         the start of this string to the end."
    shared formal String replace(String substring, 
                                 String replacement);
    
    doc "Compare this string with the given string 
         lexicographically, according to the Unicode values
         of the characters."
    shared actual formal Comparison compare(String other);
    
    doc "Determines if this string is longer than the given
         length. This is a more efficient operation than
         `string.size>length`."
    see (size)
    shared formal Boolean longerThan(Integer length);
    
    doc "Determines if this string is shorter than the given
         length. This is a more efficient operation than
         `string.size>length`."
    see (size)
    shared formal Boolean shorterThan(Integer length);
    
    doc "Determines if the given object is a string, and if
         so, if this string has the same length, and the 
         same characters, in the same order, as the given 
         string."
    shared actual formal Boolean equals(Object that);
    
    shared actual formal Integer hash;
    
    doc "Returns the string itself."
    shared actual String string { 
        return this;
    }

    shared actual Boolean empty {
        return size==0;
    }
}
