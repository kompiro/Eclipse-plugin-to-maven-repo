grammar BundleDependency;

@header {
package org.kompiro;
}

bundles : bundle (',' bundle)*;
bundle : ID bundle_options;
ID : ALPHA (ALPHA | DIGIT | PERIOD)* ;
WS : [ \t\n\r]+ -> skip;
bundle_options : (';' (bundle_version | resolution | visibility)?)*;
bundle_version : 'bundle-version="' version_range '"';
version_range :  (('[' | '(') VERSION ',' VERSION (')' | ']') )
|  VERSION  ;
VERSION : NUMBER (PERIOD NUMBER)* ;
assign : ':=';
resolution : 'resolution' assign '"'? optional '"'?;
optional : 'optional' ;
visibility : 'visibility' assign '"'? reexport '"'?;
reexport : 'reexport' ;
fragment
ALPHA : [a-zA-Z];
fragment
DIGIT : [0-9];
fragment
NUMBER : DIGIT+;
fragment
PERIOD : '.';
