## Question

Bob has to send a secret code S to his boss. He designs a method to encrypt the code using two key values N and M. The
formula that he uses to develop the encrypted code is shown below:

((((S^N %10)^M)%1000000007)

Write an algorithm to help Bob encrypt the code.

### Input

The input consists of an integer `secretCode`, representing the secret code (S).

The second line consists of an integer `firstKey`, representing the first key value (N).

The third line consists of an integer `secondKey`, representing the second key value (M).

### Output

Print an integer representing the code encrypted by Bob.

### Constraints

1≤secretCode≤10^9

0≤firstKey,secondKey≤1000000007

### Example

Input:

2

3

4

Output:

4096

S=2, N=3, M=4and the formula of the encrypted code is:

(((S^N%10)^M)%1000000007)

((((2^3%10)^4)%1000000007)=4096

So, the output is 4096.

## 题目

鲍勃需要向他的老板发送一个秘密代码 S。他设计了一种使用两个键值 N 和 M 的加密方法。他用于生成加密代码的公式如下所示：

((((S^N %10)^M)%1000000007)

编写一个算法来帮助鲍勃加密代码。

### 输入

输入包括一个整数 `secretCode`，表示秘密代码（S）。

第二行包括一个整数 `firstKey`，表示第一个键值（N）。

第三行包括一个整数 `secondKey`，表示第二个键值（M）。

### 输出

打印一个整数，表示鲍勃加密后的代码。

### 约束

1≤secretCode≤10^9

0≤firstKey,secondKey≤1000000007

### 示例

输入：

2

3

4

输出：

4096

解释：

S=2, N=3, M=4，加密代码的公式是：

(((S^N%10)^M)%1000000007)

((((2^3%10)^4)%1000000007)=4096

因此，输出是 4096。

