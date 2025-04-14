# TextGenerator: Markov Chain Text Generator

This project is a simple text generator using Markov Chains. The application reads a dictionary file, builds a transitional probability dictionary of words, and then generates text based on user inputs. It demonstrates the use of Java collections, file I/O, and basic algorithmic design.

## Features

- **Markov Chain Text Generation:** Create a dictionary of word transitions from a text file.
- **Seed-Based Generation:** Start generation from a user-specified seed word.
- **Generation Modes:**
  - **Weighted Random (`all`):** Generates text based on the probabilities of each following word.
  - **Highest Frequency (`one`):** Always selects the most frequent word.
- **Command-Line Interface:** Users interact with the application through the terminal.

## Project Structure

TextGenerator/ ├── src/ │ ├── dictionary.txt # Input text file for building the dictionary. │ └── TextGenerator/ │ ├── Main.java # Main class with the CLI. │ └── MarkovGenerator.java # Contains the Markov chain generation logic. └── README.md
