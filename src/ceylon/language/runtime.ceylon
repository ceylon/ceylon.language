import java.lang {
    System { getProperty },
    Long { MIN_VALUE, MAX_VALUE },
    Integer { intMAX_VALUE=MAX_VALUE }
}

"Represents the machine and virtual machine on which the 
 current process is executing.
 
 Holds information about runtime name, version and about 
 inherent limitations like minimum/maximum values that can 
 be represented by the runtime."
see (`value process`, `value language`, `value system`,
     `value operatingSystem`)
shared native object runtime  {
    
    "The name of the runtime / virtual machine this process 
     is running on."
    shared native String name;
    
    "The version of the runtime / virtual machine this 
     process is running on."
    shared native String version;
    
    "The number of bits used to represent the value of an 
     [[Integer]]."
    see (`class Integer`)
    shared native Integer integerSize;
    
    "The number of bits of [[Integer]] instances which may 
     be manipulated via the methods inherited from
     [[Binary]]."
    shared native Integer integerAddressableSize;
    
    "The minimum [[Integer]] value that can be represented 
     by the runtime.
     
     It is the minimum `Integer` that can be distinguished 
     from its successor using below formula:
     
     `Integer(n-1) = Integer(n) - 1` with `Integer(0) = 0`"
    see (`class Integer`)
    shared native Integer minIntegerValue;

    "The maximum [[Integer]] value that can be represented 
     by the runtime.
     
     It is the maximum `Integer` that can be distinguished 
     from its predecessor using below formula:
     
     `Integer(n+1) = Integer(n) + 1` with `Integer(0) = 0`"
    see (`class Integer`)
    shared native Integer maxIntegerValue;
    
    "The maximum size of an [[Array]] that is possible for 
     this runtime. Note that this is a theoretical limit 
     only. In practice it is usually impossible to allocate 
     an array of this size, due to memory constraints."
    see (`class Array`)
    shared native Integer maxArraySize;
    
    string => "runtime [``name`` / ``version``]";
}

shared native("jvm") object runtime  {
    
    shared native("jvm") String name => "jvm";
    
    shared native("jvm") String version => getProperty("java.specification.version");
    
    shared native("jvm") Integer integerSize => 64;
    
    shared native("jvm") Integer integerAddressableSize => 64;
    
    shared native("jvm") Integer minIntegerValue => \iMIN_VALUE;
    
    shared native("jvm") Integer maxIntegerValue => \iMAX_VALUE;
    
    shared native("jvm") Integer maxArraySize => intMAX_VALUE - 8;
    
}

shared native("js") object runtime  {
    
    shared native("js") String name {
        dynamic {
            dynamic p = process;
            if (typeof(p) != "undefined"
                    && typeof(p.execPath) == "string"
                    && p.execPath.contains("node")) {
                return "node.js";
            } else if (typeof(window) == "object") {
                return "Browser";
            }
        }
        return "Unknown JavaScript environment";
    }
    
    shared native("js") String version {
        dynamic {
            dynamic p = process;
            if (typeof(p) != "undefined"
                    && typeof(p.version) == "string") {
                return p.version;
            }
        }
        return "Unknown";
    }
    
    shared native("js") Integer integerSize => 53;
    
    shared native("js") Integer integerAddressableSize => 32;
    
    shared native("js") Integer minIntegerValue => -9007199254740991;
    
    shared native("js") Integer maxIntegerValue => 9007199254740991;
    
    shared native("js") Integer maxArraySize => 4294967295;
    
}
