<!-- omit from toc -->
# iOddNums Documentation

## Description

In the use of the class `org.apache.commons.lang3.builder.HashCodeBuilder`,
  the constructor wants two integer odd values.

If those parameters are primary number, it is even better.
And for the best, such values shouldn't be recycled by each implemented
  use of `HashCodeBuilder`'s constructor.

To respect all those best practices, we are adopting two project contants,
  stored into `HermodConstants` class:

* `int[] initialNonZeroOddNumbers` which contains all odd primary numbers
      up to 101 (for now), in ascendent order.
* `int[] multiplierNonZeroOddNumbers` which contains the reverse of
      `initialNonZeroOddNumbers`.

By using such constants, each Bean class, which inherits directly or indirectly
  from `HermodBean`, will adopt an index named `iOddNums`, to get his own
  odd primary integers to pass to his `HashCodeBuilder` constructor.

With this document, we keep track of which index was used by which Bean class.

## Indexes already adopted by class

### src/test/java

0 `it.patcha.hermod.gpt.common.bean`.TestInfoBean

### src/main/java

1. `it.patcha.hermod.gpt.common.bean.core.logic.`SendBean
2. `it.patcha.hermod.gpt.common.bean.ui.message.dispatch.`ArgsBean
