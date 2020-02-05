from game_board import *
from move import *

def find_next_move(b: GameBoard):
    """Implements the following algorithm for finding the next move in
    a tic-tac-toe game:
    
    1. If the game is drawn, return the null move (-1, -1) with 0 points.
    
    2. Check if there is a win available in one move. This move is worth 10 points and is returned.
    
    3. otherwise loop through the board, find all available moves, make these moves, and recursively call
    find_next_move() on all of these states.
    The opponent's best move is considered as the current player's worst move and is avoided at all costs, 
    given -10 points.
    
    4. return the move with the highest points
    
    @Author: Asad Durrani
    """
    if b.is_draw():
        return Move(-1, -1)
    if b.get_number_of_moves() <= 1:
        return Move(2,2) if b.is_move_available(2,2) else Move(1,1)
    moves = b.get_all_possible_moves()
    if len(moves) == 0:
        return Move(-1, -1)
    for mov in moves:
        temp = b.copy()
        temp.move(mov)
        if temp.is_game_won():
            mov.points = 10
            return mov
        if temp.is_draw():
            mov.points = 0
        nxt = find_next_move(temp)
        mov.points = -nxt.points
    ret = moves[0]
    for mov in moves:
        if mov.points >= ret.points:
            ret = mov
    return ret

if __name__ == "__main__":
    #Run this file to watch the computer play against itself, note that the first move will take a while
    b = GameBoard()
    print(b)
    while not b.is_game_won() and not b.is_draw():
        b.move(find_next_move(b))
        print(b)
    
        
