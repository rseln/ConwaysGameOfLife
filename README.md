# Conways Game of Life

This project was a recreation of The Game of Life (also known simply as Life). Life was a is a cellular automaton devised by the British mathematician John Horton Conway in 1970. Based on the original user input of the cell configureation, the program would then mutate y itself based on 4 specific rules:

1. Any live cell with fewer than two live neighbours dies (referred to as underpopulation or exposure)
2. Any live cell with more than three live neighbours dies (referred to as overpopulation or overcrowding)
3. Any live cell with two or three live neighbours lives, unchanged, to the next generation
4. Any dead cell with exactly three live neighbours will come to life

The program would then iterate through many generations until all the cells died or until the user closes the program. 
