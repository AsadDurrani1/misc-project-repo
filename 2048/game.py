import random
import os
import sys

class IllegalMoveError(Exception):
    pass

class Grid:
    def __init__(self: 'Grid'):
        self.row_length = self.col_length = 4 #modify this to play with different grid sizes.
        self.grid = [None for i in range(self.row_length*self.col_length - 2)] + [2] + [4]
        self.max_num = self.get_max_num()
        self.score = 0

    def __repr__(self: 'Grid'):
        s = ''
        filler = ' '*(len(str(self.get_max_num()))//2)
        for i in range(len(self.grid)):
            item = self.grid[i] if self.grid[i] else ' '
            s += ' [{}{}{}]\n'.format(filler, item, filler) if (i + 1) % self.row_length == 0 else ' [{}{}{}]'.format(filler,item,filler)
        return s

    def get_max_num(self: 'Grid') -> int:
        mx = 0
        for i in self.grid:
            if i and i > mx:
                mx = i
        return mx

    def get_rows(self: 'Grid') -> list:
        lst = []
        for i in range(self.row_length):
            aux = []
            for j in range(self.col_length):
                aux.append(self.grid[self.row_length*i + j])
            lst.append(aux)
        return lst

    def get_cols(self: 'Grid') -> list:
        lst = []
        for i in range(self.col_length):
            aux = []
            for j in range(self.row_length):
                aux.append(self.grid[self.col_length*j + i])
            lst.append(aux)
        return lst

    def update_grid(self: 'Grid', move_type: str, cols_or_rows: list) -> None:
        '''Given either a columns or rows list, updates main grid.'''
        current = self.grid[:]
        if move_type == 'VERTICAL':
            cols = cols_or_rows
            for i in range(len(cols)):
                for j in range(self.col_length):
                    self.grid[self.col_length*j + i] = cols[i][j]
        if move_type == 'HORIZONTAL':
            rows = cols_or_rows
            for i in range(len(rows)):
                for j in range(self.row_length):
                    self.grid[self.row_length*i + j] = rows[i][j]
        if None in self.grid and current != self.grid:
            i = random.randint(0, 15)
            while self.grid[i] != None:
                i = random.randint(0,15)
            self.grid[i] = 2*random.randint(1, 2)
        


    def move(self: 'Grid', move_type: str) -> None:
        if not move_type in 'DOWNLEFTUPRIGHT':
            raise IllegalMoveError
        if move_type == 'UP':
            cols = self.get_cols()
            for i in range(len(cols)):
                g.collapse_row(cols[i])
            g.update_grid('VERTICAL', cols)
        if move_type == 'LEFT':
            rows = self.get_rows()
            for i in range(len(rows)):
                g.collapse_row(rows[i])
            g.update_grid('HORIZONTAL', rows)
        if move_type == 'DOWN':
            cols = self.get_cols()
            for i in range(len(cols)):
                cols[i].reverse()
                g.collapse_row(cols[i])
                cols[i].reverse()
            g.update_grid('VERTICAL', cols)
        if move_type == 'RIGHT':
            rows = self.get_rows()
            for i in range(len(rows)):
                rows[i].reverse()
                g.collapse_row(rows[i])
                rows[i].reverse()
            g.update_grid('HORIZONTAL', rows)
            

    def collapse_row(self: 'Grid', lst: list) -> None:
        i = 0
        while i < len(lst) - 1:
            j = i + 1
            if lst[i] != None:
                while j < len(lst):
                    if lst[j] != None:
                        if lst[j] != lst[i]:
                            j = len(lst)
                        elif lst[i] == lst[j]:
                            lst[i] += lst[j]
                            self.score += lst[i]
                            lst[j] = None
                    j += 1
            else:
                while j < len(lst):
                    if lst[j]:
                        lst[i], lst[j] = lst[j], lst[i]
                        j = len(lst)
                        i -= 1
                    j += 1
            i += 1

def game(g: Grid) -> None:
    print(g)
    print('Score: ' + str(g.score))
    try:
        print('( ͡° ͜ʖ ͡°)')
        print('Controls: E moves up, S moves left, D moves down, and F moves right')
        move = input('Enter your move.').upper()
        if not move in 'ESDF':
            raise IllegalMoveError
        current = g.grid[:]
        g.move(moves[move])
        os.system('cls')
        if current == g.grid and not None in g.grid:
            print('( ͡° ͜ʖ ͡°)')
            print('Game over. Your score was: ' + str(g.score) + '.')
            cont = input('Press any button to continue, or press R to quit.').upper()
            if cont == 'R':
                exit()
            g.grid = [None for i in range(self.row_length*self.col_length - 2)] + [2] + [4]
            g.score = 0
        game(g)
    except IllegalMoveError:
        print('( ͠° ͟ʖ ͡°)')
        print('That move is not allowed.')
        cont = input('Enter any key to continue.')
        os.system('cls')
        game(g)
    except KeyError:
        print('( ͠° ͟ʖ ͡°)')
        print('That move is not allowed.')
        cont = input('Enter any key to continue.')
        os.system('cls')
        game(g)

        
os.system('cls')
g = Grid()
print('Welcome to 2048!')
print('\n')
print('Type your move and press enter to confirm it.')
moves = {'E': 'UP', 'D': 'DOWN', 'S': 'LEFT', 'F': 'RIGHT'}
game(g)

# :)
