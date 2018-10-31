from game_board import *
from tkinter import *
from find_next_move import *
import time
import random

"""Implements a tkinter GUI controller to play the Tic-Tac-Toe game in.
@Author: Asad Durrani"""
class GameBoardView:
    def __init__(self):
        self.game_board = GameBoard()
        self.root = Tk()
        self.root.title("Tic-Tac-Toe")
        self.canvas = Canvas(self.root, width=300, height=400, bg = "white")
        self.canvas.grid()
        b1 = Button(self.canvas, width=10, text="1-Player", padx=4,pady=4, \
                    command=self.against_computer,font="Times", bg = "lavender")
        b2 = Button(self.canvas, width=10, text="2-Player", padx=4,pady=4, \
                    command=self.two_players,font="Times",bg="lavender")
        self.canvas.create_window(100, 175, window=b1)
        self.canvas.create_window(200, 175, window=b2)
        self.canvas.create_text(150,100,text="Tic-Tac-Toe",font="Times 30 italic bold")

    def against_computer(self):
        """Begins a new game against the computer player."""
        self.canvas.delete("all")
        self.draw_grid()
        
        i = random.randint(0,10)
        if i < 5:
            m = find_next_move(self.game_board)
            self.game_board.move(m)
            self.add_shape(m.row, m.column) #50% chance of computer going first
            
        self.canvas.create_rectangle(0,300, 405, 405, fill="white", disabledoutline="white")
        self.game_board.switch_player()
        self.canvas.create_text(150, 350, text = self.game_board.get_current_player(), font = "Times 30")
        self.game_board.switch_player() #switching to other player to display the correct current player
        
        self.canvas.bind("<Button-1>", self.move_update_com)
        self.root.update()

    def two_players(self):
        """Begins a new game against a human player."""
        self.canvas.delete("all")
        self.draw_grid()
        
        self.canvas.create_rectangle(0,300, 405, 405, fill="white", disabledoutline="white")
        self.game_board.switch_player()
        self.canvas.create_text(150, 350, text = self.game_board.get_current_player(), font = "Times 30")
        self.game_board.switch_player() #switching to other player to display the correct current player

        self.canvas.bind("<Button-1>", self.move_update_2p)
        self.root.update()

    def draw_grid(self):
        """Draws a blank board."""
        self.canvas.create_line(3, 0, 3, 300, width=4)
        self.canvas.create_line(100, 0, 100, 300, width=4)
        self.canvas.create_line(200, 0, 200, 300, width=4)
        self.canvas.create_line(300, 0, 300, 300, width=4)
        self.canvas.create_line(0, 3, 300, 3, width=4)
        self.canvas.create_line(0, 100, 300, 100, width=4)
        self.canvas.create_line(0, 200, 300, 200, width=4)
        self.canvas.create_line(0, 300, 300, 300, width=4)
        

    def move_update_2p(self, event):
        """Updates the current game state and view with respect to a move made in a
        two-player game."""
        row = (event.x//100)+1
        column = (event.y//100)+1
        try:
            if event.x >= 300 or event.y >= 300:
                raise Exception
            self.canvas.create_rectangle(0,300, 405, 405, fill="white", disabledoutline="white")
            self.canvas.create_text(150, 350, text = self.game_board.get_current_player(), font = "Times 30")
            self.game_board.move(Move(row,column))
        except Exception:
            pass
        else:
            self.add_shape(row, column)
            
        finally:
            if self.game_board.is_game_won():
                self.canvas.create_rectangle(0,300, 405, 405, fill="white", disabledoutline="white")
                self.canvas.create_text(150, 350, text = self.game_board.get_current_player(), font = "Times 30")
                self.canvas.create_text(220, 350, text ="wins.", font = "Times 30")
                self.root.update()
                time.sleep(1)
                self.game_board = GameBoard()
                self.two_players()
            elif self.game_board.is_draw():
                self.canvas.create_rectangle(0,300, 405, 405, fill="white", disabledoutline="white")
                self.canvas.create_text(150, 350, text = "It's a draw.", font = "Times 30")
                self.root.update()
                time.sleep(1)
                self.game_board = GameBoard()
                self.two_players()

    def move_update_com(self, event):
        """Updates the current game state and view with respect to a move
        made against the computer player. The computer player will
        also make its next move."""
        row = (event.x//100)+1
        column = (event.y//100)+1
        try:
            if event.x >= 300 or event.y >= 300:
                raise Exception
            self.game_board.move(Move(row,column))
        except Exception:
            pass
        else:
            self.add_shape(row, column)
            self.canvas.create_rectangle(0,300, 405, 405, fill="white", disabledoutline="white")
            self.canvas.create_text(150, 350, text = self.game_board.get_current_player(), font = "Times 30")
            if self.game_board.is_game_won() or self.game_board.is_draw():
                pass
            else:
                self.root.update()
                time.sleep(0.2)
                m = find_next_move(self.game_board)
                print(self.game_board)
                self.game_board.move(m)
                self.add_shape(m.row, m.column)
        finally:
            if self.game_board.is_game_won():
                self.canvas.create_rectangle(0,300, 405, 405, fill="white", disabledoutline="white")
                self.canvas.create_text(150, 350, text = self.game_board.get_current_player(), font = "Times 30")
                self.canvas.create_text(220, 350, text ="wins.", font = "Times 30")
                self.root.update()
                time.sleep(1)
                self.game_board = GameBoard()
                self.root.update()
                self.against_computer()
            elif self.game_board.is_draw():
                self.canvas.create_rectangle(0,300, 405, 405, fill="white", disabledoutline="white")
                self.canvas.create_text(150, 350, text = "It's a draw.", font = "Times 30")
                self.root.update()
                time.sleep(1)
                self.game_board = GameBoard()
                self.root.update()
                self.against_computer()

    def add_shape(self, row, column):
        """Adds either an X or an O to the current view, depending on whose
        turn it is."""
        if self.game_board.get_current_player() == "X":
            self.canvas.create_line((row-1)*100 + 30, (column-1)*100 + 30,(row-1)*100 + 70, (column-1)*100 + 70, width=4)
            self.canvas.create_line((row-1)*100 + 30, (column-1)*100 + 70,(row-1)*100 + 70, (column-1)*100 + 30, width=4)
        elif self.game_board.get_current_player() == "O":
            self.canvas.create_oval((row-1)*100 + 30, (column-1)*100 + 30,(row-1)*100 + 70, (column-1)*100 + 70, width=4)
            
    
g = GameBoardView()
