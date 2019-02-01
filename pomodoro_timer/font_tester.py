from tkinter import *
from tkinter import font
import time

root = Tk()
def draw_font(canvas, families, i):
    c.delete("all")
    print(families[i])
    c.create_text(150, 150, text = "hello", font = families[i] + " 15 bold")
    if i < len(families):
        root.after(1000, draw_font, canvas, families, i + 1)

def convert(word):
    i = len(word) - 1
    ret = ''
    while i >= 0:
        if word[i] != ' ':
            ret = word[i] + ret
            i -= 1
        else:
            return ret
    return ret
    
families = font.families()
new = [convert(word) for word in families]
families = new
i=0
c = Canvas(root, width=300, height=300, bg="white")
c.grid()
draw_font(c, families, i)
