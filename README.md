# PokerChips Game

A Java console application that simulates multi-player poker betting rounds.  
This implementation focuses on the betting mechanics of poker (check, call, raise, fold), making it perfect for learning betting strategies and money management.

---

## Features

- **Multi-player support:** Configure any number of players
- **Budget management:** Each player starts with a specified budget
- **Complete betting mechanics:** Supports check, call, raise, and fold actions
- **Round-based gameplay:** Four betting rounds per game
- **Winner determination:** Multiple winners can share the pot
- **Bankrupt detection:** Players with zero money are automatically folded

---

## Technologies Used

- Java
- Object-Oriented Programming
- HashMap data structures
- Custom Scanner class for input handling

---

## How to Run

1. **Clone the repository**
   ```bash
   git clone https://github.com/justinmaher123/Poker-Chips.git
   cd Poker-Chips/src
   ```
2. **Compile the Java code**
   ```bash
   javac PokerChips.java
   ```
3. **Run the program**
   ```bash
   java PokerChips
   ```
   
---

## Example Run

```bash
how many players
3
Player 1 name
Alice
Player 1 budget
100
Player 2 name
Bob
Player 2 budget
100
Player 3 name
Charlie
Player 3 budget
100

Round 1 (table money: 0)
Alice's turn (current money: 100)
check=1 bid=2 fold=3
   
---

## Project Structure

```
PokerChips/
├── src/
│   └── PokerChips.java
├── README.md
└── LICENSE
```
   
---

## Author

[Justin Maher](https://github.com/justinmaher123)
   
---

## License

This project is licensed under the MIT License.
