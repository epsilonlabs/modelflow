#Ideas

For concrete syntax to set the type for property values

```
String: "String"
Char: 'c'
Integer: 29
Long: 29l
Float: 29f
Double: 29d
Boolean: true/false/TRUE/FALSE
Rel File: file("location.relative")
Abs File: file("/location.absolute")
Date RFC3339: date("yyyy-mm-dd") 
DateTime RFC3339: dt("yyyy-mm-ddThh:mm:ss[.X]Z(+/-)hh:mm")
Time RFC3339: time("hh:mm:ss[.X]Z(+/-)hh:mm")
EnvironmentVar : $NAME

Byte* : byte("...")
Binary* : binary("...")

Array: ["",""]
```