## Palindrome akka sample project

Run this project using the following command from root directory.

```
./gradlew run
```
This is a simple project created understanding the actor model with akka. Let’s build a simple system for detecting and tracking palindrome words in a given paragraph. This requirement can be further broken down into simpler tasks as follows,

* Tokenise a paragraph into words.
* Identify whether the words are palindrome or not.
* Keep track of the palindrome occurrences.

The workflow of this system with actors can be composed as below,

* This actor system comprises of a StringProcessor actor that gets Request message as list of strings. The StringProcessor splits the list and spawns PalindromePrintor actors for each string in the lists and sends the Input message as a single string to the spawned actors. 
* The PalindromePrinter actors execute the required logic and find if the string is palindrome or not and print the same. 
* Additionally, whenever a palindrome string is encountered, the PalindromePrinter actors send PalindromeEvent message to the PalindromeCountTracker. The PalindromeCountTracker actor simply increments the counter and prints the same. 

