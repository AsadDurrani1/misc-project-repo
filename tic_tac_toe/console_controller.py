from move import *
from game_board import *
from find_next_move import *

"""The user runs this file to play a tic-tac-toe game. Moves are entered
manually and updated to a GameBoard object. The user can choose to play
against another player or against the computer.
@Author: Asad Durrani"""

def move_prompt() -> Move:
    mov = input("Enter your move in the form 'row,column'." + "\n")
    try:
        return Move(int(mov[0]), int(mov[2]))
    except Exception:
        print("That move is not valid. \n")
        return move_prompt()
    
def against_computer(game_board: GameBoard, player: int) -> None:
    if player == 2:
        print(game_board)
        print("It is " + game_board.get_current_player() + "'s turn.")
        game_board.move(Move(2,2)) #computer automatically makes first move
    else: #computer automatically makes second move
        print(game_board)
        print("It is " + game_board.get_current_player() + "'s turn.")
        mov = move_prompt()
        try:
            game_board.move(mov)
        except Exception:
            print("That move is not valid.")
        else:
            if game_board.is_move_available(2,2):
                game_board.move(Move(2,2))
            else:
                game_board.move(Move(1,1))
    while True:
        print(game_board)
        print("It is " + game_board.get_current_player() + "'s turn.")
        mov = move_prompt()
        try:
            game_board.move(mov)
        except Exception:
            print("That move is not valid.")
        else:
            if game_board.is_game_won():
                print(game_board)
                game_board.switch_player()
                print("Game over. " + game_board.get_current_player() + " wins!")
                cont = input("Enter any key to continue.")
                return
            elif game_board.is_draw():
                print(game_board)
                game_board.switch_player()
                print("Game over. It's a draw.")
                cont = input("Enter any key to continue.")
                return
                
            game_board.move(find_next_move(game_board))
            
            if game_board.is_game_won():
                print(game_board)
                game_board.switch_player()
                print("Game over. " + game_board.get_current_player() + " wins!")
                cont = input("Enter any key to continue." + "\n")
                return   
            elif game_board.is_draw():
                print(game_board)
                game_board.switch_player()
                print("Game over. It's a draw.")
                cont = input("Enter any key to continue." + "\n")
                return

def two_player(game_board: GameBoard):
    while True:
        print(game_board)
        print("It is " + game_board.get_current_player() + "'s turn.")
        try:
            mov = move_prompt()
            game_board.move(mov)
        except Exception:
            print("That move is not valid.")
        else:  
            if game_board.is_game_won():
                print(game_board)
                game_board.switch_player()
                print("Game over. " + game_board.get_current_player() + " wins!")
                cont = input("Enter any key to continue." + "\n")
                return
            elif game_board.is_draw():
                print(game_board)
                game_board.switch_player()
                print("Game over. It's a draw.")
                cont = input("Enter any key to continue." + "\n")
                return
        
        
while True:
    try:
        game_board = GameBoard()
        game_type = input("Enter 'P' for two-players or 'C' to to play against the computer." + "\n")
        if game_type.upper() == "C":
            while True:
                try:
                    player = input("Enter '1' to be Player 1 or '2' to be Player 2." + "\n")
                    if player != "" and player in ("1", "2"):
                        against_computer(game_board, int(player))
                    else:
                        raise Exception
                except Exception:
                    print("That is not a valid input, please try again." + "\n")
                finally:
                    break
        elif game_type.upper() == "P":
            two_player(game_board)
        else:
            raise Exception
    except Exception: 
        print("That is not a valid input, please try again." + "\n")
