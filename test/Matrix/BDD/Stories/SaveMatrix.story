Title testing Matrix class in all possible ways for Lab2 task

Narrative:
As a student
I want get my 30 points and A mark
So that my perfect scenario

Scenario: Enter and save the matrix
Given matrix expression in a string "A = [[1, 2, 3], [3, 4, 5]]"
Then get the output string as "[[1.0, 2.0, 3.0], [3.0, 4.0, 5.0]]"
When next matrix expression in a string will be like "A"
Then get the output string as "[[1.0, 2.0, 3.0], [3.0, 4.0, 5.0]]"

Scenario: Enter the matrix in the wrong format
Given matrix expression in a string "A = [[1, 2, 3], [3]]"
Then get the output string as "We cant set matrix with different rows length"

Scenario: Enter the matrix with the wrong name
Given matrix expression in a string "Agaa = [[1]]"
Then get the output string as "An expression refers to a matrix that is not in the repository or matrix name has invalid syntax"

Scenario: Request a matrix that is not in the repository
Given matrix expression in a string "A"
Then get the output string as "An expression refers to a matrix that is not in the repository or matrix name has invalid syntax"

Scenario: The matrix with the same name is already stored in the repository
Given matrix expression in a string "A = [[1, 2, 3], [3, 4, 5]]"
Then get the output string as "[[1.0, 2.0, 3.0], [3.0, 4.0, 5.0]]"
When next matrix expression in a string will be like "A = [[1, 2, 3], [3, 4, 5], [3, 4, 5]]"
Then get the output string as "[[1.0, 2.0, 3.0], [3.0, 4.0, 5.0], [3.0, 4.0, 5.0]]"

Scenario: Saving the results of calculations in the repository
Given matrix expression in a string "A = [[1, 2, 3], [3, 4, 5]]"
Then get the output string as "[[1.0, 2.0, 3.0], [3.0, 4.0, 5.0]]"
When next matrix expression in a string will be like "B = [[3, 4, 5], [1, 2, 3]]"
Then get the output string as "[[3.0, 4.0, 5.0], [1.0, 2.0, 3.0]]"
When next matrix expression in a string will be like "C = A - B"
Then get the output string as "[[-2.0, -2.0, -2.0], [2.0, 2.0, 2.0]]"