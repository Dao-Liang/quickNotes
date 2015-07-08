The common used string class when using string regex
===================================================
[:alnum:]   The character is alphanumeric
[:alpha:]   The character is alphabetic
[:ascii:]   The character is 7-bit, i.e. is a single-byte character without the top bit set.
[:blank:]   The character is either space or tab
[:cntrl:]   The character is a control character
[:digit:]   The character is a decimal digit
[:graph:]   The character is a printable character other than whitespace
[:lower:]   The character is a lowercase letter
[:print:]   The character is printable
[:punct:]   The character is printable but neither alphanumeric nor whitespace
[:space:]   The character is whitespace
[:upper:]   The character is an uppercase letter
[:xdigit:]  The character is a hexadecimal digit

Another set of named classes is handled internally by the shell and is not sensitive to the locale:
=======
[:IDENT:]       The character is allowed to form part of a shell identifier, such as a parameter name
[:IFS:]         The character is used as an input field separator, i.e. is contained in the IFS parameter
[:IFSSPACE:]    The character is an IFS white space character; see the documentation for IFS in Section 15.6 [Parameters Used By The Shell], page 80.
[:WORD:]        The character is treated as part of a word; this test is sensitive to the value of the WORDCHARS parameter
