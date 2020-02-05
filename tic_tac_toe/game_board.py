from move import *

"""A board for a standard tic-tac-toe game. A GameBoard object has a list of its current
contents and keeps track of which player's turn it is. Moves are made by the move() method
which takes in a Move object and updates the current GameBoard's board.
@Author: Asad Durrani"""
class GameBoard:
    pieces = {0: " ", 1: "X", -1: "0"}
    players = {1: "X", -1: "O"}
    
    def __init__(self):
        self.board = [0 for i in range(9)]
        self.current_player = 1
        self.number_of_moves = 0
    
    def __repr__(self):
        s = ""
        for i in range(len(self.board)):
            if (i+1)%3 == 0:
                s += "[" + GameBoard.pieces[self[i]]+ "]" + "\n"
            else:
                s += "[" + GameBoard.pieces[self[i]]+ "]"
        return s

    def __getitem__(self, i: int) -> int:
        return self.board[i]

    def __setitem__(self, i: int, item: object):
        self.board[i] = item

    def __eq__(self, other):
        return self.board == other.board

    def move(self, move: Move) -> "GameBoard":
        """Makes a move on the ith row and jth column of the current GameBoard."""
        if not move.row > 0 or not move.column > 0:
            raise Exception
        elif self.current_player == 1 and self[3*(move.row-1) + (move.column-1)] == 0 and self.is_move_available(move.row, move.column):
            self[3*(move.row-1) + (move.column-1)] = 1
        elif self.current_player == -1 and self[3*(move.row-1) + (move.column-1)] == 0 and self.is_move_available(move.row, move.column):
            self[3*(move.row-1) + (move.column-1)] = -1
        else:
            raise Exception
        self.switch_player()
        self.number_of_moves += 1
        return self

    def switch_player(self):
        self.current_player *= -1

    def get_current_player(self):
        return GameBoard.players[self.current_player]

    def set_current_players(self, i):
        self.current_player = i
        
    def get_number_of_moves(self):
        return self.number_of_moves

    def set_number_of_moves(self, i):
        self.number_of_moves = i

    def set_board(self, lst: list):
        self.board = lst[:]

    def copy(self) -> "GameBoard":
        """Returns a deep copy of this GameBoard"""
        ret = GameBoard()
        ret.set_board(self.board)
        ret.set_current_players(self.current_player)
        ret.set_number_of_moves(self.number_of_moves)
        return ret

    def is_move_available(self, i: int, j: int) -> bool:
        """Returns true iff the square at position (i,j) is available."""
        return self[3*(i-1) + j-1] == 0

    def get_all_possible_moves(self) -> list:
        """Returns all possible moves available from this game state."""
        ret = []
        for i in range(3):
            for j in range(3):
                if self.is_move_available(i+1, j+1):
                    ret.append(Move(i+1, j+1))
        return ret

    def is_draw(self) -> bool:
        """Returns true iff the game is drawn."""
        return not 0 in self and not self.is_game_won()
        
    def is_game_won(self) -> bool:
        """Returns true iff the game is won, by either player."""
        if self[0] == self[4] == self[8] != 0:
            self.game_over = True
            return True
        if self[2] == self[4] == self[6] != 0:
            self.game_over = True
            return True
        if self[1] == self[4] == self[7] != 0:
            self.game_over = True
            return True
        if self[3] == self[4] == self[5] != 0:
            self.game_over = True
            return True
        if self[0] == self[1] == self[2] != 0:
            self.game_over = True
            return True
        if self[0] == self[3] == self[6] != 0:
            self.game_over = True
            return True
        if self[2] == self[5] == self[8] != 0:
            self.game_over = True
            return True
        if self[6] == self[7] == self[8] != 0:
            self.game_over = True
            return True
        return False
