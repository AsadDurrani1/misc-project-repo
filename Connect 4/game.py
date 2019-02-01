import os

class IllegalMoveError(Exception):
    pass

class Grid:
    def __init__(self):
        self.grid = [[None for i in range(7)] for i in range(6)]
        self.current_player = 'Player 1'
        self.last_move = (0, 0)

    def __repr__(self):
        s = ''
        for row in self.grid:
            for i in range(len(row)):
                item = '[ ]' if row[i] is None else '[{}]'.format(str(row[i]))
                if i == 6:
                    s += item + '\n'
                else:
                    s += item
        return s

    def move(self, column):
        if not column in range(1, 8):
            raise IllegalMoveError

        if self.current_player == 'Player 1':
            for i in range(len(self.grid) - 1, -1, -1):
                if self.grid[i][column-1] is None:
                    self.grid[i][column-1] = 'R'
                    self.current_player = 'Player 2'
                    self.last_move = (i, column-1)
                    return
        elif self.current_player == 'Player 2':
            for i in range(len(self.grid) - 1, -1, -1):
                if self.grid[i][column-1] is None:
                    self.grid[i][column-1] = 'B'
                    self.current_player = 'Player 1'
                    self.last_move = (i, column-1)
                    return
        raise IllegalMoveError

    def check_row_for_win(self, lst):
        counter = 0
        for i in range(len(lst) - 1):
            if lst[i] is not None and lst[i] == lst[i+1]:
                       counter += 1
            else:
                       counter = 0
            if counter == 3:
                       return True
        return False

    def get_column(self, column):
        ret = []
        for i in range(len(self.grid)):
            ret.append(self.grid[i][column])
        return ret

    def get_diagonal_1(self, row, column):
        """gets the / diagonal of the grid containing the item at (row, column)"""
        ret = []
        i = row
        j = column
        while i < 6 and j >= 0:
            ret.insert(0, (self.grid[i][j]))
            i += 1
            j -= 1
        i = row - 1
        j = column + 1
        while i >= 0 and j < 7:
            ret.append(self.grid[i][j])
            i -= 1
            j += 1
        return ret
            
    def get_diagonal_2(self, row, column):
        """gets the \ diagonal of the grid containing the item at (row, column)"""
        ret = []
        i = row
        j = column
        while i >= 0 and j >= 0:
            ret.insert(0, (self.grid[i][j]))
            i -= 1
            j -= 1
        i = row + 1
        j = column + 1
        while i <= 5 and j <= 6:
            ret.append(self.grid[i][j])
            i += 1
            j += 1
        return ret

    def game_over_win(self):
        row = self.grid[self.last_move[0]]
        column = self.get_column(self.last_move[1])
        diagonal_1 = self.get_diagonal_1(self.last_move[0], self.last_move[1])
        diagonal_2 = self.get_diagonal_2(self.last_move[0], self.last_move[1])
        return self.check_row_for_win(row) or self.check_row_for_win(column) or \
               self.check_row_for_win(diagonal_1) or self.check_row_for_win(diagonal_2)

    def game_over_draw(self):
        for row in self.grid:
            for item in row:
                if item is None:
                    return False
        return True



g = Grid()
while True:
    try:
        print(g)
        print("It is {}'s turn.".format(str(g.current_player)))
        move_input = int(input("Enter your move."))
        g.move(move_input)
        os.system('cls')
        if g.game_over_win():
            print(g)
            print("Game over. {} wins".format('Player 1' if g.current_player == 'Player 2' else 'Player 2'))
            cont = input("Enter any key to continue.")
            g = Grid()
            os.system('cls')
        if g.game_over_draw():
            print(g)
            print("It's a draw.")
            cont = input("Enter any key to continue.")
            g = Grid()
            os.system('cls')
    except IllegalMoveError:
        print("That is not a legal move.")
        cont = input("Enter any key to continue.")
        os.system('cls')
    except ValueError:
        print("That is not a legal move.")
        cont = input("Enter any key to continue.")
        os.system('cls')
            
