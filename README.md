# TextGenerator: Markov Chain Text Generator

TextGenerator is a Java-based application that uses Markov Chains to generate text. It reads an input dictionary text file to build a word transition map, then uses that map to generate sequences of words. The project demonstrates basic file I/O, text tokenization, and algorithmic generation of random text.

## Table of Contents

- [Features](#features)
- [Project Structure](#project-structure)
- [Prerequisites](#prerequisites)
- [Installation and Setup](#installation-and-setup)
- [Compilation and Running](#compilation-and-running)
- [Usage Example](#usage-example)
- [Customization](#customization)
- [Contributing](#contributing)
- [License](#license)
- [Acknowledgements](#acknowledgements)

## Features

- **Markov Chain Generation:** Builds a dictionary mapping words to their likely following words from a text source.
- **Seed-Based Generation:** Generates text starting from a user-provided seed word.
- **Configurable Modes:**
  - **Weighted Random Mode (`all`):** Selects the next word based on a probability weighted by frequency.
  - **Highest Frequency Mode (`one`):** Always selects the most frequent word in the transition map.
- **Command-Line Interface:** Simple and interactive CLI that prompts users for input parameters.
- **Extensible Design:** Easily adaptable for additional text processing or custom generation modes.

## Project Structure

```plaintext
TextGenerator/
├── src/
│   ├── dictionary.txt         # Input text file for building the Markov dictionary.
│   └── TextGenerator/
│       ├── Main.java          # Contains the main method to run the application.
│       └── MarkovGenerator.java  # Implements Markov chain logic and text generation.
├── .gitignore                 # Git ignore file to exclude build artifacts and IDE settings.
└── README.md                  # This file.
```
## Prerequisites
- Java Development Kit (JDK): Version 8 or later is recommended.

- A Command-Line Environment: Such as a terminal on macOS/Linux or Command Prompt/PowerShell on Windows.

- Optional IDE: IntelliJ IDEA, Eclipse, or NetBeans can be used for easier project management.

## Usage Example
Below is an example of running the application:
```
Enter a seed word: hello
Top possibilities after 'hello': world example test 
Enter the number of words to generate: 20
Enter mode ('all' or 'one'): all
Generated Text:
hello world how are you doing on this beautiful day with an example text generator in java.
```
- Seed Word: The starting point for text generation.

- Possibility Display: The application shows the top 3 possible transitions from the seed word.

- Mode Selection: In all mode, words are chosen randomly based on frequency; in one mode, the most frequent word is always selected.









