"An IEEE 754 64-bit [floating point number][]. A `Float` is 
 capable of approximately representing numeric values 
 between:
 
 - 2<sup>-1022</sup>, approximately 
   1.79769\{#00D7}10<sup>308</sup>, and 
 - (2-2<sup>-52</sup>)\{#00D7}2<sup>1023</sup>, 
   approximately 5\{#00D7}10<sup>-324</sup>.
 
 Zero is represented by distinct instances `+0.0`, `-0.0`, 
 but these instances are equal.
 
 In addition, the following special values exist:
 
 - [[infinity]] and `-infinity`, and
 - [[undefined values|undefined]], denoted [NaN][] by the
   IEEE standard.
 
 As required by the IEEE standard no undefined value is 
 equal to any other value, nor even to itself. Thus, the 
 definition of [[equals]] for `Float` violates the general 
 contract defined by [[Object.equals]].
 
 A floating point value with a zero [[fractionalPart]] is
 considered equal to its [[integer]] part.
 
 Literal floating point values are written with a decimal
 point and, optionally, a magnitude or exponent:
 
     1.0
     1.0E6
     1.0M
     1.0E-6
     1.0u
 
 In the case of a fractional magnitude, the decimal point is
 optional. Underscores may be used to group digits into 
 groups of three.
 
 [floating point number]: http://www.validlab.com/goldberg/paper.pdf
 [NaN]: http://en.wikipedia.org/wiki/NaN"
see (`function parseFloat`)
shared native final class Float(Float float)
        extends Object()
        satisfies Number<Float> & 
                  Exponentiable<Float,Float> {
    
    "Determines whether this value is undefined. The IEEE
     standard denotes undefined values [NaN][] (an 
     abbreviation of Not a Number). Undefined values include:
     
     - _indeterminate forms_ including `0.0/0.0`, 
       `infinity/infinity`, `0.0*infinity`, and
       `infinity-infinity`, along with
     - _complex numbers_ like `sqrt(-1.0)` and `log(-1.0)`.
     
     An undefined value has the property that it is not 
     [[equal|Object.equals]] (`==`) to itself, and as a 
     consequence the undefined value cannot sensibly be used 
     in most collections.
     
     [NaN]: http://en.wikipedia.org/wiki/NaN"
    shared Boolean undefined => this!=this;
    
    "Determines whether this value is infinite in magnitude. 
     Produces `true` for `infinity` and `-infinity`. 
     Produces `false` for a finite number, `+0.0`, `-0.0`, 
     or undefined."
    see (`value infinity`, `value finite`)
    shared Boolean infinite 
            => this==infinity || this==-infinity;
    
    "Determines whether this value is finite. Produces
     `false` for `infinity`, `-infinity`, and undefined."
    see (`value infinite`, `value infinity`)
    shared Boolean finite 
            => this!=infinity && this!=-infinity 
                    && !this.undefined;
    
    "The sign of this value. Produces `1` for a positive 
     number or `infinity`. Produces `-1` for a negative
     number or `-infinity`. Produces `0.0` for `+0.0`, 
     `-0.0`, or undefined."
    shared actual native Integer sign;
    
    "Determines if this value is a positive number or
     `infinity`. Produces `false` for a negative number, 
     `+0.0`, `-0.0`, or undefined."
    shared actual native Boolean positive;
    
    "Determines if this value is a negative number or
     `-infinity`. Produces `false` for a positive number, 
     `+0.0`, `-0.0`, or undefined."
    shared actual native Boolean negative;
    
    "Determines if this value is a positive number, `+0.0`, 
     or `infinity`. Produces `false` for a negative number, 
     `-0.0`, or undefined."
    shared native Boolean strictlyPositive;
    
    "Determines if this value is a negative number, `-0.0`, 
     or `-infinity`. Produces `false` for a positive number, 
     `+0.0`, or undefined."
    shared native Boolean strictlyNegative;
    
    "Determines if the given object is equal to this `Float`,
     that is, if:
     
     - the given object is also a `Float`,
     - neither this value nor the given value is 
       [[undefined]], and either
     - both values are [[infinite]] and have the same 
       [[sign]], or both represent the same finite floating 
       point value as defined by the IEEE specification.
     
     Or if:
     
     - the given object is an [[Integer]],
     - this value is neither [[undefined]], nor [[infinite]],
     - the [[fractionalPart]] of this value equals `0.0`, 
       and
     - the [[integer]] part of this value equals the given 
       integer."
    shared actual native Boolean equals(Object that);
    
    shared actual native Integer hash;
    
    shared actual native Comparison compare(Float other);
    shared actual native Float plus(Float other);
    shared actual native Float minus(Float other);
    shared actual native Float times(Float other);
    shared actual native Float divided(Float other);
    
    "The result of raising this number to the given floating
     point power, where, following the definition of the
     IEEE `pow()` function, the following indeterminate 
     forms all evaluate to `1.0`:
     
     - `0.0^0.0`,
     - `infinity^0.0` and `(-infinity)^0.0`, 
     - `1.0^infinity` and `(-1.0)^infinity`.
     
     Furthermore:
     
      - `0.0^infinity` evaluates to `0.0`, and
      - `0.0^(-infinity)` evaluates to `infinity`.
     
     If this is a [[negative]] number, and the given 
     [[power|other]] has a nonzero [[fractionalPart]], the 
     result is [[undefined]].
     
     For any negative power `y<0.0`:
     
     - `0.0^y` evaluates to `infinity`,
     - `(-0.0)^y` evaluates to `-infinity`, and
     - for any nonzero floating point number `x`, `x^y` 
       evaluates to `1.0/x^(-y)`."
    shared actual native Float power(Float other);
    
    shared actual native Float wholePart;
    shared actual native Float fractionalPart;
    
    shared actual native Float magnitude;
    
    shared actual native Float negated;
    
    "This value, represented as an [[Integer]], after 
     truncation of its fractional part, if such a 
     representation is possible."
    throws (`class OverflowException`,
        "if the the [[wholePart]] of this value is too large 
         or too small to be represented as an `Integer`")
    shared native Integer integer;
    
    shared actual native Float timesInteger(Integer integer);    
    shared actual native Float plusInteger(Integer integer);
    
    "The result of raising this number to the given integer
     power, where the following indeterminate forms evaluate 
     to `1.0`:
     
     - `0.0^0`,
     - `infinity^0` and `(-infinity)^0`.
     
     For any negative integer power `n<0`:
     
     - `0.0^n` evaluates to `infinity`,
     - `(-0.0)^n` evaluates to `-infinity`, and
     - for any nonzero floating point number `x`, `x^n` 
       evaluates to `1.0/x^(-n)`."
    shared actual native Float powerOfInteger(Integer integer);
    
    "A string representing this floating point number.
     
     - `\"NaN\"`, for any [[undefined value|undefined]]
     - `\"Infinity\"`, for [[infinity]], 
     - `\"-Infinity\"`, for [[-infinity]], or,
     - a Ceylon floating point literal that evaluates to 
       this floating point number, for example, `\"1.0\"`, 
       `\"-0.0\"`, or `\"1.5E10\"`."
    shared actual native String string;
    
    shared actual native Boolean largerThan(Float other); 
    shared actual native Boolean smallerThan(Float other); 
    shared actual native Boolean notSmallerThan(Float other); 
    shared actual native Boolean notLargerThan(Float other); 
}
