# Minesweeper

Pre Work:
Setting length, width and number of mines in the map.

Input Style:
input the "x,y,style" to open the block(like click on the map,style==0 means left click,style==1 means right click)
(2,3,0) means in the position(2,3) left click the block(guess not mine).
(2,1,1) means in the position(2,1) right click the block(guess it is mine).

Response:
Wrong answer: Game Over.
Click opened block:  Check the number in the block and open all the possible blocks around this.
Left click covered block: Guess the block is not a mine, then check the block.
Right click covered block: Guess the block is a mine.


