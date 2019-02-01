from timer import *
from tkinter import *

class TimerView:   
    def __init__(self, mode = "POMODORO", width = 300, height = 150, study_time = 25, rest_time = 5, font = "MingLiU-ExtB", text_color = "white"):
        self.mode = mode
        self.counter = 0
        self.width = width
        self.height = height
        self.rest_time = rest_time
        self.study_time = study_time
        self.timer = Timer(self.study_time if self.mode == "POMODORO" else self.rest_time)
        self.font = font
        self.text_color = text_color
        self.root = Tk()
        self.root.geometry("{}x{}".format(self.width, self.height))
        self.root.title("Pomodoro Timer")
        self.canvas = Canvas(self.root, width = self.width,
                             height = self.height, bg="#333333")
        self.canvas.grid()
        b = Button(self.canvas, width = self.width//15, text="Begin Pomodoro",
                   command=self.begin_timer, font= "Helvetica " + str(self.height//12) , bg = "#85cdb6")
        self.canvas.create_window(self.width//2, self.height//2, window=b)

    def begin_timer(self):
        if self.timer.minutes > 0 or self.timer.seconds > 0:
            self.canvas.delete("all")
            self.canvas.create_text(self.width//2, self.height//2 - self.height//3.5, text = self.mode, font = self.font + " " + str(self.height//8) +
                                    " bold", fill = self.text_color)
            self.canvas.create_text(self.width//2, self.height//2-self.height//15,
                                    text=repr(self.timer), font = self.font + " " + str(self.height//5) + " bold", fill = self.text_color)
            self.canvas.create_text(self.width//2, self.height//2+self.height//3, text = "TALLY: " + str(self.counter),
                                    font = self.font + " " + str(self.height//8) + " bold", fill = self.text_color)
            self.timer.count_down()
            self.root.update()
            self.root.after(1000, self.begin_timer)
        else:
            self.canvas.delete("all")
            self.canvas.create_text(self.width//2, self.height//2-self.height//15,
                                    text=repr(self.timer), font = self.font + " " + str(self.height//5) + " bold", fill = self.text_color)
            self.root.update()
            winsound.PlaySound("Napalm Death - You Suffer", winsound.SND_ASYNC)
            self.canvas.create_text(self.width//2, self.height//2+self.height//3,
                                    text = "TALLY: " + str(self.counter), font = self.font + " " + str(self.height//8) + "  bold", fill = self.text_color)
            self.canvas.create_text(self.width//2, self.height//2 + self.height//7, text = self.mode + " CLEAR",
                                    font = self.font + " " +  str(self.height//8) + " bold", fill = self.text_color)
            if self.mode == "POMODORO":
                self.counter += 1
            self.mode = "POMODORO" if self.mode == "BREAK" else "BREAK"
            self.timer.set_time(self.study_time if self.mode == "POMODORO" else self.rest_time)
            self.root.after(3000, self.begin_timer)

t = TimerView(width = 300, height = 200)
