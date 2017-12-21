Title testing Matrix class in all possible ways for Lab2 task

Narrative:
As a student
I want get my 30 points and A mark
So that my perfect scenario

Scenario: An expression contains matrices in a string format,
        the expression is calculated without saving the result
Given repository empty 
Then matrix expression in a string "([[1, 2, 3], [3, 4, 5]] - [[3, 4, 5], [1, 2, 3]])^T"
Then get the output string as "[[-2.0, 2.0], [-2.0, 2.0], [-2.0, 2.0]]"

Scenario: An expression contains matrices in a string format;
        expression is calculated and the result is stored in the repository
Given matrix expression in a string "A = ([[1, 2, 3], [3, 4, 5]] - [[3, 4, 5], [1, 2, 3]])^T"
Then get the output string as "[[-2.0, 2.0], [-2.0, 2.0], [-2.0, 2.0]]"
When next matrix expression in a string will be like "A"
Then get the output string as "[[-2.0, 2.0], [-2.0, 2.0], [-2.0, 2.0]]"

Scenario: An expression contains matrices in the wrong string format;
Given matrix expression in a string "A = ([[1, 2, 3], [3, 4, 5]] - [3, 4, 5], [1 2 3]])^T"
Then get the output string as "We cant set matrix with different rows length"

Scenario: expression refers to the matrix from the repository;
        the expression is calculated without saving the result;
Given matrix expression in a string "A = [[1, 2, 3], [3, 4, 5]]"
Then get the output string as "[[1.0, 2.0, 3.0], [3.0, 4.0, 5.0]]"
When next matrix expression in a string will be like "B = [[3, 4, 5], [1, 2, 3]]"
Then get the output string as "[[3.0, 4.0, 5.0], [1.0, 2.0, 3.0]]"
When next matrix expression in a string will be like "A - B"
Then get the output string as "[[-2.0, -2.0, -2.0], [2.0, 2.0, 2.0]]"

Scenario: expression refers to the matrix from the repository;
        expression is calculated and result is stored in the repository;
Given matrix expression in a string "A = [[1, 2, 3], [3, 4, 5]]"
Then get the output string as "[[1.0, 2.0, 3.0], [3.0, 4.0, 5.0]]"
When next matrix expression in a string will be like "B = [[3, 4, 5], [1, 2, 3]]"
Then get the output string as "[[3.0, 4.0, 5.0], [1.0, 2.0, 3.0]]"
When next matrix expression in a string will be like "C = A - B"
Then get the output string as "[[-2.0, -2.0, -2.0], [2.0, 2.0, 2.0]]"
When next matrix expression in a string will be like "C"
Then get the output string as "[[-2.0, -2.0, -2.0], [2.0, 2.0, 2.0]]"

Scenario: An expression refers to a matrix that is not in the repository
Given matrix expression in a string "A = [[1, 2, 3], [3, 4, 5]]"
When next matrix expression in a string will be like "A - B"
Then get the output string as "An expression refers to a matrix that is not in the repository or matrix name has invalid syntax"

Scenario: An expression refers to a non-square matrix which can't have inverse matrix
Given matrix expression in a string "([[1, 2, 3], [3, 4, 5]])^-1"
Then get the output string as "Inverse matrix can not be found in a non-square matrix"
When next matrix expression in a string will be like "([[1, 2, 3], [3, 4, 5], [6, 7, 8]])^-1"
Then get the output string as "Inverse matrix doesn't exist"

Scenario: An expression refers to substraction of different-size matrixes
Given matrix expression in a string "([[1, 2, 3], [3, 4, 5]]) - [[1, 2, 3]]"
Then get the output string as "It is impossible to substract matrices of different sizes"