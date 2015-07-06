grammar BundleDependency;

@header {
    package org.kompiro;
}

bundles : bundle (',' bundle)*;
bundle : ID ';'? (bundle_version ';'?)? (resolution ';'?)? (visibility ';'?)?;
ID : ([a-z'.'])+ ;
WS : [ \t\n\r]+ -> skip;
bundle_version : 'bundle-version="' version_range '"';
version_range : (('[' | '(') VERSION ',' VERSION (')' | ']'))
| VERSION  ;
VERSION : ([0-9'.'])+ ;
resolution : 'resolution:=optional';
visibility : 'visibility:=reexport';