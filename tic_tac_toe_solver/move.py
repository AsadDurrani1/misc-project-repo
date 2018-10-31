"""Implements a representation of a move in a tic-tac-toe game. A move has a
row, a column, and a number of points which is by default 0."""
class Move:
    def __init__(self, row, column, points = 0):
        self.row = row
        self.column = column
        self.points = points

    def __repr__(self):
        return "({}, {}), Points: {}".format(self.row, self.column, self.points)

    def __eq__(self, other):
        return self.row == other.row and self.column == other.column
