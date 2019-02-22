#!/bin/bash

# tests 1-6 parts a-e

# test 7 is maybe wrong because needs string formatter

LINE="--------------------"

set -e

javac FieldFormat.java
javac DataDrawer.java
javac ArrayFormat.java

echo $LINE

javac Test1.java && java Test1    # PASS

echo $LINE

javac Test2.java && java Test2    # PASS

echo $LINE

javac Test3.java && java Test3    # PASS

echo $LINE

javac Test4.java && java Test4    # PASS

echo $LINE

javac Test5.java && java Test5    # PASS

echo $LINE

javac Test6.java && java Test6    # PASS

echo $LINE

javac Test7.java && java Test7    # PASS

echo $LINE

# javac Test8.java && java Test8
#
# echo $LINE

