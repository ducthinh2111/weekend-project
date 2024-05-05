## Situation
- Unittest require a lot of mocking to be done.
- Most of the mocking is easy to write but it take too much time.

## Expectation
- A solution that consume a class/file then generate sample mocking code for every external call, organize as a test file.
- The generated output just act as a sample that can be quickly copy the needed part.
- Target mocking framework can be either `Mockito` (for popularity) or `jMockit` (for execution speed).