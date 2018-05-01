[![Build Status](https://travis-ci.org/thomaskrause/graphANNIS.svg?branch=develop)](https://travis-ci.org/thomaskrause/graphANNIS)

graphANNIS
==========

This is a prototype for a new backend implementation of the ANNIS linguistic search and visualization system (http://github.com/korpling/ANNIS/). 

It is part of my ongoing thesis and while there are test cases it is **highly experimental code and it is not ready to be used by end-users yet**!

How to compile
---------------

graphANNIS is written in the Rust programming language (https://www.rust-lang.org).
You can install Rust from https://www.rust-lang.org/en-US/install.html.
After you have installed Rust, you can can build the complete project with

```
cargo build --release
```

3rd party dependencies
----------------------

This software depends on several 3rd party libraries. These are documented in the BOM.txt file in this folder.

Author(s)
---------

* Thomas Krause (thomaskrause@posteo.de)
